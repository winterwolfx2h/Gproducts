package com.Bcm.Controller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ParentService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/poplan")
public class POPlanController {

    @Autowired
    private ParentService parentService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private SubMarketService subMarketService;


    final POPlanService popService;

    public POPlanController(POPlanService popService) {
        this.popService = popService;
    }

    @GetMapping("/listPoPlans")
    public List<POPlan> read() {
        return popService.read();
    }

    @PostMapping("/addPOPlan")
    public ResponseEntity<?> create(@RequestBody POPlan poPlan) {

        String parentName = poPlan.getParent().getName();
        String marketName = poPlan.getMarket().getName();
        String subMarketName = poPlan.getSubMarket().getName();

        Parent parent = parentService.findByName(parentName);
        Market market = marketService.findByName(marketName);
        SubMarket subMarket = subMarketService.findByName(subMarketName);


        if (parent != null && market != null && subMarket != null ) {
            poPlan.setParent(parent);
            poPlan.setMarket(market);
            poPlan.setSubMarket(subMarket);

            POPlan createdPlan = popService.create(poPlan);
            return ResponseEntity.ok(createdPlan);
        } else {
            StringBuilder errorMessage = new StringBuilder("The following entities were not found:");
            if (parent == null) errorMessage.append(" parent with name: ").append(parentName);
            if (market == null) errorMessage.append(" Market with name: ").append(marketName);
            if (subMarket == null) errorMessage.append(" SubMarket with name: ").append(subMarketName);
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
    }

    @PutMapping("/updatePOPlan/{TMCODE}")
    public ResponseEntity<?> update(@PathVariable int TMCODE, @RequestBody POPlan poPlan) {
        try {
            POPlan updatedPlan = popService.update(TMCODE, poPlan);
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
