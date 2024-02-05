package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.Bcm.Model.ProductOfferingABE.SubClasses.ValidFor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "ProductOfferingRelationship")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferingRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int por_Code;

    @Column(name = "type", nullable = false)
    @OneToMany(mappedBy = "productOfferingRelationship")
    private List<Type> type;

    @Column(name = "validFor", nullable = false)
    @OneToMany(mappedBy = "productOfferingRelationship")
    private List<ValidFor> validFor;


}

