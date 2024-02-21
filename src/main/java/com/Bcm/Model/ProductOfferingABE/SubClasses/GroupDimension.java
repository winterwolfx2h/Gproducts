package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "GroupDimension")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "GroupDimension_seq_generator", sequenceName = "GroupDimension_sequence", allocationSize = 1)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int po_GdCode;

    @Column(name = "name", nullable = false)
    private String name;

}
