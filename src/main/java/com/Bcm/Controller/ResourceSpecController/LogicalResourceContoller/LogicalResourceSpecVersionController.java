package com.Bcm.Controller.ResourceSpecController.LogicalResourceContoller;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResourceSpecVersion;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceSpecVersionService.LogicalResourceSpecVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/LogicalResourceSpecVersion")
public class LogicalResourceSpecVersionController {

    @Autowired
    LogicalResourceSpecVersionService LogicalResourceSpecVersionService;

    @PostMapping("/addProdOff")
    public ResponseEntity<LogicalResourceSpecVersion> createLogicalResourceSpecVersion(@RequestBody LogicalResourceSpecVersion LogicalResourceSpecVersion) {
        LogicalResourceSpecVersion createdLogicalResourceSpecVersion = LogicalResourceSpecVersionService.create(LogicalResourceSpecVersion);
        return ResponseEntity.ok(createdLogicalResourceSpecVersion);
    }

    @GetMapping
    public ResponseEntity<List<LogicalResourceSpecVersion>> getAllLogicalResourceSpecVersions() {
        List<LogicalResourceSpecVersion> LogicalResourceSpecVersions = LogicalResourceSpecVersionService.read();
        return ResponseEntity.ok(LogicalResourceSpecVersions);
    }

    @GetMapping("/{LRSVID}")
    public ResponseEntity<LogicalResourceSpecVersion> getLogicalResourceSpecVersionById(@PathVariable("LRSVID") int LRSVID) {
        LogicalResourceSpecVersion LogicalResourceSpecVersion = LogicalResourceSpecVersionService.findById(LRSVID);
        return ResponseEntity.ok(LogicalResourceSpecVersion);
    }

    @PutMapping("/{LRSVID}")

    public ResponseEntity<LogicalResourceSpecVersion> updateLogicalResourceSpecVersion(
            @PathVariable("LRSVID") int LRSVID,
            @RequestBody LogicalResourceSpecVersion updatedLogicalResourceSpecVersion) {

        LogicalResourceSpecVersion updatedProduct = LogicalResourceSpecVersionService.update(LRSVID, updatedLogicalResourceSpecVersion);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{LRSVID}")
    public ResponseEntity<String> deleteLogicalResourceSpecVersion(@PathVariable("LRSVID") int LRSVID) {
        String resultMessage = LogicalResourceSpecVersionService.delete(LRSVID);
        return ResponseEntity.ok(resultMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
