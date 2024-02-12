package com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ResourceConfigVersion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceConfigVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int RCVID;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "resourceConfiguration_id")
    private ResourceConfiguration resourceConfiguration;
}
