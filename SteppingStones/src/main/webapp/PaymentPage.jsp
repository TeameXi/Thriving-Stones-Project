<%-- 
    Document   : PaymentPage
    Created on : 14 Sep, 2018, 8:09:18 PM
    Author     : DEYU
--%>
<%@page import="model.PaymentDAO"%>
<%@page import="entity.Payment"%>
<%@page import="model.StudentDAO"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.18/datatables.min.css"/>
<div class="col-md-10">

    <div style="text-align: center;margin: 20px;"><span class="tab_active">Make Payment</span></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">

            <form id="paymentPage" method="POST" class="form-horizontal" action="PaymentHandlerServlet">

                <%                    
                    if (user != null) {
                        out.println("<input type='hidden' name='branch' value='" + user.getBranchId() + "'/>");
                    }
                    String redirectStudentID = "";
                    if (request.getParameter("studentID") != null) {
                        redirectStudentID = request.getParameter("studentID").trim();
                    }
                    int studentID = 0;
                    if (redirectStudentID != null && !redirectStudentID.isEmpty()) {
                        studentID = Integer.parseInt(redirectStudentID);
                    }
                    String studentName = StudentDAO.retrieveStudentName(studentID);
                    int levelID = StudentDAO.retrieveStudentLevelbyID(studentID, branch_id);
                    String level = LevelDAO.retrieveLevel(levelID);
                    ArrayList<Payment> paymentData = new ArrayList<>();
                    PaymentDAO.getStudentRegFeesData(studentID, paymentData);
                    PaymentDAO.getStudentDepositData(studentID, paymentData);
                    PaymentDAO.getStudentTutionFeesData(studentID, paymentData);
                    //System.out.println(paymentData.size());
                %>
                Student Name: <label> <%out.println(studentName);%></label><br>
                Level: <label> <%out.println(level);%></label><br><br>
                <input type="hidden" value="<%=studentID%>" name="student_id">
                <input type="hidden" value="<%=level%>" name="level">
                <%
                    if(paymentData.size() != 0){
                %>
                <label>Outstanding Charges</label>
                <div class="table-responsive-sm">
                    <table id="paymentTable" class="table display responsive nowrap" style="width:100%">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col">Account Type</th>
                                <th scope="col">Details</th>
                                <th scope="col">Due Date</th>
                                <th scope="col">Charge Amount</th>
                                <th scope="col">Outstanding Charge(s)</th>
                                <th scope="col">Payment Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                        
                            <%
                                for (Payment payment : paymentData) {
                                    out.println("<tr><td>" + payment.getPaymentType() + "</td>");
                                    out.println("<td>" + payment.getDetails() + "</td>");
                                    out.println("<td>" + payment.getDueDate() + "</td>");
                                    if (payment.getPaymentType().equals("First Installment") && payment.getChargeAmount() == 0) {
                                        out.println("<td>");
                            %>
                        <input name="<%=payment.getClassID()%>" class="form-control" type="number" step="0.01">
                        <%
                                        out.println("</td><td>");
                        %>
                        <!--<input name="regFees" id="phone" class="form-control" type="number">-->
                        <%
                                        out.println("</td><td>");
                                    } else {
                                        out.println("<td>" + payment.getChargeAmount() + "</td>");
                                        out.println("<td>" + payment.getOutstandingCharges() + "</td><td>");
                                    }
                                
                        %>
                        <input type="hidden" value="<%=payment.getClassID()%>" name="classId[]">
                        <input type="hidden" value="<%=payment.getPaymentType()%>" name="paymentType[]">
                        <input type="hidden" value="<%=payment.getDueDate()%>" name="paymentDueDate[]">
                        <input type="hidden" value="<%=payment.getOutstandingCharges()%>" name="outstandingAmount[]">
                        <input type="hidden" value="<%=payment.getNoOfLessons()%>" name="noOfLessons[]">
                        <input type="hidden" value="<%=payment.getDetails()%>" name="subject[]">
                        <input type="hidden" value="<%=payment.getChargeAmount()%>" name="chargeAmount[]">
                        <input name="paymentAmount[]" id="paymentAmount" class="form-control calculate" type="number" step="0.01">
                        <%        
                                    out.println("</td></tr>");
                                }

                                out.println("<tr><td> </td><td> </td><td> </td><td> </td><td><label>Total</label></td><td>");
                        %>
                        <input name="totalAmount" id="totalAmount" class="form-control calculate" type="number" readonly>
                        <%        
                                out.println("</td></tr>");
                        %>

                        </tbody> 
                    </table>
                </div>
                <div class="form-group">
                    <div>
                        <button type="submit" class="btn btn-default" name="select" value="select">Update Payment</button>
                    </div>
                </div>
            </form>
            <%        
                            }else{
                                out.println("<label>" + studentName + " has no outstanding fees.</label>");
                            }
            %>

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
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

<script>
    $(document).ready(function () {
        $('#paymentTable').dataTable( {
            "paging":   false,
            "ordering": false,
            "info":     false,
            "searching": false
        } );

//        var chargeAmount = $("input[name='chargeAmount[]']").map(function(){return $(this).val();}).get();
//        console.log("Amount " + chargeAmount);
        
        $('.calculate').keyup(function () {
            var values = $("input[name='paymentAmount[]']").map(function(){return $(this).val();}).get();    
            var total = 0;
            for (var i = 0; i < values.length; i++) {
                var paymentAmount = parseFloat(values[i]);
                if(isNaN(paymentAmount)){   
                }else{
                    //console.log(paymentAmount > parseFloat(chargeAmount[i]));
                    var total = total + paymentAmount;
                }
            }
            $('#totalAmount').val(total);
        });
        
        
        
//        $('#paymentPage').bootstrapValidator({
//        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
//            feedbackIcons: {
//                valid: 'glyphicon glyphicon-ok',
//                invalid: 'glyphicon glyphicon-remove',
//                validating: 'glyphicon glyphicon-refresh'
//            },
//            fields: {
//                'paymentAmount[]': {
//                    validators: { 
//                        integer: {
//                            message: 'Integer Only'
//                        }
//                    }
//                }
//            }
//        });
    });

</script>


