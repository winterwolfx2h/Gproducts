package com.Bcm.Model.ResourceSpecABE.LogicalResourceBE;


import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.LogicalResourceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "LogicalResource")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int LRID;

    @Column(name = "status", nullable = false)
    @OneToMany(mappedBy = "logicalResource")
    private List<LogicalResourceStatus> status;

    @Column(name = "validFor", nullable = false)
    private Date validFor;
}
