<%-- 
    Document   : CreateNewStudent
    Created on : 11 Jul, 2018, 3:26:40 PM
    Author     : DEYU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Application Form</title>
    </head>
    <body>
        <h1>Student Application Form</h1>
        <form action="CreateNewStudentServlet" method="post">
            StudentID:
            <input type ="text" name ="studentID" required><br>
            Student Name:
            <input type ="text" name ="studentName" required><br>
            Student Age:
            <input type ="text" name ="age" required><br>
            Student Gender:
            <select name = "gender">
                <option value="M">Male</option>
                <option value="F">Female</option>
            </select><br>
            Level:
            <select name = "lvl">
                <option value="Pri 3">Primary 3</option>
                <option value="Pri 4">Primary 4</option>
                <option value="Pri 5">Primary 5</option>
                <option value="Pri 6">Primary 6</option
                <option value="Sec 1">Secondary 1</option>
                <option value="Sec 2">Secondary 2</option>
                <option value="Sec 3">Secondary 3</option>
                <option value="Sec 4">Secondary 4</option>
            </select><br>
            Student Address:
            <input type ="text" name ="address" required><br>
            Student Contact Number:
            <input type ="text" name ="phone" required><br>
            Most Recent School Result:<br>
            <select name = "Sub1">
                <option value="Engish">Engish</option>
                <option value="Maths">Maths</option>
                <option value="Science">Science</option>
                <option value="E-Maths">E.Maths</option>
                <option value="Add-Maths">Add.Maths</option>
            </select>
            CA1<input type ="text" name ="FCA1">SA1<input type ="text" name ="FSA1">
            CA2<input type ="text" name ="FCA2">SA2<input type ="text" name ="FSA2"><br>            
            <select name = "Sub2">
                <option value="Engish">Engish</option>
                <option value="Maths">Maths</option>
                <option value="Science">Science</option>
                <option value="E-Maths">E.Maths</option>
                <option value="Add-Maths">Add.Maths</option>
            </select>
            CA1<input type ="text" name ="SCA1">SA1<input type ="text" name ="SSA1">
            CA2<input type ="text" name ="SCA2">SA2<input type ="text" name ="SSA2"><br>
            <select name = "Sub3">
                <option value="Engish">Engish</option>
                <option value="Maths">Maths</option>
                <option value="Science">Science</option>
                <option value="E-Maths">E.Maths</option>
                <option value="Add-Maths">Add.Maths</option>
            </select>
            CA1<input type ="text" name ="TCA1">SA1<input type ="text" name ="TSA1">
            CA2<input type ="text" name ="TCA2">SA2<input type ="text" name ="TSA2"><br>
            <button type="submit" value = "insert" name = "insert">Insert</button> 
        </form>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                    out.println(status); 
            }
        %>
    </body>
</html>
