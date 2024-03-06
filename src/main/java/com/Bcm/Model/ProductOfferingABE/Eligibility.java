package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "Eligibility")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eligibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eligibilityId", nullable = false)
    private int eligibilityId;

    @Column(name = "channel", nullable = false)
    private String channel;
    }
