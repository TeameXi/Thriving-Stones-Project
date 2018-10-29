<%@page import="model.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="protect_branch_admin.jsp"%>
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_recurring.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_editors.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_multiselect.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_material.css" type="text/css" charset="utf-8">
<%@include file="header.jsp"%>
<style>
    .autocomplete-items {
        position: absolute;
        border: 1px solid #d4d4d4;
        border-bottom: none;
        border-top: none;
        z-index: 99;
        /*position the autocomplete items to be the same width as the container:*/
        top: 100%;
        left: 0;
        right: 0;
    }
    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff; 
        border-bottom: 1px solid #d4d4d4; 
    }
    .autocomplete-items div:hover {
        /*when hovering an item:*/
        background-color: #e9e9e9; 
    }
    .autocomplete-active {
        /*when navigating through the items using the arrow keys:*/
        background-color: DodgerBlue !important; 
        color: #ffffff; 
    }
</style>

<div class="col-md-10">
<div class="row" id="errorMsg"></div>
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Register For Classes </span></h5></div>
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-9">


            <form action="RegisterForClassesServlet" method="post" autocomplete = "off">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Student</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                                <%  
                                    ArrayList<String> stu = StudentDAO.listAllStudents(branch_id);
                                    String redirectStudentName = "";
                                    if (request.getParameter("studentName") != null) {
                                        redirectStudentName = request.getParameter("studentName").trim();
                                    } 

                                %>
                            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
                            <input id="studentName"  name="student" placeholder="Name  &  Phone or Email" class="form-control"  type="text" value="<%=redirectStudentName%>">
                        </div>
                    </div>
                </div>
                <br/><br/>

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <button type="submit" class="btn btn2 center-block" name="search">Search For Classes</button>
                    </div>
                </div>

            </form><br><br>
            <%            
                String errorMsg = (String) request.getAttribute("errorMsg");
                if (errorMsg != null) {
                    out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
                }

                String status = (String) request.getAttribute("status");
                if (status != null) {
                    out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
                    //response.sendRedirect("DisplayStudents.jsp");
                }
                
                String paymentStatus = (String) request.getParameter("status");
                if (paymentStatus != null) {
                    out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+paymentStatus+"</strong></div>");
                }

                ArrayList<Class> premiumClasses = (ArrayList<Class>) request.getAttribute("premiumClasses");
                ArrayList<Class> normalClasses = (ArrayList<Class>) request.getAttribute("normalClasses");
                ArrayList<Class> enrolledClasses = (ArrayList<Class>) request.getAttribute("enrolledClasses");
                ArrayList<Class> combinedClasses = (ArrayList<Class>) request.getAttribute("combinedClasses");
                String level = (String) request.getAttribute("level");
                String studentName = (String) request.getAttribute("studentName");

                Integer student_id = (Integer) request.getAttribute("student_id");
 
                if (premiumClasses != null || normalClasses != null) {
                    request.setAttribute("studentName", studentName);
            %>
                    Student Name: <label> <%out.println(studentName);%></label>
                    &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Level: <label> <%out.println(level);%></label><br><br>
                 <input type="hidden" name="studentiiD" value="${student_id}"id="studentiiD">  
            <%
                if (enrolledClasses.size() > 0) {
            %>
            
            <label>Currently Enrolled Classes:</label><br>
                <div class="table-responsive-sm">
                    <table id="enrolledClassesTable" class="table display responsive nowrap" style="width:100%">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col"> </th>
                                <th scope="col">Class</th>
                                <th scope="col">Class Timing</th>
                                <th scope="col">Starting Date</th>
                                <th scope="col">Monthly Fees</th>
                            </tr>
                        </thead>
                        <tbody>
            <%
                    for (Class cls : enrolledClasses) {
            %>

            <tr><td><span class="survey-completes">
                <a href="#small" onclick="deleteStudentClass('<%=cls.getClassID()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
            </span></td>
<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to delete the student from the class?</div>
            <div class="modal-footer centered">
                <a id="confirm_btn"><button type="button" class="small_button pw_button del_button autowidth">Yes, Remove</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
            <%
                        if(cls.getType().equals("P")){
                            out.println("<td>"+cls.getSubject()+" (Premium)</td>");
                        }else{
                            if(!cls.getCombinedLevel().equals("")){
                                out.println("<td>"+cls.getSubject()+" (Combined)</td>");
                            }else{
                                out.println("<td>"+cls.getSubject()+"</td>");
                            }
                        }
                        
                        out.println("<td>"+ cls.getStartTime().substring(0, 5) + "-" + cls.getEndTime().substring(0, 5) +" ("+cls.getClassDay()+")"+"</td>");
                        out.println("<td>"+cls.getStartDate()+"</td>");
                        out.println("<td>"+cls.getMthlyFees()+"</td>");
                        out.println("</tr>");
                    }
            %>
            </tbody> 
                </table>
            </div>
            <%
                }
            %>
            
           <form action="RegisterForClassesServlet" method="post" id="registrationform">  
            <input type="hidden" name="studentID" value="${student_id}">
            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
            <%
                if(normalClasses.size() > 0 || combinedClasses.size() > 0){
            %> 
            
            
            <div class="table-responsive-sm">
                <table id="registerClassesTable" class="table display responsive nowrap" style="width:100%">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col"> </th>
                            <th scope="col">Class</th>
                            <th scope="col">Class Timing</th>
                            <th scope="col">Starting Date</th>
                            <th scope="col">Monthly Fees</th>
                            <th scope="col">Join Date</th>
                        </tr>
                    </thead>
                    <tbody>
                    <label>Register for Normal Classes:</label> <br>                 
                    <%
                        for (Class norCls : normalClasses) {
                            request.setAttribute("value", norCls.getClassID());
                            String norClsStartDate = norCls.getStartDate();
                    %>
                                <tr><td><input type= "checkbox" name ="normalClassValue" value = "${value}">
                                        <input type='hidden' name='normalClsStartDate' value="<%=norClsStartDate%>"></td>
                    <%
                            out.println("<td>"+norCls.getSubject()+"</td>");
                            out.println("<td>"+norCls.getStartTime().substring(0, 5)+ "-" + norCls.getEndTime().substring(0, 5) + " ("+norCls.getClassDay()+")"+"</td>");
                            out.println("<td>"+norCls.getStartDate()+"</td>");
                            out.println("<td>"+norCls.getMthlyFees()+"</td>");
                            out.println("<td>");
                            %>
                            <div class="input-group">
                                <input name="<%=norCls.getClassID()%>" type='text' class='form-control n_join_date'  placeholder='YYYY-MM-DD'> 
                            </div>
                            <%
                            out.println("</td>");
                            out.println("</tr>");    
                                   
                        }
                    %>
                                
                    <%
                        for (Class comCls : combinedClasses) {
                            request.setAttribute("value", comCls.getClassID());
                            String comClsStartDate = comCls.getStartDate();
                    %>
                                <tr><td><input type= "checkbox" name ="comClassValue" value = "${value}">
                                        <input type='hidden' name='comClsStartDate' value="<%=comClsStartDate%>"></td>
                    <%
                            out.println("<td>"+comCls.getSubject()+" (Combined)</td>");
                            out.println("<td>"+comCls.getStartTime().substring(0, 5)+ "-" + comCls.getEndTime().substring(0, 5) + " ("+comCls.getClassDay()+")"+"</td>");
                            out.println("<td>"+comCls.getStartDate()+"</td>");
                            out.println("<td>"+comCls.getMthlyFees()+"</td>");
                            out.println("<td>");
                            %>
                            <div class="input-group">
                                <input name="<%=comCls.getClassID()%>" type='text' class='form-control n_join_date'  placeholder='YYYY-MM-DD'> 
                            </div>
                            <%
                            out.println("</td>");
                            out.println("</tr>");    
                                   
                        }
                    %>
                    </tbody> 
                        </table>
                    </div>                                
            <%
                }
                if(premiumClasses.size() > 0){
            %>
            <div class="table-responsive-sm">
                <table id="registerClassesTable" class="table display responsive nowrap" style="width:100%">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col"> </th>
                            <th scope="col">Class</th>
                            <th scope="col">Class Timing</th>
                            <th scope="col">Starting Date</th>
                            <th scope="col">Monthly Fees</th>
                            <th scope="col">Join Date</th>
                            <th scope="col">Payment Per Term/Month</th>
                        </tr>
                    </thead>
                    <tbody>
                    <label>Register for Premium Classes:</label> <br>                 
                    <%
                        for (Class preCls : premiumClasses) {
                            request.setAttribute("preValue", preCls.getClassID());
                            String preClsStartDate = preCls.getStartDate();
                    %>
                                <tr><td><input type= "checkbox" name ="premiumClassValue" value = "${preValue}">
                                        <input type='hidden' name='premiumClsStartDate' value="<%=preClsStartDate%>"></td>
                    <%
                            out.println("<td>"+preCls.getSubject()+"</td>");
                            out.println("<td>"+preCls.getStartTime().substring(0, 5)+ "-" + preCls.getEndTime().substring(0, 5) + " ("+preCls.getClassDay()+")"+"</td>");
                            out.println("<td>"+preCls.getStartDate()+"</td>");
                            out.println("<td>"+preCls.getMthlyFees()+"</td>");
                            String paymentType = preCls.getClassID()+"_paymentType";
                            %>
                                    <td>
                                        <div class="input-group">
                                        <input name="<%=preCls.getClassID()%>" type='text' class='form-control p_join_date'  placeholder='YYYY-MM-DD'>
                                        </div></td>
                                    
                                    <td><select name=<%=paymentType%> class="form-control" id="paymentType">
                                            <option value="term">Pay Per Term</option>
                                            <option value="month">Pay Per Month</option></td>
                            <%
                            out.println("</tr>");               
                        }
                    %>
                    </tbody> 
                        </table>
                    </div>
            <%     
                }
                if(premiumClasses.size() == 0 && normalClasses.size() == 0){
                    out.println("<label>No class available to register.</label><br><br>");
                }else{
            %>  
            <div class="form-group">
                <div>
                    <button type="submit" class="btn btn2 center-block" name="select" value="select">Register Class</button>
                </div>
            </div>    
            <%
                }
            }
            %>
            
            </form>
        </div>
    </div>

</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/styling/js/jquery.autocomplete.js"></script>
<script charset="utf-8">

    $(function () {
        var studentName = [<% for (int i = 0; i < stu.size(); i++) { %>"<%= stu.get(i) %>"<%= i + 1 < stu.size() ? ",":"" %><% } %>];
        autocomplete(document.getElementById("studentName"), studentName);
        
        if($('#errorMsg').length){
           $('#errorMsg').fadeIn().delay(3000).fadeOut();
        }
        
        $('.n_join_date').datetimepicker({
            format: 'YYYY-MM-DD'
        });
        
        $('.p_join_date').datetimepicker({
            format: 'YYYY-MM-DD'
        });
    });
    
    function deleteStudentClass(class_id) {
        console.log(class_id);
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteStudentQueryAjax(class_id);
        });
    }
    
    function deleteStudentQueryAjax(class_id) {
        var studentID = $("#studentiiD").val();
        console.log(class_id);
        console.log("What" + studentID);
        $('#small').modal('hide');
        $.ajax({
            type: 'POST',
            url: 'DeleteStudentFromClassServlet',
            dataType: 'JSON',
            data: {classID: class_id, studentID: studentID},
            success: function (data) {
                console.log(data);
                if (data === 1) {
                    $("#sid_" + studentID).remove();
                    html = '<div class="alert alert-success col-md-5"><strong>Successfully Deleted!</strong> Deleted Student from class successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                }
                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(4000).fadeOut();
                location.reload();
            }
        });
    }
    
    $(document).ready(function () {
        $('#enrolledClassesTable').dataTable( {
            "paging":   false,
//            "ordering": false,
            "info":     false,
            "searching": false
        });
        $('#registerClassesTable').dataTable( {
            "paging":   false,
//            "ordering": false,
            "info":     false,
            "searching": false
        });
    });
    
</script>
