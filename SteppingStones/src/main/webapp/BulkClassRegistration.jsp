<%-- 
    Document   : Bulk Class Registration
    Created on : 10 Sep, 2018, 11:21:00 AM
    Author     : DEYU
--%>

<%@page import="model.LevelDAO"%>
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
                    <label class="col-lg-2 control-label">Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="levelID" class="form-control" onchange="retrieveClasses(this)" id="levelID">
                                <option value="">Select Level</option>
                                <%                                    
                                    LevelDAO levels = new LevelDAO();
                                    ArrayList<String> levelList = levels.retrieveAllLevels();

                                    for (String level : levelList) {
                                        out.println("<option value='" + LevelDAO.retrieveLevelID(level) + "'>" + level + "</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="branchID" value="<%=branch_id%>" id = "branchID">
                <div id="ClassOptions"></div>               
                            
                <%
                String status = (String) request.getParameter("status");
                if (status != null) {
                    out.println("<div id='status' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
                }
                
                String errorMsg = (String) request.getParameter("errorMsg");
                if (errorMsg != null) {
                    out.println("<div id='status' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
                }
                %>
            <div class="table-responsive-sm">
            <div id="studentTable"></div>
            
            </div>
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
                },
                levelID: {
                    validators: {
                        notEmpty: {
                            message: 'Please select level'
                        }
                    }
                }
            }
        });
    });
</script>

<script>   
    (document).ready(function () {
        $('#BulkRegistration').dataTable( {
            "paging":   true,
            "info":     false,
            "searching": false
        });
    });
    
    function retrieveClasses(selectObject){
        levelID = $("#levelID").val();
        branchID = $("#branchID").val();

        studentTable.innerHTML = "";
        
        $.ajax({
            type: 'POST',
            url: 'RetrieveClassesByLevelServlet',
            dataType: 'JSON',
            data: {levelID: levelID, branchID: branchID},
            success: function (data) {  
                var ClassOptions = document.getElementById('ClassOptions');
                
                if(data.length !== 0){
                    var html = '<div class="form-group"><label class="col-lg-2 control-label">Class</label><div class="col-lg-7 inputGroupContainer"><div class="input-group">\n\
                                    <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>\n\
                                    <select name="classID" class="form-control" onchange="retrieveStudents(this)" id="classID">\n\
                                    <option value="">Select Class</option><div id="ClassOptions"></div>';
                    for(i = 0; i < data.length; i++){
                        html += "<option value='" + data[i].class + "'>" + data[i].name + "</option>";
                    }  
                    html += '</select></div></div></div>';                        

                    ClassOptions.innerHTML = html;
                }else{
                    var html = '<div class="form-group"><label class="col-lg-2 control-label">Class</label><div class="col-lg-7 inputGroupContainer"><div class="input-group">\n\
                                    <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>\n\
                                    <select name="classID" class="form-control" id="classID">\n\
                                    <option value="">No classes at this level</option><div id="ClassOptions"></div>';
                    html += '</select></div></div></div>';
                    ClassOptions.innerHTML = html;
                }
            }
        });
    }
    
    function retrieveStudents(selectObject){
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

                    html += '<table id="BulkRegistration" class="table display responsive nowrap" style="width:70%"><thead class="thead-light"><tr><th scope="col"></th><th scope="col">Student Name</th><th scope="col">Outstanding Tuition Fees</th></tr></thead><tbody>';
                    var i;
                    for(i = 0; i < data.length; i++){
                        html += '<tr><td><input type="checkbox" onchange="markAttendance(this)" name="studentID" value=' + data[i].student + '></td><td>' + data[i].name + '</td>\n\
                                    <td><input type ="number" name ='+ data[i].student + "tuitionFees" +' class="form-control"></td>\n\
                                    <td><input type="hidden" name="studentName" value="${classID}"></td></tr>';
                    }
                    html += '</tbody></table><br/>';
                    html += "<div class='form-group'><div class='col-lg-2 col-lg-offset-2'><button type='submit' class='btn btn-default' name='enroll'>Register Student</button></div></div>"
                    studentTable.innerHTML = html;
                }else{
                    studentTable.innerHTML = '<h4>No students available to enroll!</h4>';
                }
            }
        });
    }
    
</script>

