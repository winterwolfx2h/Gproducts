package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.LrServiceId;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.LrServiceIdRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.LrServiceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LrServiceIdServiceImpl implements LrServiceIdService {

    @Autowired
    LrServiceIdRepository lrServiceIdRepository;

    @Override
    public LrServiceId create(LrServiceId LrServiceId) {
        try {
            return lrServiceIdRepository.save(LrServiceId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating LrServiceId");
        }
    }

    @Override
    public List<LrServiceId> read() {
        try {
            return lrServiceIdRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public LrServiceId update(int pr_LrServiceId, LrServiceId updatedLrServiceId) {
        try {
            Optional<LrServiceId> existingLrServiceIdOptional = lrServiceIdRepository.findById(pr_LrServiceId);
            if (existingLrServiceIdOptional.isPresent()) {
                LrServiceId existingLrServiceId = existingLrServiceIdOptional.get();
                existingLrServiceId.setName(updatedLrServiceId.getName());
                return lrServiceIdRepository.save(existingLrServiceId);
            } else {
                throw new RuntimeException("LrServiceId with ID " + pr_LrServiceId + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating LrServiceId");
        }
    }

    @Override
    public String delete(int pr_LrServiceId) {
        try {
            lrServiceIdRepository.deleteById(pr_LrServiceId);
            return ("LrServiceId with ID " + pr_LrServiceId + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting LrServiceId");
        }
    }

    @Override
    public LrServiceId findById(int pr_LrServiceId) {
        try {
            Optional<LrServiceId> optionalLrServiceId = lrServiceIdRepository.findById(pr_LrServiceId);
            return optionalLrServiceId.orElseThrow(() -> new RuntimeException("LrServiceId with ID " + pr_LrServiceId + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding LrServiceId");
        }
    }

    @Override
    public List<LrServiceId> searchByKeyword(String name) {
        try {
            return lrServiceIdRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching LrServiceId by keyword");
        }
    }

    @Override
    public LrServiceId findByName(String name) {
        try {
            Optional<LrServiceId> optionalLrServiceId = lrServiceIdRepository.findByName(name);
            return optionalLrServiceId.orElseThrow(() -> new RuntimeException("LrServiceId with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding LrServiceId");
        }
    }

}
