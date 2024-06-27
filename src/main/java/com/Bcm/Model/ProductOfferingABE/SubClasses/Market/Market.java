package com.Bcm.Model.ProductOfferingABE.SubClasses.Market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Market")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("market")
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

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubMarket> subMarkets = new ArrayList<>();

    public void addSubMarket(SubMarket subMarket) {
        subMarkets.add(subMarket);
        subMarket.setMarket(this);
    }

    public void removeSubMarket(SubMarket subMarket) {
        subMarkets.remove(subMarket);
        subMarket.setMarket(null);
    }
}
