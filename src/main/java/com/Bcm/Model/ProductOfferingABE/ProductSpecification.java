package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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

    @Column(name = "category", nullable = false)
    private String category;

    @ElementCollection
    @CollectionTable(name = "product_specification_po_plan", joinColumns = @JoinColumn(name = "po_spec_code"))
    @Column(name = "shdes")
    private List<String> poPlanSHDES;

    @Column(name = "BS_externalId", nullable = true)
    private String BS_externalId;

    @Column(name = "CS_externalId", nullable = true)
    private String CS_externalId;

}
