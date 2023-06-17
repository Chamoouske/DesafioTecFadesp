package fadesp.desafio.tec.desafio.model;

import fadesp.desafio.tec.desafio.error.ValidationErrorException;

import java.util.Arrays;
import java.util.List;

public class PaymentProcess {
    private long codPayment;
    private String newStatus;

    public long getCodPayment() {
        return codPayment;
    }

    public void setCodPayment(long codPayment) {
        this.codPayment = codPayment;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void verifyStatus(){
        List<String> validStatus = Arrays.asList("Processado com Falha", "Pendente de Processamento", "Processado com Sucesso");
        if(!validStatus.contains(this.newStatus)){
            throw new ValidationErrorException("newStatus", "New status is invalid! Accept only: Pendente de Processamento, Processado com Falha or Processado com Sucesso");
        }
    }
}
