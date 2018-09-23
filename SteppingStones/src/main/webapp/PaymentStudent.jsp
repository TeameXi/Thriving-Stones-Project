<%@page import="model.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
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

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Student Payment</span></h5></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">


            <form action="PaymentStudentServlet" method="post" autocomplete = "off">

                <div class="form-group">
                    <label class="col-lg-2 control-label">Student</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                                <%                                    ArrayList<String> stu = StudentDAO.listAllStudents(branch_id);
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
                        <button type="submit" class="btn btn-default" name="search">Make Payment</button>
                    </div>
                </div>

            </form><br><br>
            <%
                String status = (String) request.getAttribute("status");
                if (status != null) {
                    out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>" + status + "</strong></div>");
                    //response.sendRedirect("DisplayStudents.jsp");
                }

                String paymentStatus = (String) request.getParameter("status");
                if (paymentStatus != null) {
                    out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>" + paymentStatus + "</strong></div>");
                }
            %>
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
        var studentName = [<% for (int i = 0; i < stu.size(); i++) {%>"<%= stu.get(i)%>"<%= i + 1 < stu.size() ? "," : ""%><% }%>];
                autocomplete(document.getElementById("studentName"), studentName);

        if ($('#errorMsg').length) {
            $('#errorMsg').fadeIn().delay(3000).fadeOut();
        }

        $('.join_date').datetimepicker({
            format: 'YYYY-MM-DD'
        });
    });

</script>
