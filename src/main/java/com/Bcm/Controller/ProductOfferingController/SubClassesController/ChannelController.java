package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.ChannelAlreadyExistsException;
import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Channel Controller", description = "All of the Channel's methods")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {

    final ChannelService channelService;
    final EligibilityService eligibilityService;

    @PostMapping("/addChannels")
    public ResponseEntity<?> createChannels(@RequestBody Channel channel) {
        try {
            Channel createdChannel = channelService.create(channel);
            return ResponseEntity.ok(createdChannel);
        } catch (FamilyAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/listChannel")
    public ResponseEntity<List<Channel>> getAllFamilies() {
        try {
            List<Channel> families = channelService.read();
            return ResponseEntity.ok(families);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{channelCode}")
    public ResponseEntity<Channel> getChannelById(@PathVariable("channelCode") int channelCode) {
        try {
            Channel Channel = channelService.findById(channelCode);
            return ResponseEntity.ok(Channel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{channelCode}")
    public ResponseEntity<?> updateChannel(
            @PathVariable("channelCode") int channelCode, @RequestBody Channel updatedChannel) {
        try {
            Channel updatedGroup = channelService.update(channelCode, updatedChannel);
            return ResponseEntity.ok(updatedGroup);
        } catch (ChannelAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{channelCode}")
    public ResponseEntity<String> deleteChannel(@PathVariable("channelCode") int channelCode) {
        try {
            String resultMessage = channelService.delete(channelCode);
            return ResponseEntity.ok(resultMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Channel>> searchFamiliesByKeyword(@RequestParam("name") String name) {
        try {
            List<Channel> searchResults = channelService.searchByKeyword(name);
            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
