package fadesp.desafio.tec.desafio.payment.specification;

import fadesp.desafio.tec.desafio.payment.dto.Payment;
import fadesp.desafio.tec.desafio.payment.entity.PaymentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification implements Specification<PaymentEntity> {
    private final transient Payment payment;

    public PaymentSpecification(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Predicate toPredicate(Root<PaymentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "codPayment", payment.getCodPayment()));
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "cpfPayer", payment.getCodPayment()));
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "paymentMethod", payment.getCodPayment()));
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "cardNumber", payment.getCodPayment()));
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "price", payment.getCodPayment()));
        if (payment.getCodPayment() != null)
            predicates.add(getPredicate(root, criteriaBuilder, "statusPayment", payment.getCodPayment()));

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate getPredicate(Root<PaymentEntity> root, CriteriaBuilder criteriaBuilder, String field, Object value) {
        return criteriaBuilder.equal(root.get(field), value);
    }
}
