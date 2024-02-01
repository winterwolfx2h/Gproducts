package com.Bcm.Model.ProductOfferingABE;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "POPlan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int PO_ID;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String description;

    @ApiModelProperty(required = true)
    private String parent;


}


