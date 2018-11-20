<%-- 
    Document   : Dashboard2
    Created on : 16 Nov, 2018, 5:45:34 PM
    Author     : Zang Yu
--%>
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
                                                            <th>date</th>
                                                            <th>replaced class</th>
                                                            <th>name</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>                                            
                                                <%
                                                    ArrayList<Tutor> tutorss = TutorDAO.tutorWithTotalClasses(branch_id);

                                                    for(Tutor t : tutorss){
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

                                        
                    <% } %>
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

