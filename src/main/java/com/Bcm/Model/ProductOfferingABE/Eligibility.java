package com.Bcm.Model.ProductOfferingABE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection
    @Column(name = "channel")
    private List<String> channel;
}
