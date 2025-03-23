package controller;

import basedonne.Client_bd;
import model.Client;
import view.ClientView;

public class ClientControleer {
    private Client_bd client_bd;
    private ClientView view;

    public ClientControleer(ClientView view) {
        this.client_bd = new Client_bd();
        this.view = view;
    }

    public Client ajouterClient() {
        String prenom = view.getInput("Entrez le prénom du client : ");
        String nom = view.getInput("Entrez le nom du client : ");
        String tele = view.getInput("Entrez le numéro de téléphone du client : ");
        String email = view.getInput("Entrez l'email du client : ");

        int id_client = client_bd.ajouter_client(nom, prenom, tele, email);

        if (id_client != 0) {
            view.afficherMessage("Le client a bien été ajouté.");
            return new Client(id_client, nom, prenom, tele, email);
        } else {
            view.afficherMessage("Un problème est survenu, le client n'a pas été ajouté.");
            return null;
        }
    }

    public Client trouverClient() {
        String prenom = view.getInput("Entrez le prénom du client recherché : ");
        String nom = view.getInput("Entrez le nom du client recherché : ");

        Client client = client_bd.find_client(nom, prenom);

        if (client != null) {
            view.afficherMessage("Le client a été trouvé.");
            return client;
        } else {
            view.afficherMessage("Aucun client trouvé avec ce nom et prénom.");
            return null;
        }
    }
}
