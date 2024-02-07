package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductTechnicalType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ProductTechnicalTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductTechnicalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTechnicalTypeServiceImpl implements ProductTechnicalTypeService {


    @Autowired
    ProductTechnicalTypeRepository productTechnicalTypeRepository;

    @Autowired
    ProductTechnicalTypeService productTechnicalTypeService;

    @Override
    public ProductTechnicalType create(ProductTechnicalType productTechnicalType) {
        return productTechnicalTypeRepository.save(productTechnicalType);
    }

    @Override
    public List<ProductTechnicalType> read() {
        return productTechnicalTypeRepository.findAll();
    }

    @Override
    public ProductTechnicalType update(int po_ProdTechTypeCode, ProductTechnicalType updatedProductTechnicalType) {
        Optional<ProductTechnicalType> existingProductTechnicalTypeTechnicalTypeOptional = productTechnicalTypeRepository.findById(po_ProdTechTypeCode);

        if (existingProductTechnicalTypeTechnicalTypeOptional.isPresent()) {
            ProductTechnicalType existingProductTechnicalType = existingProductTechnicalTypeTechnicalTypeOptional.get();
            existingProductTechnicalType.setName(updatedProductTechnicalType.getName());
            return productTechnicalTypeRepository.save(existingProductTechnicalType);
        } else {
            throw new RuntimeException("Could not find Product Technical Type with ID: " + po_ProdTechTypeCode);
        }
    }

    @Override
    public String delete(int po_ProdTechTypeCode) {
        productTechnicalTypeRepository.deleteById(po_ProdTechTypeCode);
        return ("Product Technical Type was successfully deleted");
    }

    @Override
    public ProductTechnicalType findById(int po_ProdTechTypeCode) {
        Optional<ProductTechnicalType> optionalPlan = productTechnicalTypeRepository.findById(po_ProdTechTypeCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Product Technical Type with ID " + po_ProdTechTypeCode + " not found"));
    }


    @Override
    public List<ProductTechnicalType> searchByKeyword(String name) {
        return productTechnicalTypeRepository.searchByKeyword(name);
    }


}
