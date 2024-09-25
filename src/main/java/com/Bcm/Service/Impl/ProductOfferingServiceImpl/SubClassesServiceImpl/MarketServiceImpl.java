package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.MarketAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.*;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.MarketRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubMarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

  final MarketRepository marketRepository;
  final ProductOfferingService productOfferingService;
  private final SubMarketRepository subMarketRepository;
  private static final String SID = "SubMarket with ID ";
  private static final String MID = "Market with ID ";
  private static final String NTF = " not found.";
  private static final String invArg = "Invalid argument provided for finding Market";

  @Transactional
  @Override
  public MarketResponseDTO createOrUpdateMarket(MarketRequestDTO marketRequestDTO) {
    Optional<Market> existingMarketOptional = marketRepository.findByName(marketRequestDTO.getName());
    Market market;
    if (existingMarketOptional.isPresent()) {
      market = existingMarketOptional.get();
    } else {
      market = new Market();
      market.setName(marketRequestDTO.getName());
      market.setDescription(marketRequestDTO.getDescription());
    }

    for (SubMarketRequestDTO subMarketDTO : marketRequestDTO.getSubMarkets()) {
      SubMarket subMarket =
          subMarketRepository
              .findBySubMarketName(subMarketDTO.getSubMarketName())
              .orElseGet(
                  () -> {
                    SubMarket newSubMarket = new SubMarket();
                    newSubMarket.setSubMarketName(subMarketDTO.getSubMarketName());
                    newSubMarket.setSubMarketDescription(subMarketDTO.getSubMarketDescription());
                    return newSubMarket;
                  });

      if (!market.getSubMarkets().contains(subMarket)) {
        market.addSubMarket(subMarket);
      }
    }

    market = marketRepository.save(market);

    List<SubMarketResponseDTO> subMarketResponseDTOs = new ArrayList<>();
    for (SubMarket subMkt : market.getSubMarkets()) {
      SubMarketResponseDTO subMarketResponseDTO =
          new SubMarketResponseDTO(
              subMkt.getPo_SubMarketCode(), subMkt.getSubMarketName(), subMkt.getSubMarketDescription());
      subMarketResponseDTOs.add(subMarketResponseDTO);
    }

    return new MarketResponseDTO(
        market.getPo_MarketCode(), market.getName(), market.getDescription(), subMarketResponseDTOs);
  }

  @Override
  public List<Market> read() {
    try {
      return marketRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving Markets");
    }
  }

  @Transactional
  @Override
  public MarketResponseDTO update(int po_MarketCode, MarketRequestDTOUpdate marketRequestDTOUpdate) {
    Market existingMarket = findExistingMarket(po_MarketCode);

    validateMarketName(existingMarket, marketRequestDTOUpdate.getName());

    updateMarketDetails(existingMarket, marketRequestDTOUpdate);

    List<SubMarket> updatedSubMarkets = processSubMarkets(marketRequestDTOUpdate, existingMarket);

    existingMarket.getSubMarkets().clear();
    existingMarket.getSubMarkets().addAll(updatedSubMarkets);

    existingMarket = marketRepository.save(existingMarket);

    return buildMarketResponseDTO(existingMarket);
  }

  private Market findExistingMarket(int po_MarketCode) {
    return marketRepository
        .findById(po_MarketCode)
        .orElseThrow(() -> new ResourceNotFoundException(MID + po_MarketCode + NTF));
  }

  private void validateMarketName(Market existingMarket, String newName) {
    if (!existingMarket.getName().equals(newName) && marketRepository.existsByName(newName)) {
      throw new MarketAlreadyExistsException("Market with name '" + newName + "' already exists.");
    }
  }

  private void updateMarketDetails(Market existingMarket, MarketRequestDTOUpdate marketRequestDTOUpdate) {
    existingMarket.setName(marketRequestDTOUpdate.getName());
    existingMarket.setDescription(marketRequestDTOUpdate.getDescription());
  }

  private List<SubMarket> processSubMarkets(MarketRequestDTOUpdate marketRequestDTOUpdate, Market existingMarket) {
    Map<Integer, SubMarket> existingSubMarketsMap = mapExistingSubMarkets(existingMarket);

    List<SubMarket> updatedSubMarkets = new ArrayList<>();
    for (SubMarketRequestDTO subMarketRequestDTO : marketRequestDTOUpdate.getSubMarkets()) {
      if (subMarketRequestDTO.getPo_SubMarketCode() != null) {
        SubMarket subMarket = updateExistingSubMarket(subMarketRequestDTO, existingSubMarketsMap);
        updatedSubMarkets.add(subMarket);
      } else {
        SubMarket newSubMarket = createNewSubMarketIfNotExists(subMarketRequestDTO, updatedSubMarkets, existingMarket);
        if (newSubMarket != null) {
          updatedSubMarkets.add(newSubMarket);
        }
      }
    }

    addRemainingSubMarkets(existingMarket, updatedSubMarkets);
    return updatedSubMarkets;
  }

  private Map<Integer, SubMarket> mapExistingSubMarkets(Market existingMarket) {
    return existingMarket.getSubMarkets().stream()
        .collect(Collectors.toMap(SubMarket::getPo_SubMarketCode, subMarket -> subMarket));
  }

  private SubMarket updateExistingSubMarket(
      SubMarketRequestDTO subMarketRequestDTO, Map<Integer, SubMarket> existingSubMarketsMap) {
    SubMarket subMarket = existingSubMarketsMap.get(subMarketRequestDTO.getPo_SubMarketCode());
    if (subMarket == null) {
      throw new ResourceNotFoundException(SID + subMarketRequestDTO.getPo_SubMarketCode() + NTF);
    }
    subMarket.setSubMarketName(subMarketRequestDTO.getSubMarketName());
    subMarket.setSubMarketDescription(subMarketRequestDTO.getSubMarketDescription());
    return subMarket;
  }

  private SubMarket createNewSubMarketIfNotExists(
      SubMarketRequestDTO subMarketRequestDTO, List<SubMarket> updatedSubMarkets, Market existingMarket) {
    boolean existsInUpdatedList =
        updatedSubMarkets.stream().anyMatch(sm -> sm.getSubMarketName().equals(subMarketRequestDTO.getSubMarketName()));

    if (!existsInUpdatedList) {
      SubMarket newSubMarket = new SubMarket();
      newSubMarket.setSubMarketName(subMarketRequestDTO.getSubMarketName());
      newSubMarket.setSubMarketDescription(subMarketRequestDTO.getSubMarketDescription());
      newSubMarket.setMarket(existingMarket);
      return newSubMarket;
    }
    return null;
  }

  private void addRemainingSubMarkets(Market existingMarket, List<SubMarket> updatedSubMarkets) {
    existingMarket.getSubMarkets().stream()
        .filter(subMarket -> !updatedSubMarkets.contains(subMarket))
        .forEach(updatedSubMarkets::add);
  }

  private MarketResponseDTO buildMarketResponseDTO(Market existingMarket) {
    List<SubMarketResponseDTO> subMarketResponseDTOs = new ArrayList<>();
    for (SubMarket subMkt : existingMarket.getSubMarkets()) {
      SubMarketResponseDTO subMarketResponseDTO =
          new SubMarketResponseDTO(
              subMkt.getPo_SubMarketCode(), subMkt.getSubMarketName(), subMkt.getSubMarketDescription());
      subMarketResponseDTOs.add(subMarketResponseDTO);
    }

    return new MarketResponseDTO(
        existingMarket.getPo_MarketCode(),
        existingMarket.getName(),
        existingMarket.getDescription(),
        subMarketResponseDTOs);
  }

  @Override
  public String delete(int po_MarketCode) {
    try {

      marketRepository.deleteById(po_MarketCode);
      return (MID + po_MarketCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Market");
    }
  }

  @Override
  public Market findById(int po_MarketCode) {
    try {
      Optional<Market> optionalMarket = marketRepository.findById(po_MarketCode);
      return optionalMarket.orElseThrow(() -> new RuntimeException(MID + po_MarketCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public List<Market> searchByKeyword(String name) {
    try {
      return marketRepository.searchByKeyword(name);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for searching Market by keyword");
    }
  }

  @Override
  public Market findByName(String name) {
    try {
      Optional<Market> optionalMarket = marketRepository.findByName(name);
      return optionalMarket.orElseThrow(() -> new RuntimeException(MID + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean findByNameexist(String name) {
    try {
      Optional<Market> optionalMarket = marketRepository.findByName(name);
      return optionalMarket.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean existsById(int po_MarketCode) {
    return marketRepository.existsById(po_MarketCode);
  }

  @Override
  public List<MarketResponseDTO> getAllMarkets() {
    List<Market> markets = marketRepository.findAll();
    List<MarketResponseDTO> list = new ArrayList<>();
    for (Market market1 : markets) {
      List<SubMarketResponseDTO> subMarketResponseDTOs = new ArrayList<>();
      for (SubMarket subMkt : market1.getSubMarkets()) {
        SubMarketResponseDTO subMarketResponseDTO =
            new SubMarketResponseDTO(
                subMkt.getPo_SubMarketCode(), subMkt.getSubMarketName(), subMkt.getSubMarketDescription());
        subMarketResponseDTOs.add(subMarketResponseDTO);
      }
      MarketResponseDTO apply =
          new MarketResponseDTO(
              market1.getPo_MarketCode(), market1.getName(), market1.getDescription(), subMarketResponseDTOs);
      list.add(apply);
    }
    return list;
  }

  @Override
  public List<SubMarket> readSubMarkets() {
    try {
      return subMarketRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving SubMarkets", e);
    }
  }

  @Override
  public boolean findBySubMarketNameExist(String subMarketName) {
    try {
      Optional<SubMarket> optionalSubMarket = subMarketRepository.findBySubMarketName(subMarketName);
      return optionalSubMarket.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding SubMarket", e);
    }
  }

  @Override
  @Transactional
  public void deleteSubMarket(int po_SubMarketCode) {
    Optional<SubMarket> subMarketOptional = subMarketRepository.findById(po_SubMarketCode);
    if (subMarketOptional.isPresent()) {
      subMarketRepository.delete(subMarketOptional.get());
    } else {
      throw new ResourceNotFoundException(SID + po_SubMarketCode + NTF);
    }
  }

  @Transactional
  @Override
  public void unlinkSubMarketFromMarket(int MarketId, int subMarketId) {
    Optional<Market> MarketOptional = marketRepository.findById(MarketId);
    if (MarketOptional.isEmpty()) {
      throw new ResourceNotFoundException(MID + MarketId + NTF);
    }

    Market Market = MarketOptional.get();
    Optional<SubMarket> subMarketOptional = subMarketRepository.findById(subMarketId);
    if (subMarketOptional.isEmpty()) {
      throw new ResourceNotFoundException(SID + subMarketId + NTF);
    }

    SubMarket subMarket = subMarketOptional.get();
    if (!Market.getSubMarkets().contains(subMarket)) {
      throw new IllegalArgumentException(SID + subMarketId + " is not linked to Market with ID " + MarketId);
    }

    Market.removeSubMarket(subMarket);
    subMarket.setMarket(null);

    marketRepository.save(Market);
    subMarketRepository.save(subMarket);
  }
}
