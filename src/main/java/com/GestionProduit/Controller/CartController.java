package com.GestionProduit.Controller;

import com.GestionProduit.Model.DTO.CartItemDetails;
import com.GestionProduit.Service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart Controller", description = "Operations related to shopping cart")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;

  @PostMapping("/add")
  public ResponseEntity<String> addToCart(
      @RequestParam List<Long> productIds,
      @RequestParam List<Integer> quantities,
      @RequestParam List<List<Integer>> taxCodes) {
    if (productIds.size() != quantities.size() || productIds.size() != taxCodes.size()) {
      return ResponseEntity.badRequest().body("All input lists must have the same size.");
    }

    for (int i = 0; i < productIds.size(); i++) {
      cartService.addToCart(productIds.get(i), quantities.get(i), taxCodes.get(i));
    }
    return ResponseEntity.ok("Products added to cart");
  }

  @GetMapping("/view")
  public ResponseEntity<List<CartItemDetails>> viewCart() {
    List<CartItemDetails> cartDetails = cartService.getCartDetails();
    return ResponseEntity.ok(cartDetails);
  }

  @DeleteMapping("/remove/{productId}")
  public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
    cartService.removeFromCart(productId);
    return ResponseEntity.ok("Product removed from cart");
  }

  @GetMapping("/total")
  public ResponseEntity<String> calculateTotal() {
    double totalHT = cartService.calculateHTPrice();
    double totalTTC = cartService.calculateTTCPrice();
    String responseMessage =
        String.format("Shopping value for your cart is:\nHT value: %.2f USD\nTTC value: %.2f USD", totalHT, totalTTC);
    return ResponseEntity.ok(responseMessage);
  }

  @PostMapping("/checkout")
  public ResponseEntity<String> checkout() {
    double total = cartService.calculateTTCPrice();
    cartService.clearCart();
    return ResponseEntity.ok("Order placed. Total amount: " + total);
  }
}
