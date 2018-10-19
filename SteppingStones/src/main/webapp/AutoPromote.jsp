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
    <div style="text-align: center;margin: 20px;">Auto Promote </h5></div>
    <div class="row">
        <div class="col-md-3" style="width: 15%"></div>
        <div class="col-md-7" style="width: 65%">
            <form id="filterLevelForm" method="POST" class="form-horizontal" action="AutoPromoteServlet">
                <input type='hidden' name='branch' value='<%=branchID%>'>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <select id="lvlSelect" multiple="multiple" name="level" class="form-control">
                                <option value='0'>Select All</option>
                                <option value='1'>Primary 1</option>
                                <option value='2'>Primary 2</option>
                                <option value='3'>Primary 3</option>
                                <option value='4'>Primary 4</option>
                                <option value='5'>Primary 5</option>
                                <option value='6'>Primary 6</option>
                                <option value='7'>Secondary 1</option>
                                <option value='8'>Secondary 2</option>
                                <option value='9'>Secondary 3</option>
                                <option value='10'>Secondary 4</option>
                                
                            </select>
                        </div>
                    </div>
                </div>
                            
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
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
                        <button type="submit" class="btn btn-default" name="promote">Promote</button>
                    </div>
                </div>
            </form>    
                    
            <%  }else{
                    if (errorMsg == null) {
                        ArrayList<Student> allStudents = StudentDAO.listAllStudentsByBranch(1);
                        if(allStudents.size() > 0){
            %>
            <table class="table table-bordered">
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
<!-- Detail Dialog -->
<div class="modal fade" id="viewParent" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Parent Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Fullname :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_parent_name">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Nationality :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_parent_nationality"></label></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Company :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_company"></label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Designation :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_designation">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Mobile :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_phone">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Email :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_email">-</label></p>
                    </div>
                </div><br/>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>       
    </div>
</div>
<!-- End of Detail Dialog -->

<!-- Detail Dialog -->
<div class="modal fade" id="viewAttendance" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Attendance Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table>
                    <tr>
                        <th>Lesson Date</th><th>Present/Absent</th>
                    </tr>
                    
                </table>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>       
    </div>
</div>
<!-- End of Detail Dialog -->

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
</script>

<script>
    $(document).ready(function () {
        $("#viewParent").on("show.bs.modal", function (e) { 
            var student_id = $(e.relatedTarget).data('target-id');
            var branch_id = $("#branch").val();
            $.ajax({
                url: 'RetrieveParentServlet',
                dataType: 'JSON',
                data: {studentID: student_id, branch_id: branch_id},
                success: function (data) {
                    if(data["fullname"] != ""){
                        $("#view_parent_name").text(data["fullname"]);
                    }
                    if (data["nationality"] !== "") {
                        $("#view_parent_nationality").text(data["nationality"]);
                    }
                    if (data["company"] !== "") {
                        $("#view_company").val(data["company"]);
                    }
                    if (data["designation"] !== "") {
                        $("#view_designation").text(data["designation"]);
                    }
                    if (data["phone"] !== "") {
                        $("#view_phone").text(data["phone"]);
                    }
                    if (data["email"] !== "") {
                        $("#view_email").text(data["email"]);
                    }
                }
            });
        });
    });
    
    </script>






