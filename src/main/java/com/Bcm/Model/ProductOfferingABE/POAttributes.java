package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "attributeType", nullable = false)
    private String attributeType;

    @Column(name = "dataType", nullable = false)
    private String dataType;

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
    private List<ValueDescription> valueDescription = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "AttributesDomaine", joinColumns = @JoinColumn(name = "poAttribute_code"))
    private List<DefaultMaxSize> defaultMaxSize = new ArrayList<>();

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueDescription {
        @Column(name = "value", nullable = true)
        public String value;

        @Column(name = "description", nullable = true)
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
        @Column(name = "maxSize", nullable = true)
        public String maxSize;

        @Column(name = "defaultvalue", nullable = true)
        private String defaultvalue;
    }
}
