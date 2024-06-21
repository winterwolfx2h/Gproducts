package com.Bcm.Model.ProductOfferingABE.SubClasses;

import com.Bcm.Model.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "Channel")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_seq_generator")
    @SequenceGenerator(name = "channel_seq_generator", sequenceName = "channel_sequence", allocationSize = 1)
    @Column(name = "channelCode")
    private int channelCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class)
    @JoinColumn(name = "channelCode")
    @JsonIgnore
    private List<Product> products;
}
