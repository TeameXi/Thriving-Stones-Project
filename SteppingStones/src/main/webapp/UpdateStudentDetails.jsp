<%-- 
    Document   : UpdateStudentDetails
    Created on : 11 Jul, 2018, 9:42:49 PM
    Author     : DEYU
--%>

<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Student Details</title>
    </head>
    <%
        ArrayList<Student> stu = (ArrayList<Student>)request.getAttribute("StudentData");
        String studentID = (String)request.getAttribute("StudentID");
        if(stu != null){
            Student s = null;
            for(int i = 0; i< stu.size(); i++){
                s = stu.get(i);
            }
            if(s != null){
                request.setAttribute("StudentName", s.getName());
                request.setAttribute("Age", s.getAge());
                request.setAttribute("Gender", s.getGender());
                request.setAttribute("Level", s.getLevel());
                request.setAttribute("Address", s.getAddress());
                request.setAttribute("Phone", s.getPhone());
                request.setAttribute("reqAmt", s.getReqAmt());
                request.setAttribute("outstandingAmt", s.getOutstandingAmt());
            }else{
                out.println("Student ID not exists in Database!");
            }
        }
        
    %>
    <body>
        <h1>Update Student Details</h1>       
        <form action="CreateNewStudentServlet" method="post" required>
            StudentID:
            <input type ="text" name ="studentID" value = "${StudentID}" required><br>
            Student Name:
            <input type ="text" name ="studentName" value = "${StudentName}" required><br>
            Student Age:
            <input type ="text" name ="age" value = "${Age}" required><br>
            Student Gender:
            <select name = "gender">
                <option value="${Gender}">"${Gender}"</option>
                <option value="M">M</option>
                <option value="F">F</option>
            </select><br>
            Level:
            <select name = "lvl">
                <option value="${Level}">"${Level}"</option>
                <option value="Pri 3">Pri 3</option>
                <option value="Pri 4">Pri 4</option>
                <option value="Pri 5">Pri 5</option>
                <option value="Pri 6">Pri 6</option
                <option value="Sec 1">Sec 1</option>
                <option value="Sec 2">Sec 2</option>
                <option value="Sec 3">Sec 3</option>
                <option value="Sec 4">Sec 4</option>
            </select><br>
            Student Address:
            <input type ="text" name ="address" value = "${Address}" required><br>
            Student Contact Number:
            <input type ="text" name ="phone" value = "${Phone}" required><br>
            Required Amount:
            <input type ="text" name ="reqAmt" value = "${reqAmt}" required><br>
            Outstanding Amount:
            <input type ="text" name ="outstandingAmt" value = "${outstandingAmt}" required><br>
            <button type="submit" value = "update" name = "update">Update</button> 
        </form>        
    </body>
</html>
