/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;
import model.FirebaseConnection;

public class TutorDAO {

    public void addTutor(String tutorID, String name, int age, String phoneNo, String gender, String emailAdd, String password) {
        FirebaseConnection.initFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("tutors");

        Tutor tutor = new Tutor(tutorID, name, age, phoneNo, gender, emailAdd, password);
        DatabaseReference objRef = ref.push();
        objRef.setValueAsync(tutor);
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
                System.out.println(dataSnapshot.toString());
                Iterator iter = dataSnapshot.getChildren().iterator();
                while (iter.hasNext()) {
                    DataSnapshot data = (DataSnapshot) iter.next();
                    if (data.getKey().equals(id)) {
                        String name = (String) data.child("name").getValue();
                        int age = (Integer) data.child("age").getValue();
                        String phoneNo = (String) data.child("phoneNo").getValue();
                        String gender = (String) data.child("gender").getValue();
                        String emailAdd = (String) data.child("emailAdd").getValue();
                        String password = (String) data.child("password").getValue();
                        Tutor tutor = new Tutor(id, name, age, phoneNo, gender, emailAdd, password);
                        CallbackData call = new CallbackData();
                        call.callback(tutor);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        if (CallbackData.obj != null) {
            Tutor tutor = (Tutor) CallbackData.obj;
            return tutor;
        }
        return null;
    }
}
