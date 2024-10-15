package com.GestionProduit.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Review")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "productId", nullable = true)
  private String productId;

  @Column(name = "content", nullable = true)
  private String content;

  @Column(name = "sentiment", nullable = true)
  private String sentiment;

}
