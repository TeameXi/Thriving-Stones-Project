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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author DEYU
 */
public class StudentClassDAO {
    public static Map<String, String> getStudentsInSpecificClass(String classKey){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("StudentClass").child(classKey);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<String, String> list = new HashMap<>();
        
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> students = dataSnapshot.getChildren();
                for(DataSnapshot student: students){
                    list.put(student.getKey(), (String) student.getValue());                   
                }                
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("The read failed: " + de.getCode());
            }            
        });        
        try {
            //wait for firebase to save record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public static void saveClassWithStudents(String classKey, Map<String, String> students){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("StudentClass").child(classKey);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        ref.setValue(students, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("Record saved!");
                countDownLatch.countDown();
            }
        });
        try {
            //wait for firebase to save record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
