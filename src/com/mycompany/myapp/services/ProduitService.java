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
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class ProduitService {
    public static ProduitService instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ProduitService getInstance() {
        if(instance == null )
            instance = new ProduitService();
        return instance ;
    }
    
    
    
    public ProduitService() {
        req = new ConnectionRequest();
        
    }
    
    
    
    //ajout 
    public boolean ajoutProduit(Produit produit) {
        
        String url =Statics.BASE_URL+"produit/addProduitJSON/new?nom="+produit.getNom()+"&quantite="+produit.getQuantite()+"&prix="+produit.getPrix()+"&descp_p="+produit.getDescp_p()+"&image="+produit.getImage(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return false;
        
   }
    public  List<Produit> getAllProduits() {
        List<Produit> produits = new ArrayList<>();

         String url = Statics.BASE_URL+"produit/produit/21";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapProduits = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapProduits.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Produit re = new Produit();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nom = obj.get("nom").toString();
                        String descp_p = obj.get("descp_p").toString();
                        String image = obj.get("image").toString();
                        float quantite = Float.parseFloat(obj.get("quantite").toString());
                        float prix = Float.parseFloat(obj.get("prix").toString());
                        
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescp_p(descp_p);
                        re.setImage(image);
                        re.setPrix(prix);
                        re.setQuantite((int)quantite);

                        
                                      
                        //insert data into ArrayList result
                        produits.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return produits;
    }
     //affichage

    public ArrayList<Produit>affichageProduit() {
        ArrayList<Produit> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"produit/produit/21";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapProduits = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapProduits.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Produit re = new Produit();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nom = obj.get("nom").toString();
                        String descp_p = obj.get("descp_p").toString();
                        String image = obj.get("image").toString();
                        float quantite = Float.parseFloat(obj.get("quantite").toString());
                        float prix = Float.parseFloat(obj.get("prix").toString());
                        
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescp_p(descp_p);
                        re.setImage(image);
                        re.setPrix(prix);
                        re.setQuantite((int)quantite);

                        
                                      
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
     //Delete 
   public boolean deleteProduit(int id) {
    String url = Statics.BASE_URL + "produit/deleteProduitJSON/"+id;
    req.setUrl(url);
    req.setHttpMethod("DELETE");

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            // Check the response code and set the resultOk accordingly
            int responseCode = evt.getResponseCode();
            resultOk = responseCode == 200 || responseCode == 204;
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
}

    
    
    //Update 
    public boolean modifierProduit(Produit produit) {
        String url = Statics.BASE_URL +"produit/updateProduitJSON/"+produit.getId()+"?nom="+produit.getNom()+"&quantite="+produit.getQuantite()+"&prix="+produit.getPrix()+"&descp_p="+produit.getDescp_p()+"&image="+produit.getImage();
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
    

}
