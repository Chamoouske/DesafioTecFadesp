package fadesp.desafio.tec.desafio.payment.designpattern.factory;

import fadesp.desafio.tec.desafio.payment.designpattern.state.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;

public class PaymentStatusFactory {
    private static PaymentStatusFactory instance;
    private final HashMap<String, Callable<PaymentStatus>> statusMap = new HashMap<>();

    private PaymentStatusFactory() {
    }

    public static PaymentStatus create(String status) throws Exception {
        Callable<PaymentStatus> state = getInstance().statusMap.getOrDefault(status, PendenteDeProcessamentoState::new);

        return state.call();
    }

    public void register(String status, Callable<PaymentStatus> state) {
        statusMap.put(status, state);
    }

    public static PaymentStatusFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PaymentStatusFactory();
        }
        return instance;
    }
}
