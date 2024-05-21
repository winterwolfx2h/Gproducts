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

    @Column(name = "stock_Indicator")
    private Boolean stock_Indicator;

    @ElementCollection
    @CollectionTable(name = "eligibility_channel", joinColumns = @JoinColumn(name = "eligibilityId"))
    @Column(name = "channelName", nullable = true)
    private List<String> channels;
}
