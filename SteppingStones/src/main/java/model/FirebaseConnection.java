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
<<<<<<< HEAD
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\DEYU\\Documents\\YR3 Term1\\IS Application Project\\Thriving-Stones-Project\\SteppingStones\\src\\team-exi-thriving-stones-firebase-adminsdk-luopb-284f7a1c5a.json");
=======
            FileInputStream serviceAccount = new FileInputStream("/Users/huixintang/Desktop/team-exi-thriving-stones-firebase-adminsdk-luopb-284f7a1c5a.json");
>>>>>>> e0addacc48eb355de4c9008d62e05fbe3ac5e6f9
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
