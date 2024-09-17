package com.Bcm.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ProductType")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productTypeCode", nullable = false)
    private int productTypeCode;

    @Column(name = "typeName", nullable = false)
    private String typeName;

    @Column(name = "description", nullable = false)
    private String description;
}
