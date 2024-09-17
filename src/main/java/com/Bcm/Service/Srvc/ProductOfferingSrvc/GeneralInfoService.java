package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GeneralInfoService {

    GeneralInfoDTO getDTOById(int Product_id) throws ProductOfferingNotFoundException;

    ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO);

    List<GeneralInfoDTO> getAllGeneralInfoDTOs();

    ProductOffering getProductOfferingById(int Product_id) throws ProductOfferingNotFoundException;

    ProductOffering updatePOPrice(GeneralInfoDTO generalInfoDTO, int Product_id, int productPriceCode)
            throws ProductOfferingNotFoundException;

    ProductOffering updatePOBusinessProc(GeneralInfoDTO generalInfoDTO, int Product_id, int businessProcess_id)
            throws ProductOfferingNotFoundException;

    ProductOffering updatePOSrvcPr(GeneralInfoDTO generalInfoDTO, int Product_id, Integer pr_id, Integer serviceId)
            throws ProductOfferingNotFoundException;

    ProductOffering updatePOStockInd(GeneralInfoDTO generalInfoDTO, int productId, boolean stockInd)
            throws ProductOfferingNotFoundException;

    ProductOffering updateGeneralInfoDTO(GeneralInfoDTO generalInfoDTO, int productId)
            throws ProductOfferingNotFoundException;
}
