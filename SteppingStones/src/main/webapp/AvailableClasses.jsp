<%-- 
    Document   : AvailableClasses
    Created on : 2 Jan, 2019, 12:46:51 AM
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
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Classes Info</span></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
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
                        out.println("<div id='status' class='alert alert-success col-md-12'><strong>" + status + "</strong></div>");
                    }

                    String errorMsg = (String) request.getParameter("errorMsg");
                    if (errorMsg != null) {
                        out.println("<div id='status' class='alert alert-danger col-md-12'><strong>" + errorMsg + "</strong></div>");
                    }
                %>
                <div class="statusMsg"></div>
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
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>

<!--Edit Function -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>

<script>
                                $(function () {
                                    if ($('#status').length) {
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

    $(document).ready(function () {
        $('#BulkRegistration').dataTable({
            "paging": true,
            "info": false,
            "searching": false
        });
    });

    function retrieveClasses(selectObject) {
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

                if (data.length !== 0) {
                    var html = '<div class="form-group"><label class="col-lg-2 control-label">Class</label><div class="col-lg-7 inputGroupContainer"><div class="input-group">\n\
                                    <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>\n\
                                    <select name="classID" class="form-control" onchange="retrieveStudents(this)" id="classID">\n\
                                    <option value="">Select Class</option><div id="ClassOptions"></div>';
                    for (i = 0; i < data.length; i++) {
                        html += "<option value='" + data[i].class + "'>" + data[i].name + "</option>";
                    }
                    html += '</select></div></div></div>';

                    ClassOptions.innerHTML = html;
                } else {
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

    function retrieveStudents(selectObject) {
        classID = $("#classID").val();
        levelID = $("#levelID").val();

        $.ajax({
            type: 'POST',
            url: 'RetrieveEnrolledStudentByLevelServlet',
            dataType: 'JSON',
            data: {classID: classID, levelID: levelID},
            success: function (data) {
                var studentTable = document.getElementById('studentTable');
                //console.log(data);
                if (data.length !== 0) {
                    var html = '<br><h4>Enrolled Students List</h4><br>';
                    html += '<table id="BulkRegistration" class="table display responsive nowrap" style="width:90%"><thead class="thead-light"><tr>\n\
                            <th scope="col"></th>\n\
                            <th scope="col">Student Name</th><th scope="col">Tuition Fees</th><th scope="col"></th></tr></thead><tbody>'; 
                    
                    var i;
                    for (i = 0; i < data.length; i++) {
                        html += '<tr><td>' +  (i+1) + '</td><td>' + data[i].name + '</td>\n\
                                <td><input type ="number" name ='+ data[i].student+"classFees"+' id='+ "classFees"+ data[i].student +' value=' + data[i].classFees +' class="form-control"></td>\n\
                                <td><a data-toggle="modal" class="btn btn1 btn-sm" onclick="updateTuitionRate('+"'"+data[i].student+"'"+')" >Update</a></td>\n\
                                <input type="hidden" name="studentName" value="${classID}"></tr>';
                    }
                    html += '</tbody></table><br/>';
                    studentTable.innerHTML = html;
                } else {
                    studentTable.innerHTML = '<h4>No students enrolled in this class yet!</h4>';
                }
            }
        });
    }

    function updateTuitionRate(studentID) {
        classID = $("#classID").val();
        fees = $('#classFees' + studentID).val();
        console.log("classID: " + classID + "studentID " + studentID + "classFees " + fees);
        $.ajax({    
            type:'POST',
            url: 'UpdateTuitionFeesServlet',
            data:{ classID: classID, fees: fees,studentID: studentID, action:"update"},
            dataType: "json",
            success: function(response) {
                if(response === 1){
                    html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Update successfully</div>';
                }else{
                    html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                }
                $(".statusMsg").html(html);
                $('.statusMsg').fadeIn().delay(1000).fadeOut();
            }
        });
        
    }
    
//    $('.edit_tuitionFees').editable({
//            url: function(params) {
//            //console.log(params);
//                $.ajax({    
//                    type:'POST',
//                    url: 'UpdateTuitionFeesServlet',
//                    data:{ id:params["pk"],action:"edit",
//                            payRate:params["value"]},
//                    dataType: "json",
//                    success: function(response) {
//                        //console.log(response);
//                        if(response === 1){
//                            html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Updated successfully</div>';
//                        }else{
//                            html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
//                        }
//                        $(".statusMsg").html(html);
//                        $('.statusMsg').fadeIn().delay(1000).fadeOut();
//                    }
//                });
//            },
//            send: 'always',
//            type: 'number',
//            step:'any',
//            pk: 1
//    });


</script>

