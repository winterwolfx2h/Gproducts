package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.MarketAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {

  final MarketService marketService;

  @PostMapping("/addMarket")
  public ResponseEntity<String> createMarket(@RequestBody Market market) {
    try {
      Market createdMarket = marketService.create(market);
      return ResponseEntity.ok(createdMarket.toString());
    } catch (RuntimeException e) {
      if (e.getMessage().contains("already exists")) {
        return ResponseEntity.status(409).body("Market with name '" + market.getName() + "' already exists");
      }
      return ResponseEntity.status(500).body("Internal server error");
    }
  }

  @GetMapping("/listMarket")
  public ResponseEntity<List<Market>> getAllMarkets() {
    try {
      List<Market> markets = marketService.read();
      return ResponseEntity.ok(markets);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/{po_MarketCode}")
  public ResponseEntity<Market> getMarketById(@PathVariable("po_MarketCode") int po_MarketCode) {
    try {
      Market market = marketService.findById(po_MarketCode);
      return ResponseEntity.ok(market);
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(null);
    }
  }

  @PutMapping("/{po_MarketCode}")
  public ResponseEntity<?> updateMarket(
      @PathVariable("po_MarketCode") int po_MarketCode, @RequestBody Market updatedMarket) {
    try {
      Market updatedMarketResult = marketService.update(po_MarketCode, updatedMarket);
      return ResponseEntity.ok(updatedMarketResult);

    } catch (MarketAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{po_MarketCode}")
  public ResponseEntity<String> deleteMarket(@PathVariable("po_MarketCode") int po_MarketCode) {
    try {
      String resultMessage = marketService.delete(po_MarketCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Market>> searchMarketsByKeyword(@RequestParam("name") String name) {
    try {
      List<Market> searchResults = marketService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
