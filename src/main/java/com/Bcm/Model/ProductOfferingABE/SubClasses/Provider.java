package com.Bcm.Model.ProductOfferingABE.SubClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Provider")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "Provider_seq_generator", sequenceName = "Provider_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_ProviderCode;

    @Column(name = "name", nullable = false)
    private String name;

}