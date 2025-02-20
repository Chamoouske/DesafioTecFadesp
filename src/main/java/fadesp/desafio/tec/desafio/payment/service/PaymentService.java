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

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private static Page<PaymentDto> mapToDto(Page<PaymentEntity> payments) {
        return payments.map(PaymentBuilder::build);
    }

    @Transactional
    public void deletePayment(Long codPayment) {
        paymentRepository.deleteById(codPayment);
    }

    @Transactional
    public PaymentDto savePayment(Payment payment) {
        validateDetails(payment);
        return PaymentBuilder.build(paymentRepository.save(PaymentBuilder.build(payment)));
    }

    public Page<PaymentDto> searchPayments(Payment payment, Pageable pageable) {
        Page<PaymentEntity> payments = paymentRepository.findAll(new PaymentSpecification(payment), pageable);
        return mapToDto(payments);
    }

    private void validateDetails(Payment payment) {
        validateNumberCard(payment);
    }

    private void validateNumberCard(Payment payment) {
        if ((payment.getPaymentMethod().equals(PaymentMethodEnum.CREDITO) || payment.getPaymentMethod().equals(PaymentMethodEnum.DEBITO)) && Objects.equals(payment.getCardNumber(), "0")) {
            throw new ValidationErrorException("cardNumber", "Card Number is invalid! Must not be null");
        }
    }
}
