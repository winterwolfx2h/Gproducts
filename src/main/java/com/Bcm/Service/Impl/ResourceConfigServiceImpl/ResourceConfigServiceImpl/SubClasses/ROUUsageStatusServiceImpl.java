package com.Bcm.Service.Impl.ResourceConfigServiceImpl.ResourceConfigServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUUsageStatus;
import com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses.ROUUsageStatusRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ROUUsageStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ROUUsageStatusServiceImpl implements ROUUsageStatusService {

    @Autowired
    ROUUsageStatusRepository ROUUsageStatusRepository;

    @Override
    public ROUUsageStatus create(ROUUsageStatus ROUUsageStatus) {
        try {
            if (ROUUsageStatus == null) {
                throw new IllegalArgumentException(" ROUUsageStatus cannot be null");
            }
            return ROUUsageStatusRepository.save(ROUUsageStatus);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  ROUUsageStatus: " + e.getMessage());
        }
    }

    @Override
    public List<ROUUsageStatus> read() {
        return ROUUsageStatusRepository.findAll();
    }

    @Override
    public ROUUsageStatus update(int FID, ROUUsageStatus updatedROUUsageStatus) {
        try {
            Optional<ROUUsageStatus> existingROUUsageStatusOptional = ROUUsageStatusRepository.findById(FID);

            if (existingROUUsageStatusOptional.isPresent()) {
                ROUUsageStatus existingROUUsageStatus = existingROUUsageStatusOptional.get();
                existingROUUsageStatus.setName(updatedROUUsageStatus.getName());
                return ROUUsageStatusRepository.save(existingROUUsageStatus);
            } else {
                throw new RuntimeException("Could not find  ROUUsageStatus with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  ROUUsageStatus Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  ROUUsageStatus: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<ROUUsageStatus> optionalROUUsageStatus = ROUUsageStatusRepository.findById(FID);
        if (optionalROUUsageStatus.isPresent()) {
            ROUUsageStatusRepository.deleteById(FID);
            return (" ROUUsageStatus was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  ROUUsageStatus with ID: " + FID);
        }
    }

    @Override
    public ROUUsageStatus findById(int FID) {
        Optional<ROUUsageStatus> optionalROUUsageStatus = ROUUsageStatusRepository.findById(FID);
        return optionalROUUsageStatus.orElseThrow(() -> new RuntimeException(" ROUUsageStatus with ID " + FID + " not found"));
    }

    @Override
    public List<ROUUsageStatus> searchByKeyword(String name) {
        return ROUUsageStatusRepository.searchByKeyword(name);
    }
}