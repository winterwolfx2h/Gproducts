package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BusinessProcess")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessProcess_id", nullable = false)
    private int businessProcess_id;

    @Column(name = "type_id", nullable = false)
    private Integer type_id;

    @Column(name = "Product_id", nullable = true)
    private Integer Product_id;
}

