package com.Bcm.Model.ProductOfferingABE.SubClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "AttributeCategory")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttributeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AttributeCategory_seq_generator")
    @SequenceGenerator(name = "AttributeCategory_seq_generator", sequenceName = "AttributeCategory_sequence", allocationSize = 1)
    @Column(name = "po_AttributeCategoryCode", nullable = false)
    private int po_AttributeCategoryCode;

    @Column(name = "name", nullable = false)
    private String name;
}


