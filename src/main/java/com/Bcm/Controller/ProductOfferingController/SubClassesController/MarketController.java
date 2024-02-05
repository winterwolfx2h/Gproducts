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

    @PostMapping
    public ResponseEntity<Market> createMarket(@RequestBody Market Market) {
        Market createdMarket = marketService.create(Market);
        return ResponseEntity.ok(createdMarket);
    }

    @GetMapping
    public ResponseEntity<List<Market>> getAllMarkets() {
        List<Market> Markets = marketService.read();
        return ResponseEntity.ok(Markets);
    }

    @GetMapping("/{po_MarketCode}")
    public ResponseEntity<Market> getMarketById(@PathVariable("po_MarketCode") int po_MarketCode) {
        Market Market = marketService.findById(po_MarketCode);
        return ResponseEntity.ok(Market);
    }

    @PutMapping("/{po_MarketCode}")
    public ResponseEntity<Market> updateMarket(
            @PathVariable("po_MarketCode") int po_MarketCode,
            @RequestBody Market updatedMarket) {

        Market updatedGroup = marketService.update(po_MarketCode, updatedMarket);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_MarketCode}")
    public ResponseEntity<String> deleteMarket(@PathVariable("po_MarketCode") int po_MarketCode) {
        String resultMessage = marketService.delete(po_MarketCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Market>> searchMarketsByKeyword(@RequestParam("name") String name) {
        List<Market> searchResults = marketService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
