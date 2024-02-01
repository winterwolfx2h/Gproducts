package com.Bcm.Controller;

import com.Bcm.Model.POPlan;
import com.Bcm.Service.Srvc.POPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Qualifier
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/poplan")
public class POPlanController {

    @Autowired
    POPlanService poPlanService;

    @GetMapping("/listPoPlans")
    public List<POPlan> read(){
        return poPlanService.read();
    }

    @PostMapping("/post")
    public POPlan create(@RequestBody POPlan poPlan){
        return poPlanService.create(poPlan);
    }

    @PutMapping("/post/{PO_ID}")
    public POPlan update (@PathVariable int PO_ID, @RequestBody POPlan poPlan){
        return poPlanService.update(PO_ID, poPlan);
    }

    @DeleteMapping("/{PO_ID}")
    public String delete(@PathVariable int PO_ID){
        return poPlanService.delete(PO_ID);
    }
    @GetMapping("/getById/{PO_ID}")
    public ResponseEntity<POPlan> getById(@PathVariable int PO_ID) {
        try {
            POPlan foundPlan = poPlanService.findById(PO_ID);
            return ResponseEntity.ok(foundPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/searchByKeyword")
    public List<POPlan> searchByKeyword(@RequestParam String name) {
        return poPlanService.searchByKeyword(name);
    }

}
