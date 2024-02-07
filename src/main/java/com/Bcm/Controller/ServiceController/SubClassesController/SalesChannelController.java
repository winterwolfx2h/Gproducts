package com.Bcm.Controller.ServiceController.SubClassesController;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.SalesChannelService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<SalesChannel> createSalesChannel(@RequestBody SalesChannel SalesChannel) {
        SalesChannel createdSalesChannel = salesChannelService.create(SalesChannel);
        return ResponseEntity.ok(createdSalesChannel);
    }

    @GetMapping
    public ResponseEntity<List<SalesChannel>> getAllSalesChannels() {
        List<SalesChannel> SalesChannels = salesChannelService.read();
        return ResponseEntity.ok(SalesChannels);
    }

    @GetMapping("/{SC_code}")
    public ResponseEntity<SalesChannel> getSalesChannelById(@PathVariable("SC_code") int SC_code) {
        SalesChannel SalesChannel = salesChannelService.findById(SC_code);
        return ResponseEntity.ok(SalesChannel);
    }

    @PutMapping("/{SC_code}")
    public ResponseEntity<SalesChannel> updateSalesChannel(
            @PathVariable("SC_code") int SC_code,
            @RequestBody SalesChannel updatedSalesChannel) {

        SalesChannel updatedGroup = salesChannelService.update(SC_code, updatedSalesChannel);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{SC_code}")
    public ResponseEntity<String> deleteSalesChannel(@PathVariable("SC_code") int SC_code) {
        String resultMessage = salesChannelService.delete(SC_code);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalesChannel>> searchSalesChannelsByKeyword(@RequestParam("name") String name) {
        List<SalesChannel> searchResults = salesChannelService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
