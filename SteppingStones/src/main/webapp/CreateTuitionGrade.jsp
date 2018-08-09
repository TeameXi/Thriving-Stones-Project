<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_tutor.jsp"%>
<%@include file="header.jsp"%>
  
<div class="col-md-10">
        <div style="text-align: center;margin: 20px;"><span class="tab_active">Students Grades </span></h5></div>
        
        <form action="CreateTuitionGradeServlet" method="post">
            <h4>Available Classes</h4>
            <%
                ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(user_id, branch_id);
                if(classes.size() > 0){
                    out.println("<table class='table table-bordered'>");
                    out.println("<thead class='table_title'><tr><th>Level</th><th>Subject</th><th>Class Timing</th><th></th></tr></thead><tbody>");
                    for(Class cls: classes){
                        
                        out.println("<tr><td>"+cls.getLevel() + "</td><td>" + cls.getSubject() + "</td><td>" + cls.getClassTime()+" ( "+cls.getClassDay()+" )");
                        request.setAttribute("ClassID", cls.getClassID());
            %>           
                        <td><button type="submit" value="${ClassID}" name="select" class="btn btn-default">Add Grades</button></td></tr>                           
            <%
                    }
                    out.println("</tbody></table>");
                }
            %>  
        </form>
        
        <form action="CreateTuitionGradeServlet" method="post">
           
            
            <%
                ArrayList<String> students = (ArrayList<String>)request.getAttribute("students");
                Class cls = (Class)request.getAttribute("class");              
                if(cls != null){
                    request.setAttribute("classID", cls.getClassID());
                    request.setAttribute("sub", cls.getSubject());
                    out.println("<h4><br><br>Students From Class '<strong>" + cls.getLevel() + " " + cls.getSubject() + " "+ cls.getClassTime()+" ("+cls.getClassDay() +")" + "'</strong></h4></br>");
                }
                if(students != null && students.size() > 0){
            %>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">Assessment Type</label>  
                        <div class="col-lg-3 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="zmdi zmdi-label"></i></span>
                                <select name="assessmentType" class="form-control" >
                                    <option value="CA1">CA1</option>
                                    <option value="SA1">SA1</option>
                                    <option value="CA2">CA2</option>
                                    <option value="SA2">SA2</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <h4>Student Lists</h4><br/>
            <%
                    for(String studentName: students){
                        out.println("<div class='form-group'><label class='col-lg-2 control-label'>"+studentName + "</label><div class='col-lg-3 inputGroupContainer'><div class='input-group'><span class='input-group-addon'><i class='zmdi zmdi-font'></i></span>");
                        request.setAttribute("studentName", studentName);
            %>    
            <input type ="number" name ="${studentName}" class="form-control"></div></div></div><br>            
            <%
                    }
            %>
                <input type="hidden" name="classID" value="${classID}">
                <br/>
                
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn-default" name="insert" name="insert">Add Grades</button>
                    </div>
                </div>
                <br/><br/>
           
            <%
                }
            %>            
        </form>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println(status);
            }
        %>
</div>
</div>
</div>
<%@include file="footer.jsp"%>