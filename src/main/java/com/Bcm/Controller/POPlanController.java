package com.Bcm.Controller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/poplan")
public class POPlanController {


    final MarketService marketService;
    final SubMarketService subMarketService;
    final POPlanService popService;

    public POPlanController(MarketService marketService, SubMarketService subMarketService, POPlanService popService) {
        this.marketService = marketService;
        this.subMarketService = subMarketService;
        this.popService = popService;
    }

    @GetMapping("/listPoPlans")
    public List<POPlan> read() {
        return popService.read();
    }

    @PostMapping("/addPOPlan")
    public ResponseEntity<?> create(@RequestBody POPlan poPlan) {

        String marketName = poPlan.getMarket().getName();
        String subMarketName = poPlan.getSubMarket().getName();

        Market market = marketService.findByName(marketName);
        SubMarket subMarket = subMarketService.findByName(subMarketName);


        if (market != null && subMarket != null) {
            poPlan.setMarket(market);
            poPlan.setSubMarket(subMarket);

            POPlan createdPlan = popService.create(poPlan);
            return ResponseEntity.ok(createdPlan);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (market == null) errorMessage.append(" Market with name: ").append(marketName);
            if (subMarket == null) errorMessage.append(" SubMarket with name: ").append(subMarketName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }
    @PutMapping("/updatePOPlan/{TMCODE}")
    public ResponseEntity<?> update(@PathVariable int TMCODE, @RequestBody POPlan poPlan) {
        try {
            POPlan existingPlan = popService.findById(TMCODE);
            if (existingPlan == null) {
                return ResponseEntity.notFound().build();
            }
            String marketName = poPlan.getMarket().getName();
            String subMarketName = poPlan.getSubMarket().getName();
            Market existingMarket = marketService.findByName(marketName);
            SubMarket existingSubMarket = subMarketService.findByName(subMarketName);
            if (existingMarket == null || existingSubMarket == null) {
                return ResponseEntity.badRequest().body("Market or SubMarket not found.");
            }
            existingPlan.setDES(poPlan.getDES());
            existingPlan.setSHDES(poPlan.getSHDES());
            existingPlan.setMarket(existingMarket);
            existingPlan.setSubMarket(existingSubMarket);

            POPlan updatedPlan = popService.update(TMCODE, existingPlan);
            return ResponseEntity.ok(updatedPlan);
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{TMCODE}")
    public String delete(@PathVariable int TMCODE) {
        return popService.delete(TMCODE);
    }

    @GetMapping("/getById/{TMCODE}")
    public ResponseEntity<POPlan> getById(@PathVariable int TMCODE) {
        try {
            POPlan foundPlan = popService.findById(TMCODE);
            return ResponseEntity.ok(foundPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/searchByKeyword")
    public List<POPlan> searchByKeyword(@RequestParam String DES) {
        try {
            return popService.searchByKeyword(DES);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private RuntimeException handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                e.getMessage(),
                "There was an error processing the request."
        );
        return new RuntimeException(errorMessage.toString(), e);
    }
}
