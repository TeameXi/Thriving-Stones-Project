<%-- 
    Document   : Bulk Class Registration
    Created on : 10 Sep, 2018, 11:21:00 AM
    Author     : DEYU
--%>

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
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Bulk Class Registration</span></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="bulkRegistrationForm" method="POST" class="form-horizontal" action="BulkClassRegistrationServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Class</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="classID" class="form-control" onchange="retrieveStudents(this)" id="classID">
                                <option value="">Select Class</option>
                                <%                                    
                                    ClassDAO classes = new ClassDAO();
                                    ArrayList<Class> classList = classes.listAllClasses(branch_id);

                                    for (Class c : classList) {
                                        out.println("<option value='" + c.getClassID() + "'>" + c.getLevel() + " " + c.getSubject() + " (" + c.getClassDay() + " " + c.getClassTime() + ")</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>
                <%
                String status = (String) request.getParameter("status");
                if (status != null) {
                    out.println("<div id='status' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
                }
                %>

            <div id="studentTable"></div>  
            </form>
            
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
        if($('#status').length){
           $('#status').fadeIn().delay(2000).fadeOut();
        }
        
        $('#bulkRegistrationForm').bootstrapValidator({
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
                }
            }
        });
    });
</script>

<script>    
    function retrieveStudents(selectObject){
        lessonID = selectObject.value;
        classID = $("#classID").val();
        
        $.ajax({
            type: 'POST',
            url: 'RetrieveStudentByLevelServlet',
            dataType: 'JSON',
            data: {classID: classID},
            success: function (data) {    
                var studentTable = document.getElementById('studentTable');
                if(data.length !== 0){
                    var html = '<br><h4>Tick students to enroll</h4><br>';
                    
                    html += '<table class="table table-bordered"><thead class="table_title"><tr><th>Ticker</th><th>Student Name</th><th>Outstanding Deposit</th><th>Outstanding Tuition Fees</th></tr><tbody>';
                    var i;
                    for(i = 0; i < data.length; i++){
                        html += '<tr class="table_content"><td><input type="checkbox" onchange="markAttendance(this)" name="studentID" value=' + data[i].student + '></td><td>' + data[i].name + '</td>\n\
                                    <td><input type ="number" name ='+ data[i].student + "deposit" +' class="form-control"></td>\n\
                                    <td><input type ="number" name ='+ data[i].student + "tuitionFees" +' class="form-control"></td>\n\
                                    <td><input type="hidden" name="studentName" value="${classID}"></td></tr>';
                    }
                    html += '</tbody></table><br/>';
                    html += "<div class='form-group'><div class='col-lg-2 col-lg-offset-2'><button type='submit' class='btn btn-default' name='enroll'>Register Student</button></div></div>"
                    studentTable.innerHTML = html;
                }else{
                    studentTable.innerHTML = '<h4>No students available!</h4>';
                }
            }
        });
    }

</script>
