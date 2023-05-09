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

/**
 *
 * @author USER
 */
public class MessageEdit extends BaseForm{
      Form current;
      /*
    public MessageEdit(Resources res , Message r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField objet = new TextField(r.getContent() , "Content" , 20 , TextField.ANY);
        TextField description = new TextField(r.getSurnom() , "Surnom" , 20 , TextField.ANY);
               TextField etat = new TextField(r.getEmail() , "Email" , 20 , TextField.ANY);
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        ComboBox etatCombo = new ComboBox();
        
        etatCombo.addItem("Non Traiter");
        
        etatCombo.addItem("Traiter");
        
      
        
        
        
        
     
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setObjet(objet.getText());
           r.setDescription(description.getText());
           
           if(etatCombo.getSelectedIndex() == 0 ) {
               r.setEtat(0);
           }
           else 
               r.setEtat(1);
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceReclamation.getInstance().modifierReclamation(r)) { // if true
           new ListReclamationForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListReclamationForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(objet),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                etatCombo,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
*/
}
