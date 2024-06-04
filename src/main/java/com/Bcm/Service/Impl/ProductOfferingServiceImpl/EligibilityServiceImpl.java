package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Model.ProductOfferingABE.Eligibility;
import com.Bcm.Repository.ProductOfferingRepo.EligibilityRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EligibilityServiceImpl implements EligibilityService {

    private final EligibilityRepository eligibilityRepository;
    private final ChannelRepository channelRepository;
    private final EntityRepository entityRepository;

    @Override
    public List<Eligibility> create(List<Eligibility> eligibilityList) {
        List<Eligibility> nonDuplicateEligibilities = new ArrayList<>();

        for (Eligibility eligibility : eligibilityList) {
            if (eligibility.getChannels() == null || eligibility.getChannels().isEmpty()) {
                throw new InvalidInputException("Channels list cannot be empty.");
            }

            List<String> missingChannels = eligibility.getChannels().stream()
                    .filter(channelName -> !channelRepository.existsByName(channelName))
                    .collect(Collectors.toList());

            if (!missingChannels.isEmpty()) {
                throw new InvalidInputException("Channel(s) with name '" + String.join(", ", missingChannels) + "' do not exist.");
            }

            if (eligibility.getEntities() == null || eligibility.getEntities().isEmpty()) {
                throw new InvalidInputException("Entity list cannot be empty.");
            }

            List<String> missingEntities = eligibility.getEntities().stream()
                    .filter(entityname -> !entityRepository.existsByName(entityname))
                    .collect(Collectors.toList());

            if (!missingEntities.isEmpty()) {
                throw new InvalidInputException("Entity(s) with name '" + String.join(", ", missingEntities) + "' do not exist.");
            }


            nonDuplicateEligibilities.add(eligibility);
        }

        return eligibilityRepository.saveAll(nonDuplicateEligibilities);
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
            existingEligibility.setChannels(updatedEligibility.getChannels());
            existingEligibility.setEntities(updatedEligibility.getEntities());

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
        return optionalEligibility.orElseThrow(() -> new RuntimeException("Eligibility with ID " + eligibilityId + " not found"));
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
