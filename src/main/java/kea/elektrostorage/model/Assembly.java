package kea.elektrostorage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component resulteresI; // Den komponent som styklisten producerer
}
