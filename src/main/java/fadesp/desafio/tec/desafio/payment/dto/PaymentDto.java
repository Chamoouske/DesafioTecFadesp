package fadesp.desafio.tec.desafio.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fadesp.desafio.tec.desafio.payment.designpattern.factory.PaymentStatusFactory;
import fadesp.desafio.tec.desafio.payment.designpattern.state.PaymentStatus;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.math.BigDecimal;

public class PaymentDto implements Payment {
    private Long codPayment;
    private String cpfPayer;
    private PaymentMethodEnum paymentMethod;
    private String cardNumber;
    private BigDecimal price;
    private String statusPayment;
    @JsonIgnore
    private PaymentStatus state;

    public PaymentDto() throws Exception {
        this.state = PaymentStatusFactory.create(statusPayment);
        this.statusPayment = state.getStatus().getStatus();
    }

    @Override
    public Long getCodPayment() {
        return this.codPayment;
    }

    public void setCodPayment(Long codPayment) {
        this.codPayment = codPayment;
    }

    @Override
    public String getStatusPayment() {
        return state.getStatus().getStatus();
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

    @Override
    public void pendenteDeProcessamento() {
        this.state = this.state.pendenteDeProcessamento();
    }

    @Override
    public void processadoComFalha() {
        this.state = this.state.processadoComFalha();
    }

    @Override
    public void processadoComSucesso() {
        this.state = this.state.processadoComSucesso();
    }

    @Override
    public void deletePayment() {
        this.state = this.state.deletePayment();
    }
}
