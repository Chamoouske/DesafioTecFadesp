package fadesp.desafio.tec.desafio.payment.designpattern.factory;

import fadesp.desafio.tec.desafio.payment.designpattern.state.PaymentStatus;
import fadesp.desafio.tec.desafio.payment.designpattern.state.PendenteDeProcessamentoState;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class PaymentStatusFactory {
    private static final HashMap<String, Callable<PaymentStatus>> statusMap = new HashMap<>();

    private PaymentStatusFactory() {
    }

    public static PaymentStatus create(String status) throws Exception {
        Callable<PaymentStatus> state = statusMap.getOrDefault(status, PendenteDeProcessamentoState::new);

        return state.call();
    }

    public static void register(String status, Callable<PaymentStatus> state) {
        statusMap.put(status, state);
    }
}
