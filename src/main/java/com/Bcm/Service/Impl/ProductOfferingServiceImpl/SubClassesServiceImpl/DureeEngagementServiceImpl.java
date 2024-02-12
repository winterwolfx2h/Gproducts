package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DureeEngagement;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.DureeEngagementRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.DureeEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DureeEngagementServiceImpl implements DureeEngagementService {

    @Autowired
    DureeEngagementRepository dureeEngagementRepository;

    @Override
    public DureeEngagement create(DureeEngagement dureeEngagement) {
        return dureeEngagementRepository.save(dureeEngagement);
    }

    @Override
    public List<DureeEngagement> read() {
        return dureeEngagementRepository.findAll();
    }

    @Override
    public DureeEngagement update(int po_DureeEngCode, DureeEngagement updatedDureeEngagement) {
        Optional<DureeEngagement> existingDureeEngagementOptional = dureeEngagementRepository.findById(po_DureeEngCode);

        if (existingDureeEngagementOptional.isPresent()) {
            DureeEngagement existingDureeEngagement = existingDureeEngagementOptional.get();
            existingDureeEngagement.setName(updatedDureeEngagement.getName());
            return dureeEngagementRepository.save(existingDureeEngagement);
        } else {
            throw new RuntimeException("Could not find Duree Engagement with ID: " + po_DureeEngCode);
        }
    }

    @Override
    public String delete(int po_DureeEngCode) {
        dureeEngagementRepository.deleteById(po_DureeEngCode);
        return ("Duree Engagement was successfully deleted");
    }

    @Override
    public DureeEngagement findById(int po_DureeEngCode) {
        Optional<DureeEngagement> optionalDureeEngagement = dureeEngagementRepository.findById(po_DureeEngCode);
        return optionalDureeEngagement.orElseThrow(() -> new RuntimeException("Duree Engagement with ID " + po_DureeEngCode + " not found"));
    }

    @Override
    public List<DureeEngagement> searchByKeyword(String name) {
        return dureeEngagementRepository.searchByKeyword(name);
    }
}
