package fadesp.desafio.tec.desafio.payment.designpattern.state;

import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

import java.io.Serializable;

public abstract class PaymentStatus implements PaymentStatusState, Serializable {
    protected PaymentStatusEnum status;

    protected PaymentStatus(PaymentStatusEnum status) {
        this.status = status;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }
}
