package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Repository.ProductOfferingRepo.ProductSpecificationRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {


    @Autowired
    ProductSpecificationRepository productOfferingRepository;

    @Autowired
    ProductSpecificationService productSpecificationService;

    @Override
    public ProductSpecification create(ProductSpecification productSpecification) {
        return productOfferingRepository.save(productSpecification);
    }

    @Override
    public List<ProductSpecification> read() {
        return productOfferingRepository.findAll();
    }



    @Override
    public ProductSpecification update(int po_SpecCode, ProductSpecification updatedproductSpecification) {
        Optional<ProductSpecification> existingProductOptional = productOfferingRepository.findById(po_SpecCode);

        if (existingProductOptional.isPresent()) {
            ProductSpecification existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedproductSpecification.getName());
            existingProduct.setFamily(updatedproductSpecification.getFamily());
            existingProduct.setCategory(updatedproductSpecification.getCategory());
            existingProduct.setProductType(updatedproductSpecification.getProductType());
            existingProduct.setMarket(updatedproductSpecification.getMarket());
            existingProduct.setProductSubType(updatedproductSpecification.getProductSubType());
            existingProduct.setProductTechnicalType(updatedproductSpecification.getProductTechnicalType());
            existingProduct.setTimeZoneType(updatedproductSpecification.getTimeZoneType());
            existingProduct.setRatePlan(updatedproductSpecification.getRatePlan());
            existingProduct.setDateFinEngagement(updatedproductSpecification.getDateFinEngagement());
            existingProduct.setDureeEngagement(updatedproductSpecification.getDureeEngagement());

            return productOfferingRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Product Specification with ID: " + po_SpecCode);
        }
    }











    @Override
    public String delete(int po_SpecCode) {
        productOfferingRepository.deleteById(po_SpecCode);
        return ("Product Specification was successfully deleted");
    }

    @Override
    public ProductSpecification findById(int po_SpecCode) {
        Optional<ProductSpecification> optionalPlan = productOfferingRepository.findById(po_SpecCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Product Specification with ID " + po_SpecCode + " not found"));
    }


    @Override
    public List<ProductSpecification> searchByKeyword(String name) {
        return productOfferingRepository.searchByKeyword(name);
    }


}
