<%@page import="model.LevelDAO"%>
<%@page import="entity.Student"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_student.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div id="update_status"></div>
    <%       
        int studentID = 0;
        if (user != null) {
            studentID = user.getRespectiveID();
        }

        StudentDAO studentDao = new StudentDAO();
        Student student = studentDao.retrieveStudentbyID(studentID, branch_id);
    %> 
    <div style="text-align: center;margin: 20px;"><h4>Manage Account</h4></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form class="form-horizontal" id="manageParentAccount">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Username</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input class="form-control" id="name" value="<%=student.getName()%>" readonly  type="text">
                            <input type="hidden" value="<%=student.getStudentID()%>" id="student_id"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Gender</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-male-female"></i></span>
                            <input class="form-control" id="gender" value="<%=student.getGender()%>" readonly  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Mobile </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name='phone' id="phone" class="form-control" type="text" value="<%=student.getPhone()%>">
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Address </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-pin"></i></span>
                            <input name='address' id="address" class="form-control" type="text" value="<%=student.getAddress()%>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Email </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name='email' id="email" placeholder="E-Mail Address" class="form-control"  type="text" value="<%=student.getEmail()%>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">School </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input name='school' id="school" class="form-control"  type="text" value="<%=student.getSchool()%>" readonly  type="text">
                        </div>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Level </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <input name='level' id="level" class="form-control"  type="text" value="<%=student.getLevel()%>" readonly  type="text">
                        </div>
                    </div>
                </div>



                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="button" class="btn btn-default" onclick="updateStudentAccount()">Save Changes</button>
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
                            function updateStudentAccount() {
                                event.preventDefault();
                                id = $("#student_id").val();            
                                name = $("#name").val();            
                                address = $("#address").val();
                                phone = $("#phone").val();
                                email = $("#email").val();

                                $.ajax({
                                    type: 'POST',
                                    url: 'UpdateStudentServlet',
                                    dataType: 'JSON',
                                    data: {studentID: id, name: name, address: address, phone: phone, email: email},
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
                                $('#manageStudentAccount').bootstrapValidator({
                                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
                                    feedbackIcons: {
                                        valid: 'glyphicon glyphicon-ok',
                                        invalid: 'glyphicon glyphicon-remove',
                                        validating: 'glyphicon glyphicon-refresh'
                                    },
                                    fields: {
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
                                        }
                                    }
                                });
                            });
</script>




