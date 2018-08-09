<%-- 
    Document   : ScheduleCreation
    Created on : Jul 23, 2018, 11:10:02 PM
    Author     : Riana
--%>


<%@page import="java.util.ArrayList"%>
<%@page import="entity.Branch"%>
<%@page import="entity.Level"%>
<%@page import="entity.Subject"%>
<%@page import="java.util.List"%>
<%@page import="entity.Tutor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <%
         if(request.getAttribute("errors") != null){
                List<String> errors = (ArrayList<String>)request.getAttribute("errors");
        
    %>
                <script>alert('<%= errors.toString()%>')</script>
    <%
            }
    %>
    <%
        List<Branch> branchList = (List<Branch>) request.getAttribute("BranchList");
        List<Level> levelList = (List<Level>) request.getAttribute("LevelList");
        List<Subject> subjectList = (List<Subject>) request.getAttribute("SubjectList");
        System.out.println("subjectList" + subjectList);
        List<Tutor> tutorList = (List<Tutor>) request.getAttribute("TutorList");
        
    %>
    
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <form method="post" action="ScheduleCreationServlet">
            Branch : 
            <select name="branch">
                <%
                    for(Branch b:branchList){
                        %>
                        <option value="<%=b.getBranchId()%>"><%=b.getName()%></option>
                        <%
                    }
                %>
            </select>
            <br/>
            Term : 
            <select name="term">
                <option value="1">Term I</option>
                <option value="2">Term II</option>
                <option value="3">Term III</option>
                <option value="4">Term IV</option>
            </select>
            <br/>
            Level :
            <select name="level">
                <%
                    for(Level l:levelList){
                        %>
                        <option value="<%=l.getLevel_id()%>"><%=l.getLevelName()%></option>
                        <%
                    }
                %>
            </select>
            <br/>
            Subject :
            <select name="subject">
                <%
                    for(Subject s:subjectList){
                        %>
                        <option value="<%=s.getSubjectId()%>"><%=s.getSubjectName()%></option>
                        <%
                    }
                %>
            </select>
            <br/>
            fees : 
            <input type="text" name="fees" />
            <br/>
            has reminder for fees : 
            <input type="checkbox" name="reminderfee"/>
            <br/>
            timing : 
            <input type="text" name="timing"/>
            <br/>
            Class Day : 
            <input type="text" name="classDay"/>
            <br/>
            Start Date : 
            <input type="text" name="startDate"/>
            <br/>
            End Date : 
            <input type="text" name="endDate"/>
            <br/>
            Tutor :
            <select name="tutor">
                <%
                    for(Tutor t:tutorList){
                        %>
                        <option value="<%=t.getTutorId()%>"><%=t.getName()%></option>
                        <%
                    }
                %>
            </select>
            <br/>
            <input type="submit"/>
        </form>
    </body>
</html>
