package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "RelationType")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "Type_seq_generator", sequenceName = "Type_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poRelationType_code;

    @Column(name = "name", nullable = false)
    private String name;

}