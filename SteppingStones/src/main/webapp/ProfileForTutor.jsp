<%@page import="entity.Tutor"%>
<%@page import="model.TutorDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_tutor.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div id="update_status"></div>
    <%               
        int tutorId = 0;
        if(user != null){
            tutorId = user.getUserId();
        }
        
        TutorDAO tutorDao = new TutorDAO();
        Tutor tutor = tutorDao.retrieveSpecificTutorById(tutorId);
        
        String phone = "";
        if(tutor.getPhone() != 0){
            phone = ""+tutor.getPhone();
        }
    %> 
    <div style="text-align: center;margin: 20px;"><h4>Manage Account</h4></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form class="form-horizontal" id="manageTutorAccount">
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Username</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input class="form-control" value="<%=tutor.getName()%>" readonly  type="text">
                            <input type="hidden" value="<%=tutor.getTutorId()%>" id="tutor_id"/>
                        </div>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">NRIC</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                            <% if (tutor.getNric().length() != 0){
                                    out.println("<input id='tutor_nric' class='form-control' value='"+tutor.getNric()+"' readonly  type='text'>");
                               }else{
                                    out.println("<input name='tutor_nric' id='tutor_nric' class='form-control' value='' type='text'>");
                               }
                            %>
                           
                        </div>
                    </div>
                </div>
                            
                            
                <div class="form-group">
                    <label class="col-lg-2 control-label">Phone </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phone" id="phone" class="form-control" type="text" value="<%=phone%>">
                        </div>
                    </div>
                </div>
                            
                <div class="form-group">
                    <label class="col-lg-2 control-label">Address</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-home"></i></span>
                            <textarea id="address" placeholder="Address" class="form-control"><%=tutor.getAddress()%></textarea>
                        </div>
                    </div>
                </div>
                
               
                <div class="form-group">
                    <label class="col-lg-2 control-label">Image</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div id="imageContainer">       
                            <% 
                                if (tutor.getImage_url() != ""){
                                    out.println("<img width='100px;' src='"+tutor.getImage_url()+"'></img>");
                                }
                            %>
                        </div><br/>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-image"></i></span>
                            <input id="tutor_image"  class="form-control" type="file">
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
                            <input id="dob" type='text' class="form-control" value="<%=tutor.getBirth_date()%>" readOnly />
                        </div>
                       
                    </div>
                </div>
                
                <% if (tutor.getGender().length() == 0 ){ 
                %>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">Gender</label>  
                        <div class="col-lg-7 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="zmdi zmdi-male-female"></i></span>
                                <select id="gender" class="form-control" >
                                    <option value="" >Select Gender</option>
                                    <option value="M">Male</option>
                                    <option value="F">Female</option>
                                </select>
                            </div>
                        </div>
                    </div>
                <% }else{ 
                  
                    String genderName = "Female";
                    if(tutor.getGender().trim().equals("M")){
                        genderName = "Male";
                    }
                %>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">Gender</label>  
                        <div class="col-lg-7 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="zmdi zmdi-male-female"></i></span>
                                <input id="gender" type='text' class="form-control" id="gender" value="<%=genderName%>" readOnly />
                            </div>
                        </div>
                    </div>
                <% } %>
                
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="email" id="email" placeholder="E-Mail Address" class="form-control"  type="text" value="<%=tutor.getEmail() %>">
                        </div>
                    </div>
                </div>
                
                        
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="button" class="btn btn-default" onclick="return updateTutorAccount();">Save Changes</button>
                    </div>
                </div>
                
                        
				
            </form>
        </div>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script>
function updateTutorAccount() {
    event.preventDefault();
    id = $("#tutor_id").val();
    nric = $("#tutor_nric").val();
    phone = $("#phone").val();
    address = $("#address").val();
    file_name = "";
    if($("#tutor_image").get(0).files.length !== 0){
        file_name = $("#tutor_image").prop("files")[0]["name"];
    }
    dob = $("#dob").val();
    gender = $("#gender").val();
    email = $("#email").val();


    $.ajax({
        type: 'POST',
        url: 'UpdateTutorServlet',
        dataType: 'JSON',
        data: {tutorID: id,nric:nric,phone:phone,address:address,image:file_name,dob:dob,gender:gender,email:email},
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


$(function () {
    $('#manageTutorAccount').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            tutor_nric: {
                validators: {   
                    stringLength: {
                        min: 9,
                        max: 9,
                        message: 'Invalid NRIC'
                    },
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
            }
        }
    });
});
</script>




