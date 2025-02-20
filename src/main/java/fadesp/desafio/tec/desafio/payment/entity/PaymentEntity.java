package fadesp.desafio.tec.desafio.payment.entity;

import fadesp.desafio.tec.desafio.config.error.BadRequestException;
import fadesp.desafio.tec.desafio.payment.dto.Payment;
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
    private String paymentMethod;
    private String cardNumber;
    @Range(min = 1L, message = "Must not be less than 1")
    private BigDecimal price;
    private String statusPayment;

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
        if (this.statusPayment.equals(statusPayment))
            throw new BadRequestException(this.statusPayment + " cannot be changed to " + statusPayment);
        if (this.statusPayment.equals("Pendente de Processamento")) {
            this.statusPayment = statusPayment;
        } else if (this.statusPayment.equals("Processado com Falha") && statusPayment.equals("Pendente de Processamento")) {
            this.statusPayment = statusPayment;
        } else {
            throw new BadRequestException(this.statusPayment + " cannot be changed to " + statusPayment);
        }
    }

    public String getCpfPayer() {
        return cpfPayer;
    }

    public void setCpfPayer(String cpfPayer) {
        this.cpfPayer = cpfPayer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
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
}
