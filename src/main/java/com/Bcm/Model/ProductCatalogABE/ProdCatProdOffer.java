package com.Bcm.Model.ProductCatalogABE;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ProdCatProdOffer")
public class ProdCatProdOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int id;
    private String validFor;
    private String x;
    private String y;
    private String u;
}
