package fadesp.desafio.tec.desafio.payment.designpattern.stratey.validators;

import fadesp.desafio.tec.desafio.payment.dto.Payment;

@FunctionalInterface
public interface PaymentValidator {
    void validate(Payment payment);
}
