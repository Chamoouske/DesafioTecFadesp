package fadesp.desafio.tec.desafio.payment.designpattern.factory;

import fadesp.desafio.tec.desafio.payment.designpattern.state.*;

import java.util.Objects;

public class PaymentStatusFactory {
    private PaymentStatusFactory() {
    }

    public static PaymentStatus create(String status) {
        PaymentStatus state = new PendenteDeProcessamentoState();
        if (Objects.nonNull(status)) {
            state = switch (status) {
                case "falha" -> new ProcessamentoComFalhaState();
                case "sucesso" -> new ProcessamentoComSucessoState();
                case "delete" -> new DeletePaymentState();
                default -> new PendenteDeProcessamentoState();
            };
        }

        return state;
    }
}
