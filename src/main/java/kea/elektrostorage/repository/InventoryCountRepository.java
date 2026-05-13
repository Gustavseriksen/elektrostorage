package kea.elektrostorage.repository;

import kea.elektrostorage.model.InventoryCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCountRepository extends JpaRepository<InventoryCount, Long> {
}
