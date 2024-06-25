package com.Bcm.Model.ProductOfferingABE.SubClasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SubFamily")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("subFamilies")
public class SubFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subFamily_seq_generator")
    @SequenceGenerator(name = "subFamily_seq_generator", sequenceName = "subFamily_sequence", allocationSize = 1)
    @Column(name = "po_SubFamilyCode", nullable = false)
    private int po_SubFamilyCode;

    @Column(name = "subFamilyName", nullable = false)
    private String subFamilyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyCode", nullable = true)
    private Family family;
}
