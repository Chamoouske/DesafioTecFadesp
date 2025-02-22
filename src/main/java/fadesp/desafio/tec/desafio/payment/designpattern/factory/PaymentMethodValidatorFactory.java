package fadesp.desafio.tec.desafio.payment.designpattern.factory;

import fadesp.desafio.tec.desafio.payment.designpattern.stratey.validators.CardValidator;
import fadesp.desafio.tec.desafio.payment.designpattern.stratey.validators.PaymentValidator;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.util.EnumMap;

public class PaymentMethodValidatorFactory {
    private static final EnumMap<PaymentMethodEnum, PaymentValidator> VALIDATORS = new EnumMap<>(PaymentMethodEnum.class);

    private PaymentMethodValidatorFactory() {
        VALIDATORS.put(PaymentMethodEnum.CREDITO, new CardValidator());
        VALIDATORS.put(PaymentMethodEnum.DEBITO, new CardValidator());
    }

    public static PaymentValidator create(PaymentMethodEnum paymentMethod) {
        return VALIDATORS.get(paymentMethod);
    }
}
