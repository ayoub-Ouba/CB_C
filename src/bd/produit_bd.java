package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.produits;
import Model.produit;

public class produit_bd {
	// liste des produits 
	 public  produits liste_produit() {
		    String query = "SELECT Id_produit,image,label,prix,id_categorie FROM produit";
		    try (Connection conn = BaseDonnees.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(query)) {
		        
		       ResultSet res = stmt.executeQuery();
		       produits produits=new produits();
		       while(res.next()) {
		            	produit prd=new produit(res.getInt("Id_produit"),res.getString("label"),res.getFloat("prix"),res.getInt("id_categorie"));
		            	produits.ajouterproduit(prd);
		        }
		       return produits;
		        
		    } catch (SQLException e) {
		        System.err.println("Erreur " + e.getMessage());
		    }
		    
		    return null; 
		}
	 //ajouter produit
	 public  static boolean ajouter_prd_commande(int id_comd,int id_prod,int quantiter) {
		    String query = "INSERT INTO `détaille_commande`(id_produit,id_commande, Quantiter_commander) VALUES (?,?,?)";
		    
		    try (Connection conn = BaseDonnees.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(query)) {
		    	stmt.setInt(1, id_prod);
		    	stmt.setInt(2, id_comd);
		    	stmt.setInt(3, quantiter);
		    	int rowsInserted = stmt.executeUpdate();
	            return rowsInserted > 0;
		    } catch (SQLException e) {
		        System.err.println("Erreur  " + e.getMessage());
		    }
		    
		    return false; 
		}
}
