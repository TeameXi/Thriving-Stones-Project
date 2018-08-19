<%@page import="model.StudentGradeDAO"%>
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
                    out.println("<thead class='table_title'><tr><th>Term</th><th>Level</th><th>Subject</th><th>Class Timing</th><th></th></tr></thead><tbody>");
                    for(Class cls: classes){
                        
                        out.println("<tr><td>"+cls.getTerm() + "</td><td>"+cls.getLevel() + "</td><td>" + cls.getSubject() + "</td><td>" + cls.getClassTime()+" ( "+cls.getClassDay()+" )");
                        request.setAttribute("ClassID", cls.getClassID());
            %>           
                        <td><button type="submit" value="${ClassID}" name="select" class="btn btn-default">Add Grades</button> 
                            <button type="submit" value="${ClassID}" name="edit" class="btn btn-default">Update Grades</button></td></tr>                           
            <%
                    }
                    out.println("</tbody></table>");
                }
            %>  
        </form>
        
        <form id="createTuitionGradeForm" action="CreateTuitionGradeServlet" method="post">
    
            <%
                ArrayList<String> students = (ArrayList<String>)request.getAttribute("students");
                Class cls = (Class)request.getAttribute("class");              
                if(cls != null){
                    request.setAttribute("classID", cls.getClassID());
                    request.setAttribute("sub", cls.getSubject());
                    out.println("<h4><br><br>Students From Class '<strong>Term " + cls.getTerm() + " - " + cls.getLevel() + " " + cls.getSubject() + " "+ cls.getClassTime()+" ("+cls.getClassDay() +")" + "'</strong></h4></br>");
                }
                if(students != null && students.size() > 0){
                    if(cls.getTerm() == 1){
                        out.println("<input type='hidden' name='assessmentType' value='CA1'/>");
                    }else if(cls.getTerm() == 2){
                        out.println("<input type='hidden' name='assessmentType' value='SA1'/>");
                    }else if(cls.getTerm() == 3){
                        out.println("<input type='hidden' name='assessmentType' value='CA2'/>");
                    }else if(cls.getTerm() == 4){
                        out.println("<input type='hidden' name='assessmentType' value='SA2'/>");
                    }

                    for(String studentName: students){
                        out.println("<div class='form-group'><label class='col-lg-2 control-label'>"+studentName + "</label><div class='col-lg-3 inputGroupContainer'><div class='input-group'><span class='input-group-addon'><i class='zmdi zmdi-font'></i></span>");
                        request.setAttribute("studentName", studentName);
            %>    
            <input type ="number" name ="grade[]" class="form-control">
            </div></div></div><br><br><br>    
            <%
                    }
            %>
                <input type="hidden" name="classID" value="${classID}">
                <br/>
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn-default" name="insert">Add Grades</button>
                    </div>
                </div>
                <br/><br/>
           
            <%
                }
            %>            
        </form>
        
        <form id="editTuitionGradeForm" action="CreateTuitionGradeServlet" method="post">
    
            <%
                ArrayList<String> editStudents = (ArrayList<String>)request.getAttribute("EditStudents");
                Class editCls = (Class)request.getAttribute("EditClass");    
                if(editCls != null){
                    request.setAttribute("classID", editCls.getClassID());
                    request.setAttribute("sub", editCls.getSubject());
                    out.println("<h4><br><br>Students From Class '<strong>Term " + editCls.getTerm() + " - " + editCls.getLevel() + " " + editCls.getSubject() + " "+ editCls.getClassTime()+" ("+editCls.getClassDay() +")" + "'</strong></h4></br>");
                }
                if(editStudents != null && editStudents.size() > 0){
                    if(editCls.getTerm() == 1){
                        out.println("<input type='hidden' name='assessmentType' value='CA1'/>");
                    }else if(editCls.getTerm() == 2){
                        out.println("<input type='hidden' name='assessmentType' value='SA1'/>");
                    }else if(editCls.getTerm() == 3){
                        out.println("<input type='hidden' name='assessmentType' value='CA2'/>");
                    }else if(editCls.getTerm() == 4){
                        out.println("<input type='hidden' name='assessmentType' value='SA2'/>");
                    }

                    for(String studentName: editStudents){
                        out.println("<div class='form-group'><label class='col-lg-2 control-label'>"+studentName + "</label><div class='col-lg-3 inputGroupContainer'><div class='input-group'><span class='input-group-addon'><i class='zmdi zmdi-font'></i></span>");
                        request.setAttribute("studentName", studentName);
            %>    
            <input type ="number" name ="grade[]" value="<%=StudentGradeDAO.retrieveStudentGrade(studentName)%>" class="form-control">
            </div></div></div><br><br><br>    
            <%
                    }
            %>
                <input type="hidden" name="classID" value="${classID}">
                <br/>
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn-default" name="update">Update Grades</button>
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
                out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
            }
            
            String errorMsg = (String) request.getAttribute("errorMsg");
            if (errorMsg != null) {
                out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
            }
        %>
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
$(function () { 
    if ($('#errorMsg').length) {
        $('#errorMsg').fadeIn().delay(2000).fadeOut();
    }
    $('#createTuitionGradeForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'grade[]': {
                validators: {
                    between: {
                        min: 0,
                        max: 100,
                        message: 'The grade must be between 0 and 100'
                    }
                }
            }
        }
    });
    $('#editTuitionGradeForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'grade[]': {
                validators: {
                    between: {
                        min: 0,
                        max: 100,
                        message: 'The grade must be between 0 and 100'
                    }
                }
            }
        }
    });
    
});
</script>
