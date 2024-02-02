package com.Bcm.Model.ProductCatalogABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ProductCatalog")
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lifecycleStatus", nullable = true)
    private String lifecycleStatus;
    @Size(max = 20)
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = true)
    private Date type;
    @Column(name = "validFor", nullable = false)
    private Date validFor;
    @Column(name = "version", nullable = true)
    private String version;

}