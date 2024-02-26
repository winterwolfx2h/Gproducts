package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Provider;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ProviderRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    @Override
    public Provider create(Provider Provider) {
        try {
            return providerRepository.save(Provider);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Provider");
        }
    }

    @Override
    public List<Provider> read() {
        try {
            return providerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Providers");
        }
    }

    @Override
    public Provider update(int po_ProviderCode, Provider updatedProvider) {
        try {
            Optional<Provider> existingProviderOptional = providerRepository.findById(po_ProviderCode);
            if (existingProviderOptional.isPresent()) {
                Provider existingProvider = existingProviderOptional.get();
                existingProvider.setName(updatedProvider.getName());
                return providerRepository.save(existingProvider);
            } else {
                throw new RuntimeException("Provider with ID " + po_ProviderCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Provider");
        }
    }

    @Override
    public String delete(int po_ProviderCode) {
        try {
            providerRepository.deleteById(po_ProviderCode);
            return ("Provider with ID " + po_ProviderCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Provider");
        }
    }

    @Override
    public Provider findById(int po_ProviderCode) {
        try {
            Optional<Provider> optionalPlan = providerRepository.findById(po_ProviderCode);
            return optionalPlan.orElseThrow(() -> new RuntimeException("Provider with ID " + po_ProviderCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Provider");
        }
    }

    @Override
    public List<Provider> searchByKeyword(String name) {
        try {
            return providerRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Provider by keyword");
        }
    }

    @Override
    public Provider findByName(String name) {
        try {
            Optional<Provider> optionalProvider = providerRepository.findByName(name);
            return optionalProvider.orElseThrow(() -> new RuntimeException("Provider with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Provider");
        }
    }
}
