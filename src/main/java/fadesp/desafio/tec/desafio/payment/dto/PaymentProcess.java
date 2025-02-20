package fadesp.desafio.tec.desafio.payment.dto;

import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

public class PaymentProcess {
    private Long codPayment;
    private PaymentStatusEnum newStatus;

    public Long getCodPayment() {
        return codPayment;
    }

    public void setCodPayment(Long codPayment) {
        this.codPayment = codPayment;
    }

    public PaymentStatusEnum getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(PaymentStatusEnum newStatus) {
        this.newStatus = newStatus;
    }

    public void changeStatus(Payment payment) {
        newStatus.changeStatus(payment);
    }
}
