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
<head>	 
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/AdminLTE.min.css">
    <style>
        .grid .survey-item {
            position: relative;
            display: inline-block;
            vertical-align: top;
            height: 90px;
            width: 230px;
            margin: 5px;
        }
        .survey-item {    
            padding: 0px;
            border-radius: 2px;
            background: white;
            box-shadow: 0 2px 1px rgba(170, 170, 170, 0.25);
        }
    </style>
</head>
<body>
    <div style="margin: 20px;"><h3>Dashboard</h3></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">       
            <%                
                BranchDAO branchDAO = new BranchDAO();
                TutorDAO tutorDAO = new TutorDAO();
                StudentDAO studentDAO = new StudentDAO();
                LevelDAO levelDAO = new LevelDAO();
                ClassDAO classDAO = new ClassDAO();
                StudentClassDAO studentClassDAO = new StudentClassDAO();
                if (role != null && role == "admin" && user.getBranchId() == 0) {

                    int numberOfBranch = branchDAO.retrieveNumberOfBranch();

                    //retrieve number of tutor                
                    int numberOfTutor = tutorDAO.retrieveNumberOfTutor();

                    //retrieve number of student                
                    int numberOfStudent = studentDAO.retrieveNumberOfStudent();

                    //retrieve number of student per level                
                    Map<String, Integer> studentPerLevel = levelDAO.retrieveStudentPerLevel();
            %>                
            <!-- Info boxes -->
            <div class="row">
                <div class="surveys grid">                    
                    <div class="survey-item">                    
                        <span class="info-box-icon bg-red"><i class="zmdi zmdi-home"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">Number of branch</span>
                            <span class="info-box-numbers"><%=numberOfBranch%></span>
                        </div>
                    </div>
                    <div class="survey-item">                    
                        <span class="info-box-icon bg-green"><i class="zmdi zmdi-accounts"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">Number of tutor</span>
                            <span class="info-box-numbers"><%=numberOfTutor%></span>
                        </div>                       
                    </div>
                    <div class="survey-item">

                        <span class="info-box-icon bg-yellows"><i class="zmdi zmdi-accounts-alt"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">NUMBER OF STUDENT</span>
                            <span class="info-box-numbers"><%=numberOfStudent%></span>
                        </div>                        
                    </div>
                </div>
            </div>
            <%
                Set<String> levels = studentPerLevel.keySet();
                if (levels.size() != 0) {
            %>
            <div class="col-md-4">
                <p class="text-center">
                    <strong>NUMBER OF STUDENT PER LEVEL</strong>
                </p>

                <%
                    for (String level : levels) {
                        int numberOfStudentPerLevel = studentPerLevel.get(level);
                        int percentage = 0;
                        if(numberOfStudent != 0){
                            percentage = (numberOfStudentPerLevel * 100) / numberOfStudent;
                        }
                %>
                <div class="progress-group">
                    <span class="progress-text"><%=level%></span>
                    <span class="progress-number"><b><%=studentPerLevel.get(level)%></b></span>

                    <div class="progress sm">
                        <div class="progress-bar progress-bar-yellow" style="width: <%=percentage%>%"></div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>       
            <%}
            } else if (role != null && role == "admin" && user.getBranchId() != 0) {
                //retrieve number of tutor                
                int numberOfTutorByBranch = tutorDAO.retrieveNumberOfTutorByBranch(user.getBranchId());
                //retrieve number of student                
                int numberOfStudentByBranch = studentDAO.retrieveNumberOfStudentByBranch(user.getBranchId());
                //retrieve number of student per level                
                Map<String, Integer> studentPerLevelByBranch = levelDAO.retrieveStudentPerLevelByBranch(user.getBranchId());

            %>
            <div class="row">
                <div class="surveys grid">                    
                    <div class="survey-item"> 
                        <span class="info-box-icon bg-green"><i class="zmdi zmdi-accounts"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">Number of tutor</span>
                            <span class="info-box-numbers"><%=numberOfTutorByBranch%></span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <div class="survey-item">
                        <span class="info-box-icon bg-yellows"><i class="zmdi zmdi-accounts-alt"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">NUMBER OF STUDENT</span>
                            <span class="info-box-numbers"><%=numberOfStudentByBranch%></span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
            </div>
            <%
                Set<String> levels = studentPerLevelByBranch.keySet();
                if (levels.size() != 0) {
            %>
            <div class="col-md-4">
                <p class="text-center">
                    <strong>NUMBER OF STUDENT PER LEVEL</strong>
                </p>

                <%
                    for (String level : levels) {
                        int numberOfStudentPerLevel = studentPerLevelByBranch.get(level);
                        int percentage = 0;
                        if(numberOfStudentByBranch != 0){
                            percentage = (numberOfStudentPerLevel * 100) / numberOfStudentByBranch;
                        }
                %>
                <div class="progress-group">
                    <span class="progress-text"><%=level%></span>
                    <span class="progress-number"><b><%=studentPerLevelByBranch.get(level)%></b></span>

                    <div class="progress sm">
                        <div class="progress-bar progress-bar-yellow" style="width: <%=percentage%>%"></div>
                    </div>
                </div>
                <%
                    }
                %>
            </div> 
            <% }
            } else if (role != null && role == "tutor") {
                ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(user.getUserId(), user.getBranchId());
                int numberOfStudents = 0;
                for (Class cls : classes) {
                    numberOfStudents += studentClassDAO.retrieveNumberOfStudentByClass(cls.getClassID());
                }
            %>
            <div class="row">
                <div class="surveys grid">                    
                    <div class="survey-item">
                        <span class="info-box-icon bg-green"><i class="zmdi zmdi-accounts"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">Number of classes</span>
                            <span class="info-box-numbers"><%=classes.size()%></span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>                   

                    <div class="survey-item">                  
                        <span class="info-box-icon bg-yellows"><i class="zmdi zmdi-accounts-alt"></i></span>

                        <div class="info-box-contents">
                            <span class="info-box-texts">NUMBER OF STUDENT</span>
                            <span class="info-box-numbers"><%=numberOfStudents%></span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
            </div>
            <% if (classes.size() != 0 && numberOfStudents != 0) { %>
            <div class="col-md-4">
                <p class="text-center">
                    <strong>NUMBER OF STUDENT PER CLASS</strong>
                </p>

                <%
                    for (Class clss : classes) {
                        int percentage = (studentClassDAO.retrieveNumberOfStudentByClass(clss.getClassID()) * 100) / numberOfStudents;
                %>
                <div class="progress-group">
                    <span class="progress-text"><%=clss.getClassDay()%>  <%=clss.getClassTime()%></span>
                    <span class="progress-number"><b><%=studentClassDAO.retrieveNumberOfStudentByClass(clss.getClassID())%></b></span>

                    <div class="progress sm">
                        <div class="progress-bar progress-bar-yellow" style="width: <%=percentage%>%"></div>
                    </div>
                </div>
                <%
                    }

                %>
            </div> 
            <%}
                }
            %>
        </div>
    </div>
</body>

<%@include file="footer.jsp"%>