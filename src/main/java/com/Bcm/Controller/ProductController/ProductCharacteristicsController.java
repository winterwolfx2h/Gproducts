package com.Bcm.Controller.ProductController;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.Product.ProductCharacteristics;
import com.Bcm.Service.Srvc.ProductSrvc.PCharacteristicsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Characteristic Controller", description = "All of the P-Characteristic's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PCharacteristic")
public class ProductCharacteristicsController {

  final PCharacteristicsService pCharacteristicsService;

  @GetMapping("/listPCharacteristics")
  @Cacheable(value = "CharacteristicsCache")
  public List<ProductCharacteristics> read() {
    return pCharacteristicsService.read();
  }

  @PostMapping("/add")
  @CacheEvict(value = "CharacteristicsCache", allEntries = true)
  public ResponseEntity<?> create(@RequestBody List<ProductCharacteristics> PCharacteristicsList) {
    try {
      List<ProductCharacteristics> createdPCharacteristicsList = new ArrayList<>();

      for (ProductCharacteristics pCharacteristic : PCharacteristicsList) {
        if (pCharacteristic == null) {
          return ResponseEntity.badRequest()
              .body("Product Characteristic is missing for one or more ProductCharacteristics.");
        }

        if (pCharacteristic.getValueDescription() == null) {
          pCharacteristic.setValueDescription(new ArrayList<>());
        }

        for (ProductCharacteristics.CharacteristicValueDes valueDescription : pCharacteristic.getValueDescription()) {
          if (valueDescription.getDescription() == null) {
            valueDescription.setDescription("Default Description");
          }
        }

        ProductCharacteristics createdPChar = pCharacteristicsService.create(pCharacteristic);
        createdPCharacteristicsList.add(createdPChar);
      }

      return ResponseEntity.ok(createdPCharacteristicsList);
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
  }

  @GetMapping("/searchByProductId")
  public List<ProductCharacteristics> searchByProductID(@RequestParam Integer productId) {
    List<ProductCharacteristics> characteristicsResponse = new ArrayList<>();
    for (ProductCharacteristics pc : pCharacteristicsService.read()) {
      if (pc.getProductId() == productId) {
        characteristicsResponse.add(pc);
      }
    }

    return characteristicsResponse;
  }

  @PutMapping("/updatePCharacteristics/{pCharacteristic_code}")
  @CacheEvict(value = "CharacteristicsCache", allEntries = true)
  public ResponseEntity<?> update(
      @PathVariable int pCharacteristic_code, @RequestBody ProductCharacteristics ProductCharacteristics) {
    try {
      ProductCharacteristics updatedProductChar =
          pCharacteristicsService.update(pCharacteristic_code, ProductCharacteristics);
      return ResponseEntity.ok(updatedProductChar);
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{pCharacteristic_code}")
  @CacheEvict(value = "CharacteristicsCache", allEntries = true)
  public String delete(@PathVariable int pCharacteristic_code) {
    return pCharacteristicsService.delete(pCharacteristic_code);
  }

  @GetMapping("/getById/{pCharacteristic_code}")
  public ResponseEntity<ProductCharacteristics> getById(@PathVariable int pCharacteristic_code) {
    try {
      ProductCharacteristics foundProductChar = pCharacteristicsService.findById(pCharacteristic_code);
      return ResponseEntity.ok(foundProductChar);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
