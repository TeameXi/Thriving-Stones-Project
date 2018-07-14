<%-- 
    Document   : CreateTutor
    Created on : 14 Jul, 2018, 2:47:47 PM
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tutor Creation</title>
    </head>
    <form action="TutorCreation.java">
        <div class="container">
            <h1>Register</h1>
            <p>Please fill in this form to create an account for the tutor.</p>
            <hr>

            <label for="tutorID"><b>ID</b></label>
            <input type="text" placeholder="Enter tutor's id" name="tutorID" required>
            
            <label for="tutorName"><b>Name</b></label>
            <input type="text" placeholder="Enter tutor's name" name="tutorName" required>
            
            <label for="age"><b>Age</b></label>
            <input type="text" placeholder="Enter tutor's age" name="age" required>
            
            <label for="gender"><b>Gender</b></label>
            <input type="text" placeholder="Enter M or F" name="gender" required>
            
            <label for="phoneNo"><b>Phone Number</b></label>
            <input type="text" placeholder="Enter tutor's Phone No" name="phoneNo" required>
            
            <label for="emailAdd"><b>Email</b></label>
            <input type="text" placeholder="Enter tutor's email" name="emailAdd" required>

            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter tutor's password" name="password" required>

            <label for="password-rep"><b>Repeat Password</b></label>
            <input type="password" placeholder="Repeat Password" name="password-rep" required>
            <hr>

            <button type="submit" class="registerbtn">Register</button>
        </div>

    </form>
</html>
