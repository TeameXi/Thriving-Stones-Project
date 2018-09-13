<%@page import="entity.Tutor"%>
<%@page import="model.TutorDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="entity.Class"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div class="row" id="errorMsg"></div>
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Attendance Taking </span></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="tutorAttendanceForm" method="POST" class="form-horizontal" action="TutorAttendanceServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Tutor</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="tutorID" class="form-control" onchange="retrieveTutorLessons()" id="tutorID">
                                <option value="">Select Tutor</option>
                                <%                                    
                                    TutorDAO tutors = new TutorDAO();
                                    ArrayList<Tutor> tutorList = tutors.retrieveAllTutorsByBranch(branch_id);

                                    for (Tutor t : tutorList) {
                                        out.println("<option value='" + t.getTutorId() + "'>" + t.getName() + "</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
            <div id="lessons">
            </div>
        </div>
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
    $(function () {
        $('#studentAttendanceForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                tutorID: {
                    validators: {
                        notEmpty: {
                            message: 'Please select a class'
                        }
                    }
                }
            }
        });
    });
</script>

<script>
    function markAttendance(selectObject){
        lessonID = selectObject.value;
        action = 'update';
        
        $.ajax({
            type: 'POST',
            url: 'TutorAttendanceServlet',
            dataType: 'JSON',
            data: {lessonID: lessonID, action: action},
            success: function (data) {
                if(data.length !== 0){
                    if(data.status){
                        html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Attendance recorded successfully!</div>';
                    }else{
                        html = '<div class="alert alert-success col-md-12"><strong>Failure!</strong> Fail to record attendance!</div>';
                    }
                    $("#errorMsg").html(html);
                    $('#errorMsg').fadeIn().delay(1000).fadeOut();
                }
            }
        });
    }
    
    function retrieveTutorLessons(){
        tutorID = $("#tutorID").val();
        action = 'retrieve';
        
        $.ajax({
            type: 'POST',
            url: 'TutorAttendanceServlet',
            dataType: 'JSON',
            data: {tutorID: tutorID, action: action},
            success: function (data) {
                var lessons = document.getElementById('lessons');
                if(data.length !== 0){
                    var html = '<form><table class="table table-bordered"><thead class="table_title"><tr><th>Subject</th><th>Lesson Timing</th><th>Level</th><th>Present</th></tr><tbody>';

                    var i;
                    for(i = 0; i < data.length; i++){
                        html += '<tr class="table_content"><td>' + data[i].subject + '</td><td>' + data[i].date + '</td><td>' + data[i].level + '</td><td><input type="checkbox" onchange="markAttendance(this)" name="studentID" value=' + data[i].lessonID + '></td></tr>';
                    }

                    html +='</tbody></table><br/></form><br>';
                    lessons.innerHTML = html;
                }else{
                    studentTable.innerHTML = '<h4>No lessons available!</h4>';
                }
            }
        });
    }
</script>
