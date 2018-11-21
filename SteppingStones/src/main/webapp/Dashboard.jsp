<%-- 
    Document   : Dashboard
    Created on : 16 Nov, 2018, 5:45:34 PM
    Author     : Zang Yu
--%>
<%@page import="model.SubjectDAO"%>
<%@page import="model.RewardDAO"%>
<%@page import="entity.Lesson"%>
<%@page import="model.LessonDAO"%>
<%@page import="entity.Student"%>
<%@page import="model.ParentChildRelDAO"%>
<%@page import="entity.Reward"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="entity.Expense"%>
<%@page import="model.ExpenseDAO"%>
<%@page import="entity.TutorPay"%>
<%@page import="entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Class"%>
<%@page import="model.BranchDAO"%>
<%@page import="model.LevelDAO"%>
<%@page import="model.StudentDAO"%>
<%@page import="model.TutorDAO"%>
<%@page import="model.ClassDAO"%>
<%@page import="model.StudentClassDAO"%>
<%
    Users user1 = (Users)session.getAttribute("user");

    if(user1 == null){
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<%@include file="header.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <title>gridster test</title>
        <meta name="author" content="gyurisc">		
        <link href="styling/css/jquery.gridster.css" rel="stylesheet" type="text/css"/>
        <link href="styling/css/styles.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/theme.css">
        <link href="styling/css/jquery.scrollable.css" rel="stylesheet" type="text/css"/>
        <style>
            .box { height: 200px; width:260px; overflow: auto; font-size: 18px;}
            .box2 { height: 225px; width:260px; overflow: auto;}
            .box3 { height: 85px; width:400px; overflow: auto;}
            .box4 { height: 225px; width:400px; overflow: auto;}
        </style>
    </head>
    <body>
        <div class="col-md-10">
            <div style="margin: 10px;"><h3>Dashboard</h3></div>
                <div class="row"> 
                    <div class="demo">
                            <div class="gridster">
                                    <ul>
                    <%                
                        BranchDAO branchDAO = new BranchDAO();
                        TutorDAO tutorDAO = new TutorDAO();
                        StudentDAO studentDAO = new StudentDAO();
                        LevelDAO levelDAO = new LevelDAO();
                        ClassDAO classDAO = new ClassDAO();
                        StudentClassDAO studentClassDAO = new StudentClassDAO();
                        
                        if (role != null && role == "admin" && user.getBranchId() != 0) {                            
                            int numberOfTutorByBranch = tutorDAO.retrieveNumberOfTutorByBranch(user.getBranchId());                                         
                            int numberOfStudentByBranch = studentDAO.retrieveNumberOfStudentByBranch(user.getBranchId());                                     
                            Map<String, Integer> studentPerLevelByBranch = levelDAO.retrieveStudentPerLevelByBranch(user.getBranchId());
                            ArrayList<Class> classes = classDAO.listAllClasses(user.getBranchId());
                    %>                   
                                        
                                        <li data-row="1" data-col="1" data-sizex="1" data-sizey="1" class="item1">                                            
                                                <div class="overview-box clearfix">
                                                    <div class="icon">
                                                        <i class="zmdi zmdi-accounts-outline"></i>
                                                    </div>
                                                    <div class="text">
                                                        <h2><%=numberOfTutorByBranch%></h2>
                                                        <span>number of tutor</span>
                                                    </div>
                                                </div>
                                        </li>
                                        <li data-row="1" data-col="2" data-sizex="1" data-sizey="1" class="item2">                                            
                                                <div class="overview-box clearfix">
                                                    <div class="icon">
                                                        <i class="zmdi zmdi-accounts"></i>
                                                    </div>
                                                    <div class="text">
                                                        <h2><%=numberOfStudentByBranch%></h2>
                                                        <span>number of student</span>
                                                    </div>
                                                </div>
                                        </li>
                                        <li data-row="1" data-col="3" data-sizex="1" data-sizey="1" class="item3">                                            
                                                <div class="overview-box clearfix">
                                                    <div class="icon">
                                                        <i class="zmdi zmdi-book"></i>
                                                    </div>
                                                    <div class="text">
                                                        <h2><%=classes.size()%></h2>
                                                        <span>number of class</span>
                                                    </div>
                                                </div>
                                        </li>
                                        <li data-row="2" data-col="5" data-sizex="2" data-sizey="2" class="item4">
                                            <h3 class="title-3 m-b-30">number of student per level </h3>
                                            <div class="box">
                                                <%
                                                Set<String> levels = studentPerLevelByBranch.keySet();                                                    
                                                if (levels.size() != 0) {
                                                %>
                                                <table class="table table-top-campaign">
                                                    <tbody>
                                                        <%
                                                        for (int j=0; j<levels.size(); j++){
                                                        %>
                                                        <tr>
                                                            <td><%=levels.toArray()[j]%></td>
                                                            <td><%=studentPerLevelByBranch.get(levels.toArray()[j])%></td>
                                                        </tr>
                                                        <% } %>
                                                    </tbody>
                                                </table>
                                                <%}%>
                                            </div>
                                        </li>
                                        
                                        <li data-row="2" data-col="1" data-sizex="2" data-sizey="2" class="item5">
                                            <h3 class="title-3 m-b-30">tutor payment details</h3>
                                            <div class="box2">
                                                <table class="table table-data2">
                                                    <thead>
                                                        <tr>                                                                
                                                            <th>name</th>
                                                            <th>status</th>
                                                            <th>salary</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>                                            
                                                <%
                                                    ArrayList<Tutor> tutors = TutorDAO.tutorWithTotalClasses(branch_id);

                                                    for(Tutor t : tutors){
                                                        double totalOwed = 0.0;
                                                        ArrayList<entity.Class> classList = ClassDAO.listAllClassesBelongToTutors(t.getTutorId(), user.getBranchId());   
                                                        for (entity.Class c : classList) {
                                                            double classDuration = ClassDAO.getClassTime(c.getClassID());
                                                            int totalAttendLessons = TutorDAO.calculateTutorAttendLessonCount(t.getTutorId(), c.getClassID());                        
                                                            totalOwed += totalAttendLessons*classDuration* c.getTutorRate();
                                                        }
                                                        ArrayList<TutorPay> replacementClasses = ClassDAO.totalReplacementClasses(t.getTutorId(), user.getBranchId());
                                                        for(TutorPay replacementClass:replacementClasses){
                                                            totalOwed += replacementClass.getMonthlySalary();
                                                        }
                                                        out.println("<tr><td>"+t.getName()+"</td><td class='denied'>Pending</td><td class='text-center'> $ "+totalOwed+"</td></tr>");
                                                    }
                                                %>                                                                                                     
                                                </tbody>
                                            </table>
                                        </div>
                                        </li>                                        
                                        <li data-row="1" data-col="4" data-sizex="3" data-sizey="1" class="item7">
                                            <h3 class="title-3 m-b-30">Replacement lesson dates</h3>
                                            <div class="box3">
                                                <table class="table table-data2">
                                                    <thead>
                                                        <tr>                                                                
                                                            <th>replaced date</th>
                                                            <th>class</th>
                                                            <th>name</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>                                            
                                                <%                                                    
                                                    for (Class c : classes) {
                                                        ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(c.getClassID());

//                                                        for (Lesson l : lessons) {
//                                                            ArrayList<String> replacement = LessonDAO.retrieveReplacementDates(l.getLessonid());
//                                                            if (replacement != null) { 
//                                                                int tutorID = c.getTutorID();
//                                                                Tutor t = TutorDAO.retrieveSpecificTutorById(tutorID);
//                                                                out.println("<tr><td>"+l.getStartDate().substring(0, 10)+"</td><td>"+c.getClassDay()+" "+c.getStartTime()+"-"+c.getEndTime()+" "+c.getLevel()+" "+c.getSubject()+"</td><td>"+t.getName()+"</td></tr>");
//                                                            }
//                                                        }
                                                        for(Lesson l: lessons){
                                                            ArrayList<String> replacement = LessonDAO.retrieveReplacementDetails(l.getLessonid());
                                                            if(replacement != null){
                                                                int tutorID = 0;
                                                                if(replacement.get(3) != null){
                                                                    tutorID = Integer.parseInt(replacement.get(3));
                                                                }
                                                                Tutor t = TutorDAO.retrieveSpecificTutorById(tutorID);
                                                                out.println("<tr><td>"+replacement.get(0)+"</td><td>"+c.getLevel()+" "+c.getSubject()+"<br>"+replacement.get(1)+"-"+replacement.get(2)+"</td><td>"+t.getName()+"</td></tr>");
                                                            }
                                                        }
                                                    }
                                                %>                                                                                                     
                                                </tbody>
                                            </table>
                                        </div>
                                        </li>
                                        <li data-row="2" data-col="3" data-sizex="2" data-sizey="2" class="item6">
                                            <h3 class="title-3 m-b-30">Current week expenses</h3>
                                            <div class="box2">
                                                <table class="table table-data2">
                                                    <thead>
                                                        <tr>                                                                
                                                            <th>description</th>
                                                            <th>date</th>                                                            
                                                            <th>amount</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%
                                                        ArrayList<Expense> expenses = ExpenseDAO.listAllExpenses();
                                                        Calendar cal = Calendar.getInstance();
                                                        cal.set(Calendar.DAY_OF_WEEK,cal.getActualMinimum(Calendar.DAY_OF_WEEK));
                                                        Date firstDayOfTheWeek = cal.getTime();
                                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                                        
                                                        for (Expense e : expenses) {                                                        
                                                            Date parsed = df.parse(e.getDate());
                                                            if(parsed.after(firstDayOfTheWeek) || (parsed.equals(firstDayOfTheWeek))){
                                                                out.println("<tr><td class='desc'>"+e.getDescription()+"</td><td>"+df.format(parsed)+"</td><td>$"+e.getAmount()+"</td></tr>");
                                                            }                                                            
                                                        }
                                                    %>                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                        </li>

                                        
                    <% } else if (role != null && role == "student"){
                        ArrayList<Class> classes = ClassDAO.getStudentEnrolledClass(user.getRespectiveID(), user.getBranchId());
                            if (classes.isEmpty()){
                                    %> 
                                    <li data-row="1" data-col="3" data-sizex="2" data-sizey="2" class="item5">
                                        <h3 class="title">Upcoming Classes</h3>
                                        <div class="box2">
                                                <table class="table table-data2">
                                                <thead>
                                                    <tr>                                                                
                                                        <th>Subject</th>
                                                        <th>Date</th>                                                            
                                                        <th>Time</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr class="tr-shadow">                                                            
                                                        <td class="desc">No</td>
                                                        <td>Upcoming</td>
                                                        <td>Class</td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                        </div>          
                                    </li>
                    <%  } else {
                    %>
                                <li data-row="1" data-col="2" data-sizex="2" data-sizey="2" class="item5">
                                    <h3 class="title">Upcoming Classes</h3>
                                    <div class="box2">
                                            <table class="table table-data2">
                                            <thead>
                                                <tr>                                                                
                                                    <th>Subject</th>
                                                    <th>Date</th>                                                            
                                                    <th>Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                <%      
                        for (Class c: classes){
                            System.out.println(classes.size());
                            Lesson l = LessonDAO.retrieveSingleLessonAfterCurr(c.getClassID());
                            if (l != null){
                                String startTime = l.getStartDate();
                                String endTime = l.getEndDate();
                %>
                                                <tr class="tr-shadow">                                                            
                                                    <td class="desc"><%=c.getSubject()%></td>
                                                    <td><%=l.getLessonDate()%></td>
                                                    <td><%=startTime.substring(startTime.indexOf(" "),startTime.indexOf(" ") + 6) + "-" + endTime.substring(endTime.indexOf(" "),endTime.indexOf(" ") + 6)%></td>
                                                </tr>                                                        
                <%
                            } else {
                %>                    <tr class="tr-shadow">                                                            
                                                    <td class="desc">No</td>
                                                    <td>Upcoming</td>
                                                    <td>Class</td>
                                                </tr>
                <%          }
                        }
                %>
                                             </tbody>
                                        </table>
                                    </div>          
                                </li>
                <%
                    }
                        int points = RewardDAO.countStudentPoint(user.getRespectiveID());
                %>
                                    <li data-row="1" data-col="1" data-sizex="1" data-sizey="1" class="item3">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-book"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=points%></h2>
                                                    <span>Available Points</span>
                                                </div>
                                            </div>
                                    </li>
                <%
                        ArrayList<Reward> rewards = RewardDAO.listAllRewardsByStudent(user.getRespectiveID());
                        if (rewards.isEmpty()){
                        %>
                                <li data-row="1" data-col="3" data-sizex="2" data-sizey="2" class="item6">
                                    <h3 class="title-3 m-b-30">Reward Redemption</h3>
                                    <div class="box2">
                                        <table class="table table-data2">
                                            <thead>
                                                <tr>                                                                
                                                    <th>Reward</th>
                                                    <th>Point Used</th>                                                            
                                                    <th>Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr class="tr-shadow">                                                            
                                                    <td class="desc">No</td>
                                                    <td>Redemption</td>
                                                    <td>Made</td>
                                                </tr>                                                        
                                            </tbody>
                                        </table>
                                    </div>          
                                </li>
                        <%
                        } else {
                            for (int i = 0; i<1; i++){
                                Reward r1 = rewards.get(i);
                            %>
                                <li data-row="1" data-col="3" data-sizex="2" data-sizey="2" class="item6">
                                    <h3 class="title-3 m-b-30">Reward Redemption</h3>
                                    <div class="box2">
                                        <table class="table table-data2">
                                            <thead>
                                                <tr>                                                                
                                                    <th>Reward</th>
                                                    <th>Points Used</th>                                                            
                                                    <th>Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr class="tr-shadow">                                                            
                                                    <td class="desc"><%=r1.getDescription()%></td>
                                                    <td><%=Math.abs(r1.getAmount())%></td>
                                                    <td><%=r1.getDate()%></td>
                                                </tr>
                                <%  
                                                if (rewards.size() >= 2){
                                                Reward r2 = rewards.get(i+1);
                                %>
                                                    <tr class="tr-shadow">                                                            
                                                    <td class="desc"><%=r2.getDescription()%></td>
                                                    <td><%=Math.abs(r2.getAmount())%></td>
                                                    <td><%=r2.getDate()%></td>
                                                </tr>
                                <%
                                                }

                                                if (rewards.size() >= 3){
                                                    Reward r3 = rewards.get(i+2);
                                %>
                                                    <tr class="tr-shadow">                                                            
                                                    <td class="desc"><%=r3.getDescription()%></td>
                                                    <td><%=Math.abs(r3.getAmount())%></td>
                                                    <td><%=r3.getDate()%></td>
                                                </tr>
                                            <%
                                                }
                                            }
                                            %>
                                            </tbody>
                                        </table>
                                    </div>          
                                </li>
                                            
                                        </div>
                                    <!-- /.info-box-content -->
                                    </div>  
                                </div>
                            </div>
                            <%
                           } 
                        } else if (role != null && role == "parent"){
                            ArrayList<Integer> childrenIDs = ParentChildRelDAO.retrieveChildren(user.getRespectiveID());
                            ArrayList<Student> children = new ArrayList<>();
                            for (int i: childrenIDs){
                                children.add(StudentDAO.retrieveStudentbyID(i,user.getBranchId()));
                            }
                            for (Student s: children){
                                ArrayList<Class> classes = ClassDAO.getStudentEnrolledClass(s.getStudentID(), user.getBranchId());
                                if (classes.isEmpty()){
                                %> 
                                <li data-row="1" data-col="1" data-sizex="2" data-sizey="2" class="item5">
                                    <h3 class="title"><%=s.getName()%>'s Upcoming Classes</h3>
                                    <div class="box2">
                                            <table class="table table-data2">
                                            <thead>
                                                <tr>                                                                
                                                    <th>Subject</th>
                                                    <th>Date</th>                                                            
                                                    <th>Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr class="tr-shadow">                                                            
                                                    <td class="desc">No</td>
                                                    <td>Upcoming</td>
                                                    <td>Class</td>
                                                </tr>
                                                
                                            </tbody>
                                        </table>
                                    </div>          
                                </li>
                                <% } else {
                    %>
                                    <li data-row="1" data-col="2" data-sizex="2" data-sizey="2" class="item5">
                                        <h3 class="title"><%=s.getName()%>'s Upcoming Classes</h3>
                                        <div class="box2">
                                                <table class="table table-data2">
                                                <thead>
                                                    <tr>                                                                
                                                        <th>Subject</th>
                                                        <th>Date</th>                                                            
                                                        <th>Time</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                    <%      
                                for (Class c: classes){
                                    Lesson l = LessonDAO.retrieveSingleLessonAfterCurr(c.getClassID());
                                    if (l != null){
                                        String startTime = l.getStartDate();
                                        String endTime = l.getEndDate();
                    %>
                                                <tr class="tr-shadow">                                                            
                                                    <td class="desc"><%=c.getSubject()%></td>
                                                    <td><%=l.getLessonDate()%></td>
                                                    <td><%=startTime.substring(startTime.indexOf(" "),startTime.indexOf(" ") + 6) + "-" + endTime.substring(endTime.indexOf(" "),endTime.indexOf(" ") + 6)%></td>
                                                </tr>                                                        
                <%
                                    }   else {
                %>                               <tr class="tr-shadow">                                                            
                                                    <td class="desc">No</td>
                                                    <td>Upcoming</td>
                                                    <td>Class</td>
                                                </tr>
                <%                  }
                                }   
                %>  
                                                </tbody>
                                            </table>
                                        </div>          
                                    </li>
                <%
                        }
                    }
                }else if (role != null && role == "admin" && user.getBranchId() == 0) {
                    int numberOfBranch = branchDAO.retrieveNumberOfBranch();
                    int numberOfTutor = tutorDAO.retrieveNumberOfTutor();
                    int numberOfStudent = studentDAO.retrieveNumberOfStudent();
                    Map<String, Integer> studentPerLevel = levelDAO.retrieveStudentPerLevel();
                %>
                                    <li data-row="1" data-col="1" data-sizex="1" data-sizey="1" class="item1">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-accounts-outline"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=numberOfBranch%></h2>
                                                    <span>number of branch</span>
                                                </div>
                                            </div>
                                    </li>
                                    <li data-row="1" data-col="2" data-sizex="1" data-sizey="1" class="item2">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-accounts"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=numberOfTutor%></h2>
                                                    <span>number of tutor</span>
                                                </div>
                                            </div>
                                    </li>
                                    <li data-row="1" data-col="3" data-sizex="1" data-sizey="1" class="item3">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-book"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=numberOfStudent%></h2>
                                                    <span>number of student</span>
                                                </div>
                                            </div>
                                    </li>
                                    <li data-row="2" data-col="1" data-sizex="2" data-sizey="2" class="item4">
                                        <h3 class="title-3 m-b-30">number of student per level </h3>
                                        <div class="box">
                                            <%                                                
                                            Set<String> levels = studentPerLevel.keySet();
                                            String[] arrayLevels = levels.toArray(new String[0]);
                                            if (levels.size() != 0) {                                              
                                            %>
                                            <table class="table table-top-campaign">
                                                <tbody>
                                                    <%
                                                    for (int j=0; j<levels.size(); j++){
                                                    %>
                                                    <tr>
                                                        <td><%=levels.toArray()[j]%></td>
                                                        <td><%=studentPerLevel.get(levels.toArray()[j])%></td>
                                                    </tr>
                                                    <% } %>
                                                </tbody>
                                            </table>
                                            <%}%>
                                        </div>
                                    </li>
                <%} else if (role != null && role == "tutor") {
                        ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(user.getRespectiveID(), user.getBranchId());
                        int numberOfStudents = 0;
                        %>
                                    <li data-row="1" data-col="1" data-sizex="1" data-sizey="1" class="item1">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-accounts-outline"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=classes.size()%></h2>
                                                    <span>number of class</span>
                                                </div>
                                            </div>
                                    </li>
                                    <li data-row="1" data-col="2" data-sizex="1" data-sizey="1" class="item2">                                            
                                            <div class="overview-box clearfix">
                                                <div class="icon">
                                                    <i class="zmdi zmdi-accounts"></i>
                                                </div>
                                                <div class="text">
                                                    <h2><%=numberOfStudents%></h2>
                                                    <span>number of student</span>
                                                </div>
                                            </div>
                                    </li>                        
                                    <li data-row="2" data-col="1" data-sizex="3" data-sizey="2" class="item5">
                                        <h3 class="title-3 m-b-30">upcoming classes </h3>
                                        <div class="box4">                                            
                                            <table class="table table-data2">
                                                <thead>
                                                    <tr>                                                                
                                                        <th>class</th>
                                                        <th>level</th>
                                                        <th>subject</th>
                                                        <th>number of student</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% for (Class cls : classes) {
                                                            numberOfStudents += studentClassDAO.retrieveNumberOfStudentByClass(cls.getClassID());
                                                            String subject = cls.getSubject();
                                                            out.println("<tr><td class='desc'>"+cls.getClassDay()+" "+cls.getStartTime()+"-"+cls.getEndTime()+" "+"</td><td>"+cls.getLevel()+"</td><td>"+subject+"</td><td class='text-center'>"+numberOfStudents+"</td></tr>");
                                                    }%>
                                                    
                                                </tbody>
                                            </table>                                           
                                        </div>
                                    </li>
                        <%}%>
                                    </ul>
                            </div>
                    </div>     
                    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js"></script>
                    <script src="styling/js/jquery.scrollable.js" type="text/javascript"></script>                    
                    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
                    <script src="styling/js/jquery.gridster.js" type="text/javascript"></script>
                    <script src="styling/js/jquery.gridster.min.js" type="text/javascript"></script>
                    <script type="text/javascript">
                        var gridster;

                        $(function() {
                                gridtster = $(".gridster ul").gridster({
                                        widget_margins: [10, 10],
                                        widget_base_dimensions: [140, 140],
                                        min_cols: 6
                                }).data('gridster');
                        });

                        $('.box').scrollable();
                                                        
                    </script>
            </div>
        </div>
</div>
</div>

</body> 

</html>
<script src="${pageContext.request.contextPath}/styling/js/main.js"></script>
