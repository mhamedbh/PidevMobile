/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.BaseForm;
import com.mycompany.myapp.gui.ListProduitForm;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ProduitService;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProduitForm extends BaseForm {

    Form current;
    String clubPicName;
    public AddProduitForm(Resources res) throws IOException {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout produit");
        getContentPane().setScrollVisible(false);
         super.addSideMenu(res);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("prod.jpg"), "", "", res);

        //
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
        RadioButton mesListes = RadioButton.createToggle("Mes produit", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("produit", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            ListProduitForm a = new ListProduitForm(res);
            a.show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //
        TextField nom = new TextField("", "Entrer nom");
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);

        TextField descp_p = new TextField("", "Entrer desc");
        descp_p.setUIID("TextFieldBlack");
        addStringValue("Description", descp_p);

        TextField prix = new TextField("", "Entrer prix");
        prix.setUIID("TextFieldBlack");
        prix.setConstraint(TextField.DECIMAL);
        prix.addDataChangeListener((i, ii) -> {
            String text = prix.getText();
            try {
                Double.parseDouble(text);
            } catch (NumberFormatException e) {
                Dialog.show("Erreur", "Veuillez entrer un nombre décimal valide pour le prix.", "OK", null);
                prix.setText("");
            }
        });
        addStringValue("Prix", prix);

        TextField quantite = new TextField("", "Entrer quantite");
        quantite.setUIID("TextFieldBlack");
        quantite.setConstraint(TextField.NUMERIC);
        quantite.addDataChangedListener((i1, i2) -> {
            String text = quantite.getText();
            for (int j = 0; j < text.length(); j++) {
                if (!Character.isDigit(text.charAt(j))) {
                    Dialog.show("Erreur", "Veuillez entrer une valeur numérique entière", "OK", null);
                    quantite.clear();
                    return;
                }
            }
        });
        addStringValue("Quantite", quantite);

       
         // add club picture
        FloatingActionButton addPicClub = FloatingActionButton.createFAB(FontImage.MATERIAL_CAMERA_ALT);
        addPicClub.setIconDefaultSize(3.0f);
        addPicClub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                 clubPicName = Util.getUUID() + ".jpg";
                
                MultipartRequest cr = new MultipartRequest() {

                    @Override
                    protected void handleErrorResponseCode(int code, String message) {
                        if (code == 500) {
                            //Do what you want here
                        }
                    }
                };
                String url = Statics.BASE_URL + "produit/uploadUserPic" ;

                cr.setUrl(url);
                cr.setPost(true);
                String mime = "image/jpeg";
                System.out.println(cr.getUrl());

                try {
                    cr.addData("file", filePath, mime);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
               cr.setFilename("file", clubPicName);//any unique name you want
                System.out.println(filePath);

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                //  
                /* try {
                    Image picClub = createImage(filePath).fill(300, 300);
                    Image roundMaskClub = Image.createImage(picClub.getWidth(), picClub.getHeight(), 0xff000000);
                    Graphics graphics = roundMaskClub.getGraphics();
                    graphics.setColor(0xffffff);
                    graphics.fillArc(0, 0, picClub.getWidth(), picClub.getWidth(), 0, 360);
                    Object maskClub = roundMaskClub.createMask();
                    picClub = picClub.applyMask(maskClub);
                    img.setImage(picClub);*/
 
                //} catch (IOException ex) {}
            }
        });
        
        addPicClub.setUIID("TextFieldBlack");
        addStringValue("", addPicClub);
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);

        //onclick button event 
        btnAjouter.addActionListener((ActionListener) (ActionEvent e) -> {
            try {

                if (nom.getText().equals("") || descp_p.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    //njibo iduser men session (current user)
                    Produit r = new Produit(
                            Integer.parseInt(quantite.getText()),
                            nom.getText(),
                            clubPicName,
                            descp_p.getText(),
                            Float.parseFloat(prix.getText())
                    );
                    // créer une nouvelle instance de Produit

                    System.out.println("data Produit == " + r);
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base
                    ProduitService.getInstance().ajoutProduit(r);

                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout

                    //ba3d ajout net3adaw lel list
                    new ListProduitForm(res).show();

                    refreshTheme();//Actualisation

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
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

        swipe.addTab("", res.getImage("back-logo.jpg"), page1);

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

}
