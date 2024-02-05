package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.ProductSubType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ProductSubTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ProductSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSubTypeServiceImpl implements ProductSubTypeService {


    @Autowired
    ProductSubTypeRepository productSubTypeRepository;

    @Autowired
    ProductSubTypeService productSubTypeService;

    @Override
    public ProductSubType create(ProductSubType ProductSubType) {
        return productSubTypeRepository.save(ProductSubType);
    }

    @Override
    public List<ProductSubType> read() {
        return productSubTypeRepository.findAll();
    }

    @Override
    public ProductSubType update(int po_ProdSubTypeCode, ProductSubType updatedProductSubType) {
        Optional<ProductSubType> existingProductSubTypeOptional = productSubTypeRepository.findById(po_ProdSubTypeCode);

        if (existingProductSubTypeOptional.isPresent()) {
            ProductSubType existingProductSubType = existingProductSubTypeOptional.get();
            existingProductSubType.setName(updatedProductSubType.getName());
            return productSubTypeRepository.save(existingProductSubType);
        } else {
            throw new RuntimeException("Could not find ProductSubType with ID: " + po_ProdSubTypeCode);
        }
    }

    @Override
    public String delete(int po_ProdSubTypeCode) {
        productSubTypeRepository.deleteById(po_ProdSubTypeCode);
        return ("ProductSubTypewas successfully deleted");
    }

    @Override
    public ProductSubType findById(int po_ProdSubTypeCode) {
        Optional<ProductSubType> optionalPlan = productSubTypeRepository.findById(po_ProdSubTypeCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("ProductSubType with ID " + po_ProdSubTypeCode + " not found"));
    }


    @Override
    public List<ProductSubType> searchByKeyword(String name) {
        return productSubTypeRepository.searchByKeyword(name);
    }


}
