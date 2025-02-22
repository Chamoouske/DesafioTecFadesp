package fadesp.desafio.tec.desafio.payment.service;

import fadesp.desafio.tec.desafio.config.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.dto.PaymentDto;
import fadesp.desafio.tec.desafio.payment.entity.PaymentBuilder;
import fadesp.desafio.tec.desafio.payment.entity.PaymentEntity;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;
import fadesp.desafio.tec.desafio.payment.repository.PaymentRepository;
import fadesp.desafio.tec.desafio.payment.specification.PaymentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void deletePayment(Long codPayment) {
        Optional<PaymentEntity> payment = paymentRepository.findAll(
                        new PaymentSpecification(PaymentBuilder.builder().withCodPayment(codPayment).build())
                        , Pageable.unpaged())
                .stream().findFirst();
        payment.orElseThrow().deletePayment();
        paymentRepository.delete(payment.orElseThrow());
    }

    @Transactional
    public PaymentDto savePayment(Payment payment) {
        ValidatorCard.validateCardNumber(payment);
        return PaymentBuilder.build(paymentRepository.save(PaymentBuilder.build(payment)));
    }

    public Page<PaymentDto> searchPayments(Payment payment, Pageable pageable) {
        Page<PaymentEntity> payments = paymentRepository.findAll(new PaymentSpecification(payment), pageable);
        return payments.map(PaymentBuilder::build);
    }
}
