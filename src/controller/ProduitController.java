package controller;

import basedonne.Produit_bd;
import basedonne.Produit_bd;
import model.Produit;
import model.Produits;
import view.ProduitView;

public class ProduitController {
    private Produit_bd produit_bd;
    private Produit produit;

    public ProduitController() {
        this.produit_bd = new Produit_bd();
    }

    public Produits liste_produit() {
        return produit_bd.liste_produit();
    } 
}
