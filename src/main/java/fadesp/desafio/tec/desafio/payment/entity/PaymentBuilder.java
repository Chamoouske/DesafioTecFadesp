package fadesp.desafio.tec.desafio.payment.entity;

import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.math.BigDecimal;

public class PaymentBuilder extends PaymentEntity {
    @SuppressWarnings("unchecked")
    public static <T> T build(Payment payment) {
        return (T) builder()
                .withCardNumber(payment.getCardNumber())
                .withCpfPayer(payment.getCpfPayer())
                .withPaymentMethod(payment.getPaymentMethod())
                .withPrice(payment.getPrice())
                .withStatusPayment(payment.getStatusPayment())
                .build();
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public PaymentEntity build() {
        return this;
    }

    public PaymentBuilder withCodPayment(Long codPayment) {
        setCodPayment(codPayment);
        return this;
    }

    public PaymentBuilder withCpfPayer(String cpfPayer) {
        setCpfPayer(cpfPayer);
        return this;
    }

    public PaymentBuilder withPaymentMethod(PaymentMethodEnum paymentMethod) {
        setPaymentMethod(paymentMethod);
        return this;
    }

    public PaymentBuilder withCardNumber(String cardNumber) {
        setCardNumber(cardNumber);
        return this;
    }

    public PaymentBuilder withPrice(BigDecimal price) {
        setPrice(price);
        return this;
    }

    public PaymentBuilder withStatusPayment(String statusPayment) {
        setStatusPayment(statusPayment);
        return this;
    }
}
