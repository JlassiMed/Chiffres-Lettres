/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.sql.Blob;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Mohamed
 */
public class User {
   private String Pseudo_us;
   private String password_us;
   private String nom_us;
   private String prenom_us;
   private String cin_us;
   private Date datenaiss_us;
   private int meilscore_us;
   private Blob photo_us;
   private String type_us;

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.Pseudo_us, other.Pseudo_us)) {
            return false;
        }
        return true;
    }

    public String getPassword_us() {
        return password_us;
    }

    public void setPassword_us(String password_us) {
        this.password_us = password_us;
    }

    public User(String Pseudo_us, String password_us, String nom_us, String prenom_us, String cin_us, Date datenaiss_us, int meilscore_us, Blob photo_us, String type_us) {
        this.Pseudo_us = Pseudo_us;
        this.password_us = password_us;
        this.nom_us = nom_us;
        this.prenom_us = prenom_us;
        this.cin_us = cin_us;
        this.datenaiss_us = datenaiss_us;
        this.meilscore_us = meilscore_us;
        this.photo_us = photo_us;
        this.type_us = type_us;
    }
   
    

    public User() {
        this.meilscore_us = 0;
        this.type_us = "N";
    }

    public String getPseudo_us() {
        return Pseudo_us;
    }

    public void setPseudo_us(String Pseudo_us) {
        this.Pseudo_us = Pseudo_us;
    }

    public String getNom_us() {
        return nom_us;
    }

    public void setNom_us(String nom_us) {
        this.nom_us = nom_us;
    }

    public String getPrenom_us() {
        return prenom_us;
    }

    public void setPrenom_us(String prenom_us) {
        this.prenom_us = prenom_us;
    }

    public String getCin_us() {
        return cin_us;
    }

    public void setCin_us(String cin_us) {
        this.cin_us = cin_us;
    }

    public Date getDatenaiss_us() {
        return datenaiss_us;
    }

    public void setDatenaiss_us(Date datenaiss_us) {
        this.datenaiss_us = datenaiss_us;
    }

    public int getMeilscore_us() {
        return meilscore_us;
    }

    public void setMeilscore_us(int meilscore_us) {
        this.meilscore_us = meilscore_us;
    }

    public Blob getPhoto_us() {
        return photo_us;
    }

    public void setPhoto_us(Blob photo_us) {
        this.photo_us = photo_us;
    }

    public String getType_us() {
        return type_us;
    }

    public void setType_us(String type_us) {
        this.type_us = type_us;
    }
   
}
