/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import DAO.DbConnect;
import Metier.NbEtchifImplements;
import Metier.User;
import static chiffreslettres.FXMLDocumentController.ValidatedUser;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Random;
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

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class LoginJeuController implements Initializable {
    Connection c= DbConnect.getConnection();
    NbEtchifImplements nbchf= new NbEtchifImplements();
    @FXML private TextField pseudoj1,pseudoj2,mdpj1,mdpj2;
    public static User ValidatedJoueur1,ValidatedJoueur2;// les deux joueurs qui vont disputer le jeu
    public static int CompteurJeu; // compteur d'épreuves incrémenté par 1 après chaque épreuve (0 est l'épreuve de sézame)
    public static int scoreTotJ1;// score totale j1 
    public static int scoreTotJ2;// score toltale j2
    public static int scoreCBJ1;// score compte est bon j1 
    public static int scoreCBJ2;// score compte est bon j2
    public static int scoreMLPJ1;// score mot le plus long j1 
    public static int scoreMLPJ2;// score mot le plus long j2
    public static int sezame;// 0 aucun joueur n'a de sézame ; 1: joueur 1 a le sez; 2: Jeoeur 2 a le sézame
    public static User JoueurGagnant;// joueur gagnat affecter après terminaison de 10 épreuves et qui va jouer l"épreuve finale 
    
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
        
            String us1= pseudoj1.getText();
            String ps1= getEncodedPassword(mdpj1.getText());
            String us2= pseudoj2.getText();
            String ps2= getEncodedPassword(mdpj2.getText());
            
            if(!us1.equals("") && !ps1.equals("") && !us2.equals("") && !ps2.equals(""))
            {
            User u1= nbchf.findUser(nbchf.Admins(nbchf.AllUs()),us1);
            User u2= nbchf.findUser(nbchf.Admins(nbchf.AllUs()),us2);
            
            if(u1 != null && u2!= null)
            {
            if(us1.equals(us2))
                javax.swing.JOptionPane.showMessageDialog(null,"Connexion impossible avec le même Joueur");
            else
            if(u1.getPassword_us().equals(ps1) && u2.getPassword_us().equals(ps2))
            {
                ValidatedJoueur1 = u1;
                ValidatedJoueur2 = u2;
                Random rand= new Random();
                int choix= rand.nextInt((2-1)+1)+1;
                Parent p;
                if(choix==1)
                {p = FXMLLoader.load(getClass().getResource("LeCompteEstBon.fxml"));}
                else
                {p = FXMLLoader.load(getClass().getResource("MotLePlusLong.fxml"));}  
            Scene n = new Scene(p);
            Stage stage = new Stage();
            stage.setScene(n);
            stage.show();
                Stage current = (Stage)(pseudoj1.getScene().getWindow()) ;
                current.close();
            }
            else if(!u1.getPassword_us().equals(ps1))
            javax.swing.JOptionPane.showMessageDialog(null,"mot de passe incorrect Joueur 1");
            else if(!u2.getPassword_us().equals(ps2))
            javax.swing.JOptionPane.showMessageDialog(null,"mot de passe incorrect Joueur 2");
            }
            else if(u1==null)
            javax.swing.JOptionPane.showMessageDialog(null,"pseudo Joueur 1 introuvable");
            else
               javax.swing.JOptionPane.showMessageDialog(null,"pseudo Joueur 2 introuvable");
             
            }
            else if(us1.equals(""))
            javax.swing.JOptionPane.showMessageDialog(null,"remplir Pseudo j1");
            else if(ps1.equals(""))
            javax.swing.JOptionPane.showMessageDialog(null,"remplir mot de passe j1");
            else if(us2.equals(""))
            javax.swing.JOptionPane.showMessageDialog(null,"remplir Pseudo j2");
            else if(ps2.equals(""))
            javax.swing.JOptionPane.showMessageDialog(null,"remplir mot de passe j2");
        

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
       ValidatedJoueur1=new User();
       ValidatedJoueur2=new User();
    CompteurJeu=0;
    scoreTotJ1=0;
    scoreTotJ2=0;// score toltale j2
    scoreCBJ1=0;// score compte est bon j1 
    scoreCBJ2=0;// score compte est bon j2
    scoreMLPJ1=0;// score mot le plus long j1 
    scoreMLPJ2=0;// score mot le plus long j2
   sezame=0;// 0 aucun joueur n'a de sézame ; 1: joueur 1 a le sez; 2: Jeoeur 2 a le sézame
    JoueurGagnant=new User();
            
            
    }    
    
}
