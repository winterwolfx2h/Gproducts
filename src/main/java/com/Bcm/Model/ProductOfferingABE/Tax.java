package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Tax")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Tax {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_seq_generator")
  @SequenceGenerator(name = "tax_seq_generator", sequenceName = "tax_sequence", allocationSize = 1)
  @Column(name = "taxCode", nullable = false)
  private int taxCode;

  @Column(name = "name", nullable = true)
  private String name;

  @Column(name = "value", nullable = true)
  private Float value;

  @Column(name = "taxType", nullable = true)
  private String taxType;

  @Column(name = "customerCategory", nullable = true)
  private String customerCategory;

  @Column(name = "externalId", nullable = true)
  private String externalId;

  @Column(name = "validFrom", nullable = true)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date validFrom;

  @Column(name = "validTo", nullable = true)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date validTo;

  @JsonIgnore
  @ManyToMany(mappedBy = "taxes")
  private Set<Product> products = new HashSet<>();
}
