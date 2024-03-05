package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @PostMapping("/addMarket")
    public ResponseEntity<Market> createMarket(@RequestBody Market market) {
        try {
            Market createdMarket = marketService.create(market);
            return ResponseEntity.ok(createdMarket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
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
    public ResponseEntity<Market> updateMarket(
            @PathVariable("po_MarketCode") int po_MarketCode,
            @RequestBody Market updatedMarket) {

        try {
            Market updatedMarketResult = marketService.update(po_MarketCode, updatedMarket);
            return ResponseEntity.ok(updatedMarketResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
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
