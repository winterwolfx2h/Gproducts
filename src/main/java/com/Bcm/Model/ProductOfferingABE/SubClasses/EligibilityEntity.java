package com.Bcm.Model.ProductOfferingABE.SubClasses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Entity")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EligibilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_generator")
    @SequenceGenerator(name = "entity_seq_generator", sequenceName = "entity_sequence", allocationSize = 1)
    @Column(name = "entityCode", nullable = false)
    private int entityCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;
}
