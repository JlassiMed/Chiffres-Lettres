/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import Metier.Dictionnaire;
import Metier.NbEtchifImplements;
import chiffreslettres.LoginJeuController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import static java.util.stream.Collectors.toList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class MotLePlusLongController implements Initializable {

    @FXML TextField user1,user2, txt1, txt2;
    @FXML TextField c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    private List<Character> w = new ArrayList<Character>();
    NbEtchifImplements nbchf= new NbEtchifImplements();
    private List<Dictionnaire> all = nbchf.Allmots();
    private List<String> ToutLesMots = all.stream().map(x -> x.getValeur_mot().toLowerCase()).collect(toList());
    
    private void handleButtonActionValider(ActionEvent event) throws IOException {
       
         
         if(LoginJeuController.CompteurJeu==1)// l'epreuve de sézame
        {
        
        }  
        else if(LoginJeuController.CompteurJeu==10)// l'etape suivante est le finale
        {
            
            
        Parent p = FXMLLoader.load(getClass().getResource("Final.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        
        }
        else{        
        Parent p = FXMLLoader.load(getClass().getResource("LeCompteEstBon.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(LoginJeuController.CompteurJeu);
        user1.setText(LoginJeuController.ValidatedJoueur1.getPseudo_us() + " (" + LoginJeuController.scoreTotJ1 + ")");
        user2.setText(LoginJeuController.ValidatedJoueur2.getPseudo_us()  + " (" + LoginJeuController.scoreTotJ2 + ")");
        user1.setDisable(true);
        user2.setDisable(true);
        Runnable timer = new Runnable()
        {
          public void run()
          {
              for (int i = 40; i >= 0; i--)
              {
                  try 
                  {
                      Thread.sleep(1000);
                  } 
                  catch (InterruptedException ex) 
                  {
                  }
              }
              System.out.println("done");
              txt1.setEditable(false);
              txt2.setEditable(false);

          }
        };
        Random rnd = new Random();
        w = GenererAffichage(ToutLesMots.get(rnd.nextInt(ToutLesMots.size())));
        c1.setText(w.get(0).toString().toUpperCase());
        c2.setText(w.get(1).toString().toUpperCase());
        c3.setText(w.get(2).toString().toUpperCase());
        c4.setText(w.get(3).toString().toUpperCase());
        c5.setText(w.get(4).toString().toUpperCase());
        c6.setText(w.get(5).toString().toUpperCase());
        c7.setText(w.get(6).toString().toUpperCase());
        c8.setText(w.get(7).toString().toUpperCase());
        c9.setText(w.get(8).toString().toUpperCase());
        c10.setText(w.get(9).toString().toUpperCase());
        Thread t = new Thread(timer);
        t.start();
    }    
    
    public List<Character> GenererAffichage(String s)
    {
        char[] mot = s.toCharArray();
        List<Character> r = new ArrayList<Character>();
        List<Character> tmp = new ArrayList<Character>();

        for (int i = 0; i < mot.length; i++)
        {
            r.add(mot[i]);
        }
        Random rnd = new Random();
        for (int i = 0; i < 10 - mot.length; i++)
        {
            r.add((char)(rnd.nextInt(26) + 'a'));
        }
        
        for (int i = 0; i < 10; i++)
        {
            int x = rnd.nextInt(r.size());
            tmp.add(r.get(x));
            r.remove(x);
        }
        return tmp;
    }
    
    @FXML
    
    private void Valider(ActionEvent event) throws IOException {
        LoginJeuController.CompteurJeu++;// incrémentation d'épreuve
        
        boolean verif = ToutLesMots.contains(txt1.getText().toLowerCase());
        List<Character> ch = new ArrayList(w);
        for (int i = 0; i < txt1.getText().length() && verif; i++)
        {
            if (ch.contains(txt1.getText().charAt(i)))
            {
                ch.remove(txt1.getText().indexOf(i));
            }
            else
                verif = false;
        }
        
        boolean verif1 = ToutLesMots.contains(txt2.getText().toLowerCase());;
        List<Character> ch1 = new ArrayList(w);
        for (int i = 0; i < txt2.getText().length() && verif1; i++)
        {
            if (ch.contains(txt2.getText().charAt(i)))
            {
                ch.remove(txt2.getText().charAt(i));
            }
            else
                verif = false;
        }
        
        if (verif && verif1)
        {
            if (txt1.getText().length() > txt2.getText().length())
            {
                LoginJeuController.scoreTotJ1 += txt1.getText().length();
                LoginJeuController.scoreMLPJ1 += txt1.getText().length();
            }
            else
            {
                LoginJeuController.scoreTotJ2 += txt2.getText().length();
                LoginJeuController.scoreMLPJ2 += txt2.getText().length();
            }
        }
        else if (verif)
        {
            LoginJeuController.scoreTotJ1 += txt1.getText().length();
            LoginJeuController.scoreMLPJ1 += txt1.getText().length();
        }
        else if (verif1)
        {
            LoginJeuController.scoreTotJ2 += txt2.getText().length();
            LoginJeuController.scoreMLPJ2 += txt2.getText().length();
        }
        
        if(LoginJeuController.CompteurJeu==1)// l'epreuve de sézame
        {
            if (txt1.getText().equals(w.toString()))
                LoginJeuController.sezame = 1;
            else if (txt2.getText().equals(w.toString()))
                LoginJeuController.sezame = 2;
        }  
        else if(LoginJeuController.CompteurJeu>=10)// l'etape suivante est le finale
        {
        if (LoginJeuController.scoreTotJ1 > LoginJeuController.scoreTotJ2)
            LoginJeuController.JoueurGagnant = LoginJeuController.ValidatedJoueur1;
        else
            LoginJeuController.JoueurGagnant = LoginJeuController.ValidatedJoueur2;
            
        Parent p = FXMLLoader.load(getClass().getResource("final.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        Stage current = (Stage)(user1.getScene().getWindow()) ;
        current.close();
        }
        else{        
        Parent p = FXMLLoader.load(getClass().getResource("LeCompteEstBon.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        Stage current = (Stage)(user1.getScene().getWindow()) ;
        current.close();
        }
    }
}
