/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author DEYU
 */
public class FirebaseConnection {
    public static void initFirebase() {
        try {
            
 

            FileInputStream serviceAccount = new FileInputStream("/Users/Riana/Desktop/FYP/Thriving-Stones-Project/SteppingStones/src/team-exi-thriving-stones-firebase-adminsdk-luopb-284f7a1c5a.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://team-exi-thriving-stones.firebaseio.com")
                    .build();
            
            FirebaseApp firebaseApp = null;
            List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
            if(firebaseApps!=null && !firebaseApps.isEmpty()){
                for(FirebaseApp app : firebaseApps){
                    if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    firebaseApp = app;
                }
                System.out.println("Use existing firebase app");
            }
            else{
                firebaseApp = FirebaseApp.initializeApp(options);
                System.out.println("Being initizalized");
            }
            System.out.println(firebaseApp);
                      
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
