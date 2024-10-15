package com.GestionProduit.Service.serviceImpl;

import com.GestionProduit.Model.Cart;
import com.GestionProduit.Model.DTO.CartItemDetails;
import com.GestionProduit.Model.DTO.TaxDetail;
import com.GestionProduit.Model.Produit;
import com.GestionProduit.Model.Tax;
import com.GestionProduit.Service.CartService;
import com.GestionProduit.Service.ProduitService;
import com.GestionProduit.Service.TaxService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
  private final ProduitService produitService;
  private final TaxService taxService;
  private Cart cart = new Cart();

  @Override
  public void addToCart(Long productId, int quantity, List<Integer> taxCodes) {
    cart.addProduct(productId, quantity, taxCodes);
  }

  @Override
  public Cart getCart() {
    return cart;
  }

  @Override
  public void removeFromCart(Long productId) {
    cart.removeProduct(productId);
  }

  @Override
  public double calculateHTPrice() {
    return cart.calculateHTPrice(produitService, taxService);
  }

  @Override
  public double calculateTTCPrice() {
    return cart.calculateTTCPrice(produitService, taxService);
  }

  @Override
  public void clearCart() {
    cart = new Cart();
  }

  @Override
  public List<CartItemDetails> getCartDetails() {
    List<CartItemDetails> details = new ArrayList<>();
    for (Long productId : cart.getItems().keySet()) {
      Produit produit = produitService.findById(productId);
      Cart.CartItem cartItem = cart.getItems().get(productId);

      List<TaxDetail> taxDetails = new ArrayList<>();
      for (int taxCode : cartItem.getTaxCodes()) {
        Tax tax = taxService.findById(taxCode);
        taxDetails.add(new TaxDetail(taxCode, tax.getValue()));
      }

      details.add(
          new CartItemDetails(
              productId,
              produit.getNom(),
              cartItem.getQuantity(),
              produit.getPrix(),
              produit.getCatp().getNom(),
              taxDetails));
    }
    return details;
  }
}
