package com.Bcm.Model.Product;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;

public class GeneralInfoMapper {

  private GeneralInfoMapper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
  }

  public static GeneralInfoDTO toDTO(ProductOffering productOffering) {
    if (productOffering == null) {
      return null;
    }
    GeneralInfoDTO dto = new GeneralInfoDTO();
    dto.setProduct_id(productOffering.getProduct_id());
    dto.setName(productOffering.getName());
    dto.setEffectiveFrom(productOffering.getEffectiveFrom());
    dto.setEffectiveTo(productOffering.getEffectiveTo());
    dto.setDescription(productOffering.getDescription());
    dto.setDetailedDescription(productOffering.getDetailedDescription());
    dto.setPoType(productOffering.getPoType());
    dto.setParent(productOffering.getParent());
    dto.setWorkingStep(productOffering.getWorkingStep());
    dto.setSellInd(productOffering.getSellInd());
    dto.setQuantityInd(productOffering.getQuantityInd());
    dto.setCategory(productOffering.getCategory());
    dto.setBS_externalId(productOffering.getBS_externalId());
    dto.setCS_externalId(productOffering.getCS_externalId());
    dto.setStatus(productOffering.getStatus());
    dto.setPoParent_Child(productOffering.getPoParent_Child());
    return dto;
  }
}
