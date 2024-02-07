package com.Bcm.Model.ServiceABE.SubClasses;

import com.Bcm.Model.ServiceABE.ServiceBusinessInteractionConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SalesChannel")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int SC_code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "serviceBusinessInteractionConfig_id")
    private ServiceBusinessInteractionConfig serviceBusinessInteractionConfig;
}
