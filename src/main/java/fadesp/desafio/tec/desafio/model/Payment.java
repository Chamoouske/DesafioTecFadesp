package fadesp.desafio.tec.desafio.model;

import jakarta.persistence.Entity;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
public class Payment extends AbstractEntity {
    @NotEmpty(message = "Must not be Empty")
    private String cpfPayer;
    @NotEmpty(message = "Must not be Empty")
    private String paymentMethod;
    private long cardNumber;
    @Range(min=1L, message = "Must not be less than 1")
    private float price;
    private String statusPayment;

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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
