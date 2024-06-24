package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.ChannelAlreadyExistsException;
import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {

    final ChannelRepository channelRepository;
    final ProductOfferingService productOfferingService;

    @Override
    public Channel create(Channel channel) {
        try {
            if (findByNameexist(channel.getName())) {
                throw new ChannelAlreadyExistsException("Channel with the same name already exists");
            }
            return channelRepository.save(channel);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating Channel", e);
        }
    }

    @Override
    public List<Channel> read() {
        try {
            return channelRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public Channel update(int channelCode, Channel updatedChannel) {
        Optional<Channel> existingChannelOptional = channelRepository.findById(channelCode);
        if (existingChannelOptional.isPresent()) {
            Channel existingChannel = existingChannelOptional.get();

            String newName = updatedChannel.getName();
            // Check if there's another Channel with the same name
            if (!existingChannel.getName().equals(newName) && channelRepository.existsByName(newName)) {
                throw new ChannelAlreadyExistsException("Channel with name '" + newName + "' already exists.");
            }

            existingChannel.setName(newName);
            existingChannel.setDescription(updatedChannel.getDescription());
            return channelRepository.save(existingChannel);
        } else {
            throw new ResourceNotFoundException("Channel with ID " + channelCode + " not found.");
        }
    }

    @Override
    public String delete(int channelCode) {
        try {
            Channel channel = findById(channelCode);
            channelRepository.deleteById(channelCode);
            // updateProductOfferingsWithDeletedChannel(channel.getName());
            return ("Channel with ID " + channelCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Channel");
        }
    }

    @Override
    public Channel findById(int channelCode) {
        try {
            Optional<Channel> optionalChannel = channelRepository.findById(channelCode);
            return optionalChannel.orElseThrow(() -> new RuntimeException("Channel with ID " + channelCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Channel");
        }
    }

    @Override
    public List<Channel> searchByKeyword(String name) {
        try {
            return channelRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Channel by keyword");
        }
    }

    @Override
    public Channel findByName(String name) {
        try {
            Optional<Channel> optionalChannel = channelRepository.findByName(name);
            return optionalChannel.orElseThrow(() -> new RuntimeException("Channel with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Channel");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<Channel> optionalChannel = channelRepository.findByName(name);
            return optionalChannel.isPresent(); // Return true if Channel exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Channel");
        }
    }

    @Override
    public boolean existsById(int channelCode) {
        return channelRepository.existsById(channelCode);
    }
}
