package com.Bcm.Model.ProductOfferingABE.SubClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "Family")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "family_seq_generator")
    @SequenceGenerator(name = "family_seq_generator", sequenceName = "family_sequence", allocationSize = 1)
    @Column(name = "po_FamilyCode", nullable = false)
    private int po_FamilyCode;

    @Column(name = "name", nullable = false)
    private String name;


    /*

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOffering> productOfferings = new ArrayList<>();

    */

}
