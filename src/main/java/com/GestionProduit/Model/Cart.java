package com.GestionProduit.Model;

import com.GestionProduit.Service.ProduitService;
import com.GestionProduit.Service.TaxService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cart implements Serializable {
  private Map<Long, CartItem> items;

  public Cart() {
    this.items = new HashMap<>();
  }

  public void addProduct(Long productId, int quantity, List<Integer> taxCodes) {
    CartItem cartItem = new CartItem(quantity, taxCodes);
    items.put(productId, cartItem);
  }

  public void removeProduct(Long productId) {
    items.remove(productId);
  }

  public boolean containsProduct(Long productId) {
    return items.containsKey(productId);
  }

  public double calculateHTPrice(ProduitService produitService, TaxService taxService) {
    double totalHT = 0;
    for (Map.Entry<Long, CartItem> entry : items.entrySet()) {
      Long productId = entry.getKey();
      CartItem cartItem = entry.getValue();
      Produit produit = produitService.findById(productId);
      totalHT += produit.getPrix() * cartItem.getQuantity();
    }
    return totalHT;
  }

  public double calculateTTCPrice(ProduitService produitService, TaxService taxService) {
    double totalTTC = 0;
    for (Map.Entry<Long, CartItem> entry : items.entrySet()) {
      Long productId = entry.getKey();
      CartItem cartItem = entry.getValue();
      Produit produit = produitService.findById(productId);
      double itemPrice = produit.getPrix() * cartItem.getQuantity();

      for (int taxCode : cartItem.getTaxCodes()) {
        Tax tax = taxService.findById(taxCode);
        itemPrice += (itemPrice * (tax.getValue() / 100));
      }

      totalTTC += itemPrice;
    }
    return totalTTC;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CartItem {
    private int quantity;
    private List<Integer> taxCodes;
  }
}
