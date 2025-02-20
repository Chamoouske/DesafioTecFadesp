package fadesp.desafio.tec.desafio.payment.resource;

import fadesp.desafio.tec.desafio.config.error.BadRequestException;
import fadesp.desafio.tec.desafio.config.error.ResourceNotFoundException;
import fadesp.desafio.tec.desafio.config.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.payment.entity.Payment;
import fadesp.desafio.tec.desafio.payment.dto.PaymentDelete;
import fadesp.desafio.tec.desafio.payment.dto.PaymentProcess;
import fadesp.desafio.tec.desafio.payment.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("payments")
public class PaymentResource {
    private final PaymentRepository paymentDAO;

    @Autowired
    public PaymentResource(PaymentRepository paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> savePayment(@Valid @RequestBody Payment payment) {
        Optional<Payment> paymentExists = paymentDAO.findById(payment.getCodPayment());
        if (paymentExists.isPresent())
            throw new ValidationErrorException("codPayment", "Payment with cod " + payment.getCodPayment() + " as already registered");
        validateDetails(payment);
        return new ResponseEntity<>(paymentDAO.save(payment), HttpStatus.CREATED);
    }

    @GetMapping(path = "search/filter={key}&value={value}")
    public ResponseEntity<?> searchPaymentsByUrlParams(@PathVariable("key") String key, @PathVariable("value") String value) {
        return new ResponseEntity<>(searchPayments(key, value), HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{codPayment}")
    @Transactional
    public ResponseEntity<?> deletePayment(@PathVariable Long codPayment) {
        Optional<Payment> payment = getPaymentIfExistsOrThrowError(codPayment);
        if (payment.get().getStatusPayment().equals("Pendente de Processamento")) {
            paymentDAO.deleteById(codPayment);
        } else {
            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + payment.get().getStatusPayment());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/")
    @Transactional
    public ResponseEntity<?> deletePaymentByBodyParams(@RequestBody PaymentDelete payment) {
        Optional<Payment> paymentExists = getPaymentIfExistsOrThrowError(payment.getCodPayment());
        if (paymentExists.get().getStatusPayment().equals("Pendente de Processamento")) {
            paymentDAO.deleteById(payment.getCodPayment());
        } else {
            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + paymentExists.get().getStatusPayment());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "process/")
    @Transactional
    public ResponseEntity<?> updateStatusPayment(@RequestBody PaymentProcess paymentProcessed) {
        paymentProcessed.verifyStatus();
        Optional<Payment> payment = getPaymentIfExistsOrThrowError(paymentProcessed.getCodPayment());
        payment.get().setStatusPayment(paymentProcessed.getNewStatus());
        paymentDAO.save(payment.get());
        return new ResponseEntity<>(paymentDAO.findById(paymentProcessed.getCodPayment()), HttpStatus.OK);
    }

    private List<Payment> searchPayments(String key, String value) {
        switch (key) {
            case "codPayment" -> {
                long convertedCodPayment = Long.parseLong(value);
                getPaymentIfExistsOrThrowError(convertedCodPayment);
                return (List<Payment>) paymentDAO.findAllById(Collections.singleton(convertedCodPayment));
            }
            case "cpfOrCnpj" -> paymentDAO.findByCpfPayer(value);
            case "statusPayment" -> paymentDAO.findByStatusPayment(value);
            case "all" -> paymentDAO.findAll();
            default ->
                    throw new ValidationErrorException("filter", "Filter for search is invalid! Accept only: all, codPayment, cpfOrCnpj or statusPayment");
        }
        return null;
    }

    private Optional<Payment> getPaymentIfExistsOrThrowError(Long id) {
        Optional<Payment> payment = paymentDAO.findById(id);
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found for ID: " + id);
        }
        return payment;
    }

    private void validateDetails(Payment payment) {
        validatePaymentMethod(payment);
        validateNumberCard(payment);
    }

    private void validatePaymentMethod(Payment payment) {
        List<String> validPaymentMethods = Arrays.asList("boleto", "pix", "cartao_credito", "cartao_debito");
        if (!validPaymentMethods.contains(payment.getPaymentMethod()))
            throw new ValidationErrorException("paymentMethod", "Payment Method is invalid! Accept only: boleto, pix, cartao_credito or cartao_debito");
    }

    private void validateNumberCard(Payment payment) {
        if ((payment.getPaymentMethod().equals("cartao_credito") || payment.getPaymentMethod().equals("cartao_debito")) && payment.getCardNumber() == 0) {
            throw new ValidationErrorException("cardNumber", "Card Number is invalid! Must not be null");
        }
    }
}
