package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "POAttributes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poAttribute_code", nullable = false)
    private int poAttribute_code;

    @Column(name = "categoryType", nullable = true)
    private String category;

    @Column(name = "externalId", nullable = true)
    private String externalId;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "description", nullable = false)
    private String description;
/*
    @ElementCollection
    @CollectionTable(name = "POAttribute_ValueDescription", joinColumns = @JoinColumn(name = "poAttribute_code"))
    @Column(name = "valueDescription")
    private List<String> valueDescription;
*/
}
