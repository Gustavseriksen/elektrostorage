package kea.elektrostorage.repository;

import kea.elektrostorage.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByOrderId(Long orderId);
    List<OrderLine> findByOrderModtagetDatoIsNotNull();
}
