package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.MarketAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.*;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.MarketRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubMarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    final MarketRepository marketRepository;
    final ProductOfferingService productOfferingService;
    private final SubMarketRepository subMarketRepository;

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
            SubMarket subMarket = subMarketRepository.findBySubMarketName(subMarketDTO.getSubMarketName())
                    .orElseGet(() -> {
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

        List<SubMarketResponseDTO> subMarketResponseDTOs = market.getSubMarkets().stream()
                .map(subMkt -> new SubMarketResponseDTO(subMkt.getPo_SubMarketCode(),
                        subMkt.getSubMarketName(),
                        subMkt.getSubMarketDescription()))
                .collect(Collectors.toList());

        return new MarketResponseDTO(market.getPo_MarketCode(), market.getName(), market.getDescription(), subMarketResponseDTOs);
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
        Optional<Market> existingMarketOptional = marketRepository.findById(po_MarketCode);
        if (existingMarketOptional.isPresent()) {
            Market existingMarket = existingMarketOptional.get();

            // Check if there's another market with the same name
            String newName = marketRequestDTOUpdate.getName();
            if (!existingMarket.getName().equals(newName) && marketRepository.existsByName(newName)) {
                throw new MarketAlreadyExistsException("Market with name '" + newName + "' already exists.");
            }

            // Update market fields
            existingMarket.setName(newName);
            existingMarket.setDescription(marketRequestDTOUpdate.getDescription());

            // Map existing subMarkets to a map for quick lookup
            Map<Integer, SubMarket> existingSubMarketsMap = existingMarket.getSubMarkets().stream()
                    .collect(Collectors.toMap(SubMarket::getPo_SubMarketCode, subMarket -> subMarket));

            // Update or add subMarkets from the request
            List<SubMarket> updatedSubMarkets = new ArrayList<>();
            for (SubMarketRequestDTO subMarketRequestDTO : marketRequestDTOUpdate.getSubMarkets()) {
                if (subMarketRequestDTO.getPo_SubMarketCode() != null) {
                    // Check if subMarket exists in the existing subMarkets map
                    SubMarket subMarket = existingSubMarketsMap.get(subMarketRequestDTO.getPo_SubMarketCode());
                    if (subMarket == null) {
                        throw new ResourceNotFoundException("SubMarket with ID " + subMarketRequestDTO.getPo_SubMarketCode() + " not found.");
                    }
                    // Update existing subMarket name and description
                    subMarket.setSubMarketName(subMarketRequestDTO.getSubMarketName());
                    subMarket.setSubMarketDescription(subMarketRequestDTO.getSubMarketDescription());
                    updatedSubMarkets.add(subMarket);
                } else {
                    // Create new subMarket only if it doesn't already exist in the updated subMarkets list
                    boolean existsInUpdatedList = updatedSubMarkets.stream()
                            .anyMatch(sm -> sm.getSubMarketName().equals(subMarketRequestDTO.getSubMarketName()));
                    if (!existsInUpdatedList) {
                        SubMarket newSubMarket = new SubMarket();
                        newSubMarket.setSubMarketName(subMarketRequestDTO.getSubMarketName());
                        newSubMarket.setSubMarketDescription(subMarketRequestDTO.getSubMarketDescription());
                        newSubMarket.setMarket(existingMarket);
                        updatedSubMarkets.add(newSubMarket);
                    }
                }
            }

            // Add existing subMarkets not updated in the request
            existingMarket.getSubMarkets().stream()
                    .filter(subMarket -> !updatedSubMarkets.contains(subMarket))
                    .forEach(updatedSubMarkets::add);

            // Set updated subMarkets in the existing market
            existingMarket.getSubMarkets().clear();
            existingMarket.getSubMarkets().addAll(updatedSubMarkets);

            // Save the updated Market (will cascade to SubMarket if new)
            existingMarket = marketRepository.save(existingMarket);

            // Prepare response DTO
            List<SubMarketResponseDTO> subMarketResponseDTOs = existingMarket.getSubMarkets().stream()
                    .map(subMkt -> new SubMarketResponseDTO(subMkt.getPo_SubMarketCode(), subMkt.getSubMarketName(), subMkt.getSubMarketDescription()))
                    .collect(Collectors.toList());

            return new MarketResponseDTO(existingMarket.getPo_MarketCode(), existingMarket.getName(), existingMarket.getDescription(), subMarketResponseDTOs);
        } else {
            throw new ResourceNotFoundException("Market with ID " + po_MarketCode + " not found.");
        }
    }


    @Override
    public String delete(int po_MarketCode) {
        try {
            Market market = findById(po_MarketCode);
            marketRepository.deleteById(po_MarketCode);
            return ("Market with ID " + po_MarketCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Market");
        }
    }

    @Override
    public Market findById(int po_MarketCode) {
        try {
            Optional<Market> optionalMarket = marketRepository.findById(po_MarketCode);
            return optionalMarket.orElseThrow(() -> new RuntimeException("Market with ID " + po_MarketCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Market");
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
            return optionalMarket.orElseThrow(() -> new RuntimeException("Market with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Market");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<Market> optionalMarket = marketRepository.findByName(name);
            return optionalMarket.isPresent(); // Return true if Market exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Market");
        }
    }

    @Override
    public boolean existsById(int po_MarketCode) {
        return marketRepository.existsById(po_MarketCode);
    }

    @Override
    public List<MarketResponseDTO> getAllMarkets() {
        List<Market> markets = marketRepository.findAll();
        return markets.stream().map(market -> {
            List<SubMarketResponseDTO> subMarketResponseDTOs = market.getSubMarkets().stream()
                    .map(subMkt -> new SubMarketResponseDTO(subMkt.getPo_SubMarketCode(),
                            subMkt.getSubMarketName(),
                            subMkt.getSubMarketDescription()))
                    .collect(Collectors.toList());
            return new MarketResponseDTO(market.getPo_MarketCode(), market.getName(), market.getDescription(), subMarketResponseDTOs);
        }).collect(Collectors.toList());
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
            return optionalSubMarket.isPresent(); // Return true if SubMarket exists, false otherwise
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
            throw new ResourceNotFoundException("SubMarket with ID " + po_SubMarketCode + " not found.");
        }
    }

    @Transactional
    @Override
    public void unlinkSubMarketFromMarket(int MarketId, int subMarketId) {
        Optional<Market> MarketOptional = marketRepository.findById(MarketId);
        if (MarketOptional.isEmpty()) {
            throw new ResourceNotFoundException("Market with ID " + MarketId + " not found.");
        }

        Market Market = MarketOptional.get();
        Optional<SubMarket> subMarketOptional = subMarketRepository.findById(subMarketId);
        if (subMarketOptional.isEmpty()) {
            throw new ResourceNotFoundException("SubMarket with ID " + subMarketId + " not found.");
        }

        SubMarket subMarket = subMarketOptional.get();
        if (!Market.getSubMarkets().contains(subMarket)) {
            throw new IllegalArgumentException("SubMarket with ID " + subMarketId + " is not linked to Market with ID " + MarketId);
        }

        Market.removeSubMarket(subMarket);
        subMarket.setMarket(null);

        marketRepository.save(Market);
        subMarketRepository.save(subMarket);
    }


}
