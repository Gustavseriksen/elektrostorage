package kea.elektrostorage.repository;

import kea.elektrostorage.model.AssemblyLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssemblyLineRepository extends JpaRepository<AssemblyLine, Long> {
}
