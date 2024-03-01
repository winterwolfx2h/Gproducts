package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.PrServiceId;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.PrServiceIdRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.PrServiceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrServiceIdServiceImpl implements PrServiceIdService {

    @Autowired
    PrServiceIdRepository prServiceIdRepository;

    @Override
    public PrServiceId create(PrServiceId PrServiceId) {
        try {
            return prServiceIdRepository.save(PrServiceId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating PrServiceId");
        }
    }

    @Override
    public List<PrServiceId> read() {
        try {
            return prServiceIdRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public PrServiceId update(int pr_PrServiceId, PrServiceId updatedPrServiceId) {
        try {
            Optional<PrServiceId> existingPrServiceIdOptional = prServiceIdRepository.findById(pr_PrServiceId);
            if (existingPrServiceIdOptional.isPresent()) {
                PrServiceId existingPrServiceId = existingPrServiceIdOptional.get();
                existingPrServiceId.setName(updatedPrServiceId.getName());
                return prServiceIdRepository.save(existingPrServiceId);
            } else {
                throw new RuntimeException("PrServiceId with ID " + pr_PrServiceId + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating PrServiceId");
        }
    }

    @Override
    public String delete(int pr_PrServiceId) {
        try {
            prServiceIdRepository.deleteById(pr_PrServiceId);
            return ("PrServiceId with ID " + pr_PrServiceId + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting PrServiceId");
        }
    }

    @Override
    public PrServiceId findById(int pr_PrServiceId) {
        try {
            Optional<PrServiceId> optionalPrServiceId = prServiceIdRepository.findById(pr_PrServiceId);
            return optionalPrServiceId.orElseThrow(() -> new RuntimeException("PrServiceId with ID " + pr_PrServiceId + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding PrServiceId");
        }
    }

    @Override
    public List<PrServiceId> searchByKeyword(String name) {
        try {
            return prServiceIdRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching PrServiceId by keyword");
        }
    }

    @Override
    public PrServiceId findByName(String name) {
        try {
            Optional<PrServiceId> optionalPrServiceId = prServiceIdRepository.findByName(name);
            return optionalPrServiceId.orElseThrow(() -> new RuntimeException("PrServiceId with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding PrServiceId");
        }
    }

}
