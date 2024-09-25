package com.Bcm.Model.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductType")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "productTypeCode", nullable = false)
  private int productTypeCode;

  @NotBlank(message = "Type name cannot be empty")
  @Column(name = "typeName", nullable = false)
  private String typeName;

  @NotBlank(message = "Description cannot be empty")
  @Column(name = "description", nullable = false)
  private String description;
}
