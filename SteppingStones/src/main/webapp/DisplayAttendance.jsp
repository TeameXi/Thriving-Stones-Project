<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="entity.Class"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="MarkAttendance.jsp">Mark Attendance </a> / <span class="tab_active">View Attendance</span></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="studentAttendanceForm" method="POST" class="form-horizontal" action="MarkStudentAttendanceServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Class</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="classID" class="form-control" onchange="retrieveLessons(this)" id="classID">
                                <option value="">Select Class</option>
                                <%                                    ClassDAO classes = new ClassDAO();
                                    ArrayList<Class> classList = classes.listAllClassesByTutorID(user.getRespectiveID(), branch_id);

                                    for (Class c : classList) {
                                        out.println("<option value='" + c.getClassID() + "'>" + c.getSubject() + " (" + c.getClassDay() + " " + c.getClassTime() + ")</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Lesson</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="lesson" class="form-control" id="lesson" onchange="retrieveStudents(this)">
                                <option value="" >Select Date</option>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
            <div id="attendanceInformation">
            </div>
            <div id="studentTable">
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
                classID: {
                    validators: {
                        notEmpty: {
                            message: 'Please select a class'
                        }
                    }
                },
                lesson: {
                    validators: {
                        notEmpty: {
                            message: 'Please select a lesson'
                        }
                    }
                }
            }
        });
    });
</script>

<script>
    function retrieveStudents(selectObject){
        lessonID = $("#lesson option:selected").val();
        classID = $("#classID").val();
        retrieveAttendance(lessonID, classID);
    }
    
    function retrieveAttendance(lessonID, classID){
        $.ajax({
            type: 'POST',
            url: 'RetrieveStudentAttendanceServlet',
            dataType: 'JSON',
            data: {classID: classID, lessonID: lessonID},
            success: function (data) {
                var attendanceInfo = document.getElementById('attendanceInformation');
                attendanceInfo.innerHTML = '<br>Class: <label>' + $("#classID option:selected").text() + '</label><br/> Lesson Date: <label>' + $("#lesson option:selected").text() + '</label><br><br><h4>Student List:</h4><br>';
                
                var studentTable = document.getElementById('studentTable');
                if(data.length !== 0){
                    var html = '<form><table class="table table-bordered"><thead class="table_title"><tr><th>Student Name</th><th>Present/Absent</th></tr><tbody>';

                    var i;
                    for(i = 0; i < data.length; i++){
                        html += '<tr class="table_content"><td>' + data[i].student + '</td>';
                        
                        if(data[i].status){
                            html +='<td>Present</td></tr>';
                        }else{
                            html +='<td>Absent</td></tr>';
                        }
                    }

                    html +='</tbody></table><br/></form><br>';
                    studentTable.innerHTML = html;
                }else{
                    studentTable.innerHTML = '<h4>No students available!</h4>';
                }
            }
        });
    }
    
    function retrieveLessons(selectObject) {
        classID = selectObject.value;
  
        $.ajax({
            type: 'POST',
            url: 'RetrieveLessonsServlet',
            dataType: 'JSON',
            data: {classID: classID},
            success: function (data) {
                var index;
                for (index = 0; index < data.length; ++index) {
                    $("#lesson").append('<option value=' + data[index].lesson + ">" + data[index].date + '</option>');
                }
            }
        });
    }
</script>
