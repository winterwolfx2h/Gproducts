package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "POAttributes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poAttribute_code", nullable = false)
    private int poAttribute_code;

    @Column(name = "shortCode", nullable = false)
    private int shortCode;

    @Column(name = "attributeCategory", nullable = true)
    private String attributeCategory;

    @Column(name = "AttributeExternalId", nullable = true)
    private int AttributeExternalId;

    @Column(name = "attributeValDesc", nullable = false)
    private String attributeValDesc;

    @Column(name = "charType", nullable = false)
    private String charType;

    @Column(name = "charValue", nullable = false)
    private String charValue;
}
