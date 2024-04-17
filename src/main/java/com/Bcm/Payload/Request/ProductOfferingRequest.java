package com.Bcm.Payload.Request;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProductOfferingRequest {
    private List<ProductOffering> productOfferings;

}
