package com.mycompany.myapp.entities;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private int id;
    private String Content,Surnom;
    private String email;
    String Image;
    String[] bannedWords = {"mot1", "mot2", "mot3"};

        public Message() {
        }

    public Message(int id, String Content, String Surnom, String email, String Image) {
        this.id = id;
        this.Content = Content;
        this.Surnom = Surnom;
        this.email = email;
        this.Image = Image;
    }

    public Message(String Content, String Surnom, String email, String Image) {
        this.Content = Content;
        this.Surnom = Surnom;
        this.email = email;
        this.Image = Image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getSurnom() {
        return Surnom;
    }

    public void setSurnom(String Surnom) {
        this.Surnom = Surnom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
    
    public static List<Message> searchByContent(List<Message> messages, String content) {
        List<Message> result = new ArrayList<Message>();
        for (Message message : messages) {
            if (message.getContent().contains(content)) {
                result.add(message);
            }
        }
        return result;
    }
}
