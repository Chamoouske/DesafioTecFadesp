package fadesp.desafio.tec.desafio.payment.designpattern.stratey.validators;

import fadesp.desafio.tec.desafio.config.error.ValidationErrorException;
import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.util.function.Predicate;

public class CardValidator implements PaymentValidator {
    private static final String CREDIT_CARD_REGEX = "\\d{16}$";
    private static final Predicate<Payment> VALIDATE_PAYMENT_METHOD_CREDIT_CARD = pay -> PaymentMethodEnum.CREDITO.equals(pay.getPaymentMethod());
    private static final Predicate<Payment> VALIDATE_PAYMENT_METHOD_DEBIT_CARD = pay -> PaymentMethodEnum.DEBITO.equals(pay.getPaymentMethod());
    private static final Predicate<Payment> VALIDATE_PAYMENT_METHOD_CARD = pay -> VALIDATE_PAYMENT_METHOD_CREDIT_CARD.test(pay) || VALIDATE_PAYMENT_METHOD_DEBIT_CARD.test(pay);

    public static boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches(CREDIT_CARD_REGEX);
    }

    @Override
    public void validate(Payment payment) {
        if (VALIDATE_PAYMENT_METHOD_CARD.test(payment) && !validateCardNumber(payment.getCardNumber()))
            throw new ValidationErrorException("cardNumber", "Card number is not valid");
    }
}
