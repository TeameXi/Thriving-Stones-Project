/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Users;
import entity.Tutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.FirebaseConnection.initFirebase;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Riana
 */
public class UsersDAO {
    private DataSnapshot dataRequired;   
    private volatile Boolean status= false;
    
    public static List<Users> getUser(final String username) {
        final List<Users> userList = new ArrayList<>();
        final CountDownLatch done = new CountDownLatch(1);
        initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users");;
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Users users = postSnapshot.getValue(Users.class);
                    if(users != null && users.getEmail().equals(username)){
                        userList.add(users);
                    }
                }
                System.out.println("user " + userList);
                done.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
        try { 
            done.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public void addUser(Tutor tutor) {
        FirebaseConnection.initFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        int index = tutor.getEmail().indexOf("@");
        String key = tutor.getEmail().substring(0, index);
        DatabaseReference ref = database.getReference().child("users").child(key);

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", tutor.getEmail());
        updates.put("password", tutor.getPassword());

        ref.setValue(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("success");
            }
        });
    }

    public void deleteUser(String tutorID) {
        status = false;
        FirebaseConnection.initFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference toDelete = database.getReference().child("users");
        TutorDAO tDAO = new TutorDAO();
        Tutor temp = tDAO.retrieveSpecificTutor(tutorID);
        String userName = temp.getEmail().substring(0, temp.getEmail().indexOf("@"));
        
        toDelete.child(userName).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("SUCCESSS");
                status = true;
            }
        });

        while (!status) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     public Users retrieveUserByEmail(final String email) {
        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/users.json");
            URLConnection userConnection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            userConnection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject  jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while(iter.hasNext()){
                String user = (String) iter.next();
                JsonElement userDataString = jobject.get(user);
                JsonObject userData = userDataString.getAsJsonObject();
                String userEmail = userData.get("email").getAsString();
                String pwd = userData.get("password").getAsString();
                Users userToReturn = new Users(userEmail, pwd);
                return userToReturn;
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
