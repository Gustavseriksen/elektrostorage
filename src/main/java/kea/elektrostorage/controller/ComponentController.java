package kea.elektrostorage.controller;

import kea.elektrostorage.model.Component;
import kea.elektrostorage.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentRepository componentRepository;

    @GetMapping
    public List<Component> getAll() {
        return componentRepository.findAll(); // Hent alle komponenter fra databasen
    }

    @PostMapping
    public Component create(@RequestBody Component component) {
        return componentRepository.save(component); // Gem ny komponent og returner den med tildelt id
    }

    @PutMapping("/{id}/udgaaet")
    public ResponseEntity<Component> markerUdgaaet(@PathVariable Long id) {
        return componentRepository.findById(id) // Find komponent med det givne id
                .map(c -> {
                    c.setUdgaaet(true);
                    return ResponseEntity.ok(componentRepository.save(c)); // Gem og returner 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // Returner 404 hvis id ikke findes
    }
}
