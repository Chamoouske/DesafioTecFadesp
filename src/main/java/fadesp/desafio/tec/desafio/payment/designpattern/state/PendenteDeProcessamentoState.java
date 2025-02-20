package fadesp.desafio.tec.desafio.payment.designpattern.state;

import fadesp.desafio.tec.desafio.config.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

public class PendenteDeProcessamentoState extends PaymentStatus {
    public PendenteDeProcessamentoState() {
        super(PaymentStatusEnum.PENDENTE_DE_PROCESSAMENTO);
    }

    @Override
    public PaymentStatus pendenteDeProcessamento() {
        throw new ValidationErrorException("statusPayment", "Payment already in " + this.status);
    }

    @Override
    public PaymentStatus processadoComFalha() {
        return new ProcessamentoComFalhaState();
    }

    @Override
    public PaymentStatus processadoComSucesso() {
        return new ProcessamentoComSucessoState();
    }

    @Override
    public PaymentStatus deletePayment() {
        return new DeletePaymentState();
    }
}
