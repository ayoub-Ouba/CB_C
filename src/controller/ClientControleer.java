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

	    public Client ajouter_client() {
	        String prenom = view.getInput("Entrez le prenom de client :");
	        String nom = view.getInput("Entrez le nom de client :");
	        String tele = view.getInput("Entrez le numero de telephone du client :");
	        String email = view.getInput("Entrez l'email de client :");
	        int id_client=client_bd.ajouter_client(nom,prenom,tele,email);
	        if(id_client!=0) {
	        	view.afficherMessage("le client est bien ajouté");
	        	return new Client(id_client,nom,prenom,tele,email);
		                   
	        	
	        	
	        }else {
	        	view.afficherMessage("il y a un probleme le client n'est pas ajouter");
	        	
	        }
	        return null;
	        
	    }
	    
	    public Client find_client() {
	        String prenom = view.getInput("Entrez le prenom de client chercher :");
	        String nom = view.getInput("Entrez le nom de client chercher :");
	        

	        if(client_bd.find_client(nom,prenom)!=null) {
	        	
	        	view.afficherMessage("le client est ce trouve");
	        	return client_bd.find_client(nom,prenom);
	        	
	        }else {
	        	view.afficherMessage("Il y a pas client avec ce nom et prenom ");
	        }
	        return null;
	        
	    }
}
