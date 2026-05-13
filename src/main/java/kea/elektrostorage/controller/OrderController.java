package kea.elektrostorage.controller;

import kea.elektrostorage.model.Order;
import kea.elektrostorage.model.OrderLine;
import kea.elektrostorage.repository.ComponentRepository;
import kea.elektrostorage.repository.OrderLineRepository;
import kea.elektrostorage.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Håndterer alle HTTP-kald til /orders
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    // Spring injicerer automatisk disse tre repositories via konstruktøren (Lombok)
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ComponentRepository componentRepository;

    // GET /orders — returnerer kun ordrer der ikke er modtaget endnu
    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findByModtagetDatoIsNull();
    }

    // GET /orders/{id} — henter én ordre, returnerer 404 hvis den ikke findes
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /orders/{id}/lines — henter alle linjer (komponenter) på én ordre
    @GetMapping("/{id}/lines")
    public List<OrderLine> getLines(@PathVariable Long id) {
        return orderLineRepository.findByOrderId(id);
    }

    // POST /orders — opretter en ny tom bestilling ud fra JSON i request body
    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    // POST /orders/{id}/lines — tilføjer en komponent til en bestilling
    // Returnerer 400 Bad Request hvis bestillingen allerede er sendt
    @PostMapping("/{id}/lines")
    public ResponseEntity<?> addLine(@PathVariable Long id,
                                     @RequestParam Long componentId,
                                     @RequestParam int antal) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) return ResponseEntity.notFound().build();

        // Sendt-tjek: sendtDato er sat hvis ordren er afsendt
        if (order.get().getSendtDato() != null) {
            return ResponseEntity.badRequest().body("Kan ikke tilføje til en sendt bestilling");
        }

        var component = componentRepository.findById(componentId);
        if (component.isEmpty()) return ResponseEntity.notFound().build();

        // Opret og gem den nye linje med reference til ordre og komponent
        OrderLine line = new OrderLine(null, order.get(), component.get(), antal);
        return ResponseEntity.ok(orderLineRepository.save(line));
    }

    // PUT /orders/{id}/send — markerer en ordre som sendt ved at sætte dags dato
    @PutMapping("/{id}/send")
    public ResponseEntity<Order> markerSendt(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setSendtDato(LocalDate.now());
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
