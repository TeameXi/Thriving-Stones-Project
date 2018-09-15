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
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Subject </span></h5></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-9">
            <form id="createSubjectForm" method="POST" class="form-horizontal" action="CreateSubjectServlet">
                <input type='hidden' id="branchInput" name='branch' value='<%=branch_id%>'>
                <div class="form-group">
                    <label class="col-lg-2 control-label">Subject Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-book"></i></span>
                            <input id="subjectName"  name="subjectName" placeholder="Subject Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div> 

                <div class="form-group" id='multiselectLevelContainer' style="display:none;">
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
                          
                <div class="costContainer" style='display:none;'>
                    <br/>
                    <h4>Cost Registration For Subject</h4><br/>
                    <table class='table'><thead class="thead-light"><th>New Subject</th><th>Offered Level</th><th>Monthly Course Fee</th></thead><tbody></tbody>
                    </table>
                    
                    <div class="form-group">
                        <div class="col-lg-2">
                            <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                            <button class="btn btn-default"  type="submit">Create Subject</button>
<!--                            <button class="btn btn-default"  onclick="return createSubjectWithCost()">Create Subject</button>-->
                        </div>
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
    
    function createSubjectWithCost(){
        subject = $("#subjectName").val();
        branch_id = $("#branchInput").val();
        levelList = $('input[name="level_id[]"]').map(function () {
                        return $(this).val();
                    }).get();
                    
        costList = $('input[name="subject_cost[]"]').map(function () {
                    return $(this).val();
                }).get();
        
        
                            
        $.ajax({
            url: 'CreateSubjectServlet',
            data: {},
            dataType: "json",
            success: function (data) {
                if(data === 1){
                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update grades successfully</div>';
                }else{
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                }
                $(".statusMsg").html(html);
                $('.statusMsg').fadeIn().delay(1000).fadeOut();
            }
        });

        return false;
    }

    $(function () {
        var subjectVal = "";
        $( "#subjectName" ).change(function() {
            subjectVal = $("#subjectName" ).val();
            $("#multiselectLevelContainer").css("display","block");
            $(".sub_name").html(subjectVal);
          
        });
        
        $('#lvlSelect').multiselect({
            buttonWidth: '200px',
            onChange: function(option, checked, select) {
                var opselected = $(option).val();
                
                var html = "";
                $('.costContainer').css("display","block");
                $el = '<input type="text" name="subject_cost[]" class="form-control" />';
                if(checked === true) {
                    if(opselected === '1' || opselected === '2' || opselected === '3' || opselected === '4' || opselected === '5' || opselected=== '6'){
                        html += '<tr class="row_'+opselected+'"><td>Primary '+opselected+' <input name="level_id[]" type="hidden" value="'+opselected+'" /> </td><td class="sub_name">'+subjectVal+'</td><td>'+$el+'</td></tr>';
                    }
                   
                    if(opselected === '7' || opselected === '8' || opselected === '9' || opselected === '10'){
                        secondaryResetNum = 0;
                        if(opselected === '7'){
                            secondaryResetNum = '1';
                        }else if(opselected === '8'){
                            secondaryResetNum = '2';
                        }else if(opselected === '9'){
                            secondaryResetNum = '3';
                        }else{
                            secondaryResetNum = '4';
                        }
                        html += '<tr class="row_'+opselected+'"><td>Secondary '+secondaryResetNum+' <input name="level_id[]" type="hidden" value="'+opselected+'" /></td><td>'+subjectVal+'</td><td>'+$el+'</td></tr>';
                    }
                   
                }else{
                    $(".row_"+opselected).remove();
                }
                $("table tbody").append(html);
               
            }
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
                },
                'subject_cost[]':{
                    validators: {
                        notEmpty: {
                            message: 'Please enter course fee'
                        }
                    }
                }
            }
        });
        
    });
</script>


