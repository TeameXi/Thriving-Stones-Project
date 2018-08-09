<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@include file="header.jsp"%>
<div class="col-md-10">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <h3>Dashboard</h3><br/>
            <%
            if(role != null && role == "admin"){
                int numberOfBranch = (int) request.getAttribute("NumberOfBranch");
                int numberOfTutor = (int) request.getAttribute("NumberOfTutor");
                int numberOfStudent = (int) request.getAttribute("NumberOfStudent");
                Map<String, Integer> studentPerLevel = (TreeMap<String, Integer>) request.getAttribute("StudentPerLevel");
                %>
                <p>Number of branch : <%=numberOfBranch%></p>
                <p>Number of tutor : <%=numberOfTutor%></p>
                <p>Number of Student : <%=numberOfStudent%></p>
                <%
                Set<String> levels = studentPerLevel.keySet();
                for(String level: levels){%>
                <p><%=level%> : <%=studentPerLevel.get(level)%></p>
                    <%
                }
                    
                %>
                <%
            }
            %>
            
        </div>
    </div>


</div>
</div>
</div>

<%@include file="footer.jsp"%>