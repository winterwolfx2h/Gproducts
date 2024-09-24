package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.MarketAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.*;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Market Controller", description = "All of the Markets methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {

  final MarketService marketService;

  @ApiOperation(value = "Create a new Market", response = MarketResponseDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Market created successfully", response = MarketResponseDTO.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  @PostMapping("/createMarket")
  public ResponseEntity<MarketResponseDTO> createMarket(@RequestBody MarketRequestDTO marketRequestDTO) {
    try {
      MarketResponseDTO createdMarket = marketService.createOrUpdateMarket(marketRequestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdMarket);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/listMarket")
  public ResponseEntity<List<MarketResponseDTO>> getAllMarkets() {
    try {
      List<MarketResponseDTO> markets = marketService.getAllMarkets();
      return ResponseEntity.ok(markets);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{po_MarketCode}")
  public ResponseEntity<Market> getMarketById(@PathVariable("po_MarketCode") int po_MarketCode) {
    try {
      Market market = marketService.findById(po_MarketCode);
      return ResponseEntity.ok(market);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("/{po_MarketCode}")
  public ResponseEntity<?> updateMarket(
      @PathVariable("po_MarketCode") int po_MarketCode, @RequestBody MarketRequestDTOUpdate marketRequestDTOUpdate) {
    try {
      MarketResponseDTO updatedMarket = marketService.update(po_MarketCode, marketRequestDTOUpdate);
      return ResponseEntity.ok(updatedMarket);
    } catch (MarketAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{po_MarketCode}")
  public ResponseEntity<String> deleteMarket(@PathVariable("po_MarketCode") int po_MarketCode) {
    try {
      String resultMessage = marketService.delete(po_MarketCode);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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

  @GetMapping("/listSubMarkets")
  public ResponseEntity<List<SubMarketListResponseDTO>> getAllSubMarkets() {
    try {
      List<SubMarket> subMarkets = marketService.readSubMarkets();
      List<SubMarketListResponseDTO> subMarketResponseDTOs = new ArrayList<>();
      for (SubMarket subMarket : subMarkets) {
        SubMarketListResponseDTO subMarketListResponseDTO =
            new SubMarketListResponseDTO(
                subMarket.getPo_SubMarketCode(),
                subMarket.getSubMarketName(),
                subMarket.getSubMarketDescription(),
                subMarket.getMarket() != null ? subMarket.getMarket().getPo_MarketCode() : null);
        subMarketResponseDTOs.add(subMarketListResponseDTO);
      }
      return ResponseEntity.ok(subMarketResponseDTOs);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping("/submarket/{po_SubMarketCode}")
  public ResponseEntity<String> deleteSubMarket(@PathVariable int po_SubMarketCode) {
    try {
      marketService.deleteSubMarket(po_SubMarketCode);
      return ResponseEntity.ok("SubMarket with ID " + po_SubMarketCode + " was successfully deleted");
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @PutMapping("/unlinkSubMarket")
  public ResponseEntity<String> unlinkSubMarketFromMarket(@RequestParam int MarketId, @RequestParam int subMarketId) {
    try {
      marketService.unlinkSubMarketFromMarket(MarketId, subMarketId);
      return ResponseEntity.ok("SubMarket with ID " + subMarketId + " unlinked from Market with ID " + MarketId);
    } catch (ResourceNotFoundException | IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }
}
