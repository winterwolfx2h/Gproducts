package com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "PhysicalPort")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysicalPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int PPID;

    @Column(name = "portType", nullable = false)
    private String portType;

    @Column(name = "ifType", nullable = false)
    private String ifType;

    @Column(name = "portNumber", nullable = false)
    private int portNumber;

    @Column(name = "duplexMode", nullable = false)
    private String duplexMode;

}
