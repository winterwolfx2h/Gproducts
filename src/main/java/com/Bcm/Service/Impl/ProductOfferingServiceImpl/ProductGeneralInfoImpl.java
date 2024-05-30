package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.GeneralInfoRepository;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service

public class ProductGeneralInfoImpl implements GeneralInfoService

{
    final GeneralInfoRepository generalInfoRepository;
    final ProductOfferingRepository productOfferingRepository;




    @Override
    public ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO) {
        // Check if an existing product offering with the same name already exists
        Optional<GeneralInfoDTO> existingProduct = generalInfoRepository.findByName(generalInfoDTO.getName());
        if (existingProduct.isPresent()) {
            throw new ProductOfferingAlreadyExistsException(
                    "A product offering with the name '" + generalInfoDTO.getName() + "' already exists."
            );
        }

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
    public List<GeneralInfoDTO> getAllGeneralInfoDTOs() {
        List<ProductOffering> productOfferings = productOfferingRepository.findAll();
        List<GeneralInfoDTO> dtos = new ArrayList<>();

        for (ProductOffering productOffering : productOfferings) {
            GeneralInfoDTO dto = convertToDTO(productOffering);
            dtos.add(dto);
        }

        return dtos;
    }


    private GeneralInfoDTO convertToDTO(ProductOffering productOffering) {
        GeneralInfoDTO dto = new GeneralInfoDTO();
        dto.setProduct_id(productOffering.getProduct_id()); // Ensure the ID is set
        dto.setName(productOffering.getName());
        dto.setPoType(productOffering.getPoType());
        dto.setEffectiveFrom(productOffering.getEffectiveFrom());
        dto.setEffectiveTo(productOffering.getEffectiveTo());
        dto.setDescription(productOffering.getDescription());
        dto.setDetailedDescription(productOffering.getDetailedDescription());
        dto.setParent(productOffering.getParent());
        dto.setCategory(productOffering.getCategory());
        dto.setWorkingStep(productOffering.getWorkingStep());
        dto.setBS_externalId(productOffering.getBS_externalId());
        dto.setCS_externalId(productOffering.getCS_externalId());
        dto.setPoParent_Child(productOffering.getPoParent_Child());
        dto.setSellIndicator(productOffering.getSellIndicator());
        dto.setQuantityIndicator(productOffering.getQuantityIndicator());
        dto.setStatus(productOffering.getStatus());


        return dto;
    }


}
