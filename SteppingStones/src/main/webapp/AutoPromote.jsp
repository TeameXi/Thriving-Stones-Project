<%@page import="entity.Student"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <%    
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
        }
        
        String status = (String) request.getAttribute("status");
        if (status != null) {
            out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
        }
        int branchID = user.getBranchId();
    %> 
    <div class="row" id="errorMessage"></div>
    <div style="text-align: center;margin: 20px;">Auto Promote </h5></div>
    <div class="row">
        <div class="col-md-3" style="width: 15%"></div>
        <div class="col-md-7" style="width: 65%">
            <form id="filterLevelForm" method="POST" class="form-horizontal" action="AutoPromoteServlet">
                <input type='hidden' id='branch' name='branch' value='<%=branchID%>'>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <%
                                LevelDAO lvlDao = new LevelDAO();
                                ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                            %>
                            <select id="lvlSelect" multiple="multiple" name="level" class="form-control">
                                <option value='0'>Select All</option>
                                <%  for (Level lvl : lvlLists) {
                                        out.println("<option value='" + lvl.getLevel_id() + "'>" + lvl.getLevelName() + "</option>");
                                    }
                                %>
                                
                            </select>
                        </div>
                    </div>
                </div>
                            
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">                        
                        <button type="submit" class="btn btn-default" name="search">Search</button>                        
                    </div>
                </div>
                
            </form><br><br>
            <%                
                ArrayList<Student> selectedStudents = new ArrayList<Student>();
                selectedStudents= (ArrayList<Student>)request.getAttribute("selectedStudents");
                if(selectedStudents != null && !selectedStudents.isEmpty()){
            %>  
            <form id="submitStudentForm" method="POST" class="form-horizontal" action="AutoPromoteServlet">
                                
                <table class="table table-bordered">
                    <thead class="table_title"><tr><th width="5%"><input type="checkbox" id="checkAllStu" name="checkAllStu" onclick="checkAll();"/></th><th width="20%">Name</th><th width="15%">Level</th><th width="27%">Email</th>
                            <%--<th width="33%">Action</th> --%></tr></thead>
                    <tbody>
                        <%
                            for (Student stu : selectedStudents) {
                                request.setAttribute("value", stu.getStudentID());
                        %>
                        <tr class="table_content">
                            <td><input type= "checkbox" name ="studentValue" id="checkAllStudent" value = "${value}"></td>
                            <%
                                out.println("<td>"+stu.getName()+"</td>");
                                out.println("<td>"+stu.getLevel()+"</td>");
                                out.println("<td>"+stu.getEmail()+"</td>");
                                %>
             <%--                 <td>
                                <a href="#viewParent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Parent</button></a>
                                <a href="#viewAttendance" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Attendance</button></a>
                                <a href="#viewGrade" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Grades</button></a>
                            </td>           --%>
                        <%
                                out.println("</tr>");
                                }
                            %>

                    </tbody>
                </table> 
                            
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">                        
                        <a href="#small" onclick="promoteAlert()" data-toggle="modal">
                            <button type="submit" class="btn btn-default" name="promote">Promote</button>
                        </a>
                    </div>
                </div>
            </form>    
                    
            <%  }else{
                    if (errorMsg == null) {
                        ArrayList<Student> allStudents = StudentDAO.listAllStudentsByBranch(1);
                        if(allStudents.size() > 0){
            %>
            <table id="stuTable" class="table table-bordered">
                <thead class="table_title"><tr><th width="20%">Name</th><th width="15%">Level</th><th width="30%">Email</th>
                        <%--<th width="35%">Action</th> --%></tr></thead>
                <tbody>
                    <%
                        for (Student stu : allStudents) {
                    %>
                    <tr class="table_content">
                        
                        <%
                            out.println("<td>"+stu.getName()+"</td>");
                            out.println("<td>"+stu.getLevel()+"</td>");
                            out.println("<td>"+stu.getEmail()+"</td>"); 
                        %>
                        <%--                  <td>
                                <a href="#viewParent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Parent</button></a>
                                <a href="#viewAttendance" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Attendance</button></a>
                                <a href="#viewGrade" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">
                                    <button type="button" class="btn btn-primary btn-xs">Grades</button></a>
                            </td>  --%>
                        <%
                            out.println("</tr>");
                            }
                        %>
                 
                </tbody>
            </table>
            <% }}} %> 
        </div>
    </div>
</div>
</div>
</div>
<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to promote these students?</div>
            <div class="modal-footer centered">
                <a id="confirm_btn"><button type="button" class="small_button pw_button del_button autowidth">Yes, Promote</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<%@include file="footer.jsp"%>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css" rel="stylesheet">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>

<script>

    $(function () {
        $('#lvlSelect').multiselect({
             buttonWidth: '200px'
        });
        
        $('#filterLevelForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                level: {
                    validators: {
                        notEmpty: {
                            message: 'Please select level'
                        }
                    }
                }
            }
        });
    });
    
    function checkAll() {
        //alert("Check all the checkboxes...");
        var checkAllStu = document.getElementById("checkAllStu");
        var allRows = document.getElementsByName("studentValue");
        if(checkAllStu.checked == true){
            for (var i=0; i < allRows.length; i++) {
                if (allRows[i].type == 'checkbox') 
                {
                    allRows[i].checked = true;
                }
            }
        }else{
            for (var i=0; i < allRows.length; i++) {
                if (allRows[i].type == 'checkbox') 
                {
                    allRows[i].checked = false;
                }
            }
        }

    }
    function promoteAlert() {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            showAlert();
        });
    }
    function showAlert() {
        $('#small').modal('hide');
        var selchbox =[];
        var check = document.getElementsByTagName("input");
        for (var i = 0; i < check.length; i++) {
            if (check[i].checked === true) {
                selchbox.push(check[i].value);
            } 
        }                      
        $.ajax({
            type: 'POST',
            url: 'PromoteStudentServlet',
            dataType: 'JSON',
            data: {selStuID: selchbox.toString()},
            success: function (data) {
                if (data === 1) {                    
                    html = '<div class="alert alert-success col-md-5"><strong>Success!</strong> Successfully Promoted.</div>';
                } else if (data === 2) {
                    html = '<div class="alert alert-danger col-md-5"><strong>Alert!</strong> Secondary 5 students cannot be promoted to next level</div>';
                }else{
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                }
                $("#errorMessage").html(html);
                $('#errorMessage').fadeIn().delay(2500).fadeOut();                
            }
        });
       
    }

</script>

<script>
    function updateStudentAccount() {
        
        branchID = $("#branch").val();
        multipleValues = $("#lvlSelect").val() || [];
        alert (multipleValues);

        $.ajax({
            type: 'POST',
            url: 'UpdateStudentServlet',
            dataType: 'JSON',
            data: {studentID: id, name: name, address: address, phone: phone, email: email},
            success: function (data) {
                if (data === 1) {
                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Updated successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#update_status").html(html);
                $('#update_status').fadeIn().delay(2000).fadeOut();
            }
        });
        return false;
    }

</script>






