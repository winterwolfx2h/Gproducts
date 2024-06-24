package com.Bcm.Model.ProductOfferingABE.SubClasses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SubFamily")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subFamily_seq_generator")
    @SequenceGenerator(name = "subFamily_seq_generator", sequenceName = "subFamily_sequence", allocationSize = 1)
    @Column(name = "po_SubFamilyCode", nullable = false)
    private int po_SubFamilyCode;

    @Column(name = "subFamilyName", nullable = false)
    private String subFamilyName;
}
