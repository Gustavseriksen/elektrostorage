package kea.elektrostorage.repository;

import kea.elektrostorage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
