package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Repository.ProductOfferingRepo.EligibilityRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EligibilityServiceImpl implements EligibilityService {

    private final EligibilityRepository eligibilityRepository;
    private final ChannelRepository channelRepository;
    private final EntityRepository entityRepository;

    @Override
    @Transactional
    public Eligibility create(Eligibility eligibility) {
        try {
            return eligibilityRepository.save(eligibility);
        } catch (Exception e) {
            // Log the exception or print the stack trace
            e.printStackTrace();
            throw new RuntimeException("Failed to create Eligibility: " + e.getMessage(), e);
        }
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
            existingEligibility.setStock_Indicator(updatedEligibility.getStock_Indicator());

            return eligibilityRepository.save(existingEligibility);
        } else {
            throw new RuntimeException("Could not find Eligibility with ID: " + eligibilityId);
        }
    }

    @Override
    public String delete(int eligibilityId) {
        eligibilityRepository.deleteById(eligibilityId);
        return ("Eligibility was successfully deleted");
    }

    @Override
    public Eligibility findById(int eligibilityId) {
        Optional<Eligibility> optionalEligibility = eligibilityRepository.findById(eligibilityId);
        return optionalEligibility.orElseThrow(
                () -> new RuntimeException("Eligibility with ID " + eligibilityId + " not found"));
    }

    @Override
    public boolean existsById(int eligibilityId) {
        return eligibilityRepository.existsById(eligibilityId);
    }

    @Override
    public boolean findByIdExists(int eligibilityId) {
        try {
            Optional<Eligibility> optionalEligibility = eligibilityRepository.findById(eligibilityId);
            return optionalEligibility.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Eligibility", e);
        }
    }
}
