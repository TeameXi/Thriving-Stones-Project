/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import entity.Tutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FirebaseConnection;

public class TutorDAO {

    private DataSnapshot dataRequired;
    private volatile Boolean status = false;

    public void addTutor(String tutorID, String name, Long age, Long phoneNo, String gender, String emailAdd, String password) {
        FirebaseConnection.initFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");

        Tutor tutor = new Tutor(tutorID, name, age, phoneNo, gender, emailAdd, password);
        DatabaseReference objRef = ref.push();
        objRef.setValueAsync(tutor);
        
        CreateRequest request = new CreateRequest().setEmail(emailAdd).setPassword(password);
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tutor retrieveSpecificTutor(final String id) {
        FirebaseConnection.initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iter = dataSnapshot.getChildren().iterator();
                while (iter.hasNext()) {
                    DataSnapshot data = (DataSnapshot) iter.next();
                    if (data.getKey().equals(id)) {
                        dataRequired = data;
                        status = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        while (!status) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String name = (String) dataRequired.child("name").getValue();
        Long age = (Long) dataRequired.child("age").getValue();
        Long phoneNo = (Long) dataRequired.child("phoneNo").getValue();
        String gender = (String) dataRequired.child("gender").getValue();
        String emailAdd = (String) dataRequired.child("emailAdd").getValue();
        String password = (String) dataRequired.child("password").getValue();
        Tutor tutor = new Tutor(id, name, age, phoneNo, gender, emailAdd, password);
        return tutor;
    }
    
    public static Tutor retrieveTutorByEmail(final String email) {
        final List<Tutor> tutorList = new ArrayList<>();
        final CountDownLatch done = new CountDownLatch(1);
        FirebaseConnection.initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");
System.out.println("-----------retrieve tutor ----------");
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tutorList.clear();
                System.out.println(dataSnapshot.toString());
                Iterator iter = dataSnapshot.getChildren().iterator();
                while (iter.hasNext()) {
                    DataSnapshot data = (DataSnapshot) iter.next();
                    Tutor tutor = data.getValue(Tutor.class);
                    if(tutor != null && tutor.getEmailAdd().equals(email)){
                        tutorList.add(tutor);
                        System.out.println(tutorList.toString());
                    }
                }
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
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return tutorList.get(0);
    }
    
    public ArrayList<Tutor> retrieveAllTutors() {
        FirebaseConnection.initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRequired = dataSnapshot;
                status = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        while (!status) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<Tutor> tutors = new ArrayList<>();
        
        Iterator iter = dataRequired.getChildren().iterator();
        while(iter.hasNext()){
            DataSnapshot data = (DataSnapshot) iter.next();
            String name = (String) data.child("name").getValue();
            Long age = (Long) data.child("age").getValue();
            Long phone = (Long) data.child("phone").getValue();
            String gender = (String) data.child("gender").getValue();
            String email = (String) data.child("email").getValue();
            String password = (String) data.child("password").getValue();
            Tutor tutor = new Tutor((String)data.getKey(), name, age, phone, gender, email, password);
            tutors.add(tutor);
        }
        return tutors;
    }
}
