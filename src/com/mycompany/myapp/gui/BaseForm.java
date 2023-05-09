/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;



/**
 *
 * @author Mhamed
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }
    
    
    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) throws IOException {
        Toolbar tb = getToolbar();
        Image img = res.getImage("Logo.png");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                Image pic = createImage(Statics.ProfilePic + SessionManager.getImage()).fill(300, 300);

        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                                new ImageViewer(pic))
        ));
        
        
        //tb.addMaterialCommandToSideMenu("", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
    
              tb.addMaterialCommandToSideMenu("Event", FontImage.MATERIAL_DATE_RANGE, e -> {
            try {
                new ListEvent(res).show();
            } catch (IOException ex) {
            }
        });
               tb.addMaterialCommandToSideMenu("Product", FontImage.MATERIAL_SHOPPING_BAG, e -> {
                  new ListProduitForm(res).show();
        });
             tb.addMaterialCommandToSideMenu("Chat", FontImage.MATERIAL_CHAT, e -> {
            try {
                new ListMessageForm(res).show();
            } catch (IOException ex) {
            }
        });
                          tb.addMaterialCommandToSideMenu("Organisation", FontImage.MATERIAL_GROUP, e -> {
            try {
                new ListOrganisationForm(res).show();
            } catch (IOException ex) {
            }
        });
                          
           tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            try {
                new Profile(res).show();
            } catch (IOException ex) {
            }
        });
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new SignInForm(res).show());
    }
}
