package com.Bcm.Service.Impl.GroupDimensionServiceImpl;

import com.Bcm.Model.ProductOfferingABE.GroupDimension;
import com.Bcm.Repository.ProductOfferingRepo.GroupDimensionRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GroupDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDimensionServiceImpl implements GroupDimensionService {


    @Autowired
    GroupDimensionRepository groupDimensionRepository;

    @Autowired
    GroupDimensionService groupDimensionService;

    @Override
    public GroupDimension create(GroupDimension groupDimension) {
        return groupDimensionRepository.save(groupDimension);
    }

    @Override
    public List<GroupDimension> read() {
        return groupDimensionRepository.findAll();
    }

    @Override
    public GroupDimension update(int po_GdCode, GroupDimension updatedGroupDimension) {
        Optional<GroupDimension> existingProductOptional = groupDimensionRepository.findById(po_GdCode);

        if (existingProductOptional.isPresent()) {
            GroupDimension existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedGroupDimension.getName());
            return groupDimensionRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + po_GdCode);
        }
    }

    @Override
    public String delete(int po_GdCode) {
        groupDimensionRepository.deleteById(po_GdCode);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public GroupDimension findById(int po_GdCode) {
        Optional<GroupDimension> optionalPlan = groupDimensionRepository.findById(po_GdCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Group Dimension with ID " + po_GdCode + " not found"));
    }


    @Override
    public List<GroupDimension> searchByKeyword(String name) {
        return groupDimensionRepository.searchByKeyword(name);
    }


}
