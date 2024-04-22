package com.Bcm.Model.Product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductSpecificationDTO {
    private String familyName;
    private String subFamily;
    private String category;
    private List<String> poPlanSHDES;
    private String BS_externalId;
    private String CS_externalId;


}
