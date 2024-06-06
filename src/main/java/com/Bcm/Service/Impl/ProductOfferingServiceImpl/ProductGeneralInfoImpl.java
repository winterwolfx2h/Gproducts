package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.GeneralInfoRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Repository.ProductResourceRepository.PhysicalResourceRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductGeneralInfoImpl implements GeneralInfoService {
    final GeneralInfoRepository generalInfoRepository;
    final ProductOfferingRepository productOfferingRepository;
    final PhysicalResourceRepository physicalResourceRepository;
    final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;

    @Override
    public ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO) {
        // Check if an existing product offering with the same name already exists
        Optional<GeneralInfoDTO> existingProduct = generalInfoRepository.findByName(generalInfoDTO.getName());
        if (existingProduct.isPresent()) {
            throw new ProductOfferingAlreadyExistsException(
                    "A product offering with the name '" + generalInfoDTO.getName() + "' already exists."
            );
        }
        generalInfoDTO.setStatus("Working state");

        // Create new ProductOffering entity from DTO
        ProductOffering productOffering = new ProductOffering();
        productOffering.setName(generalInfoDTO.getName());
        productOffering.setPoType(generalInfoDTO.getPoType());
        productOffering.setEffectiveFrom(generalInfoDTO.getEffectiveFrom());
        productOffering.setEffectiveTo(generalInfoDTO.getEffectiveTo());
        productOffering.setDescription(generalInfoDTO.getDescription());
        productOffering.setDetailedDescription(generalInfoDTO.getDetailedDescription());
        productOffering.setParent(generalInfoDTO.getParent());
        productOffering.setWorkingStep("GeneralInfo");
        productOffering.setCategory(generalInfoDTO.getCategory());
        productOffering.setBS_externalId(generalInfoDTO.getBS_externalId());
        productOffering.setCS_externalId(generalInfoDTO.getCS_externalId());
        productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
        productOffering.setSellIndicator(generalInfoDTO.getSellIndicator());
        productOffering.setQuantityIndicator(generalInfoDTO.getQuantityIndicator());
        productOffering.setStatus("Working state");

        // Save the new ProductOffering
        ProductOffering savedProductOffering = productOfferingRepository.save(productOffering);
        return savedProductOffering;
    }


    @Override
    public ProductOffering getProductOfferingById(int Product_id) throws ProductOfferingNotFoundException {
        return productOfferingRepository.findById(Product_id)
                .orElseThrow(() -> new ProductOfferingNotFoundException("Product Offering not found with id: " + Product_id));
    }

    private void convertToEntity(GeneralInfoDTO generalInfoDTO, ProductOffering productOffering) {
        productOffering.setName(generalInfoDTO.getName());
        productOffering.setPoType(generalInfoDTO.getPoType());
        productOffering.setEffectiveFrom(generalInfoDTO.getEffectiveFrom());
        productOffering.setEffectiveTo(generalInfoDTO.getEffectiveTo());
        productOffering.setDescription(generalInfoDTO.getDescription());
        productOffering.setDetailedDescription(generalInfoDTO.getDetailedDescription());
        productOffering.setParent(generalInfoDTO.getParent());
        productOffering.setWorkingStep(generalInfoDTO.getWorkingStep());
        productOffering.setCategory(generalInfoDTO.getCategory());
        productOffering.setBS_externalId(generalInfoDTO.getBS_externalId());
        productOffering.setCS_externalId(generalInfoDTO.getCS_externalId());
        productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
        productOffering.setSellIndicator(generalInfoDTO.getSellIndicator());
        productOffering.setQuantityIndicator(generalInfoDTO.getQuantityIndicator());
        productOffering.setStatus(generalInfoDTO.getStatus());
    }

    private GeneralInfoDTO convertToDTO(ProductOffering productOffering) {
        GeneralInfoDTO generalInfoDTO = new GeneralInfoDTO();
        generalInfoDTO.setProduct_id(productOffering.getProduct_id());
        generalInfoDTO.setName(productOffering.getName());
        generalInfoDTO.setPoType(productOffering.getPoType());
        generalInfoDTO.setEffectiveFrom(productOffering.getEffectiveFrom());
        generalInfoDTO.setEffectiveTo(productOffering.getEffectiveTo());
        generalInfoDTO.setDescription(productOffering.getDescription());
        generalInfoDTO.setDetailedDescription(productOffering.getDetailedDescription());
        generalInfoDTO.setParent(productOffering.getParent());
        generalInfoDTO.setWorkingStep(productOffering.getWorkingStep());
        generalInfoDTO.setCategory(productOffering.getCategory());
        generalInfoDTO.setBS_externalId(productOffering.getBS_externalId());
        generalInfoDTO.setCS_externalId(productOffering.getCS_externalId());
        generalInfoDTO.setPoParent_Child(productOffering.getPoParent_Child());
        generalInfoDTO.setSellIndicator(productOffering.getSellIndicator());
        generalInfoDTO.setQuantityIndicator(productOffering.getQuantityIndicator());
        generalInfoDTO.setStatus(productOffering.getStatus());
        return generalInfoDTO;
    }

    @Override
    public ProductOffering updateProductOffering(GeneralInfoDTO generalInfoDTO, int Product_id,
                                                 int pr_id, int serviceId) throws ProductOfferingNotFoundException {
        ProductOffering productOffering = getProductOfferingById(Product_id);
        convertToEntity(generalInfoDTO, productOffering);
        productOffering.setPR_id(pr_id);
        productOffering.setServiceId(serviceId);
        return productOfferingRepository.save(productOffering);
    }

    @Override
    public ProductOffering updateProductOfferingEligibility(GeneralInfoDTO generalInfoDTO, int Product_id, int eligibility_id) throws ProductOfferingNotFoundException {
        ProductOffering productOffering = getProductOfferingById(Product_id);
        convertToEntity(generalInfoDTO, productOffering);
        productOffering.setEligibility_id(eligibility_id);

        return productOfferingRepository.save(productOffering);
    }


    @Override
    public List<GeneralInfoDTO> getAllGeneralInfoDTOs() {
        List<ProductOffering> productOfferings = generalInfoRepository.findAll();
        List<GeneralInfoDTO> dtos = new ArrayList<>();

        for (ProductOffering productOffering : productOfferings) {
            GeneralInfoDTO dto = convertToDTO(productOffering);
            dtos.add(dto);
        }
        return dtos;
    }

}
