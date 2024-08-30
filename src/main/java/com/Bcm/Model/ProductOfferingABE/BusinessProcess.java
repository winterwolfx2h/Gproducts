package com.Bcm.Model.ProductOfferingABE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BusinessProcess")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessProcess_id", nullable = false)
    private int businessProcess_id;

    @Column(name = "businessProcess", nullable = false)
    private String name;

    @Column(name = "action", nullable = true)
    private String action;

    @Column(name = "actionDescription", nullable = true)
    private String actionDescription;
}
