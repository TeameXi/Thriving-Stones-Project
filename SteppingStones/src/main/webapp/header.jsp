<%@page import="entity.Users"%>
<!DOCTYPE html>
<html>
<head>
	<title>Stepping Stones</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link href="${pageContext.request.contextPath}/styling/css/main.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">
</head>
<body>
    <%
        Users user = (Users)session.getAttribute("user");
        String role = (String)session.getAttribute("role");
        String account = "";
        int branch_id = 0;
        int user_id = 0;
        if(user != null){
            account = user.getEmail();
            branch_id = user.getBranchId();
            user_id = user.getUserId();
        }
        
      
    %>
    <!--Header-->
    <div class="header">
        <div class="container">
            <div class="row">
               <div class="col-md-5">
                  <!-- Logo -->
                  <div class="logo">
                    <a href="index.html"><img src="${pageContext.request.contextPath}/styling/img/logo_white.png"></img></a>
                  </div>
               </div>
               <div class="col-md-5"></div>
               <div class="col-md-2">
                  <div class="navbar" role="banner">
                      <nav style="text-align: center;">
                        <ul class="nav navbar-nav">
                          <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=account %><b class="caret"></b></a>
                            <ul class="dropdown-menu animated fadeInUp">
                                <%
                                    if(role != null && role == "admin"){
                                        out.println("<li><a href='ProfileForAdmin.jsp'>Manage Account</a></li>");
                                    }else if(role != null && role == "tutor"){
                                        out.println("<li><a href='ProfileForTutor.jsp'>Manage Account</a></li>");
                                    }else if(role != null && role == "parent"){
                                        out.println("<li><a href='ProfileForParent.jsp'>Manage Account</a></li>");
                                    }
                                %>
                              <li><a href="ResetPassword.jsp">Update Password</a></li>
                              <li><a href="Logout.jsp">Logout</a></li>
                            </ul>
                          </li>
                        </ul>
                      </nav>
                  </div>
               </div>
            </div>
        </div>
    </div>



<div class="page-content">	
    <div class="row">
        <div class="col-md-2">
            <div class="sidebar content-box" style="display: block;">
                <ul class="nav">
                    <!-- Main menu -->
                    <li><a href="Dashboard.jsp"><i class="glyphicon glyphicon-dashboard"></i> Dashboard</a></li>
                    
                    <%  
                        if(user != null && user.getBranchId() == 0 && role=="admin"){
                    %>
                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi-city-alt"></i> Branch
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateBranch.jsp">Add Branch</a></li>
                                    <li><a href="DisplayBranches.jsp">View Branches</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi-account-o"></i> Branch Admin
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateAdmin.jsp">Add Branch Admin account</a></li>
                                    <li><a href="DisplayAdmins.jsp">View Branch Admin</a></li>
                                </ul>
                            </li>
                    <%
                        }else if(user != null && user.getBranchId() !=0 && role == "admin"){
                    %>
                    
                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi-accounts-alt"></i> Tutor
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateTutor.jsp">Create Tutor</a></li>
                                    <li><a href="DisplayTutors.jsp">View Tutors</a></li>
                                </ul>
                            </li>

                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi-graduation-cap"></i> Student
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateStudent.jsp">Create Student</a></li>
                                    <li><a href="DisplayStudents.jsp">View Students</a></li>
                                </ul>
                            </li>

                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi-book"></i> Subject
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateSubject.jsp">Create Subject</a></li>
                                    <li><a href="DisplaySubjects.jsp">View Subjects</a></li>
                                </ul>
                            </li>
                            <li><a href="RegisterForClasses.jsp"><i class="zmdi zmdi-library"></i>Register For Classes</a></li>
                            <li><a href="${pageContext.request.contextPath}/RetrieveScheduleCreationDetails"><i class="zmdi zmdi-calendar-note"></i> Scheduling Classes</a></li>
                            <li><a href="stats.html"><i class="zmdi zmdi-chart"></i> Financial Reports</a></li>
                            <li><a href="tables.html"><i class="zmdi zmdi-money-box"></i> Payment Handling</a></li>
                    
                    <%
                        }else if(role != null && user != null && role == "tutor" ){
                    %>
                            <li><a href=""><i class="zmdi zmdi-money-box"></i>Your Payslip</a></li>
                            <li><a href="DisplayClassesBelongToTutor.jsp"><i class="zmdi zmdi-local-library"></i>Classes</a></li>
                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi zmdi-graduation-cap"></i> Student Attendance 
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateBranch.jsp">Mark Attendance</a></li>
                                    <li><a href="DisplayBranches.jsp">View Attendance</a></li>
                                </ul>
                            </li>
                            <li><a href="stats.html"><i class="zmdi zmdi-chart"></i>Your Attendance</a></li>
                            <li><a href="CreateTuitionGrade.jsp"><i class="zmdi zmdi-star-circle"></i>Student's Grades</a></li>
                    <%
                        }else if(role != null && user != null && role == "parent" ){
                    %>
                            <li><a href=""><i class="zmdi zmdi-money-box"></i>Outstanding Fees</a></li>
                            <li><a href="DisplayClassesBelongToTutor.jsp"><i class="zmdi zmdi-local-library"></i>Classes</a></li>
                            <li class="submenu">
                                <a href="#">
                                    <i class="zmdi zmdi zmdi-graduation-cap"></i> Student Attendance 
                                    <span class="caret pull-right"></span>
                                </a>
                                <!-- Sub menu -->
                                <ul>
                                    <li><a href="CreateBranch.jsp">Mark Attendance</a></li>
                                    <li><a href="DisplayBranches.jsp">View Attendance</a></li>
                                </ul>
                            </li>
                           
                            <li><a href="stats.html"><i class="zmdi zmdi-chart"></i>Your Attendance</a></li>
                            <li><a href="CreateTuitionGrade.jsp"><i class="zmdi zmdi-star-circle"></i>Student's Grades</a></li>
                            
                    <%
                        }else if(role != null && user != null && role == "student" ){

                        }
                    %>
                    


                </ul>
            </div>
        </div>
       
   