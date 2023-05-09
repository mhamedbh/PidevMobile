/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.UserService;
import com.mycompany.myapp.utils.Statics;
import java.util.Vector;

/**
 *
 * @author Mhamed
 */
public class SignUpForm extends BaseForm {
    String  clubPicName;
    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        TextField image = new TextField("", "Pic", 20, TextField.ANY);
        /*select pic*/
        Label filename = new Label("");
       
        Style filenamestyles = filename.getUnselectedStyle();
        filenamestyles.setMarginTop(1);
        
         Font materialFont = FontImage.getMaterialDesignFont();
        materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
         Button btn_attach = new Button("Image");
        btn_attach.setUIID("addImgBtn");
        
        /*add imagefile*/
/*Button upload = new Button("jdid");
String[] imgPath = {""};
upload.addActionListener(e -> {
    Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
        if (ev != null && ev.getSource() != null) {
            imgPath[0] = (String) ev.getSource();
        }
    }, Display.GALLERY_IMAGE);
});
*/        // add club picture
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
                String url = Statics.BASE_URL + "user/uploadUserPic/" ;

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
        // actual pu

           //Role 
        //Vector 3ibara ala array 7atit fiha roles ta3na ba3d nzidouhom lel comboBox
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
         vectorRole.add("");
        vectorRole.add("NGO");
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        
       String ima;
        ima = filename.getText();
        
        
        username.setSingleLineTextArea(false);
        adresse.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        image.setSingleLineTextArea(false);
        Button next = new Button("SignUp");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> new SignInForm(res).show());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content;
        content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(adresse),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator(),
                roles,//sinon y7otich role fi form ta3 signup
                filename,
                createLineSeparator(),
                addPicClub
                

                
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        /*next.addActionListener((ActionEvent e) -> {
            
            UserService.getInstance().signup(username, password, email, confirmPassword,adresse,filename.getText(), roles, res);
            Dialog.show("Success","account is saved","OK",null);
            new SignInForm(res).show();
        });
    }*/
          next.addActionListener((ActionEvent e) -> {
            
            if (username.getText().length() == 0 || adresse.getText().length() == 0 || email.getText().length() == 0|| password.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            }

              else if (containsDigit(username.getText().toString())){
        Dialog.show("Erreur", "Le champ nom ne doit pas contenir de chiffres.", "OK", null);
       
    }  else if (containsDigit(adresse.getText().toString())){
        Dialog.show("Erreur", "Le champ adresse ne doit pas contenir de chiffres.", "OK", null);
    } else if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
    // show an error dialog
    Dialog.show("Error", "Please enter a valid email address.", "OK", null);

       
    }    else if (password.getText().length() < 6 || confirmPassword.getText().length() < 6 ){
        Dialog.show("Erreur", "Password doit contenir aux moins 6 carateres.", "OK", null);
       
    } else if (!password.getText().toString().equals(confirmPassword.getText().toString()) ){
        Dialog.show("Erreur", "Password confirmation incorrect", "OK", null);
       
    } 
 
            else {
                try {
                    
                      UserService.getInstance().signup(username, password, email, confirmPassword,adresse,clubPicName, roles, res);
            Dialog.show("Success","account is saved","OK",null);
            new SignInForm(res).show();
                    
                } catch (NumberFormatException ev) {
                    Dialog.show("Error", "Invalid input", new Command("OK"));
                }
            }
        });
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
