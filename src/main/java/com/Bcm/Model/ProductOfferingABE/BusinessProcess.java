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

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = true)
  private String description;

  @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOffering.class)
  @JoinColumn(name = "businessProcess_id")
  @JsonIgnore
  private List<ProductOffering> productOfferings;
}
