package fadesp.desafio.tec.desafio.payment.dto;

import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.math.BigDecimal;

public class PaymentDto implements Payment {
    private Long codPayment;
    private String cpfPayer;
    private PaymentMethodEnum paymentMethod;
    private String cardNumber;
    private BigDecimal price;
    private String statusPayment;

    @Override
    public Long getCodPayment() {
        return this.codPayment;
    }

    public void setCodPayment(Long codPayment) {
        this.codPayment = codPayment;
    }

    @Override
    public String getStatusPayment() {
        return this.statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    @Override
    public String getCpfPayer() {
        return this.cpfPayer;
    }

    public void setCpfPayer(String cpfPayer) {
        this.cpfPayer = cpfPayer;
    }

    @Override
    public PaymentMethodEnum getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
