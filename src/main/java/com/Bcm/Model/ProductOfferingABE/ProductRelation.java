package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ProductRelation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poRelation_Code", nullable = false)
    private int poRelation_Code;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "validFor", nullable = false)
    @Value("#{new java.util.Date()}")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date validFor;

    @Column(name = "Product_id", nullable = false)
    private int productId;
}
