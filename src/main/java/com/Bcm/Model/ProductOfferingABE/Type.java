package com.Bcm.Model.ProductOfferingABE;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private int type_id;

    @Column(name = "typeName", nullable = false)
    private String typeName;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = BusinessProcess.class)
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private List<BusinessProcess> businessProcesses;
}
