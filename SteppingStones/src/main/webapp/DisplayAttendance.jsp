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
<%@include file="protect_tutor.jsp"%>
<%@include file="header.jsp"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
  
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="MarkAttendance.jsp">Mark Attendance</a> / <span class="tab_active">View Attendance </span></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">            
            <form id="classAttendanceForm" method="POST" class="form-horizontal" action="ClassAttendanceServlet">
               <%
                    String classStr = (String) request.getParameter("classID");
                    String lessonStr = (String) request.getParameter("lessonID");
                    int classInt = 0;
                %>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Class</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="class_id" class="form-control" onchange="updateClass(this)">
                                <%                             
                                    ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(user_id, branch_id);                                                                                                          
                                    if (classStr == null) {
                                %>
                                        <option value="" >Select Class</option>
                                <%
                                    }else{
                                        classInt = Integer.parseInt(classStr);
                                        Class selectedClass = ClassDAO.getClassByID(classInt);                                        
                                        out.println("<option value='"+selectedClass.getSubjectID()+"'>"+selectedClass.getSubject()+" ("+selectedClass.getClassDay()+" "+selectedClass.getClassTime()+")</option>");                                        
                                    }
                                    for(Class cls: classes){                                        
                                            out.println("<option value='"+cls.getClassID()+"'>"+cls.getSubject()+" ("+cls.getClassDay()+" "+cls.getClassTime()+")</option>");                                            
                                       }
                                    %>
                            </select>                            
                        </div>
                    </div>
                </div>
                           
                <div class="form-group">
                    <label class="col-lg-2 control-label">Lesson Date</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="lesson_id" class="form-control" onchange="updateLesson(this)">
                                <option value="" >Select Lesson</option>
                                 <%                                 
                                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonListsByTutor(classInt,user_id);
                                for(Lesson les : lessons){ 
                                    out.println("<option value='"+les.getLessonid()+"'>"+les.getLessonDateTime().toString().split(" ")[0]+"</option>"); 
                                }
                                %>
                                
                            </select>                            
                        </div>
                    </div>
                </div>
            </form>
            <br><br>
            <%  
            if(lessonStr!=null){
                Lesson selectedLesson = LessonDAO.getLessonByID(Integer.parseInt(lessonStr)); 
                int retrievedClassId = selectedLesson.getClassid();
                ArrayList<Student> studentList = StudentClassDAO.listAllStudentByClass(retrievedClassId);
                Class cls = ClassDAO.getClassByID(retrievedClassId);
                if (studentList!=null && studentList.size() > 0) {
            %>             
              Class: <label> <%out.println(cls.getSubject()+" ("+cls.getClassDay()+" "+cls.getClassTime()+")");%></label><br>
              Lesson Date: <label> <%out.println(selectedLesson.getLessonDateTime().toString().split(" ")[0]);%></label><br>
            <br><h4>Student List:</h4> <br>                
            <form action="ClassAttendanceServlet" method="post">                
            <%
                out.println("<table class='table table-bordered'>");
                out.println("<thead class='table_title'><tr><th>Student Name</th><th>Present</th></tr></thead><tbody>");
                
                

                for (Student stu : studentList) {
                    request.setAttribute("value", stu.getStudentID());
            %>


                <tr class="table_content">
            <%  out.println("<td>"+stu.getName()+"</td>"); %>
                    <td>Present/Absent</td>    
                    
            <%  out.println("</tr>");   

                }
                out.println("</tbody></table>");
            %>
                    
                    </form>
            <%
                    }
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

<script>
function updateClass(classId) {                       
                location.href = "DisplayAttendance.jsp?classID=" + classId.value;
            }
            
function updateLesson(lessonId) {                      
                location.href = "DisplayAttendance.jsp?lessonID=" + lessonId.value;
            }
</script>