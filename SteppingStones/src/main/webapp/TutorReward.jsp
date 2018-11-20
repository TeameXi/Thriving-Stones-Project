<%@page import="java.util.Date"%>
<%@page import="entity.Reward"%>
<%@page import="java.util.List"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_tutor.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
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
    @media screen and (max-width: 480px){
        body{
            font-size: 10px;
        } 
        .form-control, .btn{
            font-size: 10px;
        }
        a {
            font-size: 10px;
        }
        #tab{
            margin-bottom: 20px !important;
        }
        .submitReward{
            float: right;
        }
    }
    @media screen and (min-width:481px) and (max-width: 767px) {
        body{
            font-size: 12px !important;
        } 
        .form-control, .btn{
            font-size: 12px !important;
        }
        a {
            font-size: 12px ;
        }
        #tab{
            margin-bottom: 20px !important;
        }
        .submitReward{
            float: right;
        }
        
    }
    @media screen and (min-width:768px) and (max-width: 991px) {
        #studentAttendanceTable_filter{
            float: right;
        }
        .submitReward{
            float: right;
        }
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active">Reward Student</span></div>
    <div class="row" id="statusMsg"></div>
    <div class="col-md-1"></div>
        <div class="col-md-9">
    <form action="TutorRewardServlet" method="post" autocomplete = "off">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Student</label>  
                    <div class="col-lg-8 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                                <%  
                                    ArrayList<String> stu = StudentDAO.listAllStudentsByTutor(user_id);
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
                        <button type="submit" class="btn btn2 center-block" name="search">Select Student</button>
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

                String level = (String) request.getAttribute("level");
                String studentName = (String) request.getAttribute("studentName");

                Integer student_id = (Integer) request.getAttribute("student_id");
                if(studentName != null){
            %>
            <form action="TutorRewardServlet" method="post" id="rewardForm">
            Student Name: <label> <%out.println(studentName);%></label>
                    &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Level: <label> <%out.println(level);%></label><br><br>
            <input type="hidden" name="studentiiD" value="${student_id}"id="studentiiD"> 
            <input type="hidden" name="tutorid" value="<%=user_id%>"id="tutorid">
            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
            <%
                List<Reward> rewardList = (ArrayList<Reward>) request.getAttribute("rewardList");
                out.println("<table class='table'>");
                out.println("<thead><tr><th scope='col'>Date</th><th scope='col'>Description</th><th scope='col'>amount</th></tr></thead>");
                out.println("<tr>");
                out.println("<td><div class='form-group'><input name='date' type='text' class='form-control rewarddate'  value='"+new Date()+"' disabled></div></td>");
                out.println("<td><div class='form-group'><input name='description' type='text' class='form-control'  placeholder='Description'></div></td>");
                out.println("<td><div class='form-group'><input name='amount' type='number' class='form-control'  placeholder='1'></div></td>");
                out.println("</tr>");
                if(rewardList != null && rewardList.size() > 0){
                    int max = 5;
                    if(rewardList.size() < 5){
                        max = rewardList.size();
                    }
                    for(int i = 0; i < max; i++){
                        out.println("<tr><td>"+ rewardList.get(i).getDate()+"</td><td>"+ rewardList.get(i).getDescription()+"</td><td>"+ rewardList.get(i).getAmount()+"</td></tr>");
                    }
                }
                out.println("</table>");
                
            %>
            <div class="form-group">
                <div class="col-md-10"></div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn2 submitReward" name="select" value="select">Submit Rewards</button>
                </div>
            </div>    
            </form>
            <%
                }
            %>
            
</div>
</div>
        <%@include file="footer.jsp"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>


<script src="${pageContext.request.contextPath}/styling/js/jquery.autocomplete.js"></script>

<script charset="utf-8">

    $(function () {
        var studentName = [<% for (int i = 0; i < stu.size(); i++) { %>"<%= stu.get(i) %>"<%= i + 1 < stu.size() ? ",":"" %><% } %>];
        autocomplete(document.getElementById("studentName"), studentName);
       
        if($('#errorMsg').length){
           $('#errorMsg').fadeIn().delay(3000).fadeOut();
           
        }
        
        
    });
    $('#rewardForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                amount: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter amount'
                        },
                        integer: {
                            message: 'Integer Only'
                        }
                    }
                }
            }
        });
</script>