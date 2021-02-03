/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.util.List;

/**
 *
 * @author Mohamed
 */
public interface NbEtchifInterface {
    public List<User> AllUs();
    public List<User> Admins(List<User> all);
    public List<User> NormalUs(List<User> all);
    
    public List<Dictionnaire> Allmots();
    public List<Dictionnaire> motsWlength(List<Dictionnaire> l,int length);
    
    public void addUser(User u);
    public void DeleteUser(User u);
    public void UpdateUser(User u);
    public User findUser(List<User> all,String pseudo);
    
    public void addMot(Dictionnaire m);
    public void DeleteMot(Dictionnaire m);
    public void UpdateMot(Dictionnaire m);
    public Dictionnaire findMot(List<Dictionnaire> allmots,String valeur_mot);
    
    public List<Partie> AllParties();
    public List<Partie> findParties(List<Partie> all,String pseudo);
    public List<Partie> findParties(List<Partie> all,String pseudoJ1,String pseudoJ2);
    public List<User> TopScoresDesc(List<User> all);
    public User meilleurScoreGlob(List<User> all);
    public User meilleurScoreCB();
    public User meilleurScoreMLP();
    
   
    
    
    
    
    
}
