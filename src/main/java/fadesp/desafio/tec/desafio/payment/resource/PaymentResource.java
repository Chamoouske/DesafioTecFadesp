package fadesp.desafio.tec.desafio.payment.resource;

import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.dto.PaymentDto;
import fadesp.desafio.tec.desafio.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentResource {
    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(@Valid @RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.savePayment(payment), HttpStatus.CREATED);
    }

    @GetMapping(path = "search")
    public ResponseEntity<Page<PaymentDto>> searchPaymentsByUrlParams(PaymentDto payment, Pageable pageable) {
        return new ResponseEntity<>(paymentService.searchPayments(payment, pageable), HttpStatus.OK);
    }

//    @DeleteMapping(path = "delete/{codPayment}")
//    public ResponseEntity<?> deletePayment(@PathVariable Long codPayment) {
//        Optional<Payment> payment = getPaymentIfExistsOrThrowError(codPayment);
//        if (payment.get().getStatusPayment().equals("Pendente de Processamento")) {
//            paymentService.deleteById(codPayment);
//        } else {
//            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + payment.get().getStatusPayment());
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping(path = "delete/")
//    public ResponseEntity<?> deletePaymentByBodyParams(@RequestBody PaymentDelete payment) {
//        Optional<Payment> paymentExists = getPaymentIfExistsOrThrowError(payment.getCodPayment());
//        if (paymentExists.get().getStatusPayment().equals("Pendente de Processamento")) {
//            paymentService.deleteById(payment.getCodPayment());
//        } else {
//            throw new BadRequestException("Payment cannot be Deleted: Payment status: " + paymentExists.get().getStatusPayment());
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PutMapping(path = "process/")
//    public ResponseEntity<?> updateStatusPayment(@RequestBody PaymentProcess paymentProcessed) {
//        paymentProcessed.verifyStatus();
//        Optional<Payment> payment = getPaymentIfExistsOrThrowError(paymentProcessed.getCodPayment());
//        payment.get().setStatusPayment(paymentProcessed.getNewStatus());
//        paymentService.savePayment(payment.get());
//        return new ResponseEntity<>(paymentService.findById(paymentProcessed.getCodPayment()), HttpStatus.OK);
//    }
//
//    private List<Payment> searchPayments(String key, String value) {
//        switch (key) {
//            case "codPayment" -> {
//                long convertedCodPayment = Long.parseLong(value);
//                getPaymentIfExistsOrThrowError(convertedCodPayment);
//                return (List<Payment>) paymentService.findAllById(Collections.singleton(convertedCodPayment));
//            }
//            case "cpfOrCnpj" -> paymentService.findByCpfPayer(value);
//            case "statusPayment" -> paymentService.findByStatusPayment(value);
//            case "all" -> paymentService.findAll();
//            default ->
//                    throw new ValidationErrorException("filter", "Filter for search is invalid! Accept only: all, codPayment, cpfOrCnpj or statusPayment");
//        }
//        return null;
//    }
//
//    private Optional<Payment> getPaymentIfExistsOrThrowError(Long id) {
//        Optional<Payment> payment = paymentService.findById(id);
//        if (payment.isEmpty()) {
//            throw new ResourceNotFoundException("Payment not found for ID: " + id);
//        }
//        return payment;
//    }
}
