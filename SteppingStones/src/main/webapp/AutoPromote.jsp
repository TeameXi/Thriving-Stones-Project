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
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="filterLevelForm" method="POST" class="form-horizontal" action="AutoPromoteServlet">
                <input type='hidden' name='branch' value='<%=branchID%>'>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <select id="lvlSelect" multiple="multiple" name="level" class="form-control">
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
                    <thead class="table_title"><tr><th></th><th>Name</th><th>Level</th><th>Email</th><th>Action</th></tr></thead>
                    <tbody>
                        <%
                            for (Student stu : selectedStudents) {
                                request.setAttribute("value", stu.getStudentID());
                        %>
                        <tr class="table_content">
                            <td><input type= "checkbox" name ="studentValue" value = "${value}"></td>
                            <%
                                out.println("<td>"+stu.getName()+"</td>");
                                out.println("<td>"+stu.getLevel()+"</td>");
                                out.println("<td>"+stu.getEmail()+"</td>");
                                %>
                            <td><button type="button" class="btn btn-primary btn-xs">Parent</button>
                            <button type="button" class="btn btn-primary btn-xs">Attendance</button>
                            <button type="button" class="btn btn-primary btn-xs">Grades</button>
                            </td>
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
                
                ArrayList<Student> allStudents = StudentDAO.listAllStudentsByBranch(1);
                if(allStudents.size() > 0){
            %>
            <table class="table table-bordered">
                <thead class="table_title"><tr><th>Name</th><th>Level</th><th>Email</th><th>Action</th></tr></thead>
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
                            <td><button type="button" class="btn btn-primary btn-xs">Parent</button>
                            <button type="button" class="btn btn-primary btn-xs">Attendance</button>
                            <button type="button" class="btn btn-primary btn-xs">Grades</button>
                            </td>
                        <%
                            out.println("</tr>");
                            }
                        %>
                 
                </tbody>
            </table>
            <% }} %> 
        </div>
    </div>
</div>
</div>
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
</script>






