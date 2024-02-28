package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poRelationType_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RelationType type;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

}

