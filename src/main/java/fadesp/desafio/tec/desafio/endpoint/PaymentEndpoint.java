package fadesp.desafio.tec.desafio.endpoint;

import fadesp.desafio.tec.desafio.error.ResourceNotFoundException;
import fadesp.desafio.tec.desafio.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.model.Payment;
import fadesp.desafio.tec.desafio.repository.PaymentRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        validateDetails(payment);
        payment.setStatusPayment("Pendente de Processamento");
        return new ResponseEntity<>(paymentDAO.save(payment), HttpStatus.CREATED);
    }
    @GetMapping(path="getAll/")
    public ResponseEntity<?> searchPayments(){
        return new ResponseEntity<>(paymentDAO.findAll(), HttpStatus.OK);
    }
    @GetMapping(path = "search/key={key}&value={value}")
    public ResponseEntity<?> searchPayment(@PathVariable("key") String key, @PathVariable("value") String value){
        return new ResponseEntity<>(resultSearchPayment(key, value), HttpStatus.OK);
    }
    @GetMapping(path="id/{value}")
    public ResponseEntity<?> searchPaymentById(@PathVariable("value") Long id){
        verifyPaymentExistsById(id);
        Optional<Payment> payment = paymentDAO.findById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    @GetMapping(path="cpf/{value}")
    public ResponseEntity<?> searchPaymentByCPFPayer(@PathVariable("value") String cpf){
        verifyPaymentExistsByCPFPayer(cpf);
        return new ResponseEntity<>(paymentDAO.findByCpfPayer(cpf), HttpStatus.OK);
    }
    @GetMapping(path = "status/{value}")
    public ResponseEntity<?> searchPaymentByStatus(@PathVariable("value") String status){
        verifyPaymentExistsByStatusPayment(status);
        return new ResponseEntity<>(paymentDAO.findByStatusPayment(status), HttpStatus.OK);
    }
    private List<Payment> resultSearchPayment(String key, String value){
        return switch (key) {
            case "id" -> (List<Payment>) paymentDAO.findAllById(Collections.singleton(convertToLongId(value)));
            case "cpfOrCnpj" -> (List<Payment>) paymentDAO.findByCpfPayer(value);
            case "statusPayment" -> (List<Payment>) paymentDAO.findByStatusPayment(value);
            default -> throw new ValidationErrorException("key", "Key for search is invalid");
        };
    }
    private Long convertToLongId(String id){
        try {
            return Long.parseLong(id);
        }catch (Exception e) {
            throw new ValidationErrorException("key", "Cannot convert '" + id + "' in to Number");
        }
    }
    private void verifyPaymentExistsById(Long id){
        Optional<Payment> payment = paymentDAO.findById(id);
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not found for ID: " + id);
        }
    }
    private void verifyPaymentExistsByCPFPayer(String cpf){
        List<Payment> payment = paymentDAO.findByCpfPayer(cpf);
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not found for CPF: " + cpf);
        }
    }
    private void verifyPaymentExistsByStatusPayment(String status){
        List<Payment> payment = paymentDAO.findByStatusPayment(status);
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not found for CPF: " + status);
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
