package com.Bcm.Model.ProductOfferingABE;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Methods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Methods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_Id", nullable = false)
    private int method_Id;

    @Column(name = "name", nullable = true)
    private String name;
}