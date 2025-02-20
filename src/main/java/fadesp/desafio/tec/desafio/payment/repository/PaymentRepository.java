package fadesp.desafio.tec.desafio.payment.repository;

import fadesp.desafio.tec.desafio.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {
}
