package com.Bcm.Model.ProductOfferingABE.SubClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Market")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_seq_generator")
    @SequenceGenerator(name = "market_seq_generator", sequenceName = "market_sequence", allocationSize = 1)
    @Column(name = "po_MarketCode", nullable = false)
    private int po_MarketCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;
}
