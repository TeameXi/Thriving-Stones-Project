/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import entity.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.FirebaseConnection.initFirebase;

/**
 *
 * @author Riana
 */
public class UsersDAO {
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
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Users users = postSnapshot.getValue(Users.class);
                    if(users != null && users.getUsername().equals(username)){
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

}
