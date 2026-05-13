package kea.elektrostorage.repository;

import kea.elektrostorage.model.AssemblyLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssemblyLineRepository extends JpaRepository<AssemblyLine, Long> {
    List<AssemblyLine> findByAssemblyId(Long assemblyId);
}
