/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author user
 */
public class Activite {
    private int id;
    private String nom_act;
    private String type_act;
    private String Event;

    public Activite() {
    }

    public Activite(int id, String nom_act, String type_act, String Event) {
        this.id = id;
        this.nom_act = nom_act;
        this.type_act = type_act;
        this.Event = Event;
    }

    public Activite(String nom_act, String type_act, String Event) {
        this.nom_act = nom_act;
        this.type_act = type_act;
        this.Event = Event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_act() {
        return nom_act;
    }

    public void setNom_act(String nom_act) {
        this.nom_act = nom_act;
    }

    public String getType_act() {
        return type_act;
    }

    public void setType_act(String type_act) {
        this.type_act = type_act;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String Event) {
        this.Event = Event;
    }

  

  

}


