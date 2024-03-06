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
    @Column(name = "businessProcessId", nullable = false)
    private int businessProcessId;

    @Column(name = "bussinessProcType", nullable = false)
    private String bussinessProcType;
}

