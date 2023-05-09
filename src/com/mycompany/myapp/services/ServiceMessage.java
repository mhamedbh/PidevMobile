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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author USER
 */
public class ServiceMessage {
       
    //singleton 
    public static ServiceMessage instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceMessage getInstance() {
        if(instance == null )
            instance = new ServiceMessage();
        return instance ;
    }
    
    
    
    public ServiceMessage() {
        req = new ConnectionRequest();
        
    }
     public void ajoutMessage(Message Message) {
        String url =Statics.BASE_URL+"message/addJSON/new?Content="+Message.getContent()+"&Surnom="+Message.getSurnom()+"&email="+Message.getEmail()+"&image="+Message.getImage(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
     public EncodedImage getImageFromUrl(String imageUrl) throws IOException {
    ConnectionRequest request = new ConnectionRequest();
    request.setUrl(imageUrl);
    NetworkManager.getInstance().addToQueueAndWait(request);
    if (request.getResponseCode() == 200) {
        byte[] data = request.getResponseData();
        if (data != null) {
            return EncodedImage.create(data);
        }
    }
    throw new IOException("Error loading image from URL: " + imageUrl);
}

      public ArrayList<Message>affichageMessage() {
        ArrayList<Message> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"message/aamessage/3";
        req.setUrl(url);
        
        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp ;
            jsonp = new JSONParser();
            
            try {
                Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                
                List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                
                for(Map<String, Object> obj : listOfMaps) {
                    Message re = new Message();
                    
                    //dima id fi codename one float 5outhouha
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                    String Content = obj.get("Content").toString();
                    
                    String Surnom = obj.get("Surnom").toString();
                    String email = obj.get("email").toString();
                    //             String Image = obj.get("Image").toString();

String image = "yass"; // initialize Image to null

try {
    image = obj.get("image").toString(); // attempt to call toString() on Image
} catch (NullPointerException e) {
    // handle the NullPointerException
    System.err.println("Image is null. Cannot call toString().");
    //Image = null ;
     image = "yass"; // initialize Image to null

    e.printStackTrace(); // print the stack trace for debugging purposes
}
System.out.println(image);
/*if (obj.containsKey("Image")) {
    Image = obj.get("Image").toString();
}*/
                    re.setId((int)id);
                    re.setContent(Content);
                    re.setSurnom(Surnom);
                    re.setEmail(email);
                    re.setImage(image);

                    
                   
                    
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
    public boolean deleteMessage(int id ) {
        String url = Statics.BASE_URL +"message/deleteJSON/"+id;
        
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
    public boolean modifierMessage(Message Message) {
            String url = Statics.BASE_URL +"message/updateJSON/"+Message.getId()+"?Content="+Message.getContent()+"&Surnom="+Message.getSurnom()+"&email="+Message.getEmail()+"&image="+Message.getImage();
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
