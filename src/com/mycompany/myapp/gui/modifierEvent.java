/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.EventService;
import java.io.IOException;

/**
 *
 * @author user
 */
public class modifierEvent extends BaseForm {
        Form current;

    public modifierEvent (Resources res, Event r) throws IOException {
        super("Events", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Event");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        
        TextField Date_deb = new TextField(r.getDate_deb(), "Date_deb", 20, TextField.ANY);
        Date_deb.getUnselectedStyle().setFgColor(0x000000);
        
        TextField Date_fin = new TextField(r.getDate_fin(), "Date_fin", 20, TextField.ANY);
        Date_fin.getUnselectedStyle().setFgColor(0x000000);

        TextField Budget_eve = new TextField(String.valueOf(r.getBudget_eve()), "Budget_eve", 20, TextField.ANY);
        Budget_eve.getUnselectedStyle().setFgColor(0x000000);

        TextField Desc_eve = new TextField(r.getDesc_eve(), "Desc_eve", 20, TextField.ANY);
        Desc_eve.getUnselectedStyle().setFgColor(0x000000);
        
         TextField Emplacement = new TextField(r.getEmplacement(), "Emplacement", 20, TextField.ANY);
       Emplacement.getUnselectedStyle().setFgColor(0x000000);
        
         TextField Type_eve = new TextField(r.getType_eve(), "Type_eve", 20, TextField.ANY);
        Type_eve.getUnselectedStyle().setFgColor(0x000000);
        
         TextField Name = new TextField(r.getName(), "Name", 20, TextField.ANY);
        Name.getUnselectedStyle().setFgColor(0x000000);

        TextField Image = new TextField(r.getImage(), "Image", 20, TextField.ANY);
        Image.getUnselectedStyle().setFgColor(0x000000);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        // Event onclick btnModifer
        btnModifier.addActionListener(e -> {
            // call the ProduitService method to modify the produit
            r.setName(Name.getText());
            r.setDate_deb(Date_deb.getText());
            r.setDate_fin(Date_fin.getText());
            r.setDesc_eve(Desc_eve.getText());
            r.setEmplacement(Emplacement.getText());
            r.setType_eve(Type_eve.getText());
            r.setImage(Image.getText());
            try {
                r.setBudget_eve(Float.parseFloat(Budget_eve.getText()));
            } catch (NumberFormatException nfe) {
                Dialog.show("Error", "Valeur de Quantite ou Prix non valide", "OK", null);
                return;
            }
            if (EventService.getInstance().modifierEvent(r)) {
                try {
                    new ListEvent(res).show();
                } catch (IOException ex) {
                }
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            try {
                new ListEvent(res).show();
            } catch (IOException ex) {
            }
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label("");

        Container content = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(Name),
                createLineSeparator(),
                new FloatingHint(Date_deb),
                createLineSeparator(),
                new FloatingHint(Date_fin),
                createLineSeparator(),
                new FloatingHint(Budget_eve),
                createLineSeparator(),
                new FloatingHint(Desc_eve),
                createLineSeparator(),
                new FloatingHint(Emplacement),
                createLineSeparator(),
                  new FloatingHint(Type_eve),
                createLineSeparator(),
                new FloatingHint(Image),
                createLineSeparator(),
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
        );

        add(content);
        show();
    }

    
}
