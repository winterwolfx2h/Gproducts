package com.Bcm.Controller.ProductOfferingController.SubClassesController;

import com.Bcm.Exception.ChannelAlreadyExistsException;
import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    public ResponseEntity<?> createChannels(@RequestBody List<Channel> channels) {
//        try {
//            List<String> createdChannelNames = new ArrayList<>(); // List to hold channel names
//
//            // Create each channel and add its name to the list of created channel names
//            for (Channel channel : channels) {
//                Channel createdChannel = channelService.create(channel);
//                createdChannelNames.add(createdChannel.getName());
//            }
//
//            // Update all existing Eligibility instances to associate them with the created channels
//            List<Eligibility> eligibilities = eligibilityService.read();
//            for (Eligibility eligibility : eligibilities) {
//                eligibility.getChannels().addAll(createdChannelNames);
//                eligibilityService.update(eligibility.getEligibilityId(), eligibility);
//            }
//
//            return ResponseEntity.ok(createdChannelNames);
//        } catch (ChannelAlreadyExistsException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (InvalidInputException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (RuntimeException e) {
//            // Log the stack trace for debugging purposes
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
//        }
//    }


    @GetMapping("/listChannel")
    public ResponseEntity<List<Channel>> getAllFamilies() {
        try {
            List<Channel> families = channelService.read();
            return ResponseEntity.ok(families);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{po_ChannelCode}")
    public ResponseEntity<Channel> getChannelById(@PathVariable("po_ChannelCode") int po_ChannelCode) {
        try {
            Channel Channel = channelService.findById(po_ChannelCode);
            return ResponseEntity.ok(Channel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{po_ChannelCode}")
    public ResponseEntity<?> updateChannel(
            @PathVariable("po_ChannelCode") int po_ChannelCode,
            @RequestBody Channel updatedChannel) {
        try {
            Channel updatedGroup = channelService.update(po_ChannelCode, updatedChannel);
            return ResponseEntity.ok(updatedGroup);
        } catch (ChannelAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{po_ChannelCode}")
    public ResponseEntity<String> deleteChannel(@PathVariable("po_ChannelCode") int po_ChannelCode) {
        try {
            String resultMessage = channelService.delete(po_ChannelCode);
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

