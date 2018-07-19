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
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorDAO {

    private DataSnapshot dataRequired;
    private volatile Boolean status = false;

    public void addTutor(String tutorID, Tutor tutor) {
        FirebaseConnection.initFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors").child(tutorID);

        ref.setValue(tutor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("success");
            }
        });

        CreateRequest request = new CreateRequest().setEmail(tutor.getEmail()).setPassword(tutor.getPassword());
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
        Tutor tutor = dataRequired.getValue(Tutor.class);
        tutor.setID(id);
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
                    if (tutor != null && tutor.getEmail().equals(email)) {
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
        while (iter.hasNext()) {
            DataSnapshot data = (DataSnapshot) iter.next();
            Tutor tutor = data.getValue(Tutor.class);
            tutor.setID(data.getKey());
            tutors.add(tutor);
        }
        return tutors;
    }

    public void removeTutor(String tutorID) {
        status = false;
        FirebaseConnection.initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");

        Tutor tutor = retrieveSpecificTutor(tutorID);
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(tutor.getEmail());
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
        } catch (FirebaseAuthException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        ref.child(tutorID).removeValue(new DatabaseReference.CompletionListener() {
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

    public void updateTutor(String tutorID, Map<String, Object> updates) {
        FirebaseConnection.initFirebase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors").child(tutorID);

        Iterator iter = updates.keySet().iterator();

        while (iter.hasNext()) {
            String toUpdate = (String) iter.next();
            System.out.println(toUpdate);
            String valueToUpdate = (String) updates.get(toUpdate);
            ref.child(toUpdate).setValue(valueToUpdate, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    System.out.println("success");
                }
            });
        }
    }
}
