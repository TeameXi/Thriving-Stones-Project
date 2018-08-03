<%-- 
    Document   : CreateSubject
    Created on : Jul 29, 2018, 2:32:46 PM
    Author     : MOH MOH SAN
--%>

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
<%   
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }    
%> 

<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateSubject.jsp">Add Subject </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createSubjectForm" method="POST" class="form-horizontal" action="">
				<div class="form-group">
                    <label class="col-lg-2 control-label">Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                            <select name="level" class="form-control" >
                                <option value="" >Select Level</option>
                                <option value="singapore">Singapore</option>
                                <option value="malaysia">Malaysia</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label">Subject Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="subjectName"  name="subjectName" placeholder="Subject Name" class="form-control"  type="text">
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


