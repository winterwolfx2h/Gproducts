package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.SubMarket;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubMarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubMarketServiceImpl implements SubMarketService {


    final SubMarketRepository SubMarketRepository;

    @Override
    public SubMarket create(SubMarket subMarket) {
        try {
            Optional<SubMarket> existingSubMarketOptional = SubMarketRepository.findByName(subMarket.getName());
            if (existingSubMarketOptional.isPresent()) {
                throw new RuntimeException("SubMarket with name '" + subMarket.getName() + "' already exists.");
            }
            return SubMarketRepository.save(subMarket);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating SubMarket");
        }
    }


    @Override
    public List<SubMarket> read() {
        try {
            return SubMarketRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving SubMarkets");
        }
    }

    @Override
    public SubMarket update(int po_SubMarketCode, SubMarket updatedSubMarket) {
        try {
            Optional<SubMarket> existingSubMarketOptional = SubMarketRepository.findById(po_SubMarketCode);
            if (existingSubMarketOptional.isPresent()) {
                SubMarket existingSubMarket = existingSubMarketOptional.get();
                existingSubMarket.setName(updatedSubMarket.getName());
                return SubMarketRepository.save(existingSubMarket);
            } else {
                throw new RuntimeException("SubMarket with ID " + po_SubMarketCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating SubMarket");
        }
    }

    @Override
    public String delete(int po_SubMarketCode) {
        try {
            SubMarketRepository.deleteById(po_SubMarketCode);
            return ("SubMarket with ID " + po_SubMarketCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting SubMarket");
        }
    }

    @Override
    public SubMarket findById(int po_SubMarketCode) {
        try {
            Optional<SubMarket> optionalSubMarket = SubMarketRepository.findById(po_SubMarketCode);
            return optionalSubMarket.orElseThrow(() -> new RuntimeException("SubMarket with ID " + po_SubMarketCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding SubMarket");
        }
    }

    @Override
    public List<SubMarket> searchByKeyword(String name) {
        try {
            return SubMarketRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching SubMarket by keyword");
        }
    }

    @Override
    public SubMarket findByName(String name) {
        try {
            Optional<SubMarket> optionalSubMarket = SubMarketRepository.findByName(name);
            return optionalSubMarket.orElseThrow(() -> new RuntimeException("SubMarket with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding SubMarket");
        }
    }
}
