/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class EventService {
     public static EventService instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static EventService getInstance() {
        if(instance == null )
            instance = new EventService();
        return instance ;
    }
    //La classe (eventservice) a une méthode publique et statique "getInstance()" qui renvoie la variable "instance" de la classe. 
    //Si la variable "instance" est nulle, elle crée une nouvelle instance de la classe "EventService" et l'assigne à la variable "instance" avant de la renvoyer.
    
    
    
    public EventService() {
        req = new ConnectionRequest();
        
    }
    //Le constructeur de la classe initialise la variable d'instance "req" en créant un nouvel objet "ConnectionRequest".
      
    //ajout 
public void ajoutEvent(Event event) {
    String url = Statics.BASE_URL + "event/addEventJSON/new?date_deb=" + event.getDate_deb() + "&date_fin=" + event.getDate_fin() + "&budget_eve=" + event.getBudget_eve() + "&desc_eve=" + event.getDesc_eve() + "&emplacement=" + event.getEmplacement() + "&type_eve=" + event.getType_eve() + "&name=" + event.getName() + "&image=" + event.getImage();

    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);

    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
}
/*cette méthode est utilisée pour ajouter un nouvel événement à la base de données en utilisant une requête de connexion à
l'aide de l'API REST.*/

                        
     //affichage  
       public ArrayList<Event>affichageEvent() {
        ArrayList<Event> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"event/ALLEVENTS/1";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapEvent = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapEvent.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Event re = new Event();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String date_deb = obj.get("date_deb").toString();
                        String date_fin = obj.get("date_fin").toString();
                        String image = obj.get("image").toString();
                        float budget_eve = Float.parseFloat(obj.get("budget_eve").toString());
                        String emplacement = obj.get("emplacement").toString();
                        String desc_eve = obj.get("desc_eve").toString();
                        String type_eve = obj.get("type_eve").toString();
                        String name = obj.get("Name").toString();


                        re.setId((int)id);
                        re.setDate_deb(date_deb);
                        re.setDate_fin(date_fin);
                        re.setImage(image);
                        re.setEmplacement(emplacement);
                        re.setDesc_eve(desc_eve);
                        re.setType_eve(type_eve);
                        re.setName(name);
                        re.setBudget_eve((int)budget_eve);

                        
                                      
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
 /*cette méthode est utilisée pour récupérer tous les événements de la base de données en utilisant une requête de connexion à 
 l'aide de l'API REST, puis de les convertir en une liste d'objets "Event".*/
       
       
     //Delete 
    public boolean deleteEvent(int id ) {
        String url = Statics.BASE_URL +"event/deleteEventJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    /*cette méthode est utilisée pour supprimer un événement de
    la base de données en utilisant une requête de suppression à l'aide de l'API REST.*/
    
    
    
    //Update 
    public boolean modifierEvent(Event event) {
        String url =Statics.BASE_URL+"event/updateEventJSON/"+event.getId()+"?date_deb=" + event.getDate_deb() + "&date_fin=" + event.getDate_fin() + "&budget_eve=" + event.getBudget_eve() + "&desc_eve=" + event.getDesc_eve() + "&emplacement=" + event.getEmplacement() + "&type_eve=" + event.getType_eve() + "&name=" + event.getName() + "&image=" + event.getImage();// aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }

    public void addEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
