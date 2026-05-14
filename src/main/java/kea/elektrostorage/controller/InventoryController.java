package kea.elektrostorage.controller;

import kea.elektrostorage.model.InventoryCount;
import kea.elektrostorage.model.OrderLine;
import kea.elektrostorage.repository.ComponentRepository;
import kea.elektrostorage.repository.InventoryCountRepository;
import kea.elektrostorage.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Håndterer alle HTTP-kald til /inventory
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final OrderLineRepository orderLineRepository;
    private final InventoryCountRepository inventoryCountRepository;
    private final ComponentRepository componentRepository;

    // GET /inventory — returnerer alle komponenter modtaget via afsluttede bestillinger
    @GetMapping
    public List<OrderLine> getInventory() {
        return orderLineRepository.findByOrderModtagetDatoIsNotNull();
    }

    // POST /inventory/count — gemmer en manuel optælling med dags dato
    @PostMapping("/count")
    public ResponseEntity<?> addCount(@RequestBody InventoryCount count) {
        if (count.getComponent() == null || count.getComponent().getId() == null) {
            return ResponseEntity.badRequest().body("Komponent-id mangler");
        }
        // Hent fuld komponent fra databasen ud fra det id frontend sender,
        // så vi ikke gemmer en halvtom Component-reference
        var component = componentRepository.findById(count.getComponent().getId());
        if (component.isEmpty()) {
            return ResponseEntity.badRequest().body("Komponent findes ikke");
        }
        count.setComponent(component.get());
        count.setDato(LocalDate.now());
        return ResponseEntity.ok(inventoryCountRepository.save(count));
    }

    // GET /inventory/counts — returnerer alle indsendte optællinger
    @GetMapping("/counts")
    public List<InventoryCount> getCounts() {
        return inventoryCountRepository.findAll();
    }
}
