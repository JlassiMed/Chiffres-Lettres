/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffreslettres;

import chiffreslettres.LoginJeuController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Emir
 */
public class LeCompteEstBonController implements Initializable {
    @FXML private TextField NombreAlea, n1, n2, n3, n4, n5, n6, nb1, nb2, nb3, nb4,user1,user2; 
    @FXML private ListView res1 ,res2;
    @FXML private ComboBox op1, op2;  
    @FXML private Label currentTime, txtOptimale;  
    @FXML private WebView page;
    @FXML private Button bt, bt1;
    private List<String> listJoueur1 = new ArrayList<String>();
    private int currentJoueur1 = 0;
    private List<String> listJoueur2 = new ArrayList<String>();
    private int currentJoueur2 = 0;
    private int[] nb = new int[6];
    private int nid, niv, res, nCal;
    private long diff;
    private String str = "";
    private String txt = "";
    private int a, b, n, r, d, i, j;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
                System.out.println(LoginJeuController.CompteurJeu);

        user1.setText(LoginJeuController.ValidatedJoueur1.getPseudo_us() + " (" + LoginJeuController.scoreTotJ1 + ")");
        user2.setText(LoginJeuController.ValidatedJoueur2.getPseudo_us() + " (" + LoginJeuController.scoreTotJ2 + ")");
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
              nb1.setEditable(false);
              nb2.setEditable(false);

          }
        };
        Random rnd = new Random();
        str=""; nid=-1; niv=-1; nCal=0; diff=1000000000;
        NombreAlea.setText(String.valueOf(rnd.nextInt(900) + 100));
        n1.setText(String.valueOf(GenerateurChoix()));
        n2.setText(String.valueOf(GenerateurChoix()));
        n3.setText(String.valueOf(GenerateurChoix()));
        n4.setText(String.valueOf(GenerateurChoix()));
        n5.setText(String.valueOf(GenerateurChoix()));
        n6.setText(String.valueOf(GenerateurChoix())); 
        listJoueur1.add(n1.getText());
        listJoueur1.add(n2.getText());
        listJoueur1.add(n3.getText());
        listJoueur1.add(n4.getText());
        listJoueur1.add(n5.getText());
        listJoueur1.add(n6.getText());
        op1.getItems().addAll("+", "-", "*", "/");
        op1.setValue("+");
        listJoueur2.add(n1.getText());
        listJoueur2.add(n2.getText());
        listJoueur2.add(n3.getText());
        listJoueur2.add(n4.getText());
        listJoueur2.add(n5.getText());
        listJoueur2.add(n6.getText());
        nb[0] = Integer.parseInt(n1.getText());
        nb[1] = Integer.parseInt(n2.getText());
        nb[2] = Integer.parseInt(n3.getText());
        nb[3] = Integer.parseInt(n4.getText());
        nb[4] = Integer.parseInt(n5.getText());
        op2.getItems().addAll("+", "-", "*", "/");
        op2.setValue("+");
        try {
            ChargerSolution();
        } catch (IOException ex) {
            Logger.getLogger(LeCompteEstBonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(timer);
        t.start();
    }    
    
    public int GenerateurChoix()
    {
        int r = 0;
        Random rnd = new Random();
        int c = rnd.nextInt(2);
        
        if (c == 0)
        {
            r = rnd.nextInt(9) + 1;
        }
        else
        {
            int[] choix = {10, 25, 50, 100};
            r = choix[rnd.nextInt(4)];
        }
        return r;
    }
    
    @FXML
    
    private void AjouterJoueur1(ActionEvent event) throws IOException {
        String FinalOp = "";
        if (currentJoueur1 == 0)
        {
           if (listJoueur1.contains(nb1.getText()) && listJoueur1.contains(nb2.getText()))
           {
               FinalOp += nb1.getText();
               listJoueur1.remove(nb1.getText());
               if (listJoueur1.contains(nb2.getText()))
               {
                   if (op1.getValue().toString().equals("-"))
                   {
                       if (Integer.parseInt(nb1.getText()) > Integer.parseInt(nb2.getText()))
                       {
                           currentJoueur1 = Integer.parseInt(nb1.getText()) - Integer.parseInt(nb2.getText());
                           res1.getItems().add(nb1.getText() + " - " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                           listJoueur1.remove(nb2.getText());
                           nb1.setText(String.valueOf(currentJoueur1));
                           nb1.setEditable(false);
                       }
                       else
                       {
                            listJoueur1.add(nb1.getText());
                            JOptionPane.showMessageDialog(null, "Veuillez inverser les nombres!");
                       }
                   }
                   else if (op1.getValue().toString().equals("/"))
                   {
                       if (Integer.parseInt(nb1.getText()) % Integer.parseInt(nb2.getText()) == 0)
                       {
                           currentJoueur1 = Integer.parseInt(nb1.getText()) / Integer.parseInt(nb2.getText());
                           res1.getItems().add(nb1.getText() + " / " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                           listJoueur1.remove(nb2.getText());
                           nb1.setText(String.valueOf(currentJoueur1));
                           nb1.setEditable(false);
                       }
                       else
                       {
                           listJoueur1.add(nb1.getText());
                           JOptionPane.showMessageDialog(null, "Operation Impossible!");
                       }
                   }
                   else if (op1.getValue().toString().equals("+"))
                   {
                       currentJoueur1 = Integer.parseInt(nb1.getText()) + Integer.parseInt(nb2.getText());
                       res1.getItems().add(nb1.getText() + " + " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                       listJoueur1.remove(nb2.getText());
                       nb1.setText(String.valueOf(currentJoueur1));
                       nb1.setEditable(false);
                   }
                   else
                   {
                       currentJoueur1 = Integer.parseInt(nb1.getText()) * Integer.parseInt(nb2.getText());
                       res1.getItems().add(nb1.getText() + " * " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                       listJoueur1.remove(nb2.getText());
                       nb1.setText(String.valueOf(currentJoueur1));
                       nb1.setEditable(false);
                   }
               }
               else
               {
                   listJoueur1.add(nb1.getText());
                   JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
               }
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
           }
        }
        else
        {
            if (listJoueur1.contains(nb2.getText()))
            {
                if (op1.getValue().toString().equals("-"))
                {
                    if (Integer.parseInt(nb1.getText()) > Integer.parseInt(nb2.getText()))
                    {
                        currentJoueur1 = Integer.parseInt(nb1.getText()) - Integer.parseInt(nb2.getText());
                        res1.getItems().add(nb1.getText() + " - " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                        listJoueur1.remove(nb2.getText());
                        nb1.setText(String.valueOf(currentJoueur1));
                        nb1.setEditable(false);
                    }
                    else
                    {
                        listJoueur1.add(nb1.getText());
                        JOptionPane.showMessageDialog(null, "Veuillez inverser les nombres!");
                    }
                }
                else if (op1.getValue().toString().equals("/"))
                {
                    if (Integer.parseInt(nb1.getText()) % Integer.parseInt(nb2.getText()) == 0)
                    {
                        currentJoueur1 = Integer.parseInt(nb1.getText()) / Integer.parseInt(nb2.getText());
                        res1.getItems().add(nb1.getText() + " / " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                        listJoueur1.remove(nb2.getText());
                        nb1.setText(String.valueOf(currentJoueur1));
                        nb1.setEditable(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Operation Impossible!");
                    }
                }
                else if (op1.getValue().toString().equals("+"))
                {
                    currentJoueur1 = Integer.parseInt(nb1.getText()) + Integer.parseInt(nb2.getText());
                    res1.getItems().add(nb1.getText() + " + " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                    listJoueur1.remove(nb2.getText());
                    nb1.setText(String.valueOf(currentJoueur1));
                    nb1.setEditable(false);
                }
                else
                {
                    currentJoueur1 = Integer.parseInt(nb1.getText()) * Integer.parseInt(nb2.getText());
                    res1.getItems().add(nb1.getText() + " * " + nb2.getText() + " = " +  String.valueOf(currentJoueur1));
                    listJoueur1.remove(nb2.getText());
                    nb1.setText(String.valueOf(currentJoueur1));
                    nb1.setEditable(false);
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
            }
        }
    }
    
    @FXML

    private void AjouterJoueur2(ActionEvent event) throws IOException {
        String FinalOp = "";
        if (currentJoueur2 == 0)
        {
           if (listJoueur2.contains(nb3.getText()) && listJoueur2.contains(nb4.getText()))
           {
               FinalOp += nb3.getText();
               listJoueur2.remove(nb3.getText());
               if (listJoueur2.contains(nb4.getText()))
               {
                   if (op2.getValue().toString().equals("-"))
                   {
                       if (Integer.parseInt(nb3.getText()) > Integer.parseInt(nb4.getText()))
                       {
                           currentJoueur2 = Integer.parseInt(nb3.getText()) - Integer.parseInt(nb4.getText());
                           res2.getItems().add(nb3.getText() + " - " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                           listJoueur2.remove(nb4.getText());
                           nb3.setText(String.valueOf(currentJoueur2));
                           nb3.setEditable(false);
                       }
                       else
                       {
                            listJoueur2.add(nb3.getText());
                            JOptionPane.showMessageDialog(null, "Veuillez inverser les nombres!");
                       }
                   }
                   else if (op2.getValue().toString().equals("/"))
                   {
                       if (Integer.parseInt(nb3.getText()) % Integer.parseInt(nb4.getText()) == 0)
                       {
                           currentJoueur2 = Integer.parseInt(nb3.getText()) / Integer.parseInt(nb4.getText());
                           res2.getItems().add(nb3.getText() + " / " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                           listJoueur2.remove(nb4.getText());
                           nb3.setText(String.valueOf(currentJoueur2));
                           nb3.setEditable(false);
                       }
                       else
                       {
                           listJoueur2.add(nb3.getText());
                           JOptionPane.showMessageDialog(null, "Operation Impossible!");
                       }
                   }
                   else if (op2.getValue().toString().equals("+"))
                   {
                       currentJoueur2 = Integer.parseInt(nb3.getText()) + Integer.parseInt(nb4.getText());
                       res2.getItems().add(nb3.getText() + " + " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                       listJoueur2.remove(nb4.getText());
                       nb3.setText(String.valueOf(currentJoueur2));
                       nb3.setEditable(false);
                   }
                   else
                   {
                       currentJoueur2 = Integer.parseInt(nb3.getText()) * Integer.parseInt(nb4.getText());
                       res2.getItems().add(nb3.getText() + " * " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                       listJoueur2.remove(nb4.getText());
                       nb3.setText(String.valueOf(currentJoueur2));
                       nb3.setEditable(false);
                   }
               }
               else
               {
                   listJoueur2.add(nb3.getText());
                   JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
               }
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
           }
        }
        else
        {
            if (listJoueur2.contains(nb4.getText()))
            {
                if (op2.getValue().toString().equals("-"))
                {
                    if (Integer.parseInt(nb3.getText()) > Integer.parseInt(nb4.getText()))
                    {
                        currentJoueur2 = Integer.parseInt(nb3.getText()) - Integer.parseInt(nb4.getText());
                        res2.getItems().add(nb3.getText() + " - " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                        listJoueur2.remove(nb4.getText());
                        nb3.setText(String.valueOf(currentJoueur2));
                        nb3.setEditable(false);
                    }
                    else
                    {
                        listJoueur2.add(nb3.getText());
                        JOptionPane.showMessageDialog(null, "Veuillez inverser les nombres!");
                    }
                }
                else if (op2.getValue().toString().equals("/"))
                {
                    if (Integer.parseInt(nb3.getText()) % Integer.parseInt(nb4.getText()) == 0)
                    {
                        currentJoueur2 = Integer.parseInt(nb3.getText()) / Integer.parseInt(nb4.getText());
                        res2.getItems().add(nb3.getText() + " / " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                        listJoueur2.remove(nb4.getText());
                        nb3.setText(String.valueOf(currentJoueur2));
                        nb3.setEditable(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Operation Impossible!");
                    }
                }
                else if (op2.getValue().toString().equals("+"))
                {
                    currentJoueur2 = Integer.parseInt(nb3.getText()) + Integer.parseInt(nb4.getText());
                    res2.getItems().add(nb3.getText() + " + " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                    listJoueur2.remove(nb3.getText());
                    nb3.setText(String.valueOf(currentJoueur2));
                    nb3.setEditable(false);
                }
                else
                {
                    currentJoueur2 = Integer.parseInt(nb3.getText()) * Integer.parseInt(nb4.getText());
                    res2.getItems().add(nb3.getText() + " * " + nb4.getText() + " = " +  String.valueOf(currentJoueur2));
                    listJoueur2.remove(nb4.getText());
                    nb3.setText(String.valueOf(currentJoueur2));
                    nb3.setEditable(false);
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Le nombre n'existe pas dans la liste des choix possibles!");
            }
        }
    }
    
    public void ChargerSolution() throws IOException
    {
        String content = "";
        content = new String (Files.readAllBytes( Paths.get("src/chiffreslettres/template.html")));
        String generatedHTML = content.replace("$nb1",n1.getText().toString()).replace("$nb2",n2.getText().toString()).replace("$nb3",n3.getText().toString()).replace("$nb4",n4.getText().toString()).replace("$nb5",n5.getText().toString()).replace("$nb6",n6.getText().toString()).replace("$nbAlea",NombreAlea.getText().toString());
        WebEngine w = page.getEngine();
        w.loadContent(generatedHTML);
    }
    
    @FXML
    private void Valider(ActionEvent event) throws IOException 
    {
        txtOptimale.setVisible(true);
        page.setVisible(true);
        bt1.setVisible(true);
        bt.setVisible(false);
        if (currentJoueur1 == Integer.parseInt(NombreAlea.getText()) || currentJoueur2 == Integer.parseInt(NombreAlea.getText()))
        {
            if (currentJoueur1 == Integer.parseInt(NombreAlea.getText()))
           {
                LoginJeuController.scoreTotJ1 += 10;
                LoginJeuController.scoreCBJ1 += 10;
                if (LoginJeuController.CompteurJeu == 0)
                    LoginJeuController.sezame = 1;
            }
            if (currentJoueur2 == Integer.parseInt(NombreAlea.getText()))
            {
                LoginJeuController.scoreTotJ2 += 10;
                LoginJeuController.scoreCBJ2 += 10;
                if (LoginJeuController.CompteurJeu == 0)
                    LoginJeuController.sezame = 2;
            }
        }
        else
        {
            if (Math.abs(currentJoueur1 - Integer.parseInt(NombreAlea.getText())) < Math.abs(currentJoueur2 - Integer.parseInt(NombreAlea.getText())))
            {
                if (Math.abs(currentJoueur1 - Integer.parseInt(NombreAlea.getText())) <= 100)
                {
                    LoginJeuController.scoreTotJ1 += 7;
                    LoginJeuController.scoreCBJ1 += 7;
                }
            }
            else
            {
                if (Math.abs(currentJoueur2 - Integer.parseInt(NombreAlea.getText())) <= 100)
                {
                    LoginJeuController.scoreTotJ2 += 7;
                    LoginJeuController.scoreCBJ2 += 7;
                }
            }
        }
    }
    
    @FXML
    private void Suivant(ActionEvent event) throws IOException
    {
        LoginJeuController.CompteurJeu++;
        if(LoginJeuController.CompteurJeu==1)// l'epreuve de sézame
        {
        
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
        Stage current = (Stage)(res1.getScene().getWindow()) ;
        current.close();
        }
        else{        
        Parent p = FXMLLoader.load(getClass().getResource("MotLePlusLong.fxml"));
        Scene n = new Scene(p);
        Stage stage = new Stage();
        stage.setScene(n);
        stage.show();
        Stage current = (Stage)(res1.getScene().getWindow()) ;
        current.close();    
        }
    }
}
