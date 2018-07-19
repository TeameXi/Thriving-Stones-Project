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
import entity.Users;
import entity.Tutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.FirebaseConnection.initFirebase;

/**
 *
 * @author Riana
 */
public class UsersDAO {
    
    private volatile Boolean status;
    
    public static List<Users> getUser(final String username) {
        final List<Users> userList = new ArrayList<>();
        final CountDownLatch done = new CountDownLatch(1);
        initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users");
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Users users = postSnapshot.getValue(Users.class);
                    if (users != null && users.getUsername().equals(username)) {
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
}
