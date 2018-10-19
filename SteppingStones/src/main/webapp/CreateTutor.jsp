<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
<%  

    String existingTutor = (String) request.getAttribute("existingTutor");
    if (existingTutor != null) {
        out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Tutor : <strong>"+existingTutor+"</strong> is already added. Try another tutor again. </div>");
    }
    
%> 
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Tutor </span> / <a href="UploadTutor.jsp">Upload Tutors</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createTutorForm" method="POST" class="form-horizontal" action="CreateTutorServlet" autocomplete="off">
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Full Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="tutorName"  name="tutorName" placeholder="Full Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="email" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                <div class="form-group" id="nricField" style="display:none">
                    <label class="col-lg-2 control-label">NRIC</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                            <input id="tutorNric"  name="tutorNric" placeholder="NRIC" class="form-control"  type="text">
                            <% if(user != null){
                                    out.println("<input type='hidden' name='branch' value='"+user.getBranchId()+"'/>");
                                }
                            %>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="phoneField" style="display:none">
                    <label class="col-lg-2 control-label">Phone </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phone" class="form-control" type="text">
                        </div>
                    </div>
                </div>
                        
                <div class="form-group" id="qualificationField" style="display:none">
                    <label class="col-lg-2 control-label">Qualification </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-graduation-cap"></i></span>
                            <input name="qualification" class="form-control" type="text">
                        </div>
                    </div>
                </div>


                <div class="form-group" id="addressField" style="display:none">
                    <label class="col-lg-2 control-label">Address</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-home"></i></span>
                            <textarea name="address" placeholder="Address" class="form-control"></textarea>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="dobField" style="display:none">
                    <label class="col-lg-2 control-label">Birth Date</label>  
                    <div class="col-lg-7 inputGroupContainer">
                  
                            <div class='input-group'>
                                <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                                <input name="birthDate" type='text' class="form-control" id="birthDate" />
                                
                            </div>
                       
                    </div>
                </div>



                <div class="form-group" id="genderField" style="display:none">
                    <label class="col-lg-2 control-label">Gender</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-male-female"></i></span>
                            <select name="gender" class="form-control" >
                                <option value="" >Select Gender</option>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                </div>
                

                <div class="form-group">
                    <div class="col-lg-7 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="button" class="btn btn-success col-md-5"  onclick="optionalTutorInfo()">+ Optional fields</button>
                        <button type="submit" class="btn btn-primary center-block">Create Tutor</button>
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
    $('#birthDate').datetimepicker({
        format: 'DD-MM-YYYY'
    });
    
    if($('#creation_status').length){
       $('#creation_status').fadeIn().delay(3000).fadeOut();
    }
    
    
    
    $('#createTutorForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            tutorNric: {
                validators: {   
                    stringLength: {
                        min: 9,
                        max: 9,
                        message: 'Invalid NRIC'
                    },
                }
            },
            tutorName:{
                validators:{
                    notEmpty: {
                        message: 'Please enter tutor name'
                    }
                }
            },
            phone: {
                validators: {
                    integer: {
                        message: 'Integer Only'
                    },
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid phone number'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Enter Email Address'
                    },
                    emailAddress: {
                        message: 'Please enter valid email address'
                    }
                }
            }
//            birthDate:{
//                validators:{
//                    notEmpty: {
//                        message: 'BirthDate cannot be empty'
//                    }
//                }
//            }
           
        }
    });
    
    
    
});
function optionalTutorInfo() {       
    var nric = document.getElementById("nricField");
    var phone = document.getElementById("phoneField");
    var qualification = document.getElementById("qualificationField");
    var address = document.getElementById("addressField");
    var dob = document.getElementById("dobField");
    var gender = document.getElementById("genderField");

    if (nric.style.display === "none"){
        nric.style.display = "block";
        phone.style.display = "block";
        qualification.style.display = "block";
        address.style.display = "block";
        dob.style.display = "block";
        gender.style.display = "block";
    } else {
        nric.style.display = "none";
        phone.style.display = "none";
        qualification.style.display = "none";
        address.style.display = "none";
        dob.style.display = "none";
        gender.style.display = "none";
    }
       
}
</script>
