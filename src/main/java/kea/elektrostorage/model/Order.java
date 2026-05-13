package kea.elektrostorage.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders") // "order" er reserveret ord i SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String trackingKode;
    private LocalDate sendtDato;
    private LocalDate forventetLeveringsDato;
    private LocalDate modtagetDato;
}
