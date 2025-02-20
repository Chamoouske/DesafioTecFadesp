package fadesp.desafio.tec.desafio.payment.dto;

import java.math.BigDecimal;

public interface Payment {
    Long getCodPayment();

    String getStatusPayment();

    String getCpfPayer();

    String getPaymentMethod();

    String getCardNumber();

    BigDecimal getPrice();
}
