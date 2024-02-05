package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "ProductOffering")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "effectiveFrom", nullable = false)
    private Date effectiveFrom;

    @Column(name = "effectiveTo", nullable = false  )
    private Date effectiveTo;

    @Column(name = "groupDimension", nullable = false)
    @OneToMany(mappedBy = "productOffering")
    private List<GroupDimension> groupDimension;

    @Column(name = "parent", nullable = false)
    @OneToMany(mappedBy = "productOffering")
    private List<Parent> parent;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "productSpec", nullable = false)
    @OneToMany(mappedBy = "productOffering")
    private List<ProductSpecification> productSpec;
}
