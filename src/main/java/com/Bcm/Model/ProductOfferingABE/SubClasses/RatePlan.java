package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "RatePlanBCM")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rateplan_seq_generator")
    @SequenceGenerator(name = "rateplan_seq_generator", sequenceName = "rateplan_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_RatePlanCode;

    @Column(name = "name", nullable = false)
    private String name;

}