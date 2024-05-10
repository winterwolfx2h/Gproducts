package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Exception.SubMarketAlreadyExistsException;
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
    private final SubMarketRepository subMarketRepository;

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
        Optional<SubMarket> existingSubMarketOptional = subMarketRepository.findById(po_SubMarketCode);

        if (existingSubMarketOptional.isEmpty()) {
            throw new ResourceNotFoundException("SubMarket with ID " + po_SubMarketCode + " not found.");
        }

        SubMarket existingSubMarket = existingSubMarketOptional.get();

        // Check if there's another submarket with the same name
        Optional<SubMarket> subMarketWithSameName = subMarketRepository.findByName(updatedSubMarket.getName());
        if (subMarketWithSameName.isPresent() && subMarketWithSameName.get().getPo_SubMarketCode() != po_SubMarketCode) {
            throw new SubMarketAlreadyExistsException("SubMarket with name '" + updatedSubMarket.getName() + "' already exists.");
        }

        // Update the existing submarket with the new data
        existingSubMarket.setName(updatedSubMarket.getName());
        existingSubMarket.setDescription(updatedSubMarket.getDescription());

        return subMarketRepository.save(existingSubMarket);
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

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<SubMarket> optionalFamily = subMarketRepository.findByName(name);
            return optionalFamily.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding SubMarket");
        }
    }

}
