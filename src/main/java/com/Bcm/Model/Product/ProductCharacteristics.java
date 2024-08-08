package com.Bcm.Model.Product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ProductCharacteristics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCharacteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pCharacteristic_code", nullable = false)
    private int pCharacteristic_code;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "mandatory", nullable = true)
    private Boolean mandatory;

    @Column(name = "displayFormat", nullable = true)
    private String displayFormat;

    @ElementCollection
    @CollectionTable(name = "CharacteristicValueDes", joinColumns = @JoinColumn(name = "pCharacteristic_code"))
    private List<CharacteristicValueDes> valueDescription = new ArrayList<>();

    @Column(name = "Product_id", nullable = false)
    private int Product_id;

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CharacteristicValueDes {
        @Column(name = "value", nullable = true)
        public String value;

        @Column(name = "description", nullable = true)
        public String description;
    }
}
