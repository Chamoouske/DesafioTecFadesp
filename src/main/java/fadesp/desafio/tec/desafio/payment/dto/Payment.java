package fadesp.desafio.tec.desafio.payment.dto;

import fadesp.desafio.tec.desafio.payment.enums.PaymentMethodEnum;

import java.math.BigDecimal;

public interface Payment {
    Long getCodPayment();

    String getStatusPayment();

    String getCpfPayer();

    PaymentMethodEnum getPaymentMethod();

    String getCardNumber();

    BigDecimal getPrice();
}
