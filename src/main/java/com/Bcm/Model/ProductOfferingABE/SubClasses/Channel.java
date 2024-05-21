package com.Bcm.Model.ProductOfferingABE.SubClasses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "po_ChannelCode", nullable = false)
    private int po_ChannelCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;
}