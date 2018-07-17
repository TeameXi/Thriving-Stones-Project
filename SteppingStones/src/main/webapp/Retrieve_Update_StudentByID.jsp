<%-- 
    Document   : RetrieveStudentByID
    Created on : 11 Jul, 2018, 8:29:52 PM
    Author     : DEYU
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.StudentGrade"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Retrieve or Update Student By ID</title>
    </head>
    <body>
        <h1>Retrieve or Update Student By ID</h1>
        <form action="Retrieve_Update_StudentServlet" method="post">
            StudentID:
            <input type ="text" name ="studentID" required><br>
            <button type="submit" value = "retrieve" name = "retrieve">Retrieve</button> 
            <button type="submit" value = "update" name = "update">Update</button>
        </form>
        <%
            ArrayList<Student> stu = (ArrayList<Student>)request.getAttribute("StudentData");
            String studentID = (String)request.getAttribute("StudentID");
            ArrayList<Class> classes = (ArrayList<Class>)request.getAttribute("StudentClasses");
       
            if(stu != null){
                Student s = null;
                for(int i = 0; i< stu.size(); i++){
                    s = stu.get(i);
                }
                if(s != null){                   
                    out.println("<table>");
                    out.println("<tr><td>StudentID</td><td>" + studentID  + "</td><tr><td>StudentName</td><td>" + s.getName() + "</td></tr>");
                    out.println("<tr><td>Gender</td><td>" + s.getGender() + "</td><tr><td>Age</td><td>" + s.getAge() + "</td></tr>");
                    out.println("<tr><td>Level</td><td>" + s.getLevel() + "</td></tr>");
                    out.println("<tr><td>Address</td><td>" + s.getAddress()  + "</td><tr><td>Phone</td><td>" + s.getPhone() + "</td></tr>");
                    out.println("<tr><td>Required Amount</td><td>" + s.getReqAmt() + "</td></tr><tr><td>Outstanding Amount</td><td>" + s.getOutstandingAmt() + "</td></tr>");
                    out.println("<tr><td>Registered Classes</td>");
                    for(Class cls: classes){
                        out.println("<td>" + cls.getSubject()+ " " + cls.getClassDay() + " " + cls.getClassTime() + 
                            " StartDate: " + cls.getStartDate() + "</td>");
                    }
                    out.println("</tr>");
                    out.println("</table><table>");                  
                    Map<String, Map<String, StudentGrade>> grades = s.getGrades();
                    Set set = grades.entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()) {
                            Map.Entry mentry = (Map.Entry) iterator.next();
                            Map<String, StudentGrade> schoolOrCentreGrades = (Map<String, StudentGrade>) mentry.getValue();
                            String schoolOrCentre = (String) mentry.getKey();
                            Set set1 = schoolOrCentreGrades.entrySet();
                            Iterator iterator1 = set1.iterator();
                            while (iterator1.hasNext()) {
                                Map.Entry mentry1 = (Map.Entry) iterator1.next();
                                StudentGrade subGrades = (StudentGrade) mentry1.getValue();
                                String sub = (String) mentry1.getKey();
                                out.println("<tr><td>" + schoolOrCentre + "-" + sub + "</td><td>CA1=" + subGrades.getCA1() + "</td><td>SA1=" + subGrades.getSA1() + 
                                        "</td><td>CA2=" + subGrades.getCA2() + "</td><td>SA2=" + subGrades.getSA2() + "</td>");
                                iterator1.remove();
                            }
                    }
                    out.println("</table>");
                }else{
                    out.println("Student Record Not Found!");
                }
            }
            
            String status = (String) request.getAttribute("status");
            if (status != null) {
                    out.println(status); 
            }
        %>
    </body>
</html>
