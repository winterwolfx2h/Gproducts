package com.Bcm.Model.ProductOfferingABE.SubClasses.Family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponseDTO {
    private int po_FamilyCode;
    private String name;
    private String description;
    private List<SubFamilyResponseDTO> subFamilies;
}
