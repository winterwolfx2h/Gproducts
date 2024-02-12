package com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "PhysicalComponent")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int PCpID;

    @Column(name = "replaceable", nullable = false)
    private Boolean replaceable;

    @Column(name = "hotSwappable", nullable = false)
    private Boolean hotSwappable;

    @Column(name = "removable", nullable = false)
    private Boolean removable;

    @Column(name = "isConfigurable", nullable = false)
    private Boolean isConfigurable;

}
