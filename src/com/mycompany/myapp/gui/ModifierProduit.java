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
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ProduitService;
import java.io.IOException;

public class ModifierProduit extends BaseForm {

    Form current;

    public ModifierProduit(Resources res, Produit r) throws IOException {
        super("Produits", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField nom = new TextField(r.getNom(), "Nom", 20, TextField.ANY);
nom.getUnselectedStyle().setFgColor(0x000000);
        TextField quantite = new TextField(String.valueOf(r.getQuantite()), "Quantite", 20, TextField.ANY);
        quantite.getUnselectedStyle().setFgColor(0x000000);

        TextField descp_p = new TextField(r.getDescp_p(), "Description", 20, TextField.ANY);
        descp_p.getUnselectedStyle().setFgColor(0x000000);

        TextField prix = new TextField(String.valueOf(r.getPrix()), "prix", 20, TextField.ANY);
        prix.getUnselectedStyle().setFgColor(0x000000);

        TextField image = new TextField(r.getImage(), "Image", 20, TextField.ANY);
        image.getUnselectedStyle().setFgColor(0x000000);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        // Event onclick btnModifer
        btnModifier.addActionListener(e -> {
            // call the ProduitService method to modify the produit
            r.setNom(nom.getText());
            try {
                r.setQuantite(Integer.parseInt(quantite.getText()));
                r.setPrix(Float.parseFloat(prix.getText()));
            } catch (NumberFormatException nfe) {
                Dialog.show("Error", "Valeur de Quantite ou Prix non valide", "OK", null);
                return;
            }
            r.setDescp_p(descp_p.getText());
            r.setImage(image.getText());
            if (ProduitService.getInstance().modifierProduit(r)) {
                new ListProduitForm(res).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListProduitForm(res).show();
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
                new FloatingHint(prix),
                createLineSeparator(),
                new FloatingHint(quantite),
                createLineSeparator(),
                new FloatingHint(descp_p),
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
