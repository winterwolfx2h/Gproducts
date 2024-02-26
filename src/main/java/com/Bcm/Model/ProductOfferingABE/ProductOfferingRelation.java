package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ProductOfferingRelation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOfferingRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poRelation_Code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poType_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Type type;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

}

