package com.GestionProduit.Model.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDetails {
  private Long productId;
  private String productName;
  private int quantity;
  private double prix;
  private String categoryName;
  private List<TaxDetail> taxes;
}
