package fadesp.desafio.tec.desafio.payment.enums;

public enum PaymentMethodEnum {
    PIX,
    BOLETO,
    CREDITO,
    DEBITO;

    public static PaymentMethodEnum getPaymentMethod(String paymentMethod) {
        return PaymentMethodEnum.valueOf(paymentMethod.toUpperCase());
    }
}
