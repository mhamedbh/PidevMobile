/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author USER
 */
public class Reponse {
     private int id,related_message_id;
    private String username,content; 

    public Reponse() {
    }

    public Reponse(int id, int related_message_id, String username, String content) {
        this.id = id;
        this.related_message_id = related_message_id;
        this.username = username;
        this.content = content;
    }

    public Reponse(int related_message_id, String username, String content) {
        this.related_message_id = related_message_id;
        this.username = username;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getRelated_message_id() {
        return related_message_id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setRelated_message_id(int related_message_id) {
        this.related_message_id = related_message_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
    
}

