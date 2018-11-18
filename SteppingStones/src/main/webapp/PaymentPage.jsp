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
        <div class="col-md-1"></div>
        <div class="col-md-9">

            <form id="paymentPage" method="POST" class="form-horizontal" action="PaymentHandlerServlet">

                <%                    
                    if (user != null) {
                        out.println("<input type='hidden' name='branch' value='" + user.getBranchId() + "'/>");
                    }
                    String redirectStudentID = "";    
                    String redirectFrom = "";
                    if (request.getParameter("from") != null) {
                        redirectFrom = request.getParameter("from").trim();
                    }
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
                    double totalOutstandingAmt = PaymentDAO.getStudentTutionFeeToAdd(studentID) + StudentDAO.retrieveStudentOutstandingAmt(studentID);
                    
                    //System.out.println(paymentData.size());
                %>
                Student Name: <label> <%out.println(studentName);%></label><br>
                Level: <label> <%out.println(level);%></label><br>
                Total Outstanding Charges: <label><%out.println("$" + totalOutstandingAmt);%></label><br><br>
                <input type="hidden" value="<%=studentID%>" name="student_id">
                <input type="hidden" value="<%=level%>" name="level">
                 
                
                <%
                    if(paymentData.size() != 0){
                %>
                <div class="form-group">
                    <label class="col-md-3 control-label">Payment Mode</label>  
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                            <select required name="payment_mode" class="form-control" onchange="dynamic(this)" id="payment_mode">
                                <option value="" >Select Payment Mode</option>
                                <option value="Cash">Cash </option>
                                <option value="Cheque">Cheque</option>
                                <option value="Bank Transfer">Bank Transfer</option>
                            </select>
                        </div>
                    </div>
                    
                </div>
                <div id="dynamicField"></div>
                <label>Outstanding Charges</label>
                <div class="table-responsive-sm">
                    <table id="paymentTable" class="table display responsive nowrap" style="width:100%">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col">Details</th>
                                <th scope="col">Due Date</th>
                                <th scope="col">Student Fees</th>
                                <th scope="col">Outstanding Fees</th>
                                <th scope="col">Payment Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                            <%
                                for (Payment payment : paymentData) {
                            %>
                            
                            <%
                                    if(payment.getPaymentType().equals("Reg Fees")){
                                        out.println("<tr><td>" + payment.getDetails() + "</td>");
                                    }else{
                                        out.println("<tr><td>" + payment.getDetails() + " : " + payment.getPaymentType() + "</td>");
                                    }
                                    
                                    
                                    out.println("<td>" + payment.getDueDate() + "</td>");
                                    if (payment.getPaymentType().equals("First Installment") && payment.getChargeAmount() == 0) {
                                        out.println("<td>");
                            %>
                        <input name="<%=payment.getClassID()%>" class="form-control" type="number" step="0.01">
                        <%
                                        out.println("</td><td>-");
                        %>
                        <!--<input name="regFees" id="phone" class="form-control" type="number">-->
                        <%
                                        out.println("</td><td>");
                                    } else if(payment.getPaymentType().equals("Deposit")){
                                        if(redirectFrom.equals("registration")){
                                            out.println("<td>");
                            %>
                        <input name="<%=payment.getClassID()%>" class="form-control" type="text" step="0.01" value="<%=payment.getChargeAmount()%>">
                        <%
                                        }else{
                                            out.println("<td>" + payment.getChargeAmount() + "</td>");
                                        }
                                            out.println("<td><strong>" + payment.getOutstandingCharges() + "</td>");
                                            
                                        //out.println("</td><td>-");
                        %>
                        <!--<input name="regFees" id="phone" class="form-control" type="number">-->
                        <%
                                        out.println("</td><td>");
                                    }else {
                                        out.println("<td>" + payment.getChargeAmount() + "</td>");
                                        out.println("<td><strong>" + payment.getOutstandingCharges() + "</td><td>");
                                    }
                                
                        %>
                        <input type="hidden" value="<%=payment.getClassID()%>" name="classId[]">
                        <input type="hidden" value="<%=payment.getPaymentType()%>" name="paymentType[]">
                        <input type="hidden" value="<%=payment.getDueDate()%>" name="paymentDueDate[]">
                        <input type="hidden" value="<%=payment.getOutstandingCharges()%>" name="outstandingAmount[]">
                        <input type="hidden" value="<%=payment.getNoOfLessons()%>" name="noOfLessons[]">
                        <input type="hidden" value="<%=payment.getDetails()%>" name="subject[]">
                        <input type="hidden" value="<%=payment.getChargeAmount()%>" name="chargeAmount[]">
                        <div class="col-md-6"><input name="paymentAmount[]" id="paymentAmount" class="form-control calculate" type="number" step="0.01"></div>
                        <%        
                                    out.println("</td></tr>");
                                }

                                out.println("<tr><td> </td><td> </td><td> </td><td><label>Total</label></td><td>");
                        %>
                        <div class="col-md-6"><input name="totalAmount" id="totalAmount" class="form-control calculate" type="number" readonly></div>
                        <%        
                                out.println("</td></tr>");
                        %>

                        </tbody> 
                    </table>
                </div>
                <div class="form-group">
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn2" name="update" value="updatePayment">Update Payment</button>
                    </div>
                    <%
                        
                        if (request.getParameter("from") != null) {
                            redirectFrom = request.getParameter("from").trim();
                        }
                        if(redirectFrom.equals("registration")){
                    %>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn1" name="update" value="updateStudentFees">Update Student Fees</button>
                    </div>
                    <%
                            
                        }
                    %>
                    <div class="col-md-3">
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
    function dynamic(selectObject) {
        payment_mode = $("#payment_mode").val();
        var dynamicField = document.getElementById('dynamicField');
        var html = '';
        if(payment_mode === "Cheque" || payment_mode === "Bank Transfer"){
            console.log("Payment Mode " + payment_mode);
            html = '<div class="form-group"><label class="col-lg-3 control-label">Payment Date</label>  <div class="col-lg-4 inputGroupContainer">\n\
                        <div class="input-group"><span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>\n\
                        <input name="payment_date" id="payment_date" class="form-control" type="date"></div></div></div>';;
            
        }else if(payment_mode === "Cash")  {
            html = '';
        }
        //$('#payment_date').validator('update');
        dynamicField.innerHTML = html;
        
    }
        
    $(document).ready(function () {
        $('#paymentTable').dataTable( {
            "paging":   false,
            "ordering": false,
            "info":     false,
            "searching": false
        });

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
////                'paymentAmount[]': {
////                    validators: { 
////                        integer: {
////                            message: 'Integer Only'
////                        }
////                    }
////                },
//                payment_mode: {
//                    validators: {
//                        notEmpty: {
//                            message: 'Please select payment mode'
//                        }
//                    }
//                },
//                payment_date: {
//                    validators: {
//                        notEmpty: {
//                            message: 'Please select payment date'
//                        }
//                    }
//                }
//            }
//        });
    });

</script>


