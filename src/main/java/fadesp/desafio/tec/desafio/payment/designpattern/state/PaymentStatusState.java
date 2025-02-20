package fadesp.desafio.tec.desafio.payment.designpattern.state;

public interface PaymentStatusState {
    PaymentStatus pendenteDeProcessamento();
    PaymentStatus processadoComFalha();
    PaymentStatus processadoComSucesso();
    PaymentStatus deletePayment();
}
