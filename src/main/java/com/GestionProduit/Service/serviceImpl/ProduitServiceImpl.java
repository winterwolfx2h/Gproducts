package com.GestionProduit.Service.serviceImpl;

import com.GestionProduit.Exception.ProduitNotFoundException;
import com.GestionProduit.Model.Category;
import com.GestionProduit.Model.Produit;
import com.GestionProduit.Model.Tax;
import com.GestionProduit.Repository.CategoryRepository;
import com.GestionProduit.Repository.ProduitRepository;
import com.GestionProduit.Repository.TaxRepository;
import com.GestionProduit.Service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProduitServiceImpl implements ProduitService {

  private static final Logger logger = LoggerFactory.getLogger(ProduitServiceImpl.class);
  private static final String TID = "Produit with ID ";
  final ProduitRepository produitRepository;
  final CategoryRepository categoryRepository;
  final TaxRepository taxRepository;

  @Override
  public Produit create(Produit produit) {
    logger.info("Creating produit: {}", produit);
    if (produitRepository.findByNom(produit.getNom()).isPresent()) {
      logger.warn("Produit with name {} already exists", produit.getNom());
      throw new ProduitNotFoundException("Produit with name " + produit.getNom() + " already exists");
    }

    if (produit.getCatp() == null || !categoryRepository.existsById(produit.getCatp().getId())) {
      logger.warn("Invalid category ID for produit: {}", produit);
      throw new ProduitNotFoundException("Invalid category ID!");
    }

    Produit savedProduit = produitRepository.save(produit);
    logger.info("Produit created successfully: {}", savedProduit);
    return savedProduit;
  }

  @Override
  public List<Produit> searchByKeywords(List<String> keywords) {
    List<Produit> foundProducts = new ArrayList<>();
    for (String keyword : keywords) {
      List<Produit> products = produitRepository.findByNomContaining(keyword);
      foundProducts.addAll(products);
    }
    return foundProducts.stream().distinct().toList();
  }

  @Override
  public List<Produit> read() {
    logger.info("Retrieving all produits");
    List<Produit> produits = produitRepository.findAll();

    for (Produit produit : produits) {
      if (produit.getCatp() != null) {
        produit.setCategoryId(produit.getCatp().getId());
      }
    }
    logger.info("Total produits retrieved: {}", produits.size());
    return produits;
  }

  @Override
  public Produit update(Long id, Produit produit) {
    logger.info("Updating produit with ID: {}", id);
    Optional<Produit> existingProduitOpt = produitRepository.findById(id);

    if (!existingProduitOpt.isPresent()) {
      String msg = "Produit not found with ID: %d".formatted(id);
      logger.warn(msg);
      throw new EntityNotFoundException(msg);
    }

    Produit existingProduit = existingProduitOpt.get();

    if (produit.getCategoryId() != null) {
      Optional<Category> categoryOpt = categoryRepository.findById(produit.getCategoryId());
      if (!categoryOpt.isPresent()) {
        String warningMsg = "Invalid category ID for produit update: %d".formatted(produit.getCategoryId());
        logger.warn(warningMsg);
        throw new IllegalArgumentException("Invalid category ID!");
      }
      existingProduit.setCatp(categoryOpt.get());
    }

    existingProduit.setNom(produit.getNom());
    existingProduit.setPrix(produit.getPrix());
    existingProduit.setQuantite(produit.getQuantite());
    existingProduit.setCategoryId(produit.getCategoryId());

    Produit updatedProduit = produitRepository.save(existingProduit);
    logger.info("Produit updated successfully: {}", updatedProduit);
    return updatedProduit;
  }

  @Override
  public String delete(long id) {
    logger.info("Deleting produit with ID: {}", id);
    try {
      produitRepository.deleteById(id);
      String message = TID + id + " was successfully deleted";
      logger.info(message);
      return message;
    } catch (IllegalArgumentException e) {
      logger.error("Invalid argument for deleting produit: {}", e.getMessage());
      throw new ProduitNotFoundException("Invalid argument provided for deleting Produit");
    }
  }

  @Override
  public Produit findById(long id) {
    logger.info("Finding produit with ID: {}", id);
    Optional<Produit> optionalProduit = produitRepository.findById(id);
    return optionalProduit.orElseThrow(
        () -> {
          String message = "%s not found".formatted(TID + id);
          logger.warn(message);
          return new ProduitNotFoundException(message);
        });
  }

  @Override
  public boolean existsById(long id) {
    logger.info("Checking existence for produit ID: {}", id);
    return produitRepository.existsById(id);
  }

  @Override
  public Optional<Produit> searchByName(String nom) {
    logger.info("Searching for produit by name: {}", nom);
    return produitRepository.findByNom(nom);
  }

  @Override
  public Map<String, Object> calculatePriceWithTax(double prix, List<Integer> taxCodes) {
    logger.info("Calculating price with tax for original price: {} and tax codes: {}", prix, taxCodes);

    List<Tax> applicableTaxes = taxRepository.findAllById(taxCodes);

    if (applicableTaxes.isEmpty()) {
      logger.error("No applicable taxes found for provided tax codes: {}", taxCodes);
      throw new RuntimeException("No applicable taxes found for provided tax codes.");
    }

    List<Map<String, Object>> ttcList = new ArrayList<>();
    double ht = prix;

    for (Tax tax : applicableTaxes) {
      double taxValue = (prix * tax.getValue()) / 100;
      double ttc = prix + taxValue;

      Map<String, Object> ttcDetails = new HashMap<>();
      ttcDetails.put("taxCode", tax.getTaxCode());
      ttcDetails.put("taxValue", taxValue);
      ttcDetails.put("TTC", ttc);
      ttcDetails.put("TaxRate", tax.getValue());

      ttcList.add(ttcDetails);
      logger.info("Tax calculation details: {}", ttcDetails);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("HT", ht);
    result.put("TTCs", ttcList);

    logger.info("Price calculation completed successfully. Result: {}", result);
    return result;
  }
}
