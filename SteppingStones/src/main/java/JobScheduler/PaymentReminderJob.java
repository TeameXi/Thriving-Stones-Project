/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobScheduler;

import entity.Branch;
import entity.Class;
import entity.Lesson;
import entity.Parent;
import entity.Student;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import model.BranchDAO;
import model.ClassDAO;
import model.LessonDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.SendSMS;
import model.StudentDAO;

/**
 *
 * @author Shawn
 */
public class PaymentReminderJob implements Runnable {

    public String a;

    public PaymentReminderJob(String a) {
        this.a = a;
    }

    @Override
    public void run() {

        BranchDAO bDAO = new BranchDAO();
        ArrayList<Branch> branches = bDAO.retrieveAllBranches();

        for (Branch b : branches) {
            int branchID = b.getBranchId();
            ArrayList<Student> students = StudentDAO.listAllStudentsByBranch(branchID);

            for (Student s : students) {
                int studentID = s.getStudentID();
                ArrayList<Class> classes = ClassDAO.getStudentEnrolledClass(studentID);
                ArrayList<Lesson> notify = new ArrayList<>();
                HashMap<Integer, Lesson> checkAgainstFirst = new HashMap<>();
                int count = 1;

                for (Class c : classes) {
                    int classID = c.getClassID();

                    //get the next lesson that require reminding
                    ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonListsAfterCurrTS(classID);

                    for (Lesson l : lessons) {
                        //add every lesson to hashmap
                        checkAgainstFirst.put(count, l);
                        count++;
                    }
                }

                LocalDateTime firstIndex = checkAgainstFirst.get(1).getStartDateTS().toLocalDateTime();

                for (int i : checkAgainstFirst.keySet()) {
                    //get the earliest class amongst those that need to remind and not reminded yet
                    if (firstIndex.isAfter(checkAgainstFirst.get(i).getStartDateTS().toLocalDateTime())) {
                        firstIndex = checkAgainstFirst.get(i).getStartDateTS().toLocalDateTime();
                    }
                }

                for (int i : checkAgainstFirst.keySet()) {
                    //check whether the remaining classes are within 7 days of the earliest class
                    LocalDateTime iteratedLDT = checkAgainstFirst.get(i).getStartDateTS().toLocalDateTime();
                    Period p1 = Period.between(firstIndex.toLocalDate(), iteratedLDT.toLocalDate());

                    int yearDiff = p1.getYears();
                    int monthDiff = p1.getMonths();
                    int dayDiff = p1.getDays();
                    //if within a week, add to notify arraylist
                    if (yearDiff == 0 & monthDiff == 0 & dayDiff <= 7) {
                        notify.add(checkAgainstFirst.get(i));
                    }
                }

                for (Lesson le : notify) {
                    LocalDateTime currTime = LocalDateTime.now();
                    Timestamp lessonTimestamp = le.getStartDateTS();
                    LocalDateTime lessonLDT = lessonTimestamp.toLocalDateTime().minusDays(7);
                    Period p = Period.between(currTime.toLocalDate(), lessonLDT.toLocalDate());

                    int yearDiff = p.getYears();
                    int monthDiff = p.getMonths();
                    int dayDiff = p.getDays();

                    if (yearDiff == 0 & monthDiff == 0 & dayDiff == 0) {
                        //get number of parents
                        int parentID = ParentChildRelDAO.getParentID(studentID);
                        ParentDAO pDAO = new ParentDAO();
                        Parent currParent = pDAO.retrieveSpecificParentById(parentID);
                        int currPhoneNo = currParent.getPhone();
                        SendSMS.sendingSMS("+65" + currPhoneNo, "Dear Parent,\n"
                                + "A gentle reminder that your child’s tuition fee is due on " + currTime.toLocalDate() + ". Thank you for the prompt payment.\n"
                                + "\n"
                                + "From Stepping Stones Learning Centre LLP");
                        for (Lesson les : notify) {
                            //set reminded to 1
                            LessonDAO.setReminded(le.getClassid(), le.getLessonid());
                        }
                    }
                }
            }
        }
    }
}
