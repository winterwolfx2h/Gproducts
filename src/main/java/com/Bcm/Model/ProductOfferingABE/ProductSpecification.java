package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Table(name = "ProductSpecification")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "ProductSpecification_seq_generator", sequenceName = "ProductSpecification_sequence", allocationSize = 1)
    @Column(name = "po_SpecCode", nullable = false)
    private int po_SpecCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "TMCODE", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private POPlan poPlan;

    @Column(name = "externalId", nullable = true)
    private String externalId;

}
