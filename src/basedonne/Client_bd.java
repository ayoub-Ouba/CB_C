package basedonne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Client;

public class Client_bd {
	
	 public int ajouter_client(String nom,String prenom,String tele,String email) {
	        String query = "INSERT INTO `client`(`nom`, `prenom`, `tele`, `Email`) VALUES (?, ?, ?, ?)";

	        try (Connection conn = BaseDonnees.getConnection(); 
	             PreparedStatement stmt = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)) {            

	            // Injection des valeurs
	            stmt.setString(1, nom);  
	            stmt.setString(2, prenom);              
	            stmt.setString(3, tele);                  
	            stmt.setString(4, email);                                 

	            // Exécuter la requête
	            int rowsInserted = stmt.executeUpdate();
	        
	            if (rowsInserted > 0) {
	                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { 
	                    if (generatedKeys.next()) {
	                        int idclient = generatedKeys.getInt(1);
	                        return idclient;
	                    }
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	           
	        }
	        return 0;
	    }

	 public Client find_client(String nom, String prenom) {
		    String query = "SELECT Id_Client, nom, prenom, tele, Email FROM client WHERE nom = ? AND prenom = ?";
		    
		    try (Connection conn = BaseDonnees.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(query)) {
		        
		        stmt.setString(1, nom);
		        stmt.setString(2, prenom);
		        
		        try (ResultSet res = stmt.executeQuery()) {  // Ajout de try-with-resources pour ResultSet
		            if (res.next()) {
		                return new Client(
		                    res.getInt("Id_Client"),
		                    res.getString("nom"),
		                    res.getString("prenom"),
		                    res.getString("tele"),
		                    res.getString("Email")
		                );
		            }
		        }
		        
		    } catch (SQLException e) {
		        System.err.println("Erreur lors de la recherche du client : " + e.getMessage());
		    }
		    
		    return null; // Aucun client trouvé
		}
}
