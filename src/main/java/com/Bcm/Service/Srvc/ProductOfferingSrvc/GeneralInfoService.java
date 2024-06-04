package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface GeneralInfoService {

    ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO);

    List<GeneralInfoDTO> getAllGeneralInfoDTOs();

    ProductOffering getProductOfferingById(int Product_id) throws ProductOfferingNotFoundException;

    ProductOffering updateProductOffering(GeneralInfoDTO generalInfoDTO, int Product_id, int pr_id, int serviceId) throws ProductOfferingNotFoundException;
}
