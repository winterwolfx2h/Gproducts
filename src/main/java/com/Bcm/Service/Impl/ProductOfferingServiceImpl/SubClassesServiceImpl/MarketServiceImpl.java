package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.MarketAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.MarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    final MarketRepository marketRepository;

    @Override
    public Market create(Market market) {
        try {
            Optional<Market> existingMarket = marketRepository.findByName(market.getName());
            if (existingMarket.isPresent()) {
                throw new RuntimeException("Market with name " + market.getName() + " already exists");
            }
            return marketRepository.save(market);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Market");
        }
    }

    @Override
    public List<Market> read() {
        try {
            return marketRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Markets");
        }
    }

    @Override
    public Market update(int po_MarketCode, Market updatedMarket) {
        // Find the existing market by ID
        Optional<Market> existingMarketOptional = marketRepository.findById(po_MarketCode);

        if (existingMarketOptional.isEmpty()) {
            throw new ResourceNotFoundException("Market with ID " + po_MarketCode + " not found.");
        }

        Market existingMarket = existingMarketOptional.get();

        // Check if there's another market with the same name
        Optional<Market> marketWithSameName = marketRepository.findByName(updatedMarket.getName());
        if (marketWithSameName.isPresent() && marketWithSameName.get().getPo_MarketCode() != po_MarketCode) {
            throw new MarketAlreadyExistsException("Market with name '" + updatedMarket.getName() + "' already exists.");
        }

        // Update the existing market with the new data
        existingMarket.setName(updatedMarket.getName());
        existingMarket.setDescription(updatedMarket.getDescription());

        return marketRepository.save(existingMarket);
    }

    @Override
    public String delete(int po_MarketCode) {
        try {
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
            return optionalMarket.orElseThrow(() -> new RuntimeException("Market with name: " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Market");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<Market> optionalFamily = marketRepository.findByName(name);
            return optionalFamily.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Market");
        }
    }

}
