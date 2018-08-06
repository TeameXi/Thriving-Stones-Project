<%-- 
    Document   : CreateSubject
    Created on : Jul 29, 2018, 2:32:46 PM
    Author     : MOH MOH SAN
--%>

<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<style>
    #generate_btn{
        padding: 5px;
        margin-left : 50px;
        background-color:#f7a4a3;
        color:#fff;
        border-radius: 5px;
    }

    #generate_btn:hover{
        background:transparent;
        border: 1px solid #f7a4a3;
        color:#f7a4a3;
    }


</style>


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
    %> 
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateSubject.jsp">Add Subject </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createSubjectForm" method="POST" class="form-horizontal" action="CreateSubjectServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Academic Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="level" class="form-control" >
                                <%
                                    LevelDAO lvlDao = new LevelDAO();
                                    ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                                %>
                                <option value="" >Select Level</option>
                                <%  for (Level lvl : lvlLists) {
                                        out.println("<option value='" + lvl.getLevel_id() + "'>" + lvl.getLevelName() + "</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Subject Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-book"></i></span>
                            <input id="subjectName"  name="subjectName" placeholder="Subject Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div> 
                
                <%  
                    BranchDAO branchDao = new BranchDAO();
                    if(true) {
                        List<Branch> branch_lists = branchDao.retrieveBranches();          
                %>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">Branch</label>  
                                <div class="col-lg-7 inputGroupContainer">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                                        <select name="branch" class="form-control" >
                                            <option value="" >Select Center</option>
                                            <% for(Branch branch: branch_lists){
                                                    out.println("<option value='"+branch.getBranchId()+"'>"+branch.getName()+"</option>");
                                               }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        
                <% 
                    }else{
                        Branch branch = branchDao.retrieveBranchById(2);
                        String branch_name = branch.getName();
                %>
                        <div class="form-group">
                                <label class="col-lg-2 control-label">Branch</label>  
                                <div class="col-lg-7 inputGroupContainer">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                                        <label class='form-control'><%=branch_name%></label>
                                    </div>
                                </div>
                            </div>
                <%        
                    }
                %>
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Create Subject</button>
                    </div>
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
        $('#createSubjectForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                subjectName: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter subject name'
                        }
                    }
                },
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


