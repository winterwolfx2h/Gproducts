package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SubMarket")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submarket_seq_generator")
    @SequenceGenerator(name = "submarket_seq_generator", sequenceName = "submarket_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "po_SubMarketCode", nullable = false)
    private int po_SubMarketCode;

    @Column(name = "name", nullable = false)
    private String name;

}
