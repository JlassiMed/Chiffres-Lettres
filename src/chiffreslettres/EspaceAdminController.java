/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import Metier.Dictionnaire;
import Metier.NbEtchifImplements;
import Metier.User;
import static chiffreslettres.FXMLDocumentController.ValidatedUser;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.lang.Byte;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class EspaceAdminController implements Initializable {
    @FXML TextField pseudo;
    @FXML TextField nom;
    @FXML TextField prenom;
    @FXML TextField cin;
    @FXML DatePicker date_naiss,date_naiss_ins;
    @FXML PasswordField password;
    @FXML ImageView photo,photo_ins;
    @FXML Label nb_mots,nb_user,nb_parties,welcome;
    @FXML Button deconnexion;
   //inscription fields
    @FXML TextField pseudo_ins,nom_ins,prenom_ins,mdp_ins,cin_ins,type_ins,mot_value,mot_id;
    @FXML ListView ListUser,list_mot;
    NbEtchifImplements nbchf= new NbEtchifImplements();
    Image imagep;
    Boolean itemcliked=false,motItemSelected=false, photo_insChanged=false,userItemSelected=false;
    @FXML Tab Gest_dict;
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
    public boolean testDate(String ch)
    {
        return !(String.valueOf(ch.charAt(2)).equals("/") || String.valueOf(ch.charAt(5)).equals("/") || !testNum(ch.substring(0, 1)) || !testNum(ch.substring(3, 4)) || !testNum(ch.substring(6, 9)));
    }
    
    @FXML
     private void handleButtonActionAjout(ActionEvent event) {
            if(itemcliked)
            {
            photo_ins.setImage(imagep);
            date_naiss_ins.setValue(null);
            pseudo_ins.setText("");
            pseudo_ins.setDisable(false);
            nom_ins.setText("");
            prenom_ins.setText("");
            mdp_ins.setText("");
            cin_ins.setText("");
            type_ins.setText("");
            itemcliked=false;
            ListUser.getSelectionModel().select(-1);
            }
            else
            {
            if(!pseudo_ins.getText().equals("") && !nom_ins.getText().equals("")&&!prenom_ins.getText().equals("") && !mdp_ins.getText().equals("")&& !cin_ins.getText().equals("") )
            {
            User u= nbchf.findUser(nbchf.Admins(nbchf.AllUs()),pseudo_ins.getText());
            if(u != null)
            { 
                javax.swing.JOptionPane.showMessageDialog(null,"pseudo existant");
            }
            
            else if(!testAlpha(nom_ins.getText()) || !testAlpha(prenom_ins.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le nom et le prenom doivent contenir seulement des caractères alphabet !");
            }
            else if(cin_ins.getText().length()!=8 || !testNum(cin_ins.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le cin doit être de longueur 8 et contient seulement des chiffres !");
            }
            else if(date_naiss_ins.getValue()==null)
            {
                javax.swing.JOptionPane.showMessageDialog(null,"Veuillez remplir la date de naissance !");

            }
            else if(!type_ins.getText().equals("A") && !type_ins.getText().equals("N"))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le type d'utilisateur doit être A : Admin et N : Normale ");
            }
            else if(!photo_insChanged)
            {
                javax.swing.JOptionPane.showMessageDialog(null,"Veuillez choisir un photo");
            }
            else
            {
                
                try
                {
                User us;
                us = new User();
                us.setPseudo_us(pseudo_ins.getText());
                us.setPassword_us(getEncodedPassword(mdp_ins.getText()));
                us.setNom_us(nom_ins.getText());
                us.setPrenom_us(prenom_ins.getText());
                us.setCin_us(cin_ins.getText());
                Date l = Date.valueOf(date_naiss_ins.getValue());
                us.setDatenaiss_us(l);
                us.setMeilscore_us(0);
                us.setType_us(type_ins.getText());
                
                
                BufferedImage bImage = SwingFXUtils.fromFXImage(photo_ins.getImage(), null);
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
                 ObservableList<String> items= ListUser.getItems();
                items.clear();
                List<String> lps=nbchf.AllUs().stream().map(User::getPseudo_us).collect(toList());
                items = FXCollections.observableArrayList();
                ListUser.setItems(items);
                for(int i=0;i<lps.size();i++)
                    {
                        items.add(lps.get(i));
                    }
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
                photo_ins.setImage(image);
            } catch (IOException ex) {
                System.out.print(ex.getMessage());
            }
       photo_insChanged=true;
    }
     @FXML
     private void handleButtonActionDeleteUser(ActionEvent event)  {
         if(itemcliked==false || ListUser.getSelectionModel().getSelectedIndex()==-1)
             javax.swing.JOptionPane.showMessageDialog(null,"veuillez selectionner un utilisateur !");
         else
         { 
          User ActualUser= new User();
         ActualUser.setPseudo_us(pseudo_ins.getText());
         try
         {nbchf.DeleteUser(ActualUser);
                         javax.swing.JOptionPane.showMessageDialog(null,"Utilisateur supprimé avec succés");
              
                ObservableList<String> items= ListUser.getItems();
                items.clear();
                List<String> lps=nbchf.AllUs().stream().map(User::getPseudo_us).collect(toList());
                items = FXCollections.observableArrayList();
                for(int i=0;i<lps.size();i++)
                    {
                    items.add(lps.get(i));
                    }
                ListUser.setItems(items);
                photo_ins.setImage(imagep);
                date_naiss_ins.setValue(null);
            pseudo_ins.setText("");
            
            nom_ins.setText("");
            prenom_ins.setText("");
            mdp_ins.setText("");
            cin_ins.setText("");
            type_ins.setText("");
         }catch(HeadlessException exx)
         {
         System.out.println(" **** in delete user "+exx.getMessage());
         }
         }

    }
     
     @FXML
     private void handleButtonActionUpdateUser(ActionEvent event)  {
         if(!itemcliked || ListUser.getSelectionModel().getSelectedIndex()==-1)
             javax.swing.JOptionPane.showMessageDialog(null,"veuillez selectionner un utilisateur!");
         else
         {
            if(!testAlpha(nom_ins.getText()) || !testAlpha(prenom_ins.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le nom et le prenom doivent contenir seulement des caractères alphabet !");
            }
            else if(cin_ins.getText().length()!=8 || !testNum(cin_ins.getText()))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le cin doit être de longueur 8 et contient seulement des chiffres !");
            }
            else if(date_naiss_ins.getValue()==null)
            {
                javax.swing.JOptionPane.showMessageDialog(null,"Veuillez remplir la date de naissance !");

            }
            else if(!type_ins.getText().equals("A") && !type_ins.getText().equals("N"))
            {
                javax.swing.JOptionPane.showMessageDialog(null,"le type d'utilisateur doit être A : Admin et N : Normale ");
            }
            else
             try{   
                User ActualUser= new User();    
                ActualUser.setPseudo_us(pseudo_ins.getText());
                ActualUser.setPassword_us(getEncodedPassword(mdp_ins.getText()));
                ActualUser.setNom_us(nom_ins.getText());
                ActualUser.setPrenom_us(prenom_ins.getText());
                ActualUser.setCin_us(cin_ins.getText());
                Date l = Date.valueOf(date_naiss_ins.getValue());
                ActualUser.setDatenaiss_us(l);
                ActualUser.setMeilscore_us(0);
                ActualUser.setType_us(type_ins.getText());
                BufferedImage bImage = SwingFXUtils.fromFXImage(photo_ins.getImage(), null);
                    try (
                        ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                        ImageIO.write(bImage, "jpg", s);
                        Blob b= new SerialBlob(s.toByteArray());
                        ActualUser.setPhoto_us(b);
                    } catch (IOException ex) {
                        System.out.print(ex.getMessage()+" in modif IO ");
                    } catch (SQLException ex) {
                        System.out.print(ex.getMessage()+" in bt modif sql");                    }
                
         nbchf.UpdateUser(ActualUser);
                         javax.swing.JOptionPane.showMessageDialog(null,"Utilisateur modifié avec succés");
                         ObservableList<String> items= ListUser.getItems();
                items.clear();
                List<String> lps=nbchf.AllUs().stream().map(User::getPseudo_us).collect(toList());
                items = FXCollections.observableArrayList();
                for(int i=0;i<lps.size();i++)
                    {
                    items.add(lps.get(i));
                    }
                ListUser.setItems(items);
                photo_ins.setImage(imagep);
                date_naiss_ins.setValue(null);
            pseudo_ins.setText("");
            
            nom_ins.setText("");
            prenom_ins.setText("");
            mdp_ins.setText("");
            cin_ins.setText("");
            type_ins.setText("");
            itemcliked=false;
            pseudo_ins.setDisable(false);
     }catch(HeadlessException ex)
     {}
       
         }
    }
     boolean ClickedAjoutMot=false;
       boolean ClickedUpdateMot=false;
      public boolean verifierMot(String ch)
      {
      String[] x= ch.split(" ");
      if(x.length>1)
      return false;
      else
      return testAlpha(ch) && ch.toLowerCase().equals(ch);
     
      }
       @FXML
      private void handleButtonActionAjoutMot(ActionEvent event) throws IOException {
        
         if(motItemSelected)
        {
            mot_value.setText("");
            mot_id.setText(nbchf.Allmots().size()+1+"");
            mot_value.setDisable(false);
            motItemSelected=false;
            list_mot.getSelectionModel().select(-1);
        }
        else
        {
          if(mot_value.getText().isEmpty())
          {
                            javax.swing.JOptionPane.showMessageDialog(null,"insérer la valeur du mot svp !");
           
          }
          else if(mot_value.getText().length()>10)
          {
              javax.swing.JOptionPane.showMessageDialog(null,"la longueur du mot doit être inférieur ou égale à 10 Ressayer!");
          }
          else if(verifierMot(mot_value.getText())==false)
          {
              javax.swing.JOptionPane.showMessageDialog(null,"le mot doit contenir seulement des alphabet miniscules et sans espaces!");
          }
          else
        try
        {
          Dictionnaire m= new Dictionnaire();
        m.setValeur_mot(mot_value.getText());
        m.setId_mot(Integer.parseInt(mot_id.getText()));
        nbchf.addMot(m);
                      javax.swing.JOptionPane.showMessageDialog(null,"mot ajoutée avec succés");
                      mot_id.setText((nbchf.Allmots().size()+1)+"");
                      mot_value.setText("");
           list_mot.getItems().clear();
            List<String> lpm=nbchf.Allmots().stream().map(Dictionnaire::getValeur_mot).collect(toList());
        ObservableList<String> mots = FXCollections.observableArrayList();
        list_mot.setItems(mots);
        for(int i=0;i<lpm.size();i++)
        {
        mots.add(lpm.get(i));
        }
        motItemSelected=false;
        }
        catch(NumberFormatException e)
        {
            System.out.println("erreur d'ajout d'un mot "+e.getMessage());
        }
        
        }
        
    }
    
      @FXML
      private void handleButtonActionUpdateMot(ActionEvent event) throws IOException {
        
         if(ClickedUpdateMot== false)
        {
            mot_value.setDisable(false);
            ClickedUpdateMot=true;
        }
          else if(list_mot.getSelectionModel().getSelectedIndex()==-1)
        {
            javax.swing.JOptionPane.showMessageDialog(null,"veuiller selectionner un mot ! ");
        
        }
        else
          if(mot_id.getText().isEmpty())
          {
                            javax.swing.JOptionPane.showMessageDialog(null,"insérer la valeur du mot svp !");
           
          }
          else
        try
        {
          Dictionnaire m= new Dictionnaire();
        m.setValeur_mot(mot_value.getText());
        m.setId_mot(Integer.parseInt(mot_id.getText()));
        nbchf.UpdateMot(m);
                      javax.swing.JOptionPane.showMessageDialog(null,"mot modfiée avec succés");
                      mot_id.setText((nbchf.Allmots().size()+1)+"");
                      mot_value.setText(m.getValeur_mot());
           list_mot.getItems().clear();
            List<String> lpm=nbchf.Allmots().stream().map(Dictionnaire::getValeur_mot).collect(toList());
        ObservableList<String> mots = FXCollections.observableArrayList();
        list_mot.setItems(mots);
        for(int i=0;i<lpm.size();i++)
        {
        mots.add(lpm.get(i));
        }
        ClickedUpdateMot=false;
        mot_value.setDisable(true);
        }
        catch(NumberFormatException e)
        {
            System.out.println("erreur dde modification d'un mot "+e.getMessage());
        }
       
    }
      @FXML
      private void handleButtonActionDeleteMot(ActionEvent event) throws IOException {
        if(ClickedUpdateMot==true)
        {
            javax.swing.JOptionPane.showMessageDialog(null,"veuiller terminer votre opération actuelle avant de passer à la suppression ! ");
        }
        else if(list_mot.getSelectionModel().getSelectedIndex()==-1)
        {
            javax.swing.JOptionPane.showMessageDialog(null,"veuiller selectionner un mot ! ");
        }
        else
          try
        {
          Dictionnaire m= new Dictionnaire();
        m.setValeur_mot(mot_value.getText());
        m.setId_mot(Integer.parseInt(mot_id.getText()));
        nbchf.DeleteMot(m);
                      javax.swing.JOptionPane.showMessageDialog(null,"mot supprimé avec succés");
                      mot_id.setText((nbchf.Allmots().size()+1)+"");
                      mot_value.setDisable(false);
                      mot_value.setText("");
                     
                                        
           list_mot.getItems().clear();
            List<String> lpm=nbchf.Allmots().stream().map(Dictionnaire::getValeur_mot).collect(toList());
        ObservableList<String> mots = FXCollections.observableArrayList();
        list_mot.setItems(mots);
        for(int i=0;i<lpm.size();i++)
        {
        mots.add(lpm.get(i));
        }
        ClickedUpdateMot=false;
        mot_value.setDisable(true);
        }
        catch(NumberFormatException e)
        {
            System.out.println("erreur dde modification d'un mot "+e.getMessage());
        }
       
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imagep= photo_ins.getImage();
        pseudo.setText(FXMLDocumentController.ValidatedUser.getPseudo_us());
        nom.setText(FXMLDocumentController.ValidatedUser.getNom_us());
        prenom.setText(FXMLDocumentController.ValidatedUser.getPrenom_us());
        cin.setText(FXMLDocumentController.ValidatedUser.getCin_us());
        LocalDate l = LocalDate.parse(FXMLDocumentController.ValidatedUser.getDatenaiss_us().toString());
        date_naiss.setValue(l);
        password.setText("");
        nb_mots.setText(String.valueOf(nbchf.Allmots().size()));
        nb_parties.setText(String.valueOf(nbchf.AllParties().size()));
        nb_user.setText(String.valueOf(nbchf.AllUs().size()));
        welcome.setText("Bienvenue "+FXMLDocumentController.ValidatedUser.getPseudo_us());
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
        
        
        //List<String> lps=nbchf.NormalUs(nbchf.AllUs()).stream().map(User::getPseudo_us).collect(toList());
        List<String> lps=nbchf.AllUs().stream().map(User::getPseudo_us).collect(toList());
        ObservableList<String> items = FXCollections.observableArrayList();
        ListUser.setItems(items);
        for(int i=0;i<lps.size();i++)
        {
        items.add(lps.get(i));
        }
        
        
        List<String> lpm=nbchf.Allmots().stream().map(Dictionnaire::getValeur_mot).collect(toList());
        ObservableList<String> mots = FXCollections.observableArrayList();
        list_mot.setItems(mots);
        for(int i=0;i<lpm.size();i++)
        {
        mots.add(lpm.get(i));
        }
        
        mot_id.setText((nbchf.Allmots().size()+1)+"");
         mot_id.setDisable(true);
        
        ListUser.setOnMouseClicked((MouseEvent event) -> {
            itemcliked=true;
            String selected= (String) ListUser.getSelectionModel().getSelectedItem();
            User sel =nbchf.findUser(nbchf.AllUs(), selected);
            pseudo_ins.setText(sel.getPseudo_us());
            nom_ins.setText(sel.getNom_us());
            prenom_ins.setText(sel.getPrenom_us());
            mdp_ins.setText("");
            cin_ins.setText(sel.getCin_us());
            type_ins.setText(sel.getType_us());
            LocalDate l1 = LocalDate.parse(sel.getDatenaiss_us().toString());
            date_naiss_ins.setValue(l1);
            try
            {
                Blob blb= sel.getPhoto_us();
                InputStream is = blb.getBinaryStream();
                BufferedImage imag=ImageIO.read(is);
                Image f= SwingFXUtils.toFXImage(imag,null);
                photo_ins.setImage(f);
            } catch (SQLException | IOException ex) {
                System.out.print("erreur affichage de photo "+ex.getMessage());
            }   
            pseudo_ins.setDisable(true);
        });
        
        
        list_mot.setOnMouseClicked((MouseEvent event) -> {
            mot_value.setDisable(true);
            motItemSelected=true;
            if(ClickedUpdateMot)
                ClickedUpdateMot=false;// en cas de changement d'index en cours de modification d'un mot
            String selected= (String) list_mot.getSelectionModel().getSelectedItem();
            Dictionnaire mot =nbchf.findMot(nbchf.Allmots(),selected);
            mot_value.setText(mot.getValeur_mot());
            mot_id.setText(""+mot.getId_mot());
           
        });
        
        // Stats
        ObservableList<PieChart.Data> pcd= FXCollections.observableArrayList();
        HashMap<String,Integer> data = nbchf.pieChartData();
        for(Entry<String,Integer> e:data.entrySet())
        {
            System.out.println(e.getKey()+" , "+e.getValue());
            PieChart.Data dt= new PieChart.Data(e.getKey(),e.getValue());
        pcd.add(dt);
        }
        pieChartDiagrame.setData(pcd);
        pieChartDiagrame.setTitle("Nombres Parties Gagnées");
        
        xaxeLC.setSide(Side.TOP);
        xaxeLC.setLabel("Nombres de parties jouées");
        //yaxeLC.setLabel("Mois");
        String []mois= new String[]{"janvier","février","mars","avril","mai","juin","juillet","aout","septembre","octobre","novombre","décembre"};
        XYChart.Series<String,Integer> s= new XYChart.Series<>();
        s.setName("Nombre parties Jouées");
        HashMap<Integer,Integer> hh= nbchf.LineChartData();
        for(Entry<Integer,Integer> e:hh.entrySet())
        {
        XYChart.Data xyd= new XYChart.Data<>(mois[e.getKey()-1],e.getValue());
        s.getData().add(xyd);
        }
        lineChartDiagrame.getData().add(s);
        lineChartDiagrame.autosize();
        lineChartDiagrame.setCreateSymbols(false);
        lineChartDiagrame.setTitle("Nombres Parties/mois 2018");
        
         List<User> lu=nbchf.BarChartData();
        XYChart.Series<String,Integer> ss= new XYChart.Series<>();
        ss.setName("Meilleur Score");
        User u1=lu.get(0);
        User u2=lu.get(1);
        User u3=lu.get(2);
        ss.getData().add(new XYChart.Data(u1.getPseudo_us(),u1.getMeilscore_us()));
        ss.getData().add(new XYChart.Data(u2.getPseudo_us(),u2.getMeilscore_us()));
        ss.getData().add(new XYChart.Data(u3.getPseudo_us(),u3.getMeilscore_us()));
        
        barChartDiagrame.getData().add(ss);
        barChartDiagrame.setTitle("LeaderBoard");
        
        //ScatterChart
        XYChart.Series<String,Integer> sc= new XYChart.Series<>();
        XYChart.Series<String,Integer> scmlp= new XYChart.Series<>();
        XYChart.Series<String,Integer> sccb= new XYChart.Series<>();
        sc.setName("Meilleur Score");
        scmlp.setName("Meiileur score MPL");
        sccb.setName("Meiileur score CB");
        
        List<User> allu= nbchf.AllUs();
        for(User u:allu)
        {
        sc.getData().add(new XYChart.Data(u.getPseudo_us(),u.getMeilscore_us()));
        scmlp.getData().add(new XYChart.Data(u.getPseudo_us(), nbchf.getMeilleurScMLP(u)));
        sccb.getData().add(new XYChart.Data(u.getPseudo_us(), nbchf.getMeilleurScCB(u)));  
        }
        scatterBarChartDiagrame.getData().add(sc);
        scatterBarChartDiagrame.getData().add(scmlp);
        scatterBarChartDiagrame.getData().add(sccb);
        scatterBarChartDiagrame.setTitle("Analyse Scores Joueurs");
        
        // stackedBarchart
        
        XYChart.Series<String,Integer> sbnbpg= new XYChart.Series<>();
        XYChart.Series<String,Integer> sbnbp= new XYChart.Series<>();
        sbnbpg.setName("Nombre Parties Gagnées");
        sbnbp.setName("Nombre Parties");
        for(User u:allu)
        {
        sbnbpg.getData().add(new XYChart.Data(u.getPseudo_us(),nbchf.getNombrePartieGagnee(u)));
        sbnbp.getData().add(new XYChart.Data(u.getPseudo_us(), nbchf.getNombrePartie(u)));  
        }
        stackedBarChartDiagrame.getData().add(sbnbpg);
        stackedBarChartDiagrame.getData().add(sbnbp);
        stackedBarChartDiagrame.setTitle("Rapport Parties gagnées/jouées");
    }    
     @FXML
    private void handleButtonActionSavePhotoAdmin(ActionEvent event) throws IOException {
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
    @FXML
    private void handleButtonActionUpdateAdmin(ActionEvent event)  {
        
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
                ActualUser.setType_us("A");
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
    private void handleButtonActionDeconnexion(ActionEvent event)  {
        
      Stage current = (Stage)(deconnexion.getScene().getWindow()) ;
                current.close();
    }
    
}
