<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<div class="col-md-10">
    <%        
        String existingStudent = (String) request.getAttribute("existingStudent");
        if (existingStudent != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Student : <strong>" + existingStudent + "</strong> is already added. Try another student again. </div>");
        }
    %>

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Student </span> / <a href="UploadStudent.jsp">Upload Students</a></h5></div>
    <form id="createTutorForm" method="POST" class="form-horizontal" action="CreateStudentServlet">
        <div class="row">

            <div class="col-md-5">
                <br/><h4 align="center">Student's Details</h4><br/>
                <% 
                    if (user != null) {
                        out.println("<input type='hidden' name='branch' value='" + user.getBranchId() + "'/>");
                    }
                %>

                <div class="form-group">
                    <label class="col-lg-4 control-label">Name</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="studentName"  name="studentName" placeholder="Full Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label">School</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="school"  name="school" placeholder="School Name" class="form-control"  type="text">
                        </div>

                    </div>

                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label">Level</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="lvl" class="form-control" >
                                <%
                                    LevelDAO lvlDao = new LevelDAO();
                                    ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                                %>
                                <option value="" >Select Level</option>
                                <%  
                                    for (Level lvl : lvlLists) {
                                        out.println("<option value='" + lvl.getLevel_id() + "'>" + lvl.getLevelName() + "</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label">Stream</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="stream" class="form-control" >
                                <option value="" >Select Stream</option>
                                <option value="Express"> Express </option>
                                <option value="Normal Academic"> Normal Academic</option>
                                <option value="Normal Technical"> Normal Technical </option>
                            </select>
                        </div>
                    </div>
                </div>               

                <div class="form-group">
                    <label class="col-lg-4 control-label">Mobile</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phone" placeholder="Mobile Number" id="phone" class="form-control" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label">Email</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="studentEmail" id="studentEmail" placeholder="E-Mail Address" class="form-control" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-4 control-label">Reg Fees</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-money"></i></span>
                            <input name="regFees" placeholder="Registration Fees" id="phone" class="form-control" type="text">
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <br/><h4>Parent's Details</h4><br/>
                <div class="form-group">
                    <label class="col-lg-2 control-label">Full name</label>  
                    <div class="col-lg-7 inputGroupContainer"> 
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="parentName"  name="parentName" placeholder="Parent Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Relationship</label>  
                    <div class="col-lg-7 inputGroupContainer"> 
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="rel"  name="relationship" placeholder="Relationship" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Mobile</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="parentPhone" placeholder="Mobile Number" class="form-control" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="parentEmail" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
            </div>
                            
            <div class="form-group">
                <div class="col-lg-7 col-lg-offset-2">
                    <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                    <button type="submit" class="btn btn-primary center-block" name="insert">Register Student</button>
                </div>
            </div>
                            
        </div>
    </form>

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
        if ($('#creation_status').length) {
            $('#creation_status').fadeIn().delay(3000).fadeOut();
        }

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
                studentName: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter student name'
                        }
                    }
                },
                school: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter school name'
                        }
                    }
                },
                lvl: {
                    validators: {
                        notEmpty: {
                            message: 'Please select level'
                        }
                    }
                },
                phone: {
                    validators: {
                        callback: {
                            message: 'Enter phone or email',
                            callback: function (value, validator) {
                                if (validator.getFieldElements('studentEmail').val().length > 0 || value.length > 0) {
                                    return true;
                                }
                            }
                        },
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
                studentEmail: {
                    validators: {
                        callback: {
                            message: 'Enter phone or email',
                            callback: function (value, validator) {
                                if (validator.getFieldElements('phone').val().length > 0 || value.length > 0) {
                                    return true;
                                }
                            }
                        },
                        emailAddress: {
                            message: 'Please enter valid email address'
                        }
                    }
                },
                regFees: {
                    validators: {
                        numeric: {
                            message: 'Please enter valid amount'
                        },
                        notEmpty: {
                            message: 'Please enter registration amount'
                        }
                    }
                },
                parentPhone: {
                    validators: {
                        integer: {
                            message: 'Integer only'
                        },
                        between: {
                            min: 80000000,
                            max: 99999999,
                            message: 'Please enter valid phone number'
                        },
                        notEmpty: {
                            message: 'Please enter phone number'
                        }
                    }
                },
                parentName: {
                    validators: {
                        notEmpty: {
                            message: 'Name cannot be empty'
                        }
                    }
                },
                rel: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter school name'
                        }
                    }
                },
                parentEmail: {
                    validators: {
                        emailAddress: {
                            message: 'Please enter valid email address'
                        }
                    }
                }

            }
        });
        $('#phone').on('change', function () {
            $('#createTutorForm').data('bootstrapValidator').updateStatus('studentEmail', 'NOT_VALIDATED').validateField('studentEmail');
        });
        $('#studentEmail').on('change', function () {
            $('#createTutorForm').data('bootstrapValidator').updateStatus('phone', 'NOT_VALIDATED').validateField('phone');
        });
    });
</script>
