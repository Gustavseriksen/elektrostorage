package kea.elektrostorage.repository;

import kea.elektrostorage.model.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssemblyRepository extends JpaRepository<Assembly, Long> {
}
