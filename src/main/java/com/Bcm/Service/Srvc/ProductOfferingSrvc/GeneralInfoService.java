package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public interface GeneralInfoService {





    ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO);

    List<GeneralInfoDTO> getAllGeneralInfoDTOs();

//    GeneralInfoDTO updateGeneralInfoDTO(int po_code, GeneralInfoDTO updatedDTO);
}
