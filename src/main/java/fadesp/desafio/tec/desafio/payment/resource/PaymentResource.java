package fadesp.desafio.tec.desafio.payment.resource;

import fadesp.desafio.tec.desafio.payment.dto.PaymentDto;
import fadesp.desafio.tec.desafio.payment.dto.PaymentProcess;
import fadesp.desafio.tec.desafio.payment.entity.PaymentBuilder;
import fadesp.desafio.tec.desafio.payment.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentResource {
    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto payment) {
        return new ResponseEntity<>(paymentService.savePayment(payment), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> searchPaymentsByUrlParams(PaymentDto payment, Pageable pageable) {
        return new ResponseEntity<>(paymentService.searchPayments(payment, pageable), HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{codPayment}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long codPayment) {
        paymentService.deletePayment(codPayment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "process/")
    public ResponseEntity<PaymentDto> updateStatusPayment(PaymentProcess paymentProcessed) {
        Optional<PaymentDto> payment = this.paymentService.searchPayments(PaymentBuilder.builder()
                                .withCodPayment(paymentProcessed.getCodPayment())
                                .build()
                        , Pageable.unpaged())
                .stream().findFirst();
        paymentProcessed.changeStatus(payment.orElseThrow());
        return new ResponseEntity<>(paymentService.savePayment(payment.orElseThrow()), HttpStatus.OK);
    }
}
