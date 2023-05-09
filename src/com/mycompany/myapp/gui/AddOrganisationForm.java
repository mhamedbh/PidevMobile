/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import com.mycompany.myapp.gui.ListOrganisationForm;
import com.mycompany.myapp.entities.Organisation;
import com.mycompany.myapp.services.OrganisationService;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mahmoud
 */

public class AddOrganisationForm extends BaseForm  {
    
 Form current;
 String clubPicName;
    public AddOrganisationForm(Resources res) throws IOException {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Formulaire d'Ajout");
        getContentPane().setScrollVisible(false);
         super.addSideMenu(res);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("org.jpg"), "", "", res);

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
        RadioButton mesListes = RadioButton.createToggle("Mes organisations", barGroup);
        mesListes.setUIID("SelectBar");
       
       
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            ListOrganisationForm a;
            try {
                a = new ListOrganisationForm(res);
                            a.show();

            } catch (IOException ex) {
            }
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, mesListes),
                FlowLayout.encloseBottom(arrow)
        ));

        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
        });
        bindButtonSelection(mesListes, arrow);
     
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //
        TextField nom = new TextField("", "entrer nom!!");
        nom.setUIID("TextFieldBlack");
        addStringValue("nom", nom);
        
        TextField adresse = new TextField("", "entrer adresse!!");
        adresse.setUIID("TextFieldBlack");
        addStringValue("adresse", adresse);


        TextField description = new TextField("", "entrer description!!");
        description.setUIID("TextFieldBlack");
        addStringValue("description", description);

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
                String url = Statics.BASE_URL + "organisation/uploadUserPic" ;

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
                
                if (nom.getText().equals("") || description.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                }   else if (containsDigit(nom.getText().toString())){
        Dialog.show("Erreur", "Nom ne doit pas contenir de chiffres.", "OK", null);
       
                }else if (adresse.getText().toString().length() <4){
        Dialog.show("Erreur", "Adresse doit contenir aux moins 4 carateres.", "OK", null);
       
                }
                else if (description.getText().toString().length() <4){
        Dialog.show("Erreur", "Description doit contenir aux moins 4 carateres.", "OK", null);
       
                }else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data
                    
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    //njibo iduser men session (current user)
                    
                    
                    
                    Organisation r = new Organisation(
                            nom.getText(),
                            adresse.getText(),
                            description.getText(),
                            clubPicName
                            
                    );
                    // créer une nouvelle instance de Produit
                    
                    System.out.println("data Organisation == " + r);
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base
                    OrganisationService.getInstance().ajoutOrganisation(r);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel list
                    new ListOrganisationForm(res).show();
                    
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
    
 public static boolean containsDigit(String s){
        //tow new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            return true;
        }
    }
    return false;
    }

}

