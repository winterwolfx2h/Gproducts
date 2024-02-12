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
@Table(name = "poplan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TMCODE", nullable = false)
    private int TMCODE;
/*
    @NotNull(message = "TMCODE cannot be null")
    @Column(name = "TMCODE", nullable = false)
    private String TMCODE;
*/
    @NotNull(message = "Description cannot be null")
    @Column(name = "DES", nullable = false)
    private String DES;

    @NotNull(message = "SHDES cannot be null")
    @Column(name = "SHDES", nullable = false)
    private String SHDES;

    @NotNull(message = "Parent cannot be null")
    @Column(name = "parent", nullable = false)
    private String parent;
}

