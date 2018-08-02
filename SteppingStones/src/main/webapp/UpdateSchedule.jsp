<%-- 
    Document   : UpdateSchedule
    Created on : Aug 2, 2018, 2:59:43 PM
    Author     : HuiXin
--%>

<%@page import="model.SubjectDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="connection.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        LevelDAO level = new LevelDAO();
        ArrayList<String> levels = level.retrieveAllLevels();
    %>

    <form action="UpdateScheduleServlet">
        Level: <select name="level" id="level">
            <option value="">-Select Level-</option>
            <%
                int index = 1;
                for (String l : levels) {%>
            <option value=<%=index%>><%=l%></option>
            <%index += 1;
                }
            %>
        </select>

        Subject: <select name="subject" id="subject">
            <option value="">-Select Subject-</option>
            <%
                SubjectDAO sDAO = new SubjectDAO();
                // ArrayList<String> subjects = sDAO.retrieveSubjectsByLevel(level);
                ArrayList<String> subjects = sDAO.retrieveAllSubjects();
                int index1 = 1;
                for (String l : subjects) {%>
            <option value=<%=index1%>><%=l%></option>
            <%index1 += 1;
                }
            %>
        </select>
        Timing: <input type="text" name="timing"/>
        <input type="submit" value="Update Schedule">
    </form>
        
    <%
        ArrayList<String> errors = (ArrayList<String>)request.getAttribute("errors");
        
        if(errors == null){
            out.println("Updated");
        }else if (errors != null){
            boolean status = (Boolean)request.getAttribute("status");
            out.println(status);
            for (String error: errors) {
                out.println(error);
            }
        }
    %>
</html>

