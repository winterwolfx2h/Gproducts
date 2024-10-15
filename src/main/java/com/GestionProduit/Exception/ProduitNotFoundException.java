package com.GestionProduit.Exception;

public class ProduitNotFoundException extends RuntimeException {

  public ProduitNotFoundException(String msg) {
    super(msg);
  }
}
