package kea.elektrostorage.controller;

import kea.elektrostorage.model.Assembly;
import kea.elektrostorage.model.AssemblyLine;
import kea.elektrostorage.repository.AssemblyLineRepository;
import kea.elektrostorage.repository.AssemblyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Håndterer alle HTTP-kald til /assemblies
@RestController
@RequestMapping("/assemblies")
@RequiredArgsConstructor
public class AssemblyController {

    private final AssemblyRepository assemblyRepository;
    private final AssemblyLineRepository assemblyLineRepository;

    // GET /assemblies — returnerer alle styklister
    @GetMapping
    public List<Assembly> getAll() {
        return assemblyRepository.findAll();
    }

    // GET /assemblies/{id} — returnerer én stykliste, 404 hvis den ikke findes
    @GetMapping("/{id}")
    public ResponseEntity<Assembly> getOne(@PathVariable Long id) {
        return assemblyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /assemblies/{id}/lines — returnerer alle komponenter på én stykliste
    @GetMapping("/{id}/lines")
    public List<AssemblyLine> getLines(@PathVariable Long id) {
        return assemblyLineRepository.findByAssemblyId(id);
    }
}
