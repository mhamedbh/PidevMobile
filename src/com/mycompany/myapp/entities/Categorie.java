/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Lenovo
 */
public class Categorie {
    private int id;
    private String type_cat;

    public Categorie() {
    }

    public Categorie(int id, String type_cat) {
        this.id = id;
        this.type_cat = type_cat;
    }

    public Categorie(String type_cat) {
        this.type_cat = type_cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_cat() {
        return type_cat;
    }

    public void setType_cat(String type_cat) {
        this.type_cat = type_cat;
    }
    
    
}
