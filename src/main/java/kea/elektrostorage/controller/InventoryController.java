package kea.elektrostorage.controller;

import kea.elektrostorage.model.InventoryCount;
import kea.elektrostorage.model.OrderLine;
import kea.elektrostorage.repository.InventoryCountRepository;
import kea.elektrostorage.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
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

    // GET /inventory — returnerer alle komponenter modtaget via afsluttede bestillinger
    @GetMapping
    public List<OrderLine> getInventory() {
        return orderLineRepository.findByOrderModtagetDatoIsNotNull();
    }

    // POST /inventory/count — gemmer en manuel optælling med dags dato
    @PostMapping("/count")
    public InventoryCount addCount(@RequestBody InventoryCount count) {
        count.setDato(LocalDate.now());
        return inventoryCountRepository.save(count);
    }
}
