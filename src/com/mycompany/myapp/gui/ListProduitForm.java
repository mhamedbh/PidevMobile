/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ProduitService;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Lenovo
 */
public class ListProduitForm extends BaseForm {

    Form current;
    private ArrayList<Produit> produits;
    private Container produitsContainer;
    private ArrayList<Produit> produitsTemp;

    public ListProduitForm(Resources res) throws IOException {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
    //    setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);
         super.addSideMenu(res);

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("prod.jpg"), "", "", res);

        // Welcome current user
        /*System.out.println("user connecté id ="+ SessionManager.getId());
        
        
        
        System.out.println("user connecté username ="+ SessionManager.getUserName());
        
        System.out.println("user connecté password ="+ SessionManager.getPassowrd());
        
        System.out.println("user connecté email ="+ SessionManager.getEmail());
         */
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();

        RadioButton partage = RadioButton.createToggle("Ajout Produit", barGroup);
        partage.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        partage.addActionListener((e) -> {
            AddProduitForm a;
            try {
                a = new AddProduitForm(res);       
                a.show();

            } catch (IOException ex) {
            }
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, partage),
                FlowLayout.encloseBottom(arrow)
        ));
        TextField searchTF = new TextField("", "Rechercher un produit", 20, TextField.ANY);
        searchTF.getUnselectedStyle().setFgColor(0x000000);
        add(searchTF);
        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });

        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //Appel affichage methode
        produits = ProduitService.getInstance().affichageProduit();
        produitsTemp = new ArrayList<>();
        for (Produit prod : produits) {
            produitsTemp.add(prod);
        }
        produitsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        for (Produit rec : produitsTemp) {
            String urlImage = "back-logo.jpeg";//image statique pour le moment ba3d taw fi  videos jayin nwarikom image 

            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            int height = Display.getInstance().convertToPixels(11.5f);
            int width = Display.getInstance().convertToPixels(14f);

            Button image = new Button(urlim.fill(width, height));
            image.setUIID("Label");
            Container oneCont = BorderLayout.west(image);

            //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
Label nomTxt = new Label("Nom : " + rec.getNom());
Label quantiteTxt = new Label("Quantite : " + rec.getQuantite());
Label prixTxt = new Label("Prix : " + rec.getPrix() + " TND");
Label descp_pTxt = new Label("Description : " + rec.getDescp_p());

Style labelStyle = UIManager.getInstance().getComponentStyle("Label");
Font labelFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
labelStyle.setFont(labelFont);

// Set the color of the label names to pink
labelStyle.setFgColor(0xFF69B4);

nomTxt.getAllStyles().merge(labelStyle);

// Create a new style for the name getter and set its color to black
Style nameGetterStyle = new Style(labelStyle);
nameGetterStyle.setFgColor(0xFF69B4);
nomTxt.getAllStyles().setFgColor(0xFF69B4); // Set the color of the label name to pink
nomTxt.getAllStyles().setFgColor(0x000000); // Set the color of the name getter to black

quantiteTxt.getAllStyles().merge(labelStyle);
quantiteTxt.getAllStyles().setFgColor(0x000000);

prixTxt.getAllStyles().merge(labelStyle);
prixTxt.getAllStyles().setFgColor(0x000000);

descp_pTxt.getAllStyles().merge(labelStyle);
descp_pTxt.getAllStyles().setFgColor(0x000000);



                        createLineSeparator();

          try {
              
                Image pic = createImage(Statics.ProduitPicture + rec.getImage()).fill(600, 600);
                ImageViewer img = new ImageViewer(pic);
            img.setUIID("NewsTopLine");
            Label lSupprimer = new Label(" ");
            lSupprimer.setUIID("NewsTopLine");
            Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
            supprmierStyle.setFgColor(0xf21f1f);

            FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
            lSupprimer.setIcon(suprrimerImage);
            lSupprimer.setTextPosition(RIGHT);

            //click delete icon
            lSupprimer.addPointerPressedListener(l -> {
                Dialog dig = new Dialog("Suppression");
                if (dig.show("Suppression", "Vous voulez supprimer ce produit ?", "Annuler", "Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();
                }
                //delete product using service
                boolean success = ProduitService.getInstance().deleteProduit(rec.getId());
                if (success) {
                    Dialog.show("Suppression", "Produit supprimé avec succès", "OK", null);
                    try {
                        new ListProduitForm(res).show(); // handle the exception
                    } catch (IOException ex) {
                    }
                } else {
                    Dialog.show("Erreur", "Erreur lors de la suppression du produit", "OK", null);
                }
            });

            //Update icon 
            Label lModifier = new Label(" ");
            lModifier.setUIID("NewsTopLine");
            Style modifierStyle = new Style(lModifier.getUnselectedStyle());
            modifierStyle.setFgColor(0xf7ad02);

            FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
            lModifier.setIcon(mFontImage);
            lModifier.setTextPosition(LEFT);

            lModifier.addPointerPressedListener(l -> {
                    try {
                        //System.out.println("hello update");
                        new ModifierProduit(res, rec).show();
                    } catch (IOException ex) {
                    }
            });
            //Update icon 
            Label lModifie = new Label(" ");
            lModifie.setUIID("NewsTopLine");
            Style modifierStyl = new Style(lModifie.getUnselectedStyle());
            modifierStyl.setFgColor(0xf7ad02);

            FontImage mFontImae = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyl);
            lModifie.setIcon(mFontImae);
            lModifie.setTextPosition(LEFT);

            lModifie.addPointerPressedListener(l -> {
                //System.out.println("hello update");
              //  new SearchForm().show();

            });

            oneCont.add(BorderLayout.CENTER, BoxLayout.encloseY(
                     BoxLayout.encloseX(img),
                    BoxLayout.encloseX(nomTxt),
                    BoxLayout.encloseX(quantiteTxt),
                    BoxLayout.encloseX(descp_pTxt),
                    BoxLayout.encloseX(prixTxt),
                   

                    BoxLayout.encloseX(lModifier, lSupprimer)));
                            System.out.println(rec.getImage());

            // initialize the application
        } catch (Exception e) {
       
        }
            

            createLineSeparator();

            //supprimer button


            ScaleImageLabel imag = new ScaleImageLabel(urlim);

            Container containerImg = new Container();

            imag.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            produitsContainer.add(oneCont);

        }

        searchTF.addDataChangedListener((type, index) -> {
            String filterText = searchTF.getText();
            produitsTemp.clear();
            for (Produit pro : produits) {
                if (pro.getNom().toLowerCase().contains(filterText.toLowerCase())) {
                    produitsTemp.add(pro);
                }
            }
            produitsContainer.removeAll();
            for (Produit rec : produitsTemp) {

                String urlImage = "back-logo.jpeg";//image statique pour le moment ba3d taw fi  videos jayin nwarikom image 

                Image placeHolder = Image.createImage(120, 90);
                EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
                URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

                int height = Display.getInstance().convertToPixels(11.5f);
                int width = Display.getInstance().convertToPixels(14f);

                Button image = new Button(urlim.fill(width, height));
                image.setUIID("Label");
                Container oneCont = BorderLayout.west(image);

                //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
                Label nomTxt = new Label("nom : " + rec.getNom(), "NewsTopLine2");
                Label quantiteTxt = new Label("quantite : " + rec.getQuantite(), "NewsTopLine2");
                Label prixTxt = new Label("prix : " + rec.getPrix(), "NewsTopLine2");
                Label descp_pTxt = new Label("descp_p : " + rec.getDescp_p(), "NewsTopLine2");

                createLineSeparator();

               
          try {
              
                Image pic = createImage(Statics.ProduitPicture + rec.getImage()).fill(300, 300);
                ImageViewer img = new ImageViewer(pic);
            img.setUIID("NewsTopLine");
            Label lSupprimer = new Label(" ");
            lSupprimer.setUIID("NewsTopLine");
            Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
            supprmierStyle.setFgColor(0xf21f1f);

            FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
            lSupprimer.setIcon(suprrimerImage);
            lSupprimer.setTextPosition(RIGHT);

            //click delete icon
            lSupprimer.addPointerPressedListener(l -> {
                Dialog dig = new Dialog("Suppression");
                if (dig.show("Suppression", "Vous voulez supprimer ce produit ?", "Annuler", "Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();
                }
                //delete product using service
                boolean success = ProduitService.getInstance().deleteProduit(rec.getId());
                if (success) {
                    Dialog.show("Suppression", "Produit supprimé avec succès", "OK", null);
                    try {
                        new ListProduitForm(res).show(); // handle the exception
                    } catch (IOException ex) {
                    }
                } else {
                    Dialog.show("Erreur", "Erreur lors de la suppression du produit", "OK", null);
                }
            });

            //Update icon 
            Label lModifier = new Label(" ");
            lModifier.setUIID("NewsTopLine");
            Style modifierStyle = new Style(lModifier.getUnselectedStyle());
            modifierStyle.setFgColor(0xf7ad02);

            FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
            lModifier.setIcon(mFontImage);
            lModifier.setTextPosition(LEFT);

            lModifier.addPointerPressedListener(l -> {
                    try {
                        //System.out.println("hello update");
                        new ModifierProduit(res, rec).show();
                    } catch (IOException ex) {
                    }
            });
            //Update icon 
            Label lModifie = new Label(" ");
            lModifie.setUIID("NewsTopLine");
            Style modifierStyl = new Style(lModifie.getUnselectedStyle());
            modifierStyl.setFgColor(0xf7ad02);

            FontImage mFontImae = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyl);
            lModifie.setIcon(mFontImae);
            lModifie.setTextPosition(LEFT);

            lModifie.addPointerPressedListener(l -> {
                //System.out.println("hello update");
          //      new SearchForm().show();

            });

            oneCont.add(BorderLayout.CENTER, BoxLayout.encloseY(
                     BoxLayout.encloseX(img),
                    BoxLayout.encloseX(nomTxt),
                    BoxLayout.encloseX(quantiteTxt),
                    BoxLayout.encloseX(descp_pTxt),
                    BoxLayout.encloseX(prixTxt),
                   

                    BoxLayout.encloseX(lModifier, lSupprimer)));
                            System.out.println(rec.getImage());

            // initialize the application
        } catch (Exception e) {
       
        }
                ScaleImageLabel imag = new ScaleImageLabel(urlim);

                Container containerImg = new Container();

                imag.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                produitsContainer.add(oneCont);
            }
            revalidate();
        });

        add(produitsContainer);

    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Container cnt, Image img, Produit rec, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        //cnt = BorderLayout.west(image);

        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label nomTxt = new Label("nom : " + rec.getNom(), "NewsTopLine2");
        Label quantiteTxt = new Label("quantite : " + rec.getQuantite(), "NewsTopLine2");
        Label prixTxt = new Label("prix : " + rec.getPrix(), "NewsTopLine2");
        Label descp_pTxt = new Label("descp_p : " + rec.getDescp_p(), "NewsTopLine2");
        Label imageTxt = new Label("image : " + rec.getImage(), "NewsTopLine2");

        createLineSeparator();

        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);

        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);

        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression", "Vous voulez supprimer ce produit ?", "Annuler", "Oui")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //delete product using service
            boolean success = ProduitService.getInstance().deleteProduit(rec.getId());
            if (success) {
                Dialog.show("Suppression", "Produit supprimé avec succès", "OK", null);
                try {
                    new ListProduitForm(res).show(); // handle the exception
                } catch (IOException ex) {
                }
            } else {
                Dialog.show("Erreur", "Erreur lors de la suppression du produit", "OK", null);
            }
        });

        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);

        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);

        lModifier.addPointerPressedListener(l -> {
            try {
                //System.out.println("hello update");
                new ModifierProduit(res, rec).show();
            } catch (IOException ex) {
            }
        });
        //Update icon 
        Label lModifie = new Label(" ");
        lModifie.setUIID("NewsTopLine");
        Style modifierStyl = new Style(lModifie.getUnselectedStyle());
        modifierStyl.setFgColor(0xf7ad02);

        FontImage mFontImae = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyl);
        lModifie.setIcon(mFontImae);
        lModifie.setTextPosition(LEFT);

        lModifie.addPointerPressedListener(l -> {
            //System.out.println("hello update");
     //       new SearchForm().show();

        });

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(nomTxt),
                BoxLayout.encloseX(quantiteTxt),
                BoxLayout.encloseX(descp_pTxt),
                BoxLayout.encloseX(prixTxt),
                BoxLayout.encloseX(imageTxt),
                BoxLayout.encloseX(lModifier, lSupprimer, lModifie)));

    }

}
