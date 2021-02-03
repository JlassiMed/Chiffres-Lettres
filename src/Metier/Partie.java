/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.sql.Date;

/**
 *
 * @author Mohamed
 */
public class Partie {
    private int id_partie;
   private String pseudo_us1;
   private String pseudo_us2;
   private int J1ScoreMLP;
   private int J2ScoreMLP;
   private int J1ScoreCB;
   private int J2ScoreCB;
   private int J1ScoreToT;
   private int J2ScoreTot;
   private Date date_partie;
    public Partie() {
    }

    public int getJ1ScoreToT() {
        return J1ScoreToT;
    }

    public void setJ1ScoreToT(int J1ScoreToT) {
        this.J1ScoreToT = J1ScoreToT;
    }

    public int getJ2ScoreTot() {
        return J2ScoreTot;
    }

    public void setJ2ScoreTot(int J2ScoreTot) {
        this.J2ScoreTot = J2ScoreTot;
    }

    public Partie(int id_partie, String pseudo_us1, String pseudo_us2, int J1ScoreMLP, int J2ScoreMLP, int J1ScoreCB, int J2ScoreCB, int J1ScoreToT, int J2ScoreTot, Date date_partie) {
        this.id_partie = id_partie;
        this.pseudo_us1 = pseudo_us1;
        this.pseudo_us2 = pseudo_us2;
        this.J1ScoreMLP = J1ScoreMLP;
        this.J2ScoreMLP = J2ScoreMLP;
        this.J1ScoreCB = J1ScoreCB;
        this.J2ScoreCB = J2ScoreCB;
        this.J1ScoreToT = J1ScoreToT;
        this.J2ScoreTot = J2ScoreTot;
        this.date_partie = date_partie;
    }

    public Date getDate_partie() {
        return date_partie;
    }

    public void setDate_partie(Date date_partie) {
        this.date_partie = date_partie;
    }

    

   

    public int getId_partie() {
        return id_partie;
    }

    public void setId_partie(int id_partie) {
        this.id_partie = id_partie;
    }

    public String getPseudo_us1() {
        return pseudo_us1;
    }

    public void setPseudo_us1(String pseudo_us1) {
        this.pseudo_us1 = pseudo_us1;
    }

    public String getPseudo_us2() {
        return pseudo_us2;
    }

    public void setPseudo_us2(String pseudo_us2) {
        this.pseudo_us2 = pseudo_us2;
    }

    public int getJ1ScoreMLP() {
        return J1ScoreMLP;
    }

    public void setJ1ScoreMLP(int J1ScoreMLP) {
        this.J1ScoreMLP = J1ScoreMLP;
    }

    public int getJ2ScoreMLP() {
        return J2ScoreMLP;
    }

    public void setJ2ScoreMLP(int J2ScoreMLP) {
        this.J2ScoreMLP = J2ScoreMLP;
    }

    public int getJ1ScoreCB() {
        return J1ScoreCB;
    }

    public void setJ1ScoreCB(int J1ScoreCB) {
        this.J1ScoreCB = J1ScoreCB;
    }

    public int getJ2ScoreCB() {
        return J2ScoreCB;
    }

    public void setJ2ScoreCB(int J2ScoreCB) {
        this.J2ScoreCB = J2ScoreCB;
    }
   
}
