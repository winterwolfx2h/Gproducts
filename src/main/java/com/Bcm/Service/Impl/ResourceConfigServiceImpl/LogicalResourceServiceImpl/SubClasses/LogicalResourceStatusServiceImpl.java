package com.Bcm.Service.Impl.ResourceConfigServiceImpl.LogicalResourceServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.LogicalResourceStatus;
import com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.SubClasses.LogicalResourceStatusRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses.LogicalResourceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogicalResourceStatusServiceImpl implements LogicalResourceStatusService {

    @Autowired
    LogicalResourceStatusRepository LogicalResourceStatusRepository;

    @Override
    public LogicalResourceStatus create(LogicalResourceStatus LogicalResourceStatus) {
        try {
            if (LogicalResourceStatus == null) {
                throw new IllegalArgumentException(" LogicalResourceStatus cannot be null");
            }
            return LogicalResourceStatusRepository.save(LogicalResourceStatus);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  LogicalResourceStatus: " + e.getMessage());
        }
    }

    @Override
    public List<LogicalResourceStatus> read() {
        return LogicalResourceStatusRepository.findAll();
    }

    @Override
    public LogicalResourceStatus update(int FID, LogicalResourceStatus updatedLogicalResourceStatus) {
        try {
            Optional<LogicalResourceStatus> existingLogicalResourceStatusOptional = LogicalResourceStatusRepository.findById(FID);

            if (existingLogicalResourceStatusOptional.isPresent()) {
                LogicalResourceStatus existingLogicalResourceStatus = existingLogicalResourceStatusOptional.get();
                existingLogicalResourceStatus.setName(updatedLogicalResourceStatus.getName());
                return LogicalResourceStatusRepository.save(existingLogicalResourceStatus);
            } else {
                throw new RuntimeException("Could not find  LogicalResourceStatus with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  LogicalResourceStatus Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  LogicalResourceStatus: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<LogicalResourceStatus> optionalLogicalResourceStatus = LogicalResourceStatusRepository.findById(FID);
        if (optionalLogicalResourceStatus.isPresent()) {
            LogicalResourceStatusRepository.deleteById(FID);
            return (" LogicalResourceStatus was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  LogicalResourceStatus with ID: " + FID);
        }
    }

    @Override
    public LogicalResourceStatus findById(int FID) {
        Optional<LogicalResourceStatus> optionalLogicalResourceStatus = LogicalResourceStatusRepository.findById(FID);
        return optionalLogicalResourceStatus.orElseThrow(() -> new RuntimeException(" LogicalResourceStatus with ID " + FID + " not found"));
    }

    @Override
    public List<LogicalResourceStatus> searchByKeyword(String name) {
        return LogicalResourceStatusRepository.searchByKeyword(name);
    }
}
