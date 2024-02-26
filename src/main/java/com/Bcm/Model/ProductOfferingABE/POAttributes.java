package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "POAttributes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poAttribute_code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shortCode", nullable = false)
    private int shortCode;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "description", nullable = true)
    private String description;


}
