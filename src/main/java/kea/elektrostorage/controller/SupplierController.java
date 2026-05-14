package kea.elektrostorage.controller;

import kea.elektrostorage.model.Supplier;
import kea.elektrostorage.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Håndterer HTTP-kald til /suppliers — bruges af frontend til at vælge leverandør
@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierRepository supplierRepository;

    // GET /suppliers — returnerer alle leverandører
    @GetMapping
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }
}
