package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.Parent;
import com.Bcm.Repository.ProductOfferingRepo.ParentRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {


    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ParentService parentService;

    @Override
    public Parent create(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public List<Parent> read() {
        return parentRepository.findAll();
    }

    @Override
    public Parent update(int po_ParentCode, Parent updatedParent) {
        Optional<Parent> existingProductOptional = parentRepository.findById(po_ParentCode);

        if (existingProductOptional.isPresent()) {
            Parent existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedParent.getName());
            return parentRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Parent with ID: " + po_ParentCode);
        }
    }

    @Override
    public String delete(int po_ParentCode) {
        parentRepository.deleteById(po_ParentCode);
        return ("Parent was successfully deleted");
    }

    @Override
    public Parent findById(int po_ParentCode) {
        Optional<Parent> optionalPlan = parentRepository.findById(po_ParentCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Parent with ID " + po_ParentCode + " not found"));
    }


    @Override
    public List<Parent> searchByKeyword(String name) {
        return parentRepository.searchByKeyword(name);
    }


}
