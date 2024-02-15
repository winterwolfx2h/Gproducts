package com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceOrderAndUsage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ROUUsageStatus")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ROUUsageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int RUSID;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "resourceOrderAndUsage_id")
    private ResourceOrderAndUsage resourceOrderAndUsage;
}