<%-- 
    Document   : StudentApplicationForm
    Created on : 2 Aug, 2018, 1:34:11 PM
    Author     : DEYU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student Application Form</h1>
        <form action="StudentApplicationServlet" method="post">
            <h2>Students Details</h2>
            Student NRIC: 
            <input type ="text" name ="studentNRIC" placeholder= "Enter Student NRIC" required><br>
            Student Name: 
            <input type ="text" name ="studentName" placeholder= "Enter Student Name" required><br>
            Birthday: 
            <input type="date" name="bday"><br>
            Gender: 
            <select name="gender">
                <option value="-1" selected>Select Gender</option>
                <option value="F">Female</option>
                <option value="M">Male</option>
            </select><br>
            Academic Level: 
            <select name="lvl">
                <option value="-1" selected>Select Level</option>
                <option value="Primary 3">Primary 3</option>
                <option value="Primary 4">Primary 4</option>
                <option value="Primary 5">Primary 5</option>
                <option value="Primary 6">Primary 6</option>
                <option value="Secondary 1">Secondary 1</option>
                <option value="Secondary 2">Secondary 2</option>
                <option value="Secondary 3">Secondary 3</option>
                <option value="Secondary 4">Secondary 4</option>
            </select><br>
            Mobile Number:
            <input type ="text" name ="phone" placeholder= "Enter Mobile No." required><br>
            Address:
            <input type ="text" name ="address" placeholder= "Enter Address" required><br>
            
            <h2>Student's Account Information</h2>
            Email: 
            <input type ="text" name ="studentEmail" placeholder= "Enter Email" required><br>
            Password:
            <input type="password" name="studentPassword" placeholder="Enter your password" required><br>
            Confirm Password: 
            <input type="password" name="studentCfmPassword" placeholder="Confirm your password" required><br>
            
            <h2>Parents Details</h2>
            Parent Name: 
            <input type ="text" name ="parentName" placeholder= "Enter Parent Name" required><br>
            Parent Nationality: 
            <input type ="text" name ="parentNationality" placeholder= "Enter Nationality" required><br>
            Parent Company: 
            <input type ="text" name ="parentCompany" placeholder= "Enter Company" required><br>
            Parent Designation: 
            <input type ="text" name ="parentDesgination" placeholder= "Enter Designation" required><br>
            Mobile Number:
            <input type ="text" name ="parentPhone" placeholder= "Enter Mobile No." required><br>
            Email: 
            <input type ="text" name ="parentEmail" placeholder= "Enter Email" required><br><br>
                    
            <button type="submit" value = "insert" name = "insert">Insert</button>
        </form>
    </body>
</html>
