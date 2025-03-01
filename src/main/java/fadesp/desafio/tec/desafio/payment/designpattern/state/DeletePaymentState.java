package fadesp.desafio.tec.desafio.payment.designpattern.state;

import fadesp.desafio.tec.desafio.payment.designpattern.factory.PaymentStatusFactory;
import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

public class DeletePaymentState extends PaymentStatus {

    public static final String PAYMENT_IS_DELETED = "Payment is deleted";

    public DeletePaymentState() {
        super(PaymentStatusEnum.DELETE_PAYMENT);
        PaymentStatusFactory.register(PaymentStatusEnum.DELETE_PAYMENT.name(), DeletePaymentState::new);
    }

    @Override
    public PaymentStatus pendenteDeProcessamento() {
        throw new UnsupportedOperationException(PAYMENT_IS_DELETED);
    }

    @Override
    public PaymentStatus processadoComFalha() {
        throw new UnsupportedOperationException(PAYMENT_IS_DELETED);
    }

    @Override
    public PaymentStatus processadoComSucesso() {
        throw new UnsupportedOperationException(PAYMENT_IS_DELETED);
    }

    @Override
    public PaymentStatus deletePayment() {
        throw new UnsupportedOperationException("Payment already deleted");
    }
}
