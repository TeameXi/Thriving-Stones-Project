<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
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
        String existingStudent = (String) request.getAttribute("existingStudent");
        if (existingStudent != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Student : <strong>"+existingStudent+"</strong> is already added. Try another student again. </div>");
        }
    %>

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Student </span> / <a href="UploadStudents.jsp">Upload Students</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <h3>Students Details</h3><br/>
            <form id="createTutorForm" method="POST" class="form-horizontal" action="CreateStudentServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">NRIC</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                            <input id="studentNRIC"  name="studentNRIC" placeholder="NRIC" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                <% if(user != null){
                        out.println("<input type='hidden' name='branch' value='"+user.getBranchId()+"'/>");
                    }
                %>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Name**</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="studentName"  name="studentName" placeholder="Full Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Birth Date</label>  
                    <div class="col-lg-7 inputGroupContainer">
                  
                            <div class='input-group'>
                                <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                                <input name="bday" type='text' class="form-control" id="bday" />
                                
                            </div>
                       
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Gender</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-male-female"></i></span>
                            <select name="gender" class="form-control" >
                                <option value="-1" >Select Gender</option>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Academic Level**</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="lvl" class="form-control" >
                                <%
                                    LevelDAO lvlDao = new LevelDAO();
                                    ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                                %>
                                <option value="" >Select Level</option>
                                    <%  for(Level lvl: lvlLists){
                                            out.println("<option value='"+lvl.getLevel_id()+"'>"+lvl.getLevelName()+"</option>");
                                       }
                                    %>
                            </select>
                        </div>
                    </div>
                </div>
                

                <div class="form-group">
                    <label class="col-lg-2 control-label">Phone </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phone" class="form-control" type="text">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-2 control-label">Address</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-home"></i></span>
                            <textarea name="address" placeholder="Address" class="form-control"></textarea>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Email**</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="studentEmail" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <hr/>

                <h3>Parents Details</h3><br/>

                 <div class="form-group">
                    <label class="col-lg-2 control-label">Full Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="parentName"  name="parentName" placeholder="Parent Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                       <div class="form-group">
                    <label class="col-lg-2 control-label">Nationality </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-flag"></i></span>
                            <input id="parentName"  name="parentNationality" placeholder="Nationality" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                      <div class="form-group">
                    <label class="col-lg-2 control-label">Company </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                            <input id="parentCompany"  name="parentCompany" placeholder="Company" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Designation </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-case"></i></span>
                            <input id="parentDesgination"  name="parentDesgination" placeholder="Company" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

               <div class="form-group">
                    <label class="col-lg-2 control-label">Mobile** </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="parentPhone" class="form-control" type="text">
                        </div>
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Email**</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="parentEmail" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default" name="insert">Register Student</button>
                    </div>
                </div>
                
              
            </form>

        </div>
    </div>
    <%
        String status = (String) request.getAttribute("status");
        if (status != null) {
            out.println(status);
        }
    %>

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
    $('#bday').datetimepicker({
        format: 'DD-MM-YYYY'
    });
    
    
    
    $('#createTutorForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            studentNRIC: {
                validators: {   
                    stringLength: {
                        min: 9,
                        max: 9,
                        message: 'Invalid NRIC'
                    },
                }
            },
            studentName:{
                validators:{
                    notEmpty: {
                        message: 'Please enter student name'
                    }
                }
            },
            lvl:{
                validators:{
                    notEmpty: {
                        message: 'Please select level'
                    }
                }
            },
            phone: {
                validators: {
                    integer: {
                        message: 'Please enter valid phone number'
                    },
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid phone number'
                    }
                }
            },
            studentEmail: {
                validators: {
                    notEmpty: {
                        message: 'Enter Email Address'
                    },
                    emailAddress: {
                        message: 'Please enter valid email address'
                    }
                }
            },
            parentPhone: {
                validators: {
                    integer: {
                        message: 'Please enter valid phone number'
                    },
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid phone number'
                    }
                }
            },
            parentEmail:{
                validators:{
                    emailAddress: {
                        message: 'Email cannot be empty'
                    }
                }
            }
           
        }
    });
    
    
});
</script>
