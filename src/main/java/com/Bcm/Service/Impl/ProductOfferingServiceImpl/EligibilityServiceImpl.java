package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.AllChannelsAlreadyExistException;
import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Repository.ProductOfferingRepo.EligibilityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class EligibilityServiceImpl implements EligibilityService {


    final EligibilityRepository eligibilityRepository;

    @Override
    public List<Eligibility> create(List<Eligibility> eligibilityList) {
        List<Eligibility> nonDuplicateEligibilities = new ArrayList<>();
        List<Eligibility> existingEligibilities = new ArrayList<>();

        for (Eligibility eligibility : eligibilityList) {
            if (existsByChannel(eligibility.getChannel())) {
                existingEligibilities.add(eligibility);
            } else {
                nonDuplicateEligibilities.add(eligibility);
            }
        }

        if (nonDuplicateEligibilities.isEmpty()) {
            throw new AllChannelsAlreadyExistException("All the channels are already existing");
        }

        eligibilityRepository.saveAll(nonDuplicateEligibilities);

        return nonDuplicateEligibilities;
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
        List<Eligibility> eligibilities = eligibilityRepository.findByChannel(channel);
        if (!eligibilities.isEmpty()) {
            return eligibilities.get(0);
        } else {
            throw new RuntimeException("Eligibility with channel '" + channel + "' not found");
        }
    }


    @Override
    public boolean existsById(int eligibilityId) {
        return eligibilityRepository.existsById(eligibilityId);
    }


    @Override
    public boolean existsByChannel(String channel) {
        List<Eligibility> eligibilities = eligibilityRepository.findByChannel(channel);
        return !eligibilities.isEmpty();
    }
}

