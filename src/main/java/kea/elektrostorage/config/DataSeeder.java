package kea.elektrostorage.config;

import kea.elektrostorage.model.Assembly;
import kea.elektrostorage.model.AssemblyLine;
import kea.elektrostorage.model.Component;
import kea.elektrostorage.model.Order;
import kea.elektrostorage.model.OrderLine;
import kea.elektrostorage.model.Supplier;
import kea.elektrostorage.repository.AssemblyLineRepository;
import kea.elektrostorage.repository.AssemblyRepository;
import kea.elektrostorage.repository.ComponentRepository;
import kea.elektrostorage.repository.OrderLineRepository;
import kea.elektrostorage.repository.OrderRepository;
import kea.elektrostorage.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final SupplierRepository supplierRepository;
    private final ComponentRepository componentRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final AssemblyRepository assemblyRepository;
    private final AssemblyLineRepository assemblyLineRepository;

    @Override
    public void run(String... args) {

        // Leverandører
        Supplier farnell = supplierRepository.save(new Supplier(null, "ElektroGros A/S", "Fabriksvej 12, 2750 Ballerup"));
        Supplier mouser = supplierRepository.save(new Supplier(null, "Nordel Komponenter", "Industrivej 45, 2300 København S"));
        Supplier rs = supplierRepository.save(new Supplier(null, "DanEl Supply", "Erhvervsvej 8, 8000 Aarhus C"));

        // Komponenter (internNummer er et tal jf. opgavebeskrivelsen)
        Component led = componentRepository.save(new Component(null, 1, "FAR-LED5MM", false, false, farnell));
        Component modstand = componentRepository.save(new Component(null, 2, "FAR-RES220", false, false, farnell));
        Component batteriholder = componentRepository.save(new Component(null, 3, "MOU-BH9V", false, false, mouser));
        Component batteri9v = componentRepository.save(new Component(null, 4, "RS-9VBAT", false, false, rs));
        componentRepository.save(new Component(null, 5, "FAR-CAP100", false, false, farnell));
        componentRepository.save(new Component(null, 6, "MOU-NPN2N", false, false, mouser));
        componentRepository.save(new Component(null, 7, "FAR-DIODE", false, false, farnell));
        componentRepository.save(new Component(null, 8, "RS-RELAY5V", false, false, rs));
        componentRepository.save(new Component(null, 9, "MOU-SWITCH", false, false, mouser));
        componentRepository.save(new Component(null, 10, "FAR-FUSE1A", false, false, farnell));

        // Lysende LED er en samlet komponent som ikke kan bestilles — kun samles via stykliste
        Component lysendeLedKomp = componentRepository.save(new Component(null, 100, null, false, true, null));

        // Aktiv bestilling: sendt afsted men endnu ikke modtaget
        Order aktivOrdre = orderRepository.save(new Order(null, mouser, "TRACK-AKTIV", LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 20), null));
        orderLineRepository.save(new OrderLine(null, aktivOrdre, led, 10));
        orderLineRepository.save(new OrderLine(null, aktivOrdre, modstand, 10));
        orderLineRepository.save(new OrderLine(null, aktivOrdre, batteriholder, 10));

        // Færdig bestilling (leveret)
        Order faerdigOrdre = orderRepository.save(new Order(null, farnell, "TRACK123", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 15)));
        orderLineRepository.save(new OrderLine(null, faerdigOrdre, led, 100));

        // Stykliste: Lysende LED → resulterer i den separate lysendeLedKomp komponent
        Assembly lysendeLed = assemblyRepository.save(new Assembly(null, "Lysende LED", lysendeLedKomp));
        assemblyLineRepository.save(new AssemblyLine(null, lysendeLed, led, 1));
        assemblyLineRepository.save(new AssemblyLine(null, lysendeLed, modstand, 1));
        assemblyLineRepository.save(new AssemblyLine(null, lysendeLed, batteriholder, 1));
        assemblyLineRepository.save(new AssemblyLine(null, lysendeLed, batteri9v, 1));
    }
}
