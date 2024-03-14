package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.MarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    MarketRepository marketRepository;

    @Override
    public Market create(Market market) {
        try {
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
        try {
            Optional<Market> existingMarketOptional = marketRepository.findById(po_MarketCode);
            if (existingMarketOptional.isPresent()) {
                Market existingMarket = existingMarketOptional.get();
                existingMarket.setName(updatedMarket.getName());
                return marketRepository.save(existingMarket);
            } else {
                throw new RuntimeException("Market with ID " + po_MarketCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Market");
        }
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
}
