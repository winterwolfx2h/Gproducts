package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.GroupDimension;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.GroupDimensionRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.GroupDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDimensionServiceImpl implements GroupDimensionService {

    @Autowired
    GroupDimensionRepository groupDimensionRepository;

    @Override
    public GroupDimension create(GroupDimension groupDimension) {
        try {
            return groupDimensionRepository.save(groupDimension);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Group Dimension");
        }
    }

    @Override
    public List<GroupDimension> read() {
        try {
            return groupDimensionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Group Dimensions");
        }
    }

    @Override
    public GroupDimension update(int po_GdCode, GroupDimension updatedGroupDimension) {
        try {
            Optional<GroupDimension> existingGroupDimensionOptional = groupDimensionRepository.findById(po_GdCode);
            if (existingGroupDimensionOptional.isPresent()) {
                GroupDimension existingGroupDimension = existingGroupDimensionOptional.get();
                existingGroupDimension.setName(updatedGroupDimension.getName());
                return groupDimensionRepository.save(existingGroupDimension);
            } else {
                throw new RuntimeException("Group Dimension with ID " + po_GdCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Group Dimension");
        }
    }

    @Override
    public String delete(int po_GdCode) {
        try {
            groupDimensionRepository.deleteById(po_GdCode);
            return ("Group Dimension with ID " + po_GdCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Group Dimension");
        }
    }

    @Override
    public GroupDimension findById(int po_GdCode) {
        try {
            Optional<GroupDimension> optionalGroupDimension = groupDimensionRepository.findById(po_GdCode);
            return optionalGroupDimension.orElseThrow(() -> new RuntimeException("Group Dimension with ID " + po_GdCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Group Dimension");
        }
    }

    @Override
    public List<GroupDimension> searchByKeyword(String name) {
        try {
            return groupDimensionRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Group Dimension by keyword");
        }
    }
}
