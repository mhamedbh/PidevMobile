/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.io.Preferences;
/**
 *
 * @author Mhamed
 */
public class SessionManager {
        public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id ; 
    private static String nom ; 
    private static String adresse ;
    private static String email; 
    private static String passowrd ;
    private static String image;
    private static String roles;
    private static boolean bocked;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        SessionManager.adresse = adresse;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static String getPassowrd() {
        return passowrd;
    }

    public static void setPassowrd(String passowrd) {
        SessionManager.passowrd = passowrd;
    }

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        SessionManager.image = image;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String roles) {
        SessionManager.roles = roles;
    }

    public static boolean isBocked() {
        return bocked;
    }

    public static void setBocked(boolean bocked) {
        SessionManager.bocked = bocked;
    }
    

}
