<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="#">Add User </a> / <a href="#">Upload User</a></h5></div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-5 form">
            <h1>Create New Student</h1>
            <%
                ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
                if(errors != null){
                    for(String error: errors){
                        out.println(error);
                    }
                }
                String studentExist = (String) request.getAttribute("studentExist");
                if (studentExist != null) {
                    out.println(studentExist);
                }
            %>
            <form action="CreateNewStudentServlet" method="post">

                <div class="wrap-input100 validate-input m-b-23" data-validate = "Student ID is required">
                    <span class="label-input100">Student ID</span>
                    <input class="input100" type="text" name="studentID" placeholder="Type Student ID" required>
                    <span class="focus-input100" data-symbol="&#xf207;"></span>
                </div>
                <br/>

                <div class="wrap-input100 validate-input m-b-23" data-validate = "Name is required">
                    <span class="label-input100">Student's name</span>
                    <input class="input100" type="text" name="studentName" placeholder="Type Name" required>
                    <span class="focus-input100" data-symbol="&#xf207;"></span>
                </div>
                <br/>

                <div class="wrap-input100 validate-input" data-validate="Age is required">
                    <span class="label-input100">Age</span>
                    <input class="input100" type="number" name="age" placeholder="Type age" required>

                </div>
                <br/>

                <div>
                    <span class="label-input100">Gender</span>
                    <select id="gender" name="gender" class="cd-select">
                        <option value="-1" selected>Select Gender</option>
                        <option value="F" class="icon-female">Female</option>
                        <option value="M" class="icon-male">Male</option>
                    </select> 
                </div>
                
                <br/>
                     <div class="wrap-input100 validate-input" data-validate="Address is required">
                    <span class="label-input100"> Student Address</span>
                    <input class="input100" type="text" name="address" placeholder="Type Address" required>
                    <span class="focus-input100" data-symbol="&#xf1a8;"></span>
                </div>
                <br/>

                <div>
                    <span class="label-input100">Academic Level</span>
                    <select id="lvl" name="lvl" class="cd-select">
                        <option value="-1" selected>Select Level</option>
                        <option value="Pri 3">Primary 3</option>
                        <option value="Pri 4">Primary 4</option>
                        <option value="Pri 5">Primary 5</option>
                        <option value="Pri 6">Primary 6</option
                        <option value="Sec 1">Secondary 1</option>
                        <option value="Sec 2">Secondary 2</option>
                        <option value="Sec 3">Secondary 3</option>
                        <option value="Sec 4">Secondary 4</option>
                    </select> 
                </div>     

                <div class="wrap-input100 validate-input" data-validate="Phone Number is required">
                    <span class="label-input100"> Student Contact Number</span>
                    <input class="input100" type="text" name="phone" placeholder="Type phone number" required>
                    <span class="focus-input100" data-symbol="&#xf2be;"></span>
                </div>
                <br/>

                Most Recent School Result:<br>
                <select name = "Sub1">
                    <option value="Engish">Engish</option>
                    <option value="Maths">Maths</option>
                    <option value="Science">Science</option>
                    <option value="E-Maths">E.Maths</option>
                    <option value="Add-Maths">Add.Maths</option>
                </select>
                CA1<input type ="text" name ="FCA1">SA1<input type ="text" name ="FSA1">
                CA2<input type ="text" name ="FCA2">SA2<input type ="text" name ="FSA2"><br>            
                <select name = "Sub2">
                    <option value="Engish">Engish</option>
                    <option value="Maths">Maths</option>
                    <option value="Science">Science</option>
                    <option value="E-Maths">E.Maths</option>
                    <option value="Add-Maths">Add.Maths</option>
                </select>
                CA1<input type ="text" name ="SCA1">SA1<input type ="text" name ="SSA1">
                CA2<input type ="text" name ="SCA2">SA2<input type ="text" name ="SSA2"><br>
                <select name = "Sub3">
                    <option value="Engish">Engish</option>
                    <option value="Maths">Maths</option>
                    <option value="Science">Science</option>
                    <option value="E-Maths">E.Maths</option>
                    <option value="Add-Maths">Add.Maths</option>
                </select>
                CA1<input type ="text" name ="TCA1">SA1<input type ="text" name ="TSA1">
                CA2<input type ="text" name ="TCA2">SA2<input type ="text" name ="TSA2"><br>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" type="submit" value = "insert" name = "insert">
                            Insert Student
                        </button>
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
</div>
</div>

<%@include file="footer.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/styling/js/jquery.dropdown.js"></script>
<script type="text/javascript">

    $(function () {

        $('#lvl').dropdown({
            gutter: 5
        });
        
        $('#gender').dropdown({
        });

    });

</script>