import controller.ClientControleer;
import controller.LoginController;
import controller.CommandeController;
import controller.ProduitController;
import model.Client;
import model.Utilisateur;
import model.Produits;
import view.CommandView;
import view.ClientView;
import view.LoginView;
import view.ProduitView;
public class Main {

	    public static void main(String[] args) {
	    	LoginView view = new LoginView();
	    	LoginController controller = new LoginController(view);
	    	Utilisateur user_connecter =controller.login();
	    	if(user_connecter!=null) {
	      
	    		ClientView view_client = new ClientView();
		       ClientControleer controller_client = new ClientControleer(view_client);
		        //ajouter Client 
		       Client client=controller_client.ajouter_client();
		        //trouver client
		        //Client client=controller_client.find_client();
		        if(client!=null) {
		        
	    		
		        ProduitController produit_controller=new ProduitController();
	 	        //liste produits
		        Produits produits=produit_controller.liste_produit();
		 	        
		        //ajouter une commande 
		        CommandView view_commande = new CommandView();
		        CommandeController controller_commande = new CommandeController(view_commande,user_connecter,client);
			      controller_commande.commander(produits);

		        }
	    }
	   }



}
