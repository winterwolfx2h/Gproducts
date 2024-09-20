package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.ChannelAlreadyExistsException;
import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ChannelService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {

  final ChannelRepository channelRepository;
  final ProductOfferingService productOfferingService;
  private static final String CID = "Channel with ID ";
  private static final String invArg = "Invalid argument provided for finding Channel";

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
      if (!existingChannel.getName().equals(newName) && channelRepository.existsByName(newName)) {
        throw new ChannelAlreadyExistsException("Channel with name '" + newName + "' already exists.");
      }

      existingChannel.setName(newName);
      existingChannel.setDescription(updatedChannel.getDescription());

      return channelRepository.save(existingChannel);
    } else {
      throw new ResourceNotFoundException(CID + channelCode + " not found.");
    }
  }

  @Override
  public String delete(int channelCode) {
    try {
      channelRepository.deleteById(channelCode);
      return (CID + channelCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Channel");
    }
  }

  @Override
  public Channel findById(int channelCode) {
    try {
      Optional<Channel> optionalChannel = channelRepository.findById(channelCode);
      return optionalChannel.orElseThrow(() -> new RuntimeException(CID + channelCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
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
      return optionalChannel.orElseThrow(() -> new RuntimeException(CID + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean findByNameexist(String name) {
    try {
      Optional<Channel> optionalChannel = channelRepository.findByName(name);
      return optionalChannel.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean existsById(int channelCode) {
    return channelRepository.existsById(channelCode);
  }
}
