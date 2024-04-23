package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
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
    @Column(name = "TMCODE", nullable = false)
    private int TMCODE;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "detailedDescription", nullable = false)
    private String detailedDescription;

    @Column(name = "externalId", nullable = false)
    private String externalId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "po_MarketCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Market market;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "po_SubMarketCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubMarket subMarket;

    @Column(name = "status", nullable = false)
    private String status;
}
