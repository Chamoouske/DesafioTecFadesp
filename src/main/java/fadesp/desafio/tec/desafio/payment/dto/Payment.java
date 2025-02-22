package fadesp.desafio.tec.desafio.payment.dto;

import fadesp.desafio.tec.desafio.payment.designpattern.factory.PaymentMethodValidatorFactory;
import fadesp.desafio.tec.desafio.payment.designpattern.stratey.validators.PaymentValidator;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.math.BigDecimal;
import java.util.Objects;

public interface Payment {
    Long getCodPayment();

    String getStatusPayment();

    String getCpfPayer();

    PaymentMethodEnum getPaymentMethod();

    String getCardNumber();

    BigDecimal getPrice();

    void pendenteDeProcessamento();

    void processadoComFalha();

    void processadoComSucesso();

    void deletePayment();

    default void validate() {
        PaymentValidator validator = PaymentMethodValidatorFactory.create(getPaymentMethod());
        if (Objects.nonNull(validator)) validator.validate(this);
    }
}
