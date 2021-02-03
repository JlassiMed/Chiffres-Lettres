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
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class EspaceUtilisateurController implements Initializable {

    @FXML Label meil_score,nb_parties,nb_parties_gagnées,welcome;
      @FXML TextField pseudo;
    @FXML TextField nom;
    @FXML TextField prenom;
    @FXML TextField cin;
    @FXML DatePicker date_naiss;
    @FXML PasswordField password;
    @FXML ImageView photo;
    NbEtchifImplements nbchf= new NbEtchifImplements();
    @FXML PieChart pieChartDiagrame;
    @FXML LineChart<String,Integer> lineChartDiagrame;
    @FXML NumberAxis xaxeLC,yaxisSC,yaxisSB;
    @FXML CategoryAxis yaxeLC,xaxisSC,xaxisSB;
    @FXML BarChart barChartDiagrame;
    @FXML ScatterChart scatterBarChartDiagrame;
    @FXML StackedBarChart stackedBarChartDiagrame;
    
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
    private void handleButtonActionUpdateUser(ActionEvent event)  {
        
             if(!testAlpha(nom.getText()) || !testAlpha(prenom.getText()))
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
         else
         try{   
                User ActualUser= new User();    
                ActualUser.setPseudo_us(pseudo.getText());
                ActualUser.setPassword_us(getEncodedPassword(password.getText()));
                ActualUser.setNom_us(nom.getText());
                ActualUser.setPrenom_us(prenom.getText());
                ActualUser.setCin_us(cin.getText());
                Date l = Date.valueOf(date_naiss.getValue());
                ActualUser.setDatenaiss_us(l);
                ActualUser.setMeilscore_us(0);
                ActualUser.setType_us("N");
                BufferedImage bImage = SwingFXUtils.fromFXImage(photo.getImage(), null);
                    try (
                        ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                        ImageIO.write(bImage,"jpg", s);
                        Blob b= new SerialBlob(s.toByteArray());
                        ActualUser.setPhoto_us(b);
                    } catch (IOException ex) {
                        System.out.print(ex.getMessage()+" Admin in modif IO ");
                    } catch (SQLException ex) {
                        System.out.print(ex.getMessage()+" Admin in bt modif sql");                    }
                
         nbchf.UpdateUser(ActualUser);
                         javax.swing.JOptionPane.showMessageDialog(null,"Administrateur modifié avec succés");
     }catch(HeadlessException ex)
     {}
    
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
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcome.setText("Bienvenue "+FXMLDocumentController.ValidatedUser.getPseudo_us());
       pseudo.setText(FXMLDocumentController.ValidatedUser.getPseudo_us());
       pseudo.setDisable(true);
        nom.setText(FXMLDocumentController.ValidatedUser.getNom_us());
        prenom.setText(FXMLDocumentController.ValidatedUser.getPrenom_us());
        cin.setText(FXMLDocumentController.ValidatedUser.getCin_us());
        LocalDate l = LocalDate.parse(FXMLDocumentController.ValidatedUser.getDatenaiss_us().toString());
        date_naiss.setValue(l);
        password.setText("");
        try
        {
        Blob blb= FXMLDocumentController.ValidatedUser.getPhoto_us();
            InputStream is = blb.getBinaryStream();
            BufferedImage imag=ImageIO.read(is);
            Image f= SwingFXUtils.toFXImage(imag,null);
            photo.setImage(f);
        } catch (SQLException | IOException ex) {
           System.out.print("erreur affichage de photo "+ex.getMessage());
        }
        nb_parties.setText(""+nbchf.getNombrePartie(FXMLDocumentController.ValidatedUser));
        nb_parties_gagnées.setText(""+nbchf.getNombrePartieGagnee(FXMLDocumentController.ValidatedUser));
        meil_score.setText(""+FXMLDocumentController.ValidatedUser.getMeilscore_us());
        
        
        //stats
        
        ObservableList<PieChart.Data> pcd= FXCollections.observableArrayList();
        User u=FXMLDocumentController.ValidatedUser;
        
            PieChart.Data dt= new PieChart.Data("Nombre parties gagnées",nbchf.getNombrePartieGagnee(u));
            PieChart.Data dtt= new PieChart.Data("Nombre parties perdues",nbchf.getNombrePartie(u)-nbchf.getNombrePartieGagnee(u));
        pcd.add(dt);
        pcd.add(dtt);
        pieChartDiagrame.setData(pcd);
        pieChartDiagrame.setTitle("Rapport gangées/perdues");
        
        
        XYChart.Series<String,Integer> ss= new XYChart.Series<>();
        ss.setName("Nombre parties");
        String []mois= new String[]{"janvier","février","mars","avril","mai","juin","juillet","aout","septembre","octobre","novombre","décembre"};
        for(int i=0;i<12;i++ )
        {
            ss.getData().add(new XYChart.Data(mois[i],nbchf.getNombrePartieMois(u, i+1)));
        }
        barChartDiagrame.getData().add(ss);
        barChartDiagrame.setTitle("Fréquence de parties 2018");
        
        
        
        XYChart.Series<String,Integer> s= new XYChart.Series<>();
        s.setName("Meilleur score");
        HashMap<String,Integer> hh= nbchf.getDatesMelScoreParties(u);
        for(Map.Entry<String,Integer> e:hh.entrySet())
        {
        XYChart.Data xyd= new XYChart.Data<>(e.getKey(),e.getValue());
        s.getData().add(xyd);
        }
        lineChartDiagrame.getData().add(s);
        lineChartDiagrame.autosize();
        lineChartDiagrame.setCreateSymbols(false);
        lineChartDiagrame.setTitle("Evolution Scores");
        
        // stackedBarchart
        
        XYChart.Series<String,Integer> sbnbpg= new XYChart.Series<>();
        XYChart.Series<String,Integer> sbnbp= new XYChart.Series<>();
        XYChart.Series<String,Integer> sbnbpp= new XYChart.Series<>();
        sbnbpg.setName("Nombres Parties gagnées");
        sbnbp.setName("Nombres Parties");
        sbnbpp.setName("Nombres Parties perdues");
         List<String> adversaires= nbchf.getListAdversaires(u);
         
        for(int i=0;i<adversaires.size() && adversaires.get(i)!=null;i++)
        {
            System.out.println(adversaires.get(i)+" "+nbchf.getNombreVictoires(u, adversaires.get(i))+" "+nbchf.getNombreParties(u, adversaires.get(i)));
            sbnbpg.getData().add(new XYChart.Data(adversaires.get(i),nbchf.getNombreVictoires(u, adversaires.get(i))));
        sbnbp.getData().add(new XYChart.Data(adversaires.get(i), nbchf.getNombreParties(u, adversaires.get(i)))); 
         sbnbpp.getData().add(new XYChart.Data(adversaires.get(i), nbchf.getNombreParties(u, adversaires.get(i))-nbchf.getNombreVictoires(u, adversaires.get(i))));  
        }
        stackedBarChartDiagrame.getData().add(sbnbpp);
        stackedBarChartDiagrame.getData().add(sbnbpg);
       stackedBarChartDiagrame.getData().add(sbnbp);
       
      
        stackedBarChartDiagrame.setTitle("Rapport Adversaires gagnées/jouées");
    }    
    
}
