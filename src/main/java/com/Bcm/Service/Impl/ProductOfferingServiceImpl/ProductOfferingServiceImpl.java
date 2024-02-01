package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.POPlanRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

    @Autowired
    ProductOfferingRepository productOfferingRepository;

    @Autowired
    ProductOfferingService productOfferingService;

    @Override
    public ProductOffering create(ProductOffering productOffering) {
        return productOfferingRepository.save(productOffering);
    }

    @Override
    public List<ProductOffering> read() {
        return productOfferingRepository.findAll();
    }

    @Override
    public ProductOffering update(int po_code, ProductOffering updatedProductOffering) {
        Optional<ProductOffering> existingProductOptional = productOfferingRepository.findById(po_code);

        if (existingProductOptional.isPresent()) {
            ProductOffering existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductOffering.getName());
            existingProduct.setDescription(updatedProductOffering.getDescription());
            existingProduct.setEffectiveFrom(updatedProductOffering.getEffectiveFrom());
            existingProduct.setEffectiveTo(updatedProductOffering.getEffectiveTo());
            existingProduct.setGroupDimension(updatedProductOffering.getGroupDimension());
            existingProduct.setParent(updatedProductOffering.getParent());

            return productOfferingRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find product offering with ID: " + po_code);
        }
    }
    @Override
    public String delete(int po_code) {
        productOfferingRepository.deleteById(po_code);
        return ("plan was successfully deleted");
    }
    @Override
    public ProductOffering findById(int po_code) {
        Optional<ProductOffering> optionalPlan = productOfferingRepository.findById(po_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Plan with ID " + po_code + " not found"));
    }


    @Override
    public List<ProductOffering> searchByKeyword(String name) {
        return productOfferingRepository.searchByKeyword(name);
    }


}