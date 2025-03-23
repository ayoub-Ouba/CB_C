package controller;

import java.sql.Timestamp;
import java.time.Instant;

import basedonne.Command_bd;
import basedonne.Produit_bd;
import model.Client;
import model.Produits;
import model.Utilisateur;
import view.CommandView;
import view.ProduitView;

public class CommandeController {
    private Command_bd command_bd;
    private CommandView view;
    private Utilisateur utilisateur;
    private ProduitView view_prod;
    private Client client;

    public CommandeController(CommandView view, Utilisateur user, Client client) {
        this.command_bd = new Command_bd();
        this.view_prod = new ProduitView();
        this.view = view;
        this.utilisateur = user;
        this.client = client;
    }

    public boolean supprimerCommande(int id) {
        return command_bd.supprimerCommande(id);
    }

    public boolean commander(Produits produits) {
        // Date et heure de commande
        Timestamp date_commande = Timestamp.from(Instant.now());

        int id_commande = command_bd.ajouter_commande(date_commande, utilisateur.getid(), client.getid());

        boolean ajoutProduit = true;
        while (ajoutProduit) {
            // Afficher les informations des produits
            for (int i = 0; i < produits.produits.size(); i++) {
                produits.produits.get(i).information();
            }
            // Entrer le produit + la quantité 
            int produit_choisi = view_prod.getInputQ("Choisissez l'ID du produit : ");
            int quantite_cmd = view_prod.getInputQ("Entrez la quantité commandée : ");

            if (Produit_bd.ajouter_prd_commande(id_commande, produit_choisi, quantite_cmd)) {
                System.out.println("Le produit a été ajouté.");
            }

            String rep = view.getInput("Voulez-vous ajouter un autre produit ? (oui/non) : ");
            if (rep.equalsIgnoreCase("oui")) {
                ajoutProduit = true;
            } else if (rep.equalsIgnoreCase("non")) {
                ajoutProduit = false;
                System.out.println("Votre commande a bien été ajoutée.");
            } else {
                System.out.println("Réponse invalide. Fin de la commande.");
                return false;
            }
        }
        return true;
    }
}

