package fadesp.desafio.tec.desafio.payment.repository;

import fadesp.desafio.tec.desafio.payment.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PaymentRepository  extends CrudRepository<Payment, Long>, PagingAndSortingRepository<Payment, Long> {
    List<Payment> findByCpfPayer(String cpf);
    List<Payment> findByStatusPayment(String statusPayment);
    Payment findByCodPayment(int codPayment);
}
