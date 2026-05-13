package kea.elektrostorage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity                  // Databasetabel
@Data                    // Getters, setters, toString
@NoArgsConstructor       // Kræves af JPA/Hibernate
@AllArgsConstructor      // Bruges til at oprette objekter i kode
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-increment styres af databasen
    private Long id;

    private String navn;
    private String adresse;
}
