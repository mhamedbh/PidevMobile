
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
public class Produit {
    private int id, quantite;
    private String nom,image,descp_p;
    private float prix;
    private String  latitude, longitude;

    public Produit() {
    }

    public Produit(int id, int quantite, String nom, String image, String descp_p, float prix, String latitude, String longitude) {
        this.id = id;
        this.quantite = quantite;
        this.nom = nom;
        this.image = image;
        this.descp_p = descp_p;
        this.prix = prix;
               this.latitude = latitude;
        this.longitude = longitude;
    }

    public Produit(int quantite, String nom, String image, String descp_p, float prix) {
        this.quantite = quantite;
        this.nom = nom;
        this.image = image;
        this.descp_p = descp_p;
        this.prix = prix;
    }

    public Produit(int id, int quantite, String nom, String image, String descp_p, float prix) {
        this.id = id;
        this.quantite = quantite;
        this.nom = nom;
        this.image = image;
        this.descp_p = descp_p;
        this.prix = prix;
    }

    public Produit(int quantite, String nom, String image, String descp_p, float prix, String latitude, String longitude) {
        this.quantite = quantite;
        this.nom = nom;
        this.image = image;
        this.descp_p = descp_p;
        this.prix = prix;
                this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescp_p() {
        return descp_p;
    }

    public void setDescp_p(String descp_p) {
        this.descp_p = descp_p;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


    
    
}
