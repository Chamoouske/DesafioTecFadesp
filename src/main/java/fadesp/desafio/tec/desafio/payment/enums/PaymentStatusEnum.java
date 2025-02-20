package fadesp.desafio.tec.desafio.payment.enums;

import fadesp.desafio.tec.desafio.payment.dto.Payment;

public enum PaymentStatusEnum {
    PENDENTE_DE_PROCESSAMENTO("pendente") {
        @Override
        public void changeStatus(Payment payment) {
            payment.pendenteDeProcessamento();
        }
    },
    PROCESSAMENTO_COM_FALHA("falha") {
        @Override
        public void changeStatus(Payment payment) {
            payment.processadoComFalha();
        }
    },
    PROCESSAMENTO_COM_SUCESSO("sucesso") {
        @Override
        public void changeStatus(Payment payment) {
            payment.processadoComSucesso();
        }
    },
    DELETE_PAYMENT("delete") {
        @Override
        public void changeStatus(Payment payment) {
            payment.deletePayment();
        }
    };
    private final String status;

    PaymentStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public PaymentStatusEnum getPaymentStatusEnum(String status) {
        return switch (status) {
            case "falha" -> PROCESSAMENTO_COM_FALHA;
            case "sucesso" -> PROCESSAMENTO_COM_SUCESSO;
            case "delete" -> DELETE_PAYMENT;
            default -> PENDENTE_DE_PROCESSAMENTO;
        };
    }

    public abstract void changeStatus(Payment payment);
}
