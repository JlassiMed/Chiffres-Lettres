/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import Metier.Dictionnaire;
import Metier.NbEtchifImplements;
import Metier.Partie;
import Metier.User;
import chiffreslettres.LoginJeuController;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import static java.util.stream.Collectors.toList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class FinalController implements Initializable {
    @FXML TextField user1, txt;
    @FXML TextField c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    @FXML Button end, ssm,motSuivant,valider_bt;
    @FXML Label txtLong, txtVal, txtMot;
    private List<String> liste = new ArrayList<String>();
    NbEtchifImplements nbchf= new NbEtchifImplements();
    private List<Dictionnaire> all = nbchf.Allmots();
    private List<String> ToutLesMots = all.stream().map(x -> x.getValeur_mot().toLowerCase()).collect(toList());
    private int somme = 0;
    private int c = 0;
    private boolean ssmUsed = true;
    private int x = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (LoginJeuController.sezame == 1 && LoginJeuController.ValidatedJoueur1.getPseudo_us().equals(LoginJeuController.JoueurGagnant.getPseudo_us()))
        {    
            ssm.setVisible(true);
            ssmUsed = false;
        }
        else if (LoginJeuController.sezame == 2 && LoginJeuController.ValidatedJoueur2.getPseudo_us().equals(LoginJeuController.JoueurGagnant.getPseudo_us()))
        {
            ssm.setVisible(true);
            ssmUsed = false;
        }
        user1.setText(LoginJeuController.JoueurGagnant.getPseudo_us());
        Runnable timer = new Runnable()
        {
          public void run()
          {
              for (int i = 20; i >= 0; i--)
              {
                  if (liste.size() == 0)
                  {
                      end.setVisible(true);
                      if (!ssmUsed)
                      {
                        somme += 100;
                        JOptionPane.showMessageDialog(null, "Bravo! Vous avez gagné " + String.valueOf(somme) + " £");
                      }
                      else
                      {
                        JOptionPane.showMessageDialog(null, "Bravo! Vous avez gagné " + String.valueOf(somme) + " £");
                      }
                      i = -1;
                  }
                  try 
                  {
                      Thread.sleep(1000);
                  } 
                  catch (InterruptedException ex) 
                  {
                  }
              }
              System.out.println("done");
              txt.setEditable(false);
             
              end.setVisible(true);
              txt.setEditable(false);
              motSuivant.setDisable(true);
              valider_bt.setDisable(true);
          }
        };
        int i = 0;
        while (i < 10)
        {
            Random rnd = new Random();
            String w = ToutLesMots.get(rnd.nextInt(ToutLesMots.size()));
            if (!liste.contains(w))
            {
                i++;
                liste.add(w);
                ToutLesMots.remove(w);
            }
        }
        GenererAffichage(liste.get(0));
        txtLong.setText("Mot de Longueur " + liste.get(0).length() + " :");
        Thread t = new Thread(timer);
        t.start();
    }    
    
    public void GenererAffichage(String s)
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
        //return tmp;
        c1.setText(tmp.get(0).toString().toUpperCase());
        c2.setText(tmp.get(1).toString().toUpperCase());
        c3.setText(tmp.get(2).toString().toUpperCase());
        c4.setText(tmp.get(3).toString().toUpperCase());
        c5.setText(tmp.get(4).toString().toUpperCase());
        c6.setText(tmp.get(5).toString().toUpperCase());
        c7.setText(tmp.get(6).toString().toUpperCase());
        c8.setText(tmp.get(7).toString().toUpperCase());
        c9.setText(tmp.get(8).toString().toUpperCase());
        c10.setText(tmp.get(9).toString().toUpperCase());
    }
    
    @FXML
    private void Suivant(ActionEvent event) throws IOException {
        c++;
        GenererAffichage(liste.get(c%liste.size()));
        txtLong.setText("Mot de Longueur " + liste.get(c%liste.size()).length() + " :");

    }
    
    @FXML
    private void Valider(ActionEvent event) throws IOException {
        if (txt.getText().equals(liste.get(c%liste.size())))
        {
            liste.remove(txt.getText());
            somme += 100;
            x++;
            c = 0;
            GenererAffichage(liste.get(c));
            txtLong.setText("Mot de Longueur " + liste.get(c).length() + " :");
            txtMot.setText("Mot(s) Trouvé(s) : " + x + " / 10");
            txtVal.setText("Valeur Actuelle: " + somme + " £");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Mot Incorrect!");
        }
    }
    
    @FXML
    private void Sesame(ActionEvent event) throws IOException {
        liste.remove(liste.get(c));
        ssm.setVisible(false);
        somme += 100;
        x++;
    }
    
    @FXML
    private void Terminer(ActionEvent event) throws IOException {
        try
        {
            Partie p= new Partie();
        p.setPseudo_us1(LoginJeuController.ValidatedJoueur1.getPseudo_us());
        p.setPseudo_us2(LoginJeuController.ValidatedJoueur2.getPseudo_us());
            
        p.setJ1ScoreToT(LoginJeuController.scoreTotJ1);
        p.setJ2ScoreTot(LoginJeuController.scoreTotJ2);
        
        p.setJ1ScoreMLP(LoginJeuController.scoreMLPJ1);
        p.setJ2ScoreMLP(LoginJeuController.scoreMLPJ2);
        
        p.setJ1ScoreCB(LoginJeuController.scoreCBJ1);
        p.setJ2ScoreCB(LoginJeuController.scoreCBJ2);
      
        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        p.setDate_partie(ourJavaDateObject);
        
        nbchf.AddPartie(p);
        
        if (LoginJeuController.ValidatedJoueur1.getMeilscore_us()< LoginJeuController.scoreTotJ1)
        {
        User u= LoginJeuController.ValidatedJoueur1;
        u.setMeilscore_us(LoginJeuController.scoreTotJ1);
        nbchf.UpdateUser(u);
        }
        
        if (LoginJeuController.ValidatedJoueur2.getMeilscore_us()< LoginJeuController.scoreTotJ2)
        {
        User u= LoginJeuController.ValidatedJoueur2;
        u.setMeilscore_us(LoginJeuController.scoreTotJ2);
        nbchf.UpdateUser(u);
        }
         Stage current = (Stage)(user1.getScene().getWindow()) ;
        current.close();
        JOptionPane.showMessageDialog(null, "au revoir !");
        }catch(HeadlessException exx)
        {
        System.out.print(exx.getLocalizedMessage());
        }
        
    }
}
