package com.Bcm.Service.Impl.ResourceConfigServiceImpl.ResourceConfigServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUStatus;
import com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses.ROUStatusRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses.ROUStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ROUStatusServiceImpl implements ROUStatusService {

    @Autowired
    ROUStatusRepository ROUStatusRepository;

    @Override
    public ROUStatus create(ROUStatus ROUStatus) {
        try {
            if (ROUStatus == null) {
                throw new IllegalArgumentException(" ROUStatus cannot be null");
            }
            return ROUStatusRepository.save(ROUStatus);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  ROUStatus: " + e.getMessage());
        }
    }

    @Override
    public List<ROUStatus> read() {
        return ROUStatusRepository.findAll();
    }

    @Override
    public ROUStatus update(int FID, ROUStatus updatedROUStatus) {
        try {
            Optional<ROUStatus> existingROUStatusOptional = ROUStatusRepository.findById(FID);

            if (existingROUStatusOptional.isPresent()) {
                ROUStatus existingROUStatus = existingROUStatusOptional.get();
                existingROUStatus.setName(updatedROUStatus.getName());
                return ROUStatusRepository.save(existingROUStatus);
            } else {
                throw new RuntimeException("Could not find  ROUStatus with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  ROUStatus Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  ROUStatus: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<ROUStatus> optionalROUStatus = ROUStatusRepository.findById(FID);
        if (optionalROUStatus.isPresent()) {
            ROUStatusRepository.deleteById(FID);
            return (" ROUStatus was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  ROUStatus with ID: " + FID);
        }
    }

    @Override
    public ROUStatus findById(int FID) {
        Optional<ROUStatus> optionalROUStatus = ROUStatusRepository.findById(FID);
        return optionalROUStatus.orElseThrow(() -> new RuntimeException(" ROUStatus with ID " + FID + " not found"));
    }

    @Override
    public List<ROUStatus> searchByKeyword(String name) {
        return ROUStatusRepository.searchByKeyword(name);
    }
}