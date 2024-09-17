package com.Bcm.Model.ProductOfferingABE.SubClasses.Family;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Family")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "family_seq_generator")
    @SequenceGenerator(name = "family_seq_generator", sequenceName = "family_sequence", allocationSize = 1)
    @Column(name = "po_FamilyCode", nullable = false)
    private int po_FamilyCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubFamily> subFamilies = new ArrayList<>();

    public void addSubFamily(SubFamily subFamily) {
        subFamilies.add(subFamily);
        subFamily.setFamily(this);
    }

    public void removeSubFamily(SubFamily subFamily) {
        subFamilies.remove(subFamily);
        subFamily.setFamily(null);
    }
}
