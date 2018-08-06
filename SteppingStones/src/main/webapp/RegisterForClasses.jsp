<%@page import="java.util.ArrayList"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<div class="col-md-10">

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Register For Classes </span></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">


            <form action="RegisterForClassesServlet" method="post">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Student</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                                <%  
                                    String redirectStudentName = "";
                                    if (request.getParameter("studentName") != null) {
                                        redirectStudentName = request.getParameter("studentName").trim();
                                    } 

                                    int branch_id = 0;
                                    if(user != null){
                                        branch_id = user.getBranchId();
                                    }

                                %>
                            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
                            <input id="studentNRIC"  name="studentName" placeholder="Name" class="form-control"  type="text" value="<%=redirectStudentName%>">
                        </div>
                    </div>
                </div>
                <br/><br/>

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn-default" name="search">Search For Classes</button>
                    </div>
                </div>

            </form><br><br>
            <%            
                String errorMsg = (String) request.getAttribute("errorMsg");
                if (errorMsg != null) {
                    out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
                }

                String status = (String) request.getAttribute("status");
                if (status != null) {
                    out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
                    response.sendRedirect("DisplayStudents.jsp");
                }

                ArrayList<Class> classes = (ArrayList<Class>) request.getAttribute("classes");
                ArrayList<Class> enrolledClasses = (ArrayList<Class>) request.getAttribute("enrolledClasses");
                String level = (String) request.getAttribute("level");
                String studentName = (String) request.getAttribute("studentName");

                if (classes != null) {
                    request.setAttribute("studentName", studentName);
            %>
                    Student Name: <label> <%out.println(studentName);%></label><br>
                    Level: <label> <%out.println(level);%></label><br>

            <%
                if (enrolledClasses.size() > 0) {
                    out.println("<br><h4>Currently Enrolled Classes:</h4><br>");
                    for (Class cls : enrolledClasses) {
                        out.println(cls.getSubject() + ", " + cls.getClassDay() + " " + cls.getClassTime()
                                + ", StartDate: " + cls.getStartDate() + ", Monthly Fees: " + cls.getMthlyFees() + "<br>");
                    }
                }
            %> 
            <br><h4>Register for Classes:</h4> <br>                
            <%
                for (Class cls : classes) {
                    request.setAttribute("value", cls.getClassID());
            %>
            <form action="RegisterForClassesServlet" method="post">

                <input type= "checkbox" name ="classValue" value = "${value}">
                <input type="hidden" name="studentName" value="${studentName}">
                <%
                        out.println(cls.getSubject() + ", " + cls.getClassDay() + " " + cls.getClassTime()
                                + ", StartDate: " + cls.getStartDate() + ", Monthly Fees: " + cls.getMthlyFees() + "<br/>");
                    }
                %>
                <br/>
                <div class="form-group">
                    <div>
                        <button type="submit" class="btn btn-default" name="select" value="select">Register Class</button>
                    </div>
                </div>

            </form>
            <%
                }
            %>
        </div>
    </div>

</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script>
    $(function () {
        if($('#errorMsg').length){
           $('#errorMsg').fadeIn().delay(3000).fadeOut();
        }
    });
</script>
