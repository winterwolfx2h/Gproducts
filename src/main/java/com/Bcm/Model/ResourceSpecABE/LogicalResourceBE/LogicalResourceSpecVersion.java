package com.Bcm.Model.ResourceSpecABE.LogicalResourceBE;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.Format;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Table(name = "LogicalResourceSpecVersion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogicalResourceSpecVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int LRSVID;

    @Column(name = "format", nullable = false)
    @OneToMany(mappedBy = "logicalResourceSpecVersion")
    private List<Format> format;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

}