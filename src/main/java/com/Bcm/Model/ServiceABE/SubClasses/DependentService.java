package com.Bcm.Model.ServiceABE.SubClasses;

import com.Bcm.Model.ServiceABE.ServiceSpecConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "DependentService")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DependentService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int DS_code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "serviceSpecConfig_id")
    private ServiceSpecConfig serviceSpecConfig;
}