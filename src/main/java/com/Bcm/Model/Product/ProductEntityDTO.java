package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class ProductEntityDTO {

    private Integer productId;
    private Integer entityCode;
}
