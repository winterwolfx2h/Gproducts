package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.Gender;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses.GenderRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    GenderRepository GenderRepository;

    @Override
    public Gender create(Gender Gender) {
        try {
            if (Gender == null) {
                throw new IllegalArgumentException(" Gender cannot be null");
            }
            return GenderRepository.save(Gender);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  Gender: " + e.getMessage());
        }
    }

    @Override
    public List<Gender> read() {
        return GenderRepository.findAll();
    }

    @Override
    public Gender update(int FID, Gender updatedGender) {
        try {
            Optional<Gender> existingGenderOptional = GenderRepository.findById(FID);

            if (existingGenderOptional.isPresent()) {
                Gender existingGender = existingGenderOptional.get();
                existingGender.setName(updatedGender.getName());
                return GenderRepository.save(existingGender);
            } else {
                throw new RuntimeException("Could not find  Gender with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  Gender Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  Gender: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<Gender> optionalGender = GenderRepository.findById(FID);
        if (optionalGender.isPresent()) {
            GenderRepository.deleteById(FID);
            return (" Gender was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  Gender with ID: " + FID);
        }
    }

    @Override
    public Gender findById(int FID) {
        Optional<Gender> optionalGender = GenderRepository.findById(FID);
        return optionalGender.orElseThrow(() -> new RuntimeException(" Gender with ID " + FID + " not found"));
    }

    @Override
    public List<Gender> searchByKeyword(String name) {
        return GenderRepository.searchByKeyword(name);
    }
}