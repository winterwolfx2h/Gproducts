package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ProductOfferRelation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int PoOfferRelation_Code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poRelationType_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RelationType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pos_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    @Column(name = "numberRelationOfferLowerLimit", nullable = false)
    private String numberRelationOfferLowerLimit;

    @Column(name = "numberRelationOfferUpperLimit", nullable = false)
    private String numberRelationOfferUpperLimit;

}

