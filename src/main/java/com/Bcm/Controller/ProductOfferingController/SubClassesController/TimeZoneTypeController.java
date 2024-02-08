package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Model.ProductOfferingABE.SubClasses.TimeZoneType;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TimeZoneTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/timeZoneType")
public class TimeZoneTypeController {

    @Autowired
    private TimeZoneTypeService timeZoneTypeService;

    @PostMapping
    public ResponseEntity<TimeZoneType> createTimeZoneType(@RequestBody TimeZoneType timeZoneType) {
        try {
            TimeZoneType createdTimeZoneType = timeZoneTypeService.create(timeZoneType);
            return ResponseEntity.ok(createdTimeZoneType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeZoneType>> getAllTimeZoneTypes() {
        try {
            List<TimeZoneType> timeZoneTypes = timeZoneTypeService.read();
            return ResponseEntity.ok(timeZoneTypes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<TimeZoneType> getTimeZoneTypeById(@PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode) {
        try {
            TimeZoneType timeZoneType = timeZoneTypeService.findById(po_TimeZoneTypeCode);
            return ResponseEntity.ok(timeZoneType);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<TimeZoneType> updateTimeZoneType(
            @PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode,
            @RequestBody TimeZoneType updatedTimeZoneType) {

        try {
            TimeZoneType updatedTimeZoneTypeResult = timeZoneTypeService.update(po_TimeZoneTypeCode, updatedTimeZoneType);
            return ResponseEntity.ok(updatedTimeZoneTypeResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<String> deleteTimeZoneType(@PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode) {
        try {
            String resultMessage = timeZoneTypeService.delete(po_TimeZoneTypeCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TimeZoneType>> searchTimeZoneTypesByKeyword(@RequestParam("name") String name) {
        try {
            List<TimeZoneType> searchResults = timeZoneTypeService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
