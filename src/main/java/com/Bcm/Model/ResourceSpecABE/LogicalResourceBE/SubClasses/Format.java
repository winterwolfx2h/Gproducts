package com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResourceSpecVersion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Format")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int FID;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "logicalResourceSpecVersion_id")
    private LogicalResourceSpecVersion logicalResourceSpecVersion;
}