<%@page import="java.util.Date"%>
<%@page import="entity.Reward"%>
<%@page import="java.util.List"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
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
    .action{
        display: none;
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Reward Student</span></div>
    <div class="row" id="statusMsg"></div>
    <div class="col-md-1"></div>
    <div class="col-md-9">
        <form action="RedeemRewardServlet" method="post" autocomplete = "off">

            <div class="form-group">
                <label class="col-lg-2 control-label">Student</label>  
                <div class="col-lg-8 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-account-box"></i></span>
                            <%  ArrayList<String> stu = StudentDAO.listAllStudents(branch_id);
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
                out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>" + errorMsg + "</strong></div>");
            }

            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>" + status + "</strong></div>");
                //response.sendRedirect("DisplayStudents.jsp");
            }

            String paymentStatus = (String) request.getParameter("status");
            if (paymentStatus != null) {
                out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>" + paymentStatus + "</strong></div>");
            }

            String level = (String) request.getAttribute("level");
            String studentName = (String) request.getAttribute("studentName");

            Integer student_id = (Integer) request.getAttribute("student_id");
            if (studentName != null) {
                Integer point = (Integer) request.getAttribute("pointAvail");
        %>
        <form action="TutorRewardServlet" method="post" id="rewardForm">
            Student Name: <label> <%out.println(studentName);%></label>
            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Level: <label> <%out.println(level);%></label>
            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Point Available: <label id="lblPoint" ><%out.println(point);%></label><br><br>
            <input type="hidden" name="studentiiD" value="${student_id}"id="studentiiD"> 
            <input type="hidden" name="tutorid" value="<%=user_id%>"id="tutorid">
            <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
            <table id="rewardTable" class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th style="text-align: center">Item Name</th>
                        <th style="text-align: center">Image</th>
                        <th style="text-align: center">Quantity Available</th>
                        <th style="text-align: center">Point to redeem</th>
                        <th style="text-align: center">Description</th>
                        <th style="display:none"></th>
                        <th style="text-align: center">Action</th>
                        
                    </tr>
                </thead>
            </table>

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
        var studentName = [<% for (int i = 0; i < stu.size(); i++) {%>"<%= stu.get(i)%>"<%= i + 1 < stu.size() ? "," : ""%><% } %>];
                autocomplete(document.getElementById("studentName"), studentName);

        if ($('#errorMsg').length) {
            $('#errorMsg').fadeIn().delay(3000).fadeOut();

        }

    <%
            if (studentName != null) {
    %>
            myPoint = <%=(Integer) request.getAttribute("pointAvail")%>
        action = 'retrieve';
        table = $('#rewardTable').DataTable({
            "responsive": true,
            "iDisplayLength": 7,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "RetrieveRewardServlet",
                "data": {
                    //"tutorID": tutorID,
                    //"branchID": branchID,
                    "action": action,
                    "from": "redeem"
                }
            },
            "columnDefs": [
                {
                    "targets": [0, 1, 2, 3, 4],
                    "data": null,
                    "defaultContent": '',
                    "class": 'details'
                },
                {
                    "targets": 5,
                    "data": null,
                    "defaultContent": '<a class="btn btn-default edit" style="display: block">Redeem</a>',
                    "class": 'details'
                },
                {
                    "targets": 6,
                    "data": null,
                    "defaultContent": null,
                    "class": 'details action'
                }
            ],
            'columns': [
                {"data": "name"},
                {"data": "image",
                    "render": function (data) {

                        return '<img src="data:image/jpeg;base64,' + data + '" width="90px" height="90px" />';

                    }
                },
                {"data": "quantity"},
                {"data": "point"},
                {"data": "description"}
            ],
            "order": [[0, 'asc']]
        });
        $('#rewardTable tbody').on('click', '.edit', function () {
            var confirmDelete = confirm('Redeem this item?');
            
            if (confirmDelete) {
                rewardID = table.row($(this).parents('tr')).data().id;
                point = table.row($(this).parents('tr')).data().point;
                name = table.row($(this).parents('tr')).data().name;
                quantity = table.row($(this).parents('tr')).data().quantity;
                action = 'redeem';
                $.ajax({
                    type: 'POST',
                    url: 'RedeemRewardServlet',
                    dataType: 'JSON',
                    data: {rewardID: rewardID, action: action, studentID: <%=student_id%>, point: point, name: name, quantity: quantity},
                    success: function (data) {
                        if (data) {
                           
                            myPoint = (myPoint - point);
                             $('#lblPoint').text(myPoint);
                             if(data.quantity === 0){
                                 table.row($(this).parents('tr')).remove();
                             }else{
                                 table.cell(table.row($(this).parents('tr')).index(), 2).data(data.quantity).draw();
                             }
                             
                             table.ajax.reload();
                        }

                        /*if (data) {
                         lessonModal.cell(rowIndex, columnIndex).data('Absent' + ' <button id="_present" class="btn btn-default _present">Present</button>').draw();
                         lessonAttendanceTable.cell(lesson_row.index(), 2).data(data.attendance).draw();
                         }*/
                    }
                });
            }

        });
    <%
                    }
    %>

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