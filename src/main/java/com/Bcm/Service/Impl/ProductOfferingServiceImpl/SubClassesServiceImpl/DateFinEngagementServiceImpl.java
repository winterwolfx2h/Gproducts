package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DateFinEngagement;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.DateFinEngagementRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.DateFinEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DateFinEngagementServiceImpl implements DateFinEngagementService {

    @Autowired
    DateFinEngagementRepository dateFinEngagementRepository;

    @Override
    public DateFinEngagement create(DateFinEngagement dateFinEngagement) {
        return dateFinEngagementRepository.save(dateFinEngagement);
    }

    @Override
    public List<DateFinEngagement> read() {
        return dateFinEngagementRepository.findAll();
    }

    @Override
    public DateFinEngagement update(int po_DateFinEngCode, DateFinEngagement updatedDateFinEngagement) {
        Optional<DateFinEngagement> existingDateFinEngagementOptional = dateFinEngagementRepository.findById(po_DateFinEngCode);

        if (existingDateFinEngagementOptional.isPresent()) {
            DateFinEngagement existingDateFinEngagement = existingDateFinEngagementOptional.get();
            existingDateFinEngagement.setName(updatedDateFinEngagement.getName());
            return dateFinEngagementRepository.save(existingDateFinEngagement);
        } else {
            throw new RuntimeException("Could not find DateFinEngagement with ID: " + po_DateFinEngCode);
        }
    }

    @Override
    public String delete(int po_DateFinEngCode) {
        dateFinEngagementRepository.deleteById(po_DateFinEngCode);
        return ("DateFinEngagement was successfully deleted");
    }

    @Override
    public DateFinEngagement findById(int po_DateFinEngCode) {
        Optional<DateFinEngagement> optionalDateFinEngagement = dateFinEngagementRepository.findById(po_DateFinEngCode);
        return optionalDateFinEngagement.orElseThrow(() -> new RuntimeException("DateFinEngagement with ID " + po_DateFinEngCode + " not found"));
    }

    @Override
    public List<DateFinEngagement> searchByKeyword(String name) {
        return dateFinEngagementRepository.searchByKeyword(name);
    }
}
