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
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Organisation;
import com.mycompany.myapp.services.OrganisationService;
import java.io.IOException;

/**
 *
 * @author user
 */
public class ModifierOrganisation extends BaseForm {
    
    Form current;
    public ModifierOrganisation(Resources res , Organisation r) throws IOException 
    {super("Organisations", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Organisation");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField nom = new TextField(r.getNom(), "Nom", 20, TextField.ANY);
        nom.getUnselectedStyle().setFgColor(0x000000);

        TextField adresse = new TextField(r.getAdresse(), "Adresse", 20, TextField.ANY);
        adresse.getUnselectedStyle().setFgColor(0x000000);

        TextField description = new TextField(r.getDescription(), "Description", 20, TextField.ANY);
        description.getUnselectedStyle().setFgColor(0x000000);

       

        TextField image = new TextField(r.getImage(), "Image", 20, TextField.ANY);
        image.getUnselectedStyle().setFgColor(0x000000);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        // Event onclick btnModifer
        btnModifier.addActionListener(e -> {
            // call the ProduitService method to modify the produit
            r.setNom(nom.getText());
            r.setAdresse(adresse.getText());
            r.setDescription(description.getText());
            r.setImage(image.getText());
            if (OrganisationService.getInstance().modifierOrganisation(r)) {
                try {
                    new ListOrganisationForm(res).show();
                } catch (IOException ex) {
                }
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
        try {
            new ListOrganisationForm(res).show();
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
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(adresse),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                new FloatingHint(image),
                createLineSeparator(),
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
        );

        add(content);
        show();
    }

    
}
