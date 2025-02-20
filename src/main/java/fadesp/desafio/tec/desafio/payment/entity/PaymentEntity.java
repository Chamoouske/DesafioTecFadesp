package fadesp.desafio.tec.desafio.payment.entity;

import fadesp.desafio.tec.desafio.payment.designpattern.factory.PaymentStatusFactory;
import fadesp.desafio.tec.desafio.payment.designpattern.state.PaymentStatus;
import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Entity
public class PaymentEntity implements Payment {
    @Id
    @Range(min = 1L)
    private Long codPayment;
    private String cpfPayer;
    private PaymentMethodEnum paymentMethod;
    private String cardNumber;
    private BigDecimal price;
    private String statusPayment;
    private PaymentStatus state;

    public PaymentEntity() {
        this.state = PaymentStatusFactory.create(statusPayment);
    }

    public Long getCodPayment() {
        return codPayment;
    }

    public void setCodPayment(Long codPayment) {
        this.codPayment = codPayment;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getCpfPayer() {
        return cpfPayer;
    }

    public void setCpfPayer(String cpfPayer) {
        this.cpfPayer = cpfPayer;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getPrice() {
        return price;
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
