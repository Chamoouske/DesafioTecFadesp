package fadesp.desafio.tec.desafio.payment.designpattern.state;

import fadesp.desafio.tec.desafio.config.error.BadRequestException;
import fadesp.desafio.tec.desafio.payment.designpattern.factory.PaymentStatusFactory;
import fadesp.desafio.tec.desafio.payment.enums.PaymentStatusEnum;

public class ProcessamentoComFalhaState extends PaymentStatus {
    public ProcessamentoComFalhaState() {
        super(PaymentStatusEnum.PROCESSAMENTO_COM_FALHA);
        PaymentStatusFactory.getInstance().register(PaymentStatusEnum.PROCESSAMENTO_COM_FALHA.name(), ProcessamentoComFalhaState::new);
    }

    @Override
    public PaymentStatus pendenteDeProcessamento() {
        return new ProcessamentoComSucessoState();
    }

    @Override
    public PaymentStatus processadoComFalha() {
        throw new BadRequestException("Payment already in " + this.status);
    }

    @Override
    public PaymentStatus processadoComSucesso() {
        throw new BadRequestException(this.status + " cannot be changed to " + new ProcessamentoComSucessoState().getStatus());
    }

    @Override
    public PaymentStatus deletePayment() {
        throw new UnsupportedOperationException("Payment cannot be deleted");
    }
}