package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "BundledProductOffer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BundledProductOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int bdo_Code;


    @Column(name = "status", nullable = false)
    @OneToMany(mappedBy = "bundledProductOffer")
    private List<Status> status;

    @Column(name = "validFor", nullable = false)
    private String validFor;


}

