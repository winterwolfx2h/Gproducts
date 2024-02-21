package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Category")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_generator")
    @SequenceGenerator(name = "category_seq_generator", sequenceName = "category_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_CategoryCode;

    @Column(name = "name", nullable = false)
    private String name;
}


