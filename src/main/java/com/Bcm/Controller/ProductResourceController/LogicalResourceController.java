package com.Bcm.Controller.ProductResourceController;

import com.Bcm.Exception.DuplicateResourceException;
import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ProductResourceABE.LogicalResource;
import com.Bcm.Service.Srvc.ProductResourceSrvc.LogicalResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Tag(name = "Logical Resource Controller", description = "All of the Logical Resource's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/logicalResource")
public class LogicalResourceController {

  final LogicalResourceService logicalResourceService;
  private static final String error = "An unexpected error occurred";

  @PostMapping("/addLogicalResource")
  public ResponseEntity<?> createLogicalResource(@RequestBody LogicalResource logicalResource) {
    try {
      if (logicalResource.getStatus() == null || logicalResource.getStatus().isEmpty()) {
        logicalResource.setStatus("Working state");
      }
      LogicalResource createdLogicalResource = logicalResourceService.create(logicalResource);
      return ResponseEntity.ok(createdLogicalResource);
    } catch (DuplicateResourceException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }

  @GetMapping("/listLogicalResources")
  public ResponseEntity<?> getAllLogicalResources() {
    try {
      List<LogicalResource> LogicalResources = logicalResourceService.read();
      return ResponseEntity.ok(LogicalResources);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/{logResourceId}")
  public ResponseEntity<?> getLogicalResourceById(@PathVariable("logResourceId") int logResourceId) {
    try {
      LogicalResource LogicalResource = logicalResourceService.findById(logResourceId);
      return ResponseEntity.ok(LogicalResource);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @PutMapping("/{logResourceId}")
  public ResponseEntity<?> updateLogicalResource(
      @PathVariable("logResourceId") int logResourceId, @RequestBody LogicalResource updatedLogicalResource) {
    try {
      LogicalResource updatedGroup = logicalResourceService.update(logResourceId, updatedLogicalResource);
      return ResponseEntity.ok(updatedGroup);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @DeleteMapping("/{logResourceId}")
  public ResponseEntity<?> deleteLogicalResource(@PathVariable("logResourceId") int logResourceId) {
    try {
      String resultMessage = logicalResourceService.delete(logResourceId);
      return ResponseEntity.ok(resultMessage);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<LogicalResource>> searchLogicalResourceByKeyword(
      @RequestParam("logicalResourceType") String logicalResourceType) {
    List<LogicalResource> searchResults = logicalResourceService.searchByKeyword(logicalResourceType);
    return ResponseEntity.ok(searchResults);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
