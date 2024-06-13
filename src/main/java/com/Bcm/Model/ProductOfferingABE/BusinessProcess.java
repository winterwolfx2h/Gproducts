package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BusinessProcess")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessProcess {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "businessProcess_id", nullable = false)
  private int businessProcess_id;

  @Column(name = "type_id", nullable = false)
  private Integer type_id;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
  @JoinColumn(name = "businessProcess_id")
  @JsonIgnore
  private List<ProductOffering> productOfferings;

  //    @Column(name = "Product_id", nullable = true)
  //    private Integer Product_id;
}
