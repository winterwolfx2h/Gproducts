package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "POAttributes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poAttribute_code", nullable = false)
    private int poAttribute_code;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "bsexternalId", nullable = true)
    private String bsexternalId;

    @Column(name = "csexternalId", nullable = true)
    private String csexternalId;

    @Column(name = "charType", nullable = false)
    private String charType;

    @Column(name = "charValue", nullable = false)
    private String charValue;

    @ElementCollection
    @CollectionTable(name = "ValueDescription", joinColumns = @JoinColumn(name = "poAttribute_code"))
    private List<ValueDescription> valueDescription;

    @Embeddable
    public static class ValueDescription {
        @Column(name = "value", nullable = false)
        public String value;

        @Column(name = "description", nullable = false)
        public String description;
    }

}