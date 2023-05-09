/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.mycompany.myapp.gui.Profile;
import com.mycompany.myapp.gui.SessionManager;
import com.mycompany.myapp.gui.SignUpForm;
import com.mycompany.myapp.gui.WalkthruForm;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Mhamed
 */
public class UserService {
    
    
  //singleton 
    public static UserService instance = null ;
    
    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;
        String image;

     public String hashPassword(String password) {
    String salt = BCrypt.gensalt(10);
    String hashedPassword = BCrypt.hashpw(password, salt);
    return hashedPassword;
}
    
    public static UserService getInstance() {
        if(instance == null )
            instance = new UserService();
        return instance ;
    }
    
    
    
    public UserService() {
        req = new ConnectionRequest();
        
    }
    
    //Signup
    public void signup(TextField username,TextField password,TextField email,TextField confirmPassword,TextField adresse, String image, ComboBox<String> roles , Resources res ) {
        
     
        
        String url = Statics.BASE_URL+"user/addUserJSON/new?nom="+username.getText().toString()+"&email="+email.getText().toString()+
                "&password="+password.getText().toString()+"&roles="+roles.getSelectedItem().toString()+"&adresse="+adresse.getText().toString()+"&image="+image;
        
        req.setUrl(url);
       System.out.println(url);
        //Control saisi
        if(username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ")) {
            
            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
            
        }
        
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
            
        
    }
        //SignIn
    
    public void signin (TextField email, TextField password , Resources res){
     // String r = '"'+"ROLE_ADMIN"+'"';  
       String r="ROLE_ADMIN";
    String url = Statics.BASE_URL+"user/loginJson?email="+email.getText().toString()+"&password="+password.getText().toString();
   req = new ConnectionRequest(url , false); // false yaani url mezelt matba3thitch lel server
    req.setUrl(url);
   req.addResponseListener((e)->{
        
   JSONParser j = new JSONParser();
  // String json = new String(req.getResponseData())+"";
   json = new String(req.getResponseData()) + "";
  
   try{
  System.out.println(password.getText());
   if(json.equals("password not found")||json.equals("user not found")){
       Dialog.show("Echec d'authentification","Email ou mot de passe éronné","OK", null);
   }
   
   else{
      // User student = new User();
       System.out.println("data =="+json);
       Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
    
    //if(SessionManager.pref.getIsBanned().equals(true) ){
       //  Dialog.show("Echec d'authentification","Votre compte est desactivé","OK", null);}
  //  else{
    
                       Result result = Result.fromContent(user);
         String roles=result.getAsString("roles");
        float id = Float.parseFloat(user.get("id").toString());
                SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
               SessionManager.setPassowrd(user.get("password").toString());
                SessionManager.setAdresse(user.get("adresse").toString());
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setNom(user.get("nom").toString());
                SessionManager.setImage(user.get("image").toString());
              //float roles =Float.parseFloat(user.get("roles").toString());
                SessionManager.setRoles(roles);
           
                
            //    System.out.println(roles);
  // System.out.println(SessionManager.getPrenom());
                
               //System.out.println(user.get("roles"));

//System.out.println(roles.getClass().getName());
               
          new Profile(res).show();

      
     //else{
      // new ActivateForm(res).show();  
    // }
  // }
   }
   }catch(Exception ex){
   ex.printStackTrace();}
   });
   
     NetworkManager.getInstance().addToQueueAndWait(req);
    }
    /*public void EditUser(String nom,String password, String email, String image){
        String url ="";
        MultipartRequest req = new MultipartRequest();
   
        req.setPost(true);
        req.addArgument("id",String.valueOf(SessionManager.getId()));
        req.addArgument("nom",nom);
        req.addArgument("password",password);
        req.addArgument("email",email);
        System.out.println(email);
        req.addResponseListener((response)->{
            byte[] data = (byte[]) response.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if(s.equals("success")){
            }else{
            Dialog.show("Erreur","Echec de  modification","ok",null);}
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    
    }*/

    public String getPasswordByEmail(String email, Resources rs) {
        String url = Statics.BASE_URL+"user/getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";
            //   if(json.equals("user not found")){
            //   Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK", null);    
            //}
            //else{     
            try {

                System.out.println("data ==" + json);


             //   Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));


            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // } 
        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }
 // edit user profil
    public static void EditUser(int id, String username, String password, String nom, String adresse) {

        String url = Statics.BASE_URL + "user/editProfileJson?id=" + id + "&email=" + username + "&password=" + password+ "&nom=" + nom + "&adresse=" + adresse;
        MultipartRequest req = new MultipartRequest();

        req.setUrl(url);
        req.setPost(true);
        //  req.addArgument("id",String.valueOf(SessionManager.getId()));
        req.addArgument("username", username);
        req.addArgument("password", password);
        req.addArgument("nom", nom);
        req.addArgument("adresse", adresse);

        //req.addArgument("email", email);
        //System.out.println(email);
        req.addResponseListener((response) -> {

            byte[] data = (byte[]) response.getMetaData();
            String s = new String(data);
            System.out.println(s);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
        public String getClubPic() {
        String url = Statics.BASE_URL + "user/uploadUserPic/" ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                image = StringUtil.replaceAll(new String(req.getResponseData()), "\"", "");

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return image;
    }
//Delete 
    public boolean deleteUser(int id ) {
        String url = Statics.BASE_URL +"user/deleteUserJSON/"+id;
        
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
    public boolean logout() {
    String url = Statics.BASE_URL + "logout";
    req.setUrl(url);
    req.setHttpMethod("POST");
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            int status = evt.getResponseCode();
            if (status == 200) {
                // logout succeeded
                resultOk = true;
            } else {
                // logout failed
                resultOk = false;
            }
            req.removeResponseListener(this);
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
}
}
