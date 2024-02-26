package com.Bcm.Model.ProductOfferingABE;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "POAttributes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POCharacteristicType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int poCharType_code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "intCharID", nullable = false)
    private int intCharID;

    @Column(name = "extCharID", nullable = false)
    private int extCharID;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "po_ProviderCode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Provider provider;
}
