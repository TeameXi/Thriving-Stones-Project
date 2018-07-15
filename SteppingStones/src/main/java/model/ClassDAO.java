/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Class;
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
public class ClassDAO {
    public static void saveClasses(String level, String subject, String classTime, String classDay, double mthlyFees, String startDate){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("classes");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Class cls = new Class(level, subject, classTime, classDay, mthlyFees, startDate);
        ref.push().setValue(cls, new DatabaseReference.CompletionListener() {
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
    
    public static Map<String, Class> getClassByLevel(String level){
        final String lvl = level;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("classes");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        //final ArrayList<Class> classes = new ArrayList<>();
        final Map<String, Class> classes = new HashMap<>();
        
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> classs = dataSnapshot.getChildren();
                for(DataSnapshot clas: classs){
                    Class cls = clas.getValue(Class.class);
                    if(cls != null){
                        if(cls.getLevel().equals(lvl)){
                            classes.put(clas.getKey(), cls);
                        }
                    }
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
        return classes;
    }
}
