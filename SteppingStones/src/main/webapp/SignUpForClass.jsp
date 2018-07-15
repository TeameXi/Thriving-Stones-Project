<%-- 
    Document   : SignUpForClass
    Created on : 15 Jul, 2018, 2:53:19 PM
    Author     : DEYU
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register for Classes</title>
    </head>
    <body>
        <h1>Register for Classes</h1>
        <%
            String lvl = (String)request.getAttribute("level");
            String studentID = (String)request.getAttribute("studentID");
            String studentName = (String)request.getAttribute("studentName");
            Map<String, Class> clas = (Map<String, Class>) request.getAttribute("class");
        %>
        <form action="SignUpForClassServlet" method="post" required> 
            StudentID:
            <input type ="text" name ="studentID" value = "${studentID}" required><br>
            Name:
            <input type ="text" name ="studentName" value = "${studentName}" required><br>
            Level: <%out.println(lvl);%><br>
            Sign Up for Classes: <br>          
            <%  
                Set set = clas.entrySet();
                Iterator iterator = set.iterator();
                while(iterator.hasNext()){
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    Class classs = (Class) mentry.getValue();
                    String classKey = (String) mentry.getKey();
                    request.setAttribute("value", classKey + "&" + classs.toString());
            %>
            <input type= "checkbox" name ="classValue" value = "${value}">
            <%                    
                    out.println(classs.getSubject()+ " " + classs.getClassDay() + " " + classs.getClassTime() + 
                            " StartDate: " + classs.getStartDate() + " Monthly Fees: " + classs.getMthlyFees() + "<br>");
                    iterator.remove();
                }
            %> 
            <button type="submit" value = "SignUp" name = "SignUp">Sign Up</button>
        </form>
    </body>
</html>
