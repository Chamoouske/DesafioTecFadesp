package fadesp.desafio.tec.desafio.payment.repository;

import fadesp.desafio.tec.desafio.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {
    List<PaymentEntity> findByCpfPayer(String cpf);

    List<PaymentEntity> findByStatusPayment(String statusPayment);

    PaymentEntity findByCodPayment(int codPayment);
}
