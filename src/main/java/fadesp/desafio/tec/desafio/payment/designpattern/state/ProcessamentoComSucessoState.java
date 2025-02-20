package fadesp.desafio.tec.desafio.payment.designpattern.state;

import fadesp.desafio.tec.desafio.config.error.BadRequestException;
import fadesp.desafio.tec.desafio.config.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

public class ProcessamentoComSucessoState extends PaymentStatus {
    public ProcessamentoComSucessoState() {
        super(PaymentStatusEnum.PROCESSAMENTO_COM_SUCESSO);
    }

    @Override
    public PaymentStatus pendenteDeProcessamento() {
        throw new BadRequestException(this.status + " cannot be changed to " + new PendenteDeProcessamentoState().getStatus());
    }

    @Override
    public PaymentStatus processadoComFalha() {
        throw new BadRequestException(this.status + " cannot be changed to " + new ProcessamentoComFalhaState().getStatus());
    }

    @Override
    public PaymentStatus processadoComSucesso() {
        throw new ValidationErrorException("statusPayment", "Payment already in " + this.status);
    }

    @Override
    public PaymentStatus deletePayment() {
        throw new UnsupportedOperationException("Payment cannot be deleted");
    }
}
