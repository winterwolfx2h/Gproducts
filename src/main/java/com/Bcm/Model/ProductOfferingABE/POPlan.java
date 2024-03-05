package com.Bcm.Model.ProductOfferingABE;


import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "poplan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POPlan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "POPlan_seq_generator", sequenceName = "POPlan_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int TMCODE;

    @Column(name = "DES", nullable = false)
    private String DES;

    @Column(name = "SHDES", nullable = false)
    private String SHDES;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_MarketCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Market market;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_SubMarketCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubMarket subMarket;
}
