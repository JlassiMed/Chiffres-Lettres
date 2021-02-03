/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import Metier.NbEtchifImplements;
import Metier.User;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class InscriptionController implements Initializable {

       @FXML TextField pseudo;
    @FXML TextField nom;
    @FXML TextField prenom;
    @FXML TextField cin;
    @FXML DatePicker date_naiss;
    @FXML PasswordField password;
    @FXML ImageView photo;
    NbEtchifImplements nbchf= new NbEtchifImplements();
    boolean photoChanged=false;
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
    public boolean testAlpha(String ch)
    {
    String alpha="azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN";
       for(int i=0;i<ch.length();i++)
      {
          if(alpha.contains(String.valueOf(ch.charAt(i)))==false)
              return false;
      }
        return true;
    }
     public boolean testNum(String ch)
    {
    String num="0123456789";
       for(int i=0;i<ch.length();i++)
      {
          if(num.contains(String.valueOf(ch.charAt(i)))==false)
              return false;
      }
        return true;
    }
     @FXML
    private void handleButtonActionSavePhoto(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
              
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = 
                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
            FileChooser.ExtensionFilter extFilterjpg = 
                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG = 
                    new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            FileChooser.ExtensionFilter extFilterpng = 
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fileChooser.getExtensionFilters()
                    .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
 
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
             
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);
            } catch (IOException ex) {
                System.out.print(ex.getMessage());
            }
            photoChanged=true;
       
    }
     @FXML
     private void handleButtonActionAjout(ActionEvent event) {
            
            if(!pseudo.getText().equals("") && !nom.getText().equals("")&&!prenom.getText().equals("") && !password.getText().equals("")&& !cin.getText().equals("") )
            {
            User u= nbchf.findUser(nbchf.Admins(nbchf.AllUs()),pseudo.getText());
            if(u != null)
            { 
                javax.swing.JOptionPane.showMessageDialog(null,"pseudo existant");
            }
            
            else if(!testAlpha(nom.getText()) || !testAlpha(prenom.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le nom et le prenom doivent contenir seulement des caractères alphabet !");
            }
            else if(cin.getText().length()!=8 || !testNum(cin.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le cin doit être de longueur 8 et contient seulement des chiffres !");
            }
            else if(date_naiss.getValue()==null)
            {
                javax.swing.JOptionPane.showMessageDialog(null,"Veuillez remplir la date de naissance !");

            }
            
            else if(!photoChanged)
            {
                javax.swing.JOptionPane.showMessageDialog(null,"Veuillez choisir un photo");
            }
            else
            {
                
                try
                {
                User us;
                us = new User();
                
                us.setPseudo_us(pseudo.getText());
                us.setPassword_us(getEncodedPassword(password.getText()));
                us.setNom_us(nom.getText());
                us.setPrenom_us(prenom.getText());
                us.setCin_us(cin.getText());
                Date l = Date.valueOf(date_naiss.getValue());
                us.setDatenaiss_us(l);
                us.setMeilscore_us(0);
                us.setType_us("N");
                
                
                BufferedImage bImage = SwingFXUtils.fromFXImage(photo.getImage(), null);
                    try (
                        ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                        ImageIO.write(bImage, "jpg", s);
                        Blob b= new SerialBlob(s.toByteArray());
                        us.setPhoto_us(b);
                    } catch (IOException ex) {
                        System.out.print(ex.getMessage()+" in ajout");
                    } catch (SQLException ex) {
                        System.out.print(ex.getMessage()+" in bt ajout");                    }
                
                nbchf.addUser(us);
                Stage current = (Stage)(password.getScene().getWindow()) ;
                current.close();
                javax.swing.JOptionPane.showMessageDialog(null,"Inscrpiton Réussite");
                
                }catch(HeadlessException e)
                {
                    System.out.print(e.getMessage()+" in bouton ajout");
                }
                    
                }
            }
             else
                 javax.swing.JOptionPane.showMessageDialog(null,"remplir tous les champs svp !");
                
            }

    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    
    
}
