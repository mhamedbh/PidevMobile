/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author user
 */
public class Event {
    private int id;
    private String date_deb;
    private String date_fin;
    private float budget_eve;
    private String desc_eve;
    private String emplacement;
    private String type_eve;
    private String nom;
    private String image;

    public Event() {
        
    }

    public Event(int id, String nom, String date_deb, String date_fin, float budget_eve, String desc_eve, String emplacement, String type_eve, String image) {
        this.id = id;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.budget_eve = budget_eve;
        this.desc_eve = desc_eve;
        this.emplacement = emplacement;
        this.type_eve = type_eve;
        this.nom = nom;
        this.image = image;

    }

    public Event(String nom, String date_deb, String date_fin, float budget_eve, String desc_eve, String emplacement, String type_eve,String image) {
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.budget_eve = budget_eve;
        this.desc_eve = desc_eve;
        this.emplacement = emplacement;
        this.type_eve = type_eve;
        this.nom = nom;
        this.image = image;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nom;
    }

    public void setName(String Name) {
        this.nom = Name;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(String date_deb) {
        this.date_deb = date_deb;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public float getBudget_eve() {
        return budget_eve;
    }

    public void setBudget_eve(float budget_eve) {
        this.budget_eve = budget_eve;
    }
      public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc_eve() {
        return desc_eve;
    }

    public void setDesc_eve(String desc_eve) {
        this.desc_eve = desc_eve;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getType_eve() {
        return type_eve;
    }

    public void setType_eve(String type_eve) {
        this.type_eve = type_eve;
    }



    

}
