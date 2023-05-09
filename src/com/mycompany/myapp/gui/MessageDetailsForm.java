    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.mycompany.myapp.gui;

    /**
     *
     * @author USER
     */

import com.codename1.components.ImageViewer;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.services.ServiceMessage;
import java.io.IOException;

public class MessageDetailsForm extends Form {

    private final Message message;
    private final ServiceMessage messageService;

    public MessageDetailsForm(Resources res , Message message) {
        this.message = message;
        this.messageService = new ServiceMessage();

        // Set the form's title to the message subject
        setTitle(message.getContent());

        // Use a BoxLayout with the Y_AXIS to arrange the components vertically
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Add a label displaying the message content
        Label contentLabel = new Label(message.getContent());
        add(contentLabel);
String img = message.getImage() ;
        // Add an image viewer displaying the message image, if there is one
        if (message.getImage() != null) {
            String imageUrl = "file:///src/img/" + message.getImage();
            System.out.print("---------------------------------------------");
            System.out.print(img);
            ImageViewer imageViewer;
            try {
                imageViewer = new ImageViewer(messageService.getImageFromUrl(imageUrl));
                add(imageViewer);
            } catch (IOException ex) {
                System.out.println("Error loading image from URL: " + ex.getMessage());
            }
        }
    }
}
