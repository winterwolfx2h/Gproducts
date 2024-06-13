package com.Bcm.Model.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Id;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductDtoID {

  @Id private String Product_id;
}
