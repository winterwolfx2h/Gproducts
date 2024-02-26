package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.StatusRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {


    @Autowired
    StatusRepository StatusRepository;

    @Override
    public Status create(Status Status) {
        try {
            return StatusRepository.save(Status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Status");
        }
    }

    @Override
    public List<Status> read() {
        try {
            return StatusRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Statuss");
        }
    }

    @Override
    public Status update(int pos_code, Status updatedStatus) {
        try {
            Optional<Status> existingStatusOptional = StatusRepository.findById(pos_code);
            if (existingStatusOptional.isPresent()) {
                Status existingStatus = existingStatusOptional.get();
                existingStatus.setName(updatedStatus.getName());
                return StatusRepository.save(existingStatus);
            } else {
                throw new RuntimeException("Status with ID " + pos_code + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Status");
        }
    }

    @Override
    public String delete(int pos_code) {
        try {
            StatusRepository.deleteById(pos_code);
            return ("Status with ID " + pos_code + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Status");
        }
    }

    @Override
    public Status findById(int pos_code) {
        try {
            Optional<Status> optionalPlan = StatusRepository.findById(pos_code);
            return optionalPlan.orElseThrow(() -> new RuntimeException("Status with ID " + pos_code + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Status");
        }
    }

    @Override
    public List<Status> searchByKeyword(String name) {
        try {
            return StatusRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Status by keyword");
        }
    }

    @Override
    public Status findByName(String name) {
        try {
            Optional<Status> optionalStatus = StatusRepository.findByname(name);
            return optionalStatus.orElseThrow(() -> new RuntimeException("Status with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Status");
        }
    }
}
