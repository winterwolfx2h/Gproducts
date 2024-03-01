package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "LrServiceId")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LrServiceId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LrServiceId_seq_generator")
    @SequenceGenerator(name = "LrServiceId_seq_generator", sequenceName = "LrServiceId_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int pr_LrServiceId;

    @Column(name = "name", nullable = false)
    private String name;
}