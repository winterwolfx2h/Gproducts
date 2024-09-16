package com.Bcm.Model.ProductOfferingABE;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
