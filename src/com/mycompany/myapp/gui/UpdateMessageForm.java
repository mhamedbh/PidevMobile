/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
    import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.services.ServiceMessage;
import java.io.IOException;

/**
 *
 * @author USER
 */
public class UpdateMessageForm extends BaseForm{
    Form current;
    public UpdateMessageForm(Resources res , Message r) throws IOException {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Update Message");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField Content = new TextField(r.getContent() , "Content" , 20 , TextField.ANY);
        TextField Surnom = new TextField(r.getSurnom() , "Surnom" , 20 , TextField.ANY);
        TextField Email = new TextField(r.getEmail() , "Email" , 20 , TextField.ANY);
         TextField Image = new TextField(r.getImage() , "Image" , 20 , TextField.ANY);

        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        ComboBox etatCombo = new ComboBox();
        
        etatCombo.addItem("Non Traiter");
        
        etatCombo.addItem("Traiter");
        
       
        
        
        
        
        Content.setUIID("NewsTopLine");
        Surnom.setUIID("NewsTopLine");
        Email.setUIID("NewsTopLine");
        Image.setUIID("NewsTopLine");

        
        Content.setSingleLineTextArea(true);
        Surnom.setSingleLineTextArea(true);
        Email.setSingleLineTextArea(true);
        Image.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setContent(Content.getText());
           r.setSurnom(Surnom.getText());
           r.setEmail(Surnom.getText());
           r.setImage(Image.getText());

          
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceMessage.getInstance().modifierMessage(r)) { try {
           // if true
           new ListMessageForm(res).show();
               } catch (IOException ex) {
               }
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
             try {
                 new ListMessageForm(res).show();
             } catch (IOException ex) {
             }
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(Content),
                createLineSeparator(),
                new FloatingHint(Surnom),
                createLineSeparator(),
               new FloatingHint(Email),
                createLineSeparator(),
                 new FloatingHint(Image),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }

    public UpdateMessageForm(Resources theme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
