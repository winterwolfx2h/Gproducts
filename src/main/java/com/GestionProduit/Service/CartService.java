package com.GestionProduit.Service;

import com.GestionProduit.Model.Cart;
import com.GestionProduit.Model.DTO.CartItemDetails;
import java.util.List;

public interface CartService {
  void addToCart(Long productId, int quantity, List<Integer> taxCodes);

  Cart getCart();

  void removeFromCart(Long productId);

  double calculateHTPrice();

  double calculateTTCPrice();

  void clearCart();

  List<CartItemDetails> getCartDetails();
}
