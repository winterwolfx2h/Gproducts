package com.Bcm.Model.ProductOfferingABE;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "POPlan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PO_ID", nullable = false)
    private int PO_ID;

    @NotNull(message = "Name cannot be null")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Description cannot be null")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Parent cannot be null")
    @Column(name = "parent", nullable = false)
    private String parent;
}

