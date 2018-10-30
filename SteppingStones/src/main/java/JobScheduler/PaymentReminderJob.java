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
import model.BranchDAO;
import model.ClassDAO;
import model.LessonDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.SendSMS;
import model.StudentClassDAO;

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
            ArrayList<Class> classes = ClassDAO.listAllClasses(branchID);

            for (Class c : classes) {
                int classID = c.getClassID();

                //check if lesson needs reminder
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonListsAfterCurrTS(classID);

                for (Lesson l : lessons) {
                    //get check if is the day to send sms
                    LocalDateTime currTime = LocalDateTime.now();
                    Timestamp lessonTimestamp = l.getStartDateTS();
                    LocalDateTime lessonLDT = lessonTimestamp.toLocalDateTime().minusDays(7);
                    Period p = Period.between(currTime.toLocalDate(), lessonLDT.toLocalDate());
                    
                    int yearDiff = p.getYears();
                    int monthDiff = p.getMonths();
                    int dayDiff = p.getDays();

                    //send sms
                    if (yearDiff == 0 & monthDiff == 0 & dayDiff == 0) {
                        //get number of parents
                        ArrayList<Student> studs = StudentClassDAO.listAllStudentsByClass(classID);
                        for (Student s : studs) {
                            int parentID = ParentChildRelDAO.getParentID(s.getStudentID());
                            ParentDAO pDAO = new ParentDAO();
                            Parent currParent = pDAO.retrieveSpecificParentById(parentID);
                            int currPhoneNo = currParent.getPhone();
                            SendSMS.sendingSMS("+65" + currPhoneNo, "Please be reminded to submit your child/children's tuition fee to Stepping Stones Learning Centre.");
                        }
                    }
                }
            }
        }
    }
}
