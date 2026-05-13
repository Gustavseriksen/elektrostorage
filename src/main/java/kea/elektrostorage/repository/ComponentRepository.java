package kea.elektrostorage.repository;

import kea.elektrostorage.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
