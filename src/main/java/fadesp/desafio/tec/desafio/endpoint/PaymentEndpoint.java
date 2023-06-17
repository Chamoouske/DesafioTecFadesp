package fadesp.desafio.tec.desafio.endpoint;

import fadesp.desafio.tec.desafio.model.Filter;
import fadesp.desafio.tec.desafio.model.Payment;
import fadesp.desafio.tec.desafio.model.PaymentDelete;
import fadesp.desafio.tec.desafio.model.PaymentProcess;
import fadesp.desafio.tec.desafio.error.BadRequestException;
import fadesp.desafio.tec.desafio.repository.PaymentRepository;
import fadesp.desafio.tec.desafio.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.error.ResourceNotFoundException;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.Collections;

@RestController
@RequestMapping("payments")
public class PaymentEndpoint {
    private final PaymentRepository paymentDAO;
    @Autowired
    public PaymentEndpoint(PaymentRepository paymentDAO){
        this.paymentDAO = paymentDAO;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<?> savePayment(@Valid @RequestBody Payment payment){
        Optional<Payment> paymentExists = paymentDAO.findById(payment.getCodPayment());
        if(paymentExists.isPresent())
            throw new ValidationErrorException("codPayment", "Payment with cod "+payment.getCodPayment()+" as already registered");
        validateDetails(payment);
        payment.setStatusPayment("Pendente de Processamento");
        return new ResponseEntity<>(paymentDAO.save(payment), HttpStatus.CREATED);
    }
    @GetMapping(path="search/")
    public ResponseEntity<?> searchPayments(@RequestBody(required = false) Filter filter){
        if(filter == null || filter.getFilter() == null) {
            return new ResponseEntity<>(paymentDAO.findAll(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(resultSearchPayment(filter.getFilter(), filter.getValue()), HttpStatus.OK);
        }
    }
    @GetMapping(path = "search/filter={key}&value={value}")
    public ResponseEntity<?> searchPayment(@PathVariable("key") String key, @PathVariable("value") String value){
        return new ResponseEntity<>(resultSearchPayment(key, value), HttpStatus.OK);
    }
    @DeleteMapping(path = "delete/{codPayment}")
    public ResponseEntity<?> deletePayment(@PathVariable Long codPayment){
        verifyPaymentExistsById(codPayment);
        Optional<Payment> payment = paymentDAO.findById(codPayment);
        if (payment.get().getStatusPayment().equals("Pendente de Processamento")){
            paymentDAO.deleteById(codPayment);
        } else {
            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + payment.get().getStatusPayment());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping(path = "delete/")
    public ResponseEntity<?> deletePaymentByBodyParams(@RequestBody PaymentDelete payment){
        verifyPaymentExistsById(payment.getCodPayment());
        Optional<Payment> paymentExists = paymentDAO.findById(payment.getCodPayment());
        if (paymentExists.get().getStatusPayment().equals("Pendente de Processamento")){
            paymentDAO.deleteById(payment.getCodPayment());
        } else {
            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + paymentExists.get().getStatusPayment());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(path = "process/")
    @Transactional
    public ResponseEntity<?> updateStatusPayment(@RequestBody PaymentProcess paymentProcessed){
        paymentProcessed.verifyStatus();
        verifyPaymentExistsById(paymentProcessed.getCodPayment());
        Optional<Payment> payment = paymentDAO.findById(paymentProcessed.getCodPayment());
        payment.get().setStatusPayment(paymentProcessed.getNewStatus());
        paymentDAO.save(payment.get());
        return new ResponseEntity<>(paymentDAO.findById(paymentProcessed.getCodPayment()), HttpStatus.OK);
    }

    private List<Payment> resultSearchPayment(String key, String value){
        switch (key) {
            case "codPayment" -> {
                long convertedCodPayment = convertCodPayment(value);
                verifyPaymentExistsById(convertedCodPayment);
                return (List<Payment>) paymentDAO.findAllById(Collections.singleton(convertedCodPayment));
            }
            case "cpfOrCnpj" -> {
                return paymentDAO.findByCpfPayer(value);
            }
            case "statusPayment" -> {
                return paymentDAO.findByStatusPayment(value);
            }
            default -> throw new ValidationErrorException("filter", "Filter for search is invalid! Accept only: codPayment, cpfOrCnpj or statusPayment");
        }
    }
    private int convertCodPayment(String id){
        try {
            return Integer.parseInt(id);
        }catch (Exception e) {
            throw new ValidationErrorException("key", "Cannot convert '" + id + "' in a Number");
        }
    }
    private void verifyPaymentExistsById(Long id){
        Optional<Payment> payment = paymentDAO.findById((long) convertCodPayment(String.valueOf(id)));
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not found for ID: " + id);
        }
    }
    private void validateDetails(Payment payment){
        validatePaymentMethod(payment);
        validateNumberCard(payment);
    }
    private void validatePaymentMethod(Payment payment){
        List<String> validPaymentMethods = Arrays.asList("boleto", "pix", "cartao_credito", "cartao_debito");
        if(!validPaymentMethods.contains(payment.getPaymentMethod()))
            throw new ValidationErrorException("paymentMethod", "Payment Method is invalid! Accept only: boleto, pix, cartao_credito or cartao_debito");
    }
    private void validateNumberCard(Payment payment){
        if(payment.getPaymentMethod().equals("cartao_credito") || payment.getPaymentMethod().equals("cartao_debito")){
            if(payment.getCardNumber() == 0){
                throw new ValidationErrorException("cardNumber", "Card Number is invalid! Must not be null");
            }
        }
    }
}
