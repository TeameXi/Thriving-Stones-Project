/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import entity.StudentGrade;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author DEYU
 */
public class StudentGradeDAO {
    
    public static void saveSchoolGrade(String studentID, String sub1, StudentGrade stuGrade1, String sub2, StudentGrade stuGrade2, String sub3, StudentGrade stuGrade3){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(studentID).child("grades").child("School");
        Map<String, StudentGrade> grade = new HashMap<>();
        grade.put(sub1, stuGrade1);
        grade.put(sub2, stuGrade2);
        grade.put(sub3, stuGrade3);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        ref.setValue(grade, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("School grade records saved!");
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
    
    public static void saveGrades(String studentID, Map<String, Map<String, StudentGrade>> grades){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students").child(studentID).child("grades");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        ref.setValue(grades, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("Grade records saved!");
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