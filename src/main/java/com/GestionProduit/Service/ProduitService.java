package com.GestionProduit.Service;

import com.GestionProduit.Model.Produit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface ProduitService {

  Produit create(Produit produit);

    List<Produit> searchByKeywords(List<String> keywords);

    List<Produit> read();

  Produit update(Long id, Produit produit);

  String delete(long id);

  Produit findById(long id);

  boolean existsById(long id);

  Optional<Produit> searchByName(String nom);

  Map<String, Object> calculatePriceWithTax(double prix, List<Integer> taxCodes);
}
