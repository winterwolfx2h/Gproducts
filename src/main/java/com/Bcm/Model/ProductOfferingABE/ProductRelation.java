package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ProductRelation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poRelation_Code;


    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

}

