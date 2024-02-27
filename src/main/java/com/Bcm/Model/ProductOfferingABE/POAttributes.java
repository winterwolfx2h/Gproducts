package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name = "POAttributes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poAttribute_code;

    @Column(name = "shortCode", nullable = false)
    private int shortCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_AttributeCategoryCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AttributeCategory attributeCategory;

    @Column(name = "externalId", nullable = false)
    private int externalId;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "attributeValue", nullable = false)
    private String attributeValue;

    @Column(name = "attributeValDesc", nullable = false)
    private String attributeValDesc;

    @Column(name = "charType", nullable = false)
    private String charType;

    @Column(name = "charValue", nullable = false)
    private String charValue;
}
