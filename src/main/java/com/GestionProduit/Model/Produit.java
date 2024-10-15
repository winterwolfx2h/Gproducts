package com.GestionProduit.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produit")
public class Produit implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nom;

  private double prix;

  private int quantite;

  @Transient private Long categoryId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category catp;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
      name = "Product_Tax",
      joinColumns = @JoinColumn(name = "id"),
      inverseJoinColumns = @JoinColumn(name = "taxCode"))
  private Set<Tax> taxes = new HashSet<>();

  public Long getCategoryId() {
    return catp != null ? catp.getId() : null;
  }

}
