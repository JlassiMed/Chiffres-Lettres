/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author Mohamed
 */
public class Dictionnaire {
    private int id_mot;
    private String valeur_mot;

    public Dictionnaire() {
    }

    public int getId_mot() {
        return id_mot;
    }

    public void setId_mot(int id_mot) {
        this.id_mot = id_mot;
    }

    public String getValeur_mot() {
        return valeur_mot;
    }

    public void setValeur_mot(String valeur_mot) {
        this.valeur_mot = valeur_mot;
    }

    public Dictionnaire(int id_mot, String valeur_mot) {
        this.id_mot = id_mot;
        this.valeur_mot = valeur_mot;
    }
    
}
