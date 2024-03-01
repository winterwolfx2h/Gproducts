package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "PrServiceId")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrServiceId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PrServiceId_seq_generator")
    @SequenceGenerator(name = "PrServiceId_seq_generator", sequenceName = "PrServiceId_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int pr_PrServiceId;

    @Column(name = "name", nullable = false)
    private String name;
}