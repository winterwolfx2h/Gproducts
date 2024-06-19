package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.Methods;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.MethodsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Methods Controller", description = "All of the Method's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/Methods")
@RequiredArgsConstructor
public class MethodsController {

  final MethodsService methodsService;

  @PostMapping("/addMethods")
  public ResponseEntity<?> createMethod(@RequestBody Methods methods) {
    try {
      Methods createdMethods = methodsService.create(methods);
      return ResponseEntity.ok(createdMethods);
    } catch (MethodsAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }

  @GetMapping("/listMethods")
  public ResponseEntity<List<Methods>> getAllMethods() {
    try {
      List<Methods> methods = methodsService.read();
      return ResponseEntity.ok(methods);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/{method_Id}")
  public ResponseEntity<Methods> getMethodsById(@PathVariable("method_Id") int method_Id) {
    try {
      Methods methods = methodsService.findById(method_Id);
      return ResponseEntity.ok(methods);
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(null);
    }
  }

  @PutMapping("/{method_Id}")
  public ResponseEntity<?> updateMethods(
      @PathVariable("method_Id") int method_Id, @RequestBody Methods updatedMethods) {
    try {
      Methods updatedGroup = methodsService.update(method_Id, updatedMethods);
      return ResponseEntity.ok(updatedGroup);
    } catch (MethodsAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
  }

  @DeleteMapping("/{method_Id}")
  public ResponseEntity<String> deleteMethods(@PathVariable("method_Id") int method_Id) {
    try {
      String resultMessage = methodsService.delete(method_Id);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Methods>> searchMethodsByKeyword(@RequestParam("name") String name) {
    try {
      List<Methods> searchResults = methodsService.searchByKeyword(name);
      return ResponseEntity.ok(searchResults);
    } catch (RuntimeException e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
