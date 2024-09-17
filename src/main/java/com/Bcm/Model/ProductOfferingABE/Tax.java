package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tax")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_seq_generator")
    @SequenceGenerator(name = "tax_seq_generator", sequenceName = "tax_sequence", allocationSize = 1)
    @Column(name = "taxCode", nullable = false)
    private int taxCode;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "value", nullable = true)
    private Float value;

    @Column(name = "taxType", nullable = true)
    private String taxType;

    @Column(name = "customerCategory", nullable = true)
    private String customerCategory;

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class)
    @JoinColumn(name = "TaxCode")
    @JsonIgnore
    private List<Product> products;
}
