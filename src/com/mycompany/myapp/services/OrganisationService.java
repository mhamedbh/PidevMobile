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
import com.mycompany.myapp.entities.Organisation;
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
public class OrganisationService {
     public static OrganisationService instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static OrganisationService getInstance() {
        if(instance == null )
            instance = new OrganisationService();
        return instance ;
    }
    
    
    
    public OrganisationService() {
        req = new ConnectionRequest();
        
    }
      
    //ajout 
    public void ajoutOrganisation(Organisation organisation) {
        
        String url =Statics.BASE_URL+"organisation/addOrganisationJSON/new?nom="+organisation.getNom()+"&adresse="+organisation.getAdresse()+"&description="+organisation.getDescription()+"&image="+organisation.getImage(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
                        
     //affichage  
         public ArrayList<Organisation>affichageOrganisation() {
        ArrayList<Organisation> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"organisation/ALLOrganisation/1";
        req.setUrl(url);
        
        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp ;
            jsonp = new JSONParser();
            
            try {
                Map<String,Object>mapOrganisation = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                
                List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapOrganisation.get("root");
                
                for(Map<String, Object> obj : listOfMaps) {
                    Organisation re = new Organisation();
                    
                    //dima id fi codename one float 5outhouha
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                    String Nom = obj.get("nom").toString();
                    
                    String Adresse = obj.get("adresse").toString();
                    String Description = obj.get("description").toString();
                     String Image = obj.get("image").toString();

                    
                    re.setId((int)id);
                    re.setNom(Nom);
                    re.setAdresse(Adresse);
                    re.setDescription(Description);
                     re.setImage(Image);

                    
                    //Date
                   
                    
                    //insert data into ArrayList result
                    result.add(re);
                    
                    
                }
            
            }catch(Exception ex) {
                
                ex.printStackTrace();
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }

     //Delete 
    public boolean deleteOrganisation(int id ) {
        String url = Statics.BASE_URL +"organisation/deleteOrganisationJSON/"+id;
        
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
    
    
    
    //Update 
    public boolean modifierOrganisation(Organisation organisation) {
        String url =Statics.BASE_URL+"organisation/updateOrganisationJSON/"+organisation.getId()+"?nom="+organisation.getNom()+"&adresse="+organisation.getAdresse()+"&description="+organisation.getDescription()+"&image="+organisation.getImage(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ; 
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
        
    }

   
    

}
