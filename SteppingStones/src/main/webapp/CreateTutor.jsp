<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
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

    String existingTutor = (String) request.getAttribute("existingTutor");
    if (existingTutor != null) {
        out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Tutor : <strong>"+existingTutor+"</strong> is already added. Try another tutor again. </div>");
    }
    
%> 
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Tutor </span> / <a href="UploadTutor.jsp">Upload Tutors</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createTutorForm" method="POST" class="form-horizontal" action="CreateTutorServlet">
                <div class="form-group">
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
                    <label class="col-lg-2 control-label">Image</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-image"></i></span>
                            <input name="tutorImage"  class="form-control" type="file">
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
                                <input name="birthDate" type='text' class="form-control" id="birthDate" />
                                
                            </div>
                       
                    </div>
                </div>



                <div class="form-group">
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
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="email" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>



                <div class="form-group">
                    <label class="col-lg-1 control-label">Password</label>  
                    <div class="col-lg-2">
                        <input id="generate_btn" type="button" value="Generate" onClick="generatePassword(16);"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label"></label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>

                            <input name="tutorPassword" placeholder="Password" id="tutorPassword" class="form-control"  type="text" required>
                        </div>
                    </div>
                </div>





                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Create Tutor</button>
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
var Password = {

    _pattern: /[a-zA-Z0-9_\-\+\.]/,

    _getRandomByte: function ()
    {
        if (window.crypto && window.crypto.getRandomValues)
        {
            var result = new Uint8Array(1);
            window.crypto.getRandomValues(result);
            return result[0];
        } else if (window.msCrypto && window.msCrypto.getRandomValues)
        {
            var result = new Uint8Array(1);
            window.msCrypto.getRandomValues(result);
            return result[0];
        } else
        {
            return Math.floor(Math.random() * 256);
        }
    },

    generate: function (length)
    {
        return Array.apply(null, {'length': length})
                .map(function ()
                {
                    var result;
                    while (true)
                    {
                        result = String.fromCharCode(this._getRandomByte());
                        if (this._pattern.test(result))
                        {
                            return result;
                        }
                    }
                }, this)
                .join('');
    }
};

function generatePassword(len) {
    var pwd = Password.generate(len);
    $("#tutorPassword").val(pwd);
// $("#createTutorForm").bootstrapValidator('enableFieldValidators', tutorPassword, 'notEmpty', false);
}

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
            tutorID: {
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
                        message: 'Please enter valid phone number'
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
            },
            tutorImage: {
                validators: {
                    file: {
                        extension: 'jpeg,png,jpg',
                        type: 'image/jpeg,image/png,image/jpg',
                        message: 'Invalid format.Must be image'
                    }
                }
            },
            tutorPassword:{
                validators:{
                    notEmpty: {
                        message: 'Password cannot be empty'
                    }
                }
            }
           
        }
    });
    
    
});
</script>
