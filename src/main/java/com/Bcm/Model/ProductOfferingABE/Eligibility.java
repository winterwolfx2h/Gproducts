package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Eligibility")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eligibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eligibility_id", nullable = false)
    private int eligibility_id;

    @Column(name = "stock_Indicator")
    private Boolean stock_Indicator;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Product_id", insertable = true, updatable = true)
    private ProductOffering productOffering;
}
