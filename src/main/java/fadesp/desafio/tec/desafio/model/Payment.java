package fadesp.desafio.tec.desafio.model;

import fadesp.desafio.tec.desafio.error.BadRequestException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
public class Payment {
    @Id
    @Range(min = 1L)
    private long codPayment;
    @NotEmpty(message = "Must not be Empty")
    private String cpfPayer;
    @NotEmpty(message = "Must not be Empty")
    private String paymentMethod;
    private long cardNumber;
    @Range(min=1L, message = "Must not be less than 1")
    private float price;
    private String statusPayment = "Pendente de Processamento";

    public long getCodPayment() {
        return codPayment;
    }

    public void setCodPayment(int codPayment) {
        this.codPayment = codPayment;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        if(this.statusPayment.equals(statusPayment))
            throw new BadRequestException(this.statusPayment + " cannot be changed to " + statusPayment);
        if(this.statusPayment.equals("Pendente de Processamento")){
            this.statusPayment = statusPayment;
        } else if (this.statusPayment.equals("Processado com Falha") && statusPayment.equals("Pendente de Processamento")) {
            this.statusPayment = statusPayment;
        }else{
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
