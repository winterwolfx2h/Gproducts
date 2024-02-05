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
    public ResponseEntity<TimeZoneType> createTimeZoneType(@RequestBody TimeZoneType TimeZoneType) {
        TimeZoneType createdTimeZoneType = timeZoneTypeService.create(TimeZoneType);
        return ResponseEntity.ok(createdTimeZoneType);
    }

    @GetMapping
    public ResponseEntity<List<TimeZoneType>> getAllTimeZoneTypes() {
        List<TimeZoneType> TimeZoneTypes = timeZoneTypeService.read();
        return ResponseEntity.ok(TimeZoneTypes);
    }

    @GetMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<TimeZoneType> getTimeZoneTypeById(@PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode) {
        TimeZoneType TimeZoneType = timeZoneTypeService.findById(po_TimeZoneTypeCode);
        return ResponseEntity.ok(TimeZoneType);
    }

    @PutMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<TimeZoneType> updateTimeZoneType(
            @PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode,
            @RequestBody TimeZoneType updatedTimeZoneType) {

        TimeZoneType updatedGroup = timeZoneTypeService.update(po_TimeZoneTypeCode, updatedTimeZoneType);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{po_TimeZoneTypeCode}")
    public ResponseEntity<String> deleteTimeZoneType(@PathVariable("po_TimeZoneTypeCode") int po_TimeZoneTypeCode) {
        String resultMessage = timeZoneTypeService.delete(po_TimeZoneTypeCode);
        return ResponseEntity.ok(resultMessage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TimeZoneType>> searchTimeZoneTypesByKeyword(@RequestParam("name") String name) {
        List<TimeZoneType> searchResults = timeZoneTypeService.searchByKeyword(name);
        return ResponseEntity.ok(searchResults);
    }
}
