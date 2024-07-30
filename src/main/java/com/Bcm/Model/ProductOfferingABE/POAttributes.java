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

    @Column(name = "mandatory", nullable = true)
    private Boolean mandatory;

    @Column(name = "displayFormat", nullable = true)
    private String displayFormat;

    @Column(name = "externalcfs", nullable = true)
    private Boolean externalcfs;

    @Column(name = "dependentCfs", nullable = true)
    private String dependentCfs;

    @Column(name = "changeInd", nullable = true)
    private Boolean changeInd;

    @ElementCollection
    @CollectionTable(name = "AttributesValueDes", joinColumns = @JoinColumn(name = "poAttribute_code"))
    private List<ValueDescription> valueDescription;

    @ElementCollection
    @CollectionTable(name = "AttributesDefaultMaxSize", joinColumns = @JoinColumn(name = "poAttribute_code"))
    private List<DefaultMaxSize> defaultMaxSize;

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueDescription {
        @Column(name = "value", nullable = false)
        public String value;

        @Column(name = "description", nullable = false)
        public String description;

        @Column(name = "defaultvalue", nullable = true)
        private Boolean defaultvalue;
    }

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DefaultMaxSize {
        @Column(name = "maxSize", nullable = false)
        public String maxSize;

        @Column(name = "defaultvalue", nullable = true)
        private String defaultvalue;
    }
}
