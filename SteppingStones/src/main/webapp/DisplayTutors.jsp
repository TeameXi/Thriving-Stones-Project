<%-- 
    Document   : DisplayTutors
    Created on : 16 Jul, 2018, 10:08:24 AM
    Author     : huixintang
--%>

<%@page import="entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.TutorDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        TutorDAO tutors = new TutorDAO();
        ArrayList<Tutor> tutorList = tutors.retrieveAllTutors();
    %>
    //This is for testing purposes, delete at the end of iter
    <table>
        <tr>
            <th><b>Name</b></th>
            <th><b>ID</b></th>
            <th><b>Age</b></th>
            <th><b>Gender</b></th>
            <th><b>Email</b></th>
            <th><b>Phone</b></th>
        </tr>
        <% 
            if(tutorList != null){
                for (Tutor tutor:tutorList){
                    out.println("<tr><td>" + tutor.getName() + "</td><td>" 
                            + tutor.getTutorID() + "</td><td>" + tutor.getAge() 
                            + "</td><td>" + tutor.getGender() + "</td><td>" 
                            + tutor.getEmailAdd() + "</td><td>" + tutor.getPhoneNo() + "</td></tr>");
                }
            }
        %>
    </table>
</html>
