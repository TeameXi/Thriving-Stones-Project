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
import entity.Student;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author DEYU
 */
public class StudentDAO {
    //private volatile Boolean status = false;
    
    public static void insertStudent(String studentID, String studentName, int age, String gender, String lvl, String address, String phone, double reqAmt, double outstandingAmt){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(studentID); 
        Student stu = new Student(studentName, age, gender, lvl, address, phone, reqAmt, outstandingAmt); 
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        ref.setValue(stu, new DatabaseReference.CompletionListener() {
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
    
    public static ArrayList<Student> retrieveStudentbyID(String studentID){
        final ArrayList<Student> student = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(studentID);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student stu = dataSnapshot.getValue(Student.class);
                System.out.println(dataSnapshot);
                student.add(stu);
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("The read failed: " + de.getCode());
            }            
        });
        try {
            //wait for firebase to retrieve record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return student;
    }
    
    public static ArrayList<String> deleteStudentbyID(String studentID){
        final String ID = studentID;
        final ArrayList<String> statuss = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students");
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot ds) {
                if(ds.hasChild(ID)){
                    ref.child(ID).removeValue(new DatabaseReference.CompletionListener(){
                        @Override
                        public void onComplete(DatabaseError de, DatabaseReference dr) {
                            countDownLatch.countDown();
                        }       
                    });
                    statuss.add("Deleted Student Successfully!");
                }else{
                    statuss.add("Student ID " + ID + " was not found in database.");
                    countDownLatch.countDown();
                }
            }
            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("The read failed: " + de.getCode());
            }            
        });
        try {
            //wait for firebase to delete record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return statuss;
    }
}
