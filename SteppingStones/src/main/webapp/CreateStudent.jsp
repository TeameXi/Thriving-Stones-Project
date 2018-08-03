<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
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

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Student </span> / <a href="UploadStudents.jsp">Upload Students</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <h3>Students Details</h3><br/>
            <form id="createTutorForm" method="POST" class="form-horizontal" action="StudentApplicationServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">NRIC</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                            <input id="studentNRIC"  name="studentNRIC" placeholder="NRIC" class="form-control"  type="text">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-2 control-label">Full Name</label>  
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
                                <option value="O">Other</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Academic Level</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select name="lvl" class="form-control" >
                                <option value="-1" >Select Level</option>
                                <option value="Primary 3">Primary 3</option>
                                <option value="Primary 4">Primary 4</option>
                                <option value="Primary 5">Primary 5</option>
                                <option value="Primary 6">Primary 6</option>
                                <option value="Secondary 1">Secondary 1</option>
                                <option value="Secondary 2">Secondary 2</option>
                                <option value="Secondary 3">Secondary 3</option>
                                <option value="Secondary 4">Secondary 4</option>
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
                    <%  BranchDAO branchDao = new BranchDAO();
                    if(false) {
                        ArrayList<Branch> branch_lists = branchDao.retrieveAllBranches();          
                %>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">Branch</label>  
                                <div class="col-lg-7 inputGroupContainer">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                                        <select name="branch" class="form-control" >
                                            <option value="0" >Select Center</option>
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

                <hr/>
                <h3>Student's Account Information</h3><br/>
                <div class="form-group">
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="studentEmail" placeholder="E-Mail Address" class="form-control"  type="text">
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

                            <input name="studentPassword" placeholder="Password" id="studentPassword" class="form-control"  type="text" required>
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
                    <label class="col-lg-2 control-label">Mobile </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="parentPhone" class="form-control" type="text">
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
    $("#studentPassword").val(pwd);
// $("#createTutorForm").bootstrapValidator('enableFieldValidators', tutorPassword, 'notEmpty', false);
}

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
            tutorID: {
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
                        message: 'Please enter tutor name'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Please enter phone number'
                    },
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid number'
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
                        extension: 'jpeg,png',
                        type: 'image/jpeg,image/png',
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