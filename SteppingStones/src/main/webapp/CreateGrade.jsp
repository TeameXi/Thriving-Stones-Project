<%-- 
    Document   : CreateGrade.jsp
    Created on : 23 Jul, 2018, 1:03:51 PM
    Author     : 
--%>

<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Grade</title>
    </head>

    <%

    %>

    <form action="CreateGradeServlet">
        <div>
            <span class="label-input100">Select Student</span>
            <select id="student" name="student" class="cd-select">
                <%                    
                    ArrayList<Student> stuList = (ArrayList<Student>) request.getAttribute("students");
                    for (Student tempStudent : stuList) {
                        out.println("<option value=\"" + tempStudent.getStudentID() + "\">" + tempStudent.getName() + "</option>");
                    }
                %>
            </select> 
        </div>

        <div>
            <span class="label-input100">Location</span>
            <select id="location" name="location" class="cd-select">
                <option value="-1" selected>Select Location</option>
                <option value="School">School</option>
                <option value="Center">Center</option>
            </select> 
        </div>

        <div>
            <span class="label-input100">Examination</span>
            <select id="examination" name="examination" class="cd-select">
                <option value="-1" selected>Examination</option>
                <option value="ca1">CA1</option>
                <option value="sa1">SA1</option>
                <option value="ca2">CA2</option>
                <option value="sa2">SA2</option>
            </select> 
        </div>

        <%
            String level = (String) request.getAttribute("level");
            if (level.matches("(Pri)" + " " + "[1-6]")) {
        %>
        <div>
            <span class="label-input100">Subject</span>
            <select id="subject" name="subject" class="cd-select">
                <option value="-1" selected>Select Grade</option>
                <option value="English">English</option>
                <option value="Maths">Maths</option>
                <option value="Science">Science</option>
            </select> 
        </div>
        <%
        } else {
        %>
        <div>
            <span class="label-input100">Subject</span>
            <select id="subject" name="subject" class="cd-select">
                <option value="-1" selected>Select Grade</option>
                <option value="English">English</option>
                <option value="Add-Maths">Add-Maths</option>
                <option value="E-Maths">E-Maths</option>
                <option value="Chemistry">Chemistry</option>
                <option value="Biology">Biology</option>
                <option value="Physics">Physics</option>
            </select> 
        </div>
        <%            }
        %>

        Grade: <input type="text" name="grade"><br>
        
        <div class="container-login100-form-btn">
            <div class="wrap-login100-form-btn">
                <div class="login100-form-bgbtn"></div>
                <button class="login100-fo
                        rm-btn" type="submit" value = "insert" name = "insert">
                    Create Grade
                </button>
            </div>
        </div>	
    </form>
</html>
