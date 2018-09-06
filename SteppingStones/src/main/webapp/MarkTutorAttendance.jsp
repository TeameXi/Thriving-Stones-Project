<%-- 
    Document   : MarkTutorAttendance
    Created on : 5 Sep, 2018, 2:44:27 PM
    Author     : Zang Yu
--%>

<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="model.StudentClassDAO"%>
<%@page import="entity.Student"%>
<%@page import="entity.Lesson"%>
<%@page import="model.LessonDAO"%>
<%@page import="model.LessonDAO"%>
<%@page import="model.StudentGradeDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10"> 
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Tutor Attendance </span></h5></div>
    <div class="row">
        <div class="col-md-3"></div>        
        <div class="col-md-7">
            <form action="TutorAttendanceServlet" method="post">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Tutor</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                                <%  
                                    String redirectTutorName = "";
                                    if (request.getParameter("tutorName") != null) {
                                        redirectTutorName = request.getParameter("tutorName").trim();
                                    } 

                                %>
                            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
                            <input id="tutorName"  name="tutorName" placeholder="Name" class="form-control"  type="text" value="<%=redirectTutorName%>">
                        </div>
                    </div>
                </div>
                <br/><br/>

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn-default" name="search">Search For Tutor</button>
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
                    response.sendRedirect("MarkTutorAttendance.jsp");
                }
                
                ArrayList<Lesson> lessons = (ArrayList<Lesson>) request.getAttribute("lessons");
                String tutorName = (String) request.getAttribute("tutorName");
                if (lessons != null) {
                    request.setAttribute("tutorName", tutorName);
            %>
                          
            <form action="TutorAttendanceServlet" method="post">       
            <%
                if (lessons.size() > 0) {
                    out.println("<br><h4>Lessons:</h4><br>");
                    out.println("<table class='table table-bordered'>");
                    out.println("<thead class='table_title'><tr><th>Subject</th><th>Lesson Timing</th><th>Level</th><th>Present</th></tr></thead><tbody>");
                    for (Lesson lesson : lessons) {
                        request.setAttribute("value", lesson.getLessonid());
                        Class cls = ClassDAO.getClassByID(lesson.getClassid());
                        out.println("<tr class='table_content'><td>"+cls.getSubject()+"</td>");
                        out.println("<td>"+lesson.getLessonDateTime()+"</td>");
                        out.println("<td>"+cls.getLevel()+"</td>");
                        if (lesson.getTutorAttended()==0){
                            Timestamp lessonTiming =  lesson.getLessonDateTime();
                        
                            if( lessonTiming.before(new Date())){
                                out.println("<td>Absent</td>");
                            }else{
                        %>
                        <td><input type= "checkbox" name ="lessonValue" value = "${value}">
                                        <input type="hidden" name="tutorName" value="${tutorName}"></td>
                        <%
                            }
                        }else if (lesson.getTutorAttended()==1){
                            out.println("<td>Present</td>");
                        }  
                        out.println("</tr>");
                    }
                    out.println("</tbody></table>");
                }
               
            %> 
                    
                    <br/>
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-default" name="select" value="select">Save</button>
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

<%@include file="footer.jsp"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>


