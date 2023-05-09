/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Message;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author USER
 */

public class ServiceReponse {
        public static ServiceReponse instance = null ;
    private ConnectionRequest req;
  public static ServiceReponse getInstance() {
        if(instance == null )
            instance = new ServiceReponse();
        return instance ;
    }
      public ServiceReponse() {
        req = new ConnectionRequest();
        
    }
}
