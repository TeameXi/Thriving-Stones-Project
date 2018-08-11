<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="model.BranchDAO"%>
<%@page import="model.LevelDAO"%>
<%@page import="model.StudentDAO"%>
<%@page import="model.TutorDAO"%>
<%@include file="header.jsp"%>
<head>	
        <!-- Bootstrap 3.3.7 -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css//Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/AdminLTE.min.css">
</head>
<body>
<div style="margin: 20px;"><h3>Dashboard</h3></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">       
           
            <%
            if(role != null && role == "admin"){
                BranchDAO branchDAO = new BranchDAO();
                int numberOfBranch = branchDAO.retrieveNumberOfBranch();

                //retrieve number of tutor
                TutorDAO tutorDAO = new TutorDAO();
                int numberOfTutor = tutorDAO.retrieveNumberOfTutor();

                //retrieve number of student
                StudentDAO studentDAO = new StudentDAO();
                int numberOfStudent = studentDAO.retrieveNumberOfStudent();

                //retrieve number of student per level
                LevelDAO levelDAO = new LevelDAO();
                Map<String, Integer> studentPerLevel = levelDAO.retrieveStudentPerLevel();
                %>                
               
        
        <!-- Info boxes -->
        <div class="rowDashboard">
            <div class="col-md">
              <div class="info-boxs">
                <span class="info-box-icon bg-red"><i class="ion ion-ios-home"></i></span>

                <div class="info-box-contents">
                  <span class="info-box-texts">Number of branch</span>
                  <span class="info-box-numbers"><%=numberOfBranch%></span>
                </div>
                <!-- /.info-box-content -->
              </div>
              <!-- /.info-box -->
            </div>
            <div class="col-md">
              <div class="info-boxs">
                <span class="info-box-icon bg-green"><i class="ion ion-ios-people"></i></span>

                <div class="info-box-contents">
                  <span class="info-box-texts">Number of tutor</span>
                  <span class="info-box-numbers"><%=numberOfTutor%></span>
                </div>
                <!-- /.info-box-content -->
              </div>
              <!-- /.info-box -->
            </div>
        
            <div class="col-md">
             <div class="info-boxs">
               <span class="info-box-icon bg-yellows"><i class="ion ion-ios-people-outline"></i></span>

               <div class="info-box-contents">
                 <span class="info-box-texts">NUMBER OF STUDENT</span>
                 <span class="info-box-numbers"><%=numberOfStudent%></span>
               </div>
               <!-- /.info-box-content -->
             </div>
             <!-- /.info-box -->
            </div>
        </div>
        <div class="col-md-4">
        <p class="text-center">
          <strong>NUMBER OF STUDENT PER LEVEL</strong>
        </p>

        <%
        Set<String> levels = studentPerLevel.keySet();
        for(String level: levels){
        int numberOfStudentPerLevel = studentPerLevel.get(level);
        int percentage = (numberOfStudentPerLevel*100)/numberOfStudent;
        %>

        <p><%=level%> : <%=percentage%></p>
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
             <%
            }
            %>
       
    </div>
</div>
</body>

<%@include file="footer.jsp"%>