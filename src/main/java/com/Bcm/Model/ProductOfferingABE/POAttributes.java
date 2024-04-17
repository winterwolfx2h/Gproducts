package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name = "externalId", nullable = true)
    private String externalId;

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