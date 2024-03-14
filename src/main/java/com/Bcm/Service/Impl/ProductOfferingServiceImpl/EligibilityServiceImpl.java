package com.Bcm.Service.Impl.EligibilityOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Repository.ProductOfferingRepo.EligibilityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EligibilityServiceImpl implements EligibilityService {

    @Autowired
    EligibilityRepository eligibilityRepository;

    @Override
    public Eligibility create(Eligibility Eligibility) {
        return eligibilityRepository.save(Eligibility);
    }

    @Override
    public List<Eligibility> read() {
        return eligibilityRepository.findAll();
    }

    @Override
    public Eligibility update(int eligibilityId, Eligibility updatedEligibility) {
        Optional<Eligibility> existingEligibilityOptional = eligibilityRepository.findById(eligibilityId);

        if (existingEligibilityOptional.isPresent()) {
            Eligibility existingEligibility = existingEligibilityOptional.get();
            existingEligibility.setChannel(updatedEligibility.getChannel());

            return eligibilityRepository.save(existingEligibility);
        } else {
            throw new RuntimeException("Could not find Eligibility  with ID: " + eligibilityId);
        }
    }

    @Override
    public String delete(int eligibilityId) {
        eligibilityRepository.deleteById(eligibilityId);
        return ("Eligibility  was successfully deleted");
    }

    @Override
    public Eligibility findById(int eligibilityId) {
        Optional<Eligibility> optionalPlan = eligibilityRepository.findById(eligibilityId);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Eligibility  with ID " + eligibilityId + " not found"));
    }

    @Override
    public List<Eligibility> searchByKeyword(String channel) {
        return eligibilityRepository.searchByKeyword(channel);
    }

    @Override
    public Eligibility findByChannel(String channel) {
        try {
            Optional<Eligibility> optionalEligibility = eligibilityRepository.findByChannel(channel);
            return optionalEligibility.orElseThrow(() -> new RuntimeException("Eligibility  with " + channel + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Eligibility ");
        }
    }

    @Override
    public boolean existsById(int eligibilityId) {
        return eligibilityRepository.existsById(eligibilityId);
    }
}

