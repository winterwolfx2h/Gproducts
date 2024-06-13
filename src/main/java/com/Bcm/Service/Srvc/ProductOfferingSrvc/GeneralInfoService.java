package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface GeneralInfoService {

  ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO);

  List<GeneralInfoDTO> getAllGeneralInfoDTOs();

  ProductOffering getProductOfferingById(int Product_id) throws ProductOfferingNotFoundException;

  ProductOffering updateProductOffering(
      GeneralInfoDTO generalInfoDTO,
      int Product_id,
      int businessProcess_id,
      int pr_id,
      int serviceId,
      int productPriceCode,
      int productPriceGroupCode)
      throws ProductOfferingNotFoundException;

  ProductOffering updateProductOfferingEligibility(
      GeneralInfoDTO generalInfoDTO, int Product_id, int channelCode, int entityCode, int eligibility_id)
      throws ProductOfferingNotFoundException;
}
