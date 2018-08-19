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
    %> 
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateSubject.jsp">Add Subject </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createSubjectForm" method="POST" class="form-horizontal" action="CreateSubjectServlet">
                <input type='hidden' name='branch' value='<%=branch_id%>'>
                <div class="form-group">
                    <label class="col-lg-2 control-label">Subject Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-book"></i></span>
                            <input id="subjectName"  name="subjectName" placeholder="Subject Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div> 

                <div class="form-group">
                    <label class="col-lg-2 control-label">Academic Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <%
                                LevelDAO lvlDao = new LevelDAO();
                                ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                            %>
                            <select id="lvlSelect" multiple="multiple" name="level" class="form-control" >
               
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
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css" rel="stylesheet">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>

<script>

    $(function () {
        $('#lvlSelect').multiselect({
             buttonWidth: '200px'
        });
        
        $('#createSubjectForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
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


