package com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "LogicalResourceStatus")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResourceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int LRSID;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "logicalResource_id")
    private LogicalResource logicalResource;
}

