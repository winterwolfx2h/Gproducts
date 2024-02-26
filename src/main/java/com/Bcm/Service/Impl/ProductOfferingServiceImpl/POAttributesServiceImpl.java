package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Repository.ProductOfferingRepo.POAttributesRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POAttributesServiceImpl implements POAttributesService {

    @Autowired
    POAttributesRepository POAttributesRepository;

    @Override
    public POAttributes create(POAttributes POAttributes) {
        return POAttributesRepository.save(POAttributes);
    }

    @Override
    public List<POAttributes> read() {
        return POAttributesRepository.findAll();
    }

    @Override
    public POAttributes update(int po_SpecCode, POAttributes updatedPOAttributes) {
        Optional<POAttributes> existingProductOptional = POAttributesRepository.findById(po_SpecCode);

        if (existingProductOptional.isPresent()) {
            POAttributes existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedPOAttributes.getName());
            existingProduct.setShortCode(updatedPOAttributes.getShortCode());
            existingProduct.setStartDate(updatedPOAttributes.getStartDate());
            existingProduct.setEndDate(updatedPOAttributes.getEndDate());
            existingProduct.setDescription(updatedPOAttributes.getDescription());

            return POAttributesRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find POAttributes with ID: " + po_SpecCode);
        }
    }

    @Override
    public String delete(int po_SpecCode) {
        POAttributesRepository.deleteById(po_SpecCode);
        return ("POAttributes was successfully deleted");
    }

    @Override
    public POAttributes findById(int po_SpecCode) {
        Optional<POAttributes> optionalPlan = POAttributesRepository.findById(po_SpecCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("POAttributes with ID " + po_SpecCode + " not found"));
    }

    @Override
    public List<POAttributes> searchByKeyword(String name) {
        return POAttributesRepository.searchByKeyword(name);
    }

    @Override
    public POAttributes findByName(String name) {
        try {
            Optional<POAttributes> optionalPOAttributes = POAttributesRepository.findByName(name);
            return optionalPOAttributes.orElseThrow(() -> new RuntimeException("POAttributes with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding POAttributes");
        }
    }


}

