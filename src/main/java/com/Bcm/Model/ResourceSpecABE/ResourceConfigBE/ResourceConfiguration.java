package com.Bcm.Model.ResourceSpecABE.ResourceConfigBE;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ResourceConfigVersion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Table(name = "ResourceConfiguration")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int LRID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "validFor", nullable = false)
    private Date validFor;

    @Column(name = "version", nullable = false)
    @OneToMany(mappedBy = "resourceConfiguration")
    private List<ResourceConfigVersion> version;

    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;

    @Column(name = "description", nullable = false)
    private String description;


}