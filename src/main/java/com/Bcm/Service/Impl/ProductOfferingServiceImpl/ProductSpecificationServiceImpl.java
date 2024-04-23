package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.POPlan;
import com.Bcm.Model.ProductOfferingABE.ProductSpecification;
import com.Bcm.Repository.ProductOfferingRepo.ProductSpecificationRepository;
import com.Bcm.Service.Srvc.POPlanService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    final ProductSpecificationRepository productSpecificationRepository;
    final POPlanService poPlanService;

    @Override
    public ProductSpecification create(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    @Override
    public List<ProductSpecification> read() {
        return productSpecificationRepository.findAll();
    }

    @Override
    public ProductSpecification update(int po_SpecCode, ProductSpecification updatedproductSpecification) {
        Optional<ProductSpecification> existingProductOptional = productSpecificationRepository.findById(po_SpecCode);

        if (existingProductOptional.isPresent()) {
            ProductSpecification existingProduct = existingProductOptional.get();
            existingProduct.setCategory(updatedproductSpecification.getCategory());
            existingProduct.getPoPlanName();
            existingProduct.setBS_externalId(updatedproductSpecification.getBS_externalId());
            existingProduct.setCS_externalId(updatedproductSpecification.getCS_externalId());

            return productSpecificationRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find Product Specification with ID: " + po_SpecCode);
        }
    }

    @Override
    public String delete(int po_SpecCode) {
        productSpecificationRepository.deleteById(po_SpecCode);
        return ("Product Specification was successfully deleted");
    }

    @Override
    public ProductSpecification findById(int po_SpecCode) {
        Optional<ProductSpecification> optionalPlan = productSpecificationRepository.findById(po_SpecCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Product Specification with ID " + po_SpecCode + " not found"));
    }

    @Override
    public boolean existsById(int po_SpecCode) {
        return productSpecificationRepository.existsById(po_SpecCode);
    }

    @Override
    public POPlan findPOPlanByName(String poPlanName) {
        ProductSpecification productSpecification = productSpecificationRepository.findBypoPlanName(poPlanName);

        if (productSpecification == null) {
            throw new RuntimeException("ProductSpecification with poPlanName " + poPlanName + " not found");
        }
        String firstpoPlanName = productSpecification.getPoPlanName().get(0);

        return poPlanService.findByName(firstpoPlanName);
    }

    @Override
    public boolean existsByPoPlanName(String poPlanName) {
        return productSpecificationRepository.existsBypoPlanName(poPlanName);
    }
}



