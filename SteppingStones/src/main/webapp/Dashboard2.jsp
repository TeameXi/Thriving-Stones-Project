<%-- 
    Document   : Dashboard2
    Created on : 16 Nov, 2018, 5:45:34 PM
    Author     : Zang Yu
--%>
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
    </head>
    <body>
        <div class="col-md-10">
            <div style="margin: 20px;"><h3>Dashboard</h3></div>
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
                                        <li data-row="1" data-col="4" data-sizex="3" data-sizey="2" class="item2">
                                            <div class="top-campaign">
                                            <h3 class="title-3 m-b-30">top campaigns</h3>
                                            <div class="scrollbar-inner">
                                                <table class="table table-top-campaign">
                                                    <tbody>
                                                        <tr>
                                                            <td>1. Australia</td>
                                                            <td>$70,261.65</td>
                                                        </tr>
                                                        <tr>
                                                            <td>2. United Kingdom</td>
                                                            <td>$46,399.22</td>
                                                        </tr>
                                                        <tr>
                                                            <td>3. Turkey</td>
                                                            <td>$35,364.90</td>
                                                        </tr>
                                                        <tr>
                                                            <td>4. Germany</td>
                                                            <td>$20,366.96</td>
                                                        </tr> 
                                                        <tr>
                                                            <td>1. Australia</td>
                                                            <td>$70,261.65</td>
                                                        </tr>
                                                        <tr>
                                                            <td>2. United Kingdom</td>
                                                            <td>$46,399.22</td>
                                                        </tr>
                                                        <tr>
                                                            <td>3. Turkey</td>
                                                            <td>$35,364.90</td>
                                                        </tr>
                                                        <tr>
                                                            <td>4. Germany</td>
                                                            <td>$20,366.96</td>
                                                        </tr> 
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        </li>
                                        
                                        <li data-row="2" data-col="1" data-sizex="2" data-sizey="2" class="item2">hi</li>
                                        
                                        <li data-row="2" data-col="2" data-sizex="1" data-sizey="1" class="item2"><h4 class="content">WORLD</h4></li>
                                        <li data-row="3" data-col="2" data-sizex="1" data-sizey="1" class="item2"><h4 class="content">OR ELSE</h4></li>

                                        <li data-row="3" data-col="4" data-sizex="1" data-sizey="1" class="item1"></li>				

                                        <li data-row="3" data-col="5" data-sizex="1" data-sizey="1" class="item1"></li>
                                        <li data-row="3" data-col="5" data-sizex="1" data-sizey="1" class="item5"></li>

                                        
                                        <li data-row="2" data-col="6" data-sizex="1" data-sizey="2" class="item4"></li>	
                    <% } %>
                                    </ul>
                            </div>
                    </div>
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
                            
                            
                            
                            $(document).ready(function() {
                                $('.scrollbar-inner').scrollbar();
                            });
                    </script>
            </div>
        </div>
</div>
</div>

</body> 

</html>

