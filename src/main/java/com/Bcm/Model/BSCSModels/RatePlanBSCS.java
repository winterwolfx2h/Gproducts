package com.Bcm.Model.BSCSModels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="RatePlanBSCS")
public class RatePlanBSCS {

    private String tmcode;
    private String des;
    private String shdes;


}
