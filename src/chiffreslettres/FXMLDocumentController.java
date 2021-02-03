/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import DAO.DbConnect;
import Metier.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Metier.NbEtchifImplements;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import javafx.scene.control.Button;

/**
 *
 * @author Emir
 */
public class FXMLDocumentController implements Initializable {
   
    Connection c= DbConnect.getConnection();
    NbEtchifImplements nbchf= new NbEtchifImplements();
    @FXML
    private Button inscrit;
    @FXML private TextField user_pseudo;
    @FXML private TextField password;
    public static User ValidatedUser;
    
    
    public static String getEncodedPassword(String key) {
	  byte[] uniqueKey = key.getBytes();
	  byte[] hash = null;
	  try {
		hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
	  } catch (NoSuchAlgorithmException e) {
		throw new Error("no MD5 support in this VM");
	  }
	  StringBuffer hashString = new StringBuffer();
	  for ( int i = 0; i < hash.length; ++i ) {
		String hex = Integer.toHexString(hash[i]);
		if ( hex.length() == 1 ) {
		  hashString.append('0');
		  hashString.append(hex.charAt(hex.length()-1));
		} else {
		  hashString.append(hex.substring(hex.length()-2));
		}
	  }
	  return hashString.toString();
	}
    @FXML
    
    private void handleButtonAction(ActionEvent event) throws IOException {
        
      
            
            
            String us= user_pseudo.getText();
            String ps= getEncodedPassword(password.getText());
            
            if(!us.equals("") && !ps.equals(""))
            {
            User u= nbchf.findUser(nbchf.AllUs(),us);
            if(u != null)
            {
            if(u.getPassword_us().equals(ps))
                
            {
                ValidatedUser = u;
                if(u.getType_us().equals("A"))
                {  Parent p = FXMLLoader.load(getClass().getResource("EspaceAdmin.fxml"));
            Scene n = new Scene(p);
            Stage stage = new Stage();
            stage.setScene(n);
            stage.show();
            user_pseudo.setText("");
            password.setText("");
                }
                else
                {
                Parent p = FXMLLoader.load(getClass().getResource("EspaceUtilisateur.fxml"));
            Scene n = new Scene(p);
            Stage stage = new Stage();
            stage.setScene(n);
            stage.show();
            user_pseudo.setText("");
            password.setText("");
                
                }
            }
            else
            javax.swing.JOptionPane.showMessageDialog(null,"mot de passe incorrect");
            }
            else
            javax.swing.JOptionPane.showMessageDialog(null,"pseudo introuvable");
            }
            else if(us.equals(""))
            javax.swing.JOptionPane.showMessageDialog(null,"remplir Pseudo");
            else
            javax.swing.JOptionPane.showMessageDialog(null,"remplir mot de passe");
            
        

    }
    
    @FXML

    private void handleButtonAction2(ActionEvent event) throws IOException {
        
        Parent p = FXMLLoader.load(getClass().getResource("LoginJeu.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
    }
    @FXML
    private void handleButtonActionInscrit(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("Inscription.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
