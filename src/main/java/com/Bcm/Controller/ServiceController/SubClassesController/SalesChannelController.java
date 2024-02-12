package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.SalesChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/SalesChannel")
public class SalesChannelController {

    @Autowired
    private SalesChannelService salesChannelService;

    @PostMapping
    public ResponseEntity<?> createSalesChannel(@RequestBody SalesChannel salesChannel) {
        try {
            SalesChannel createdSalesChannel = salesChannelService.create(salesChannel);
            return ResponseEntity.ok(createdSalesChannel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSalesChannels() {
        try {
            List<SalesChannel> salesChannels = salesChannelService.read();
            return ResponseEntity.ok(salesChannels);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{SC_code}")
    public ResponseEntity<?> getSalesChannelById(@PathVariable("SC_code") int SC_code) {
        try {
            SalesChannel salesChannel = salesChannelService.findById(SC_code);
            return ResponseEntity.ok(salesChannel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/{SC_code}")
    public ResponseEntity<?> updateSalesChannel(
            @PathVariable("SC_code") int SC_code,
            @RequestBody SalesChannel updatedSalesChannel) {
        try {
            SalesChannel updatedGroup = salesChannelService.update(SC_code, updatedSalesChannel);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{SC_code}")
    public ResponseEntity<?> deleteSalesChannel(@PathVariable("SC_code") int SC_code) {
        try {
            String resultMessage = salesChannelService.delete(SC_code);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSalesChannelsByKeyword(@RequestParam("name") String name) {
        try {
            List<SalesChannel> searchResults = salesChannelService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
