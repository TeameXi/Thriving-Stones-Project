<!DOCTYPE html>
<html>
<head>
	<title>Stepping Stones</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link href="${pageContext.request.contextPath}/styling/css/main.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">
</head>
<body>
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
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Account <b class="caret"></b></a>
                            <ul class="dropdown-menu animated fadeInUp">
                              <li><a href="profile.html">Profile</a></li>
                              <li><a href="login.html">Logout</a></li>
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

                    <li class="submenu">
                        <a href="#">
                            <i class="zmdi zmdi-accounts-alt"></i> User Management
                            <span class="caret pull-right"></span>
                        </a>
                        <!-- Sub menu -->
                        <ul>
                            <li><a href="CreateTutor.jsp">Create User</a></li>
                            <li><a href="DisplayTutors.jsp">View Users</a></li>
                        </ul>
                    </li>

                    <li><a href="calendar.html"><i class="zmdi zmdi-calendar-note"></i> Scheduling Classes</a></li>
                    <li><a href="stats.html"><i class="zmdi zmdi-chart"></i> Financial Reports</a></li>
                    <li><a href="tables.html"><i class="zmdi zmdi-money-box"></i> Payment Handling</a></li>
                    <li><a href="buttons.html"><i class="zmdi zmdi-graduation-cap"></i>Student Management</a></li>
                    <li class="submenu">
                        <a href="#">
                            <i class="zmdi zmdi-graduation-cap"></i> Student Management
                            <span class="caret pull-right"></span>
                        </a>
                        <!-- Sub menu -->
                        <ul>
                            <li><a href="CreateNewStudent.jsp">Create Student</a></li>
                            <li><a href="DisplayStudents.jsp">View Students</a></li>
                        </ul>
                    </li>

                    <li><a href="editors.html"><i class="zmdi zmdi-book"></i>Modules Planning</a></li>


                </ul>
            </div>
        </div>
       
   