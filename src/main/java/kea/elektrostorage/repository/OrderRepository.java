package kea.elektrostorage.repository;

import kea.elektrostorage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByModtagetDatoIsNull();
}
