/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Mhamed
 */
public class User {
    private int id;
    private String nom , adresse ,email, image,roles,password;
    private boolean Blocked;

    public User() {
    }

    public User(int id, String nom, String adresse, String email, String image, String roles, String password, boolean Blocked) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.image = image;
        this.roles = roles;
        this.password = password;
        this.Blocked = Blocked;
    }

    public User(String nom, String adresse, String email, String image, String roles, String password, boolean Blocked) {
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.image = image;
        this.roles = roles;
        this.password = password;
        this.Blocked = Blocked;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public boolean isBlocked() {
        return Blocked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlocked(boolean Blocked) {
        this.Blocked = Blocked;
    }
    
}
