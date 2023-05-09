/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.gui.SignUpForm.containsDigit;
import com.mycompany.myapp.services.UserService;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

/**
 *
 * @author Mhamed
 */
public class Profile extends BaseForm {
    	public Profile(Resources res) throws IOException {
                    super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
      
        
        
        Image img = res.getImage("profile-logo-removebg-preview.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Image pic = createImage(Statics.ProfilePic + SessionManager.getImage()).fill(300, 300);
   

	   add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(1, 
                            
                            FlowLayout.encloseCenter(
                                new ImageViewer(pic))
                           
                    )
                )
        ));
        
        
      /*  Image img = res.getImage("logo.PNG");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
     //   Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
       // Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
      //  facebook.setTextPosition(BOTTOM);
       // twitter.setTextPosition(BOTTOM);
        
     
     //  Label picture = new Label(ServiceUtilisateur.UrlImage(SessionManager.getImage()),"PictureWithBackground");
       
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3 ,
                          //  facebook,
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("logo.png"), "PictureWhiteBackgrond"))
                          //  twitter
                    )
                )
        ));*/
        Container cnt = new Container(BoxLayout.y());
        String us = SessionManager.getEmail();
        System.out.println(us);

      TextField email=new TextField(us);
                      email.getUnselectedStyle().setFgColor(0x000000);

       // email.setUIID("TextFieldBlack");
        addStringValue("Email", email);

     //  TextField email=new TextField(SessionManager.getEmail(),"email",20,TextField.EMAILADDR);
       // addStringValue("E-Mail", email);
        
        TextField password = new TextField(SessionManager.getPassowrd(), "Password", 20, TextField.PASSWORD);
                password.getUnselectedStyle().setFgColor(0x000000);

       // password.setUIID("TextFieldBlack");
        addStringValue("Password", password);
        //
        TextField nom = new TextField(SessionManager.getNom(), "Nom", 20, TextField.ANY);
                nom.getUnselectedStyle().setFgColor(0x000000);

       // password.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);
         TextField adresse = new TextField(SessionManager.getAdresse(), "Adresse", 20, TextField.ANY);
                adresse.getUnselectedStyle().setFgColor(0x000000);

       // password.setUIID("TextFieldBlack");
        addStringValue("Adresse", adresse);
        Button modif = new Button ("modifier") ;
        Button supprimer = new Button ("Supprimer") ;
        Button logout = new Button ("logout") ;
     // image.setUIID("Update");
     // modif.setUIID("Edit");
    //  addStrinfValue("",modif);
   // TextField path = new TextField("");
    
      
    cnt.add(modif);
    cnt.add(supprimer);
     cnt.add(logout);

        
        modif.addActionListener ((edit) ->{
            
                        if (nom.getText().length() == 0 || adresse.getText().length() == 0 || email.getText().length() == 0|| password.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            }

              else if (containsDigit(nom.getText().toString())){
        Dialog.show("Erreur", "Le champ nom ne doit pas contenir de chiffres.", "OK", null);
       
    }  else if (containsDigit(adresse.getText().toString())){
        Dialog.show("Erreur", "Le champ adresse ne doit pas contenir de chiffres.", "OK", null);
    } else if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
    // show an error dialog
    Dialog.show("Error", "Please enter a valid email address.", "OK", null);

       
    }    else if (password.getText().length() < 6 ){
        Dialog.show("Erreur", "Password doit contenir aux moins 6 carateres.", "OK", null);
       
    } else {
            try {

        InfiniteProgress ip =new InfiniteProgress();
        final Dialog ipDig =ip.showInifiniteBlocking();
        UserService.EditUser(SessionManager.getId() ,email.getText(), password.getText(), nom.getText(), adresse.getText());
        SessionManager.setEmail(email.getText());
     //   SessionManager.setEmail(email.getText());
        SessionManager.setPassowrd(password.getText());
        SessionManager.setNom(nom.getText());
        SessionManager.setAdresse(adresse.getText());

        Dialog.show("Succes", "Modification des cordonnées avec succes", "OK" , null);
        ipDig.dispose();
        refreshTheme();
                   } catch (NumberFormatException ev) {
                    Dialog.show("Error", "Invalid input", new Command("OK"));
                }
            }
        
        });
         supprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer votre compte ?","Annuler","Oui")) {
                dig.dispose();

            }
            else {
                dig.dispose();
                      if(UserService.getInstance().deleteUser(SessionManager.getId())) {
                    new SignInForm(res).show();
                }
                 }
                //n3ayto l suuprimer men service Reclamation
         
           
        });
         logout.addPointerPressedListener(l -> {
    boolean success = UserService.getInstance().logout(); // call the logout function and get the result
    if (success) {
        // handle successful logout
        // for example, navigate to the login screen
        new SignInForm(res).show();
    } else {
        // handle failed logout
        // for example, show an error message
                    Dialog dig = new Dialog("Logout");

                Dialog.show("Failed", "Logout failed. Please try again", "OK" , null);
        dig.dispose();

    }
});
         
         addAll(cnt);
  
                
        }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
       // add(createLineSeparator(0xeeeeee));
    }
    
}
