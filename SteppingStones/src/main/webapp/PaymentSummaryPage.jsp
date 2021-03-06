<%-- 
    Document   : PaymentSummaryPage
    Created on : 17 Nov, 2018, 12:57:30 PM
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

    <div id="header" style="text-align: center;margin: 10px;">Student Payment - <span class="tab_active" style="font-size: 14px">View 1</span>/<a href="StudentPaymentStatus.jsp">View 2</a></div>
    <div class="row">
     
        <div class="col-md-10">
                <%                    
                    ArrayList<Payment> duePaymentData = new ArrayList<>();
                    PaymentDAO.getAllDueRegFees(duePaymentData);
                    PaymentDAO.getAllDueStudentDeposit(duePaymentData);
                    PaymentDAO.getAllDueStudentTutionFees(duePaymentData);

                    ArrayList<Payment> paymentData = new ArrayList<>();
                    PaymentDAO.getAllRegFees(paymentData);
                    PaymentDAO.getAllStudentDeposit(paymentData);
                    PaymentDAO.getAllStudentTutionFees(paymentData);
                %>
                
                <div class="table-responsive-sm">
                    <table id="paymentTable" class="table display nowrap" style="width:100%">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col">Student Name</th>
                                <th scope="col">Details</th>
                                <th scope="col">Due Date</th>
                                <th scope="col">Outstanding Fees</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            
                            <%
                            if(duePaymentData.size() != 0){
                                for (Payment payment : duePaymentData) {
                                    int studentID = payment.getStudentID();
                                    String studentName = StudentDAO.retrieveStudentName(studentID);
                                    out.println("<tr><td>" + studentName + "</td>");
                                    
                                    if(payment.getPaymentType().equals("Reg Fees")){
                                        out.println("<td>" + payment.getDetails() + "</td>");
                                    }else{
                                        out.println("<td>" + payment.getDetails() + " : " + payment.getPaymentType() + "</td>");
                                    }

                                    out.println("<td>" + payment.getDueDate() + "</td>");
                                    out.println("<td>" + payment.getOutstandingCharges() + "</td>");
                                    out.println("<td>");
                                    %>
                    
                        <button type="submit" class="btn btn-danger" name="details" value="<%=studentID%>" onClick="m(this.value)">Details</button>
                    
                                    <%
                                    out.println("</td></tr>");
                                    
                                }
                            }
                            if(paymentData.size() != 0){
                                for (Payment payment : paymentData) {
                                    int studentID = payment.getStudentID();
                                    String studentName = StudentDAO.retrieveStudentName(studentID);
                                    out.println("<tr><td>" + studentName + "</td>");
                                    
                                    if(payment.getPaymentType().equals("Reg Fees")){
                                        out.println("<td>" + payment.getDetails() + "</td>");
                                    }else{
                                        out.println("<td>" + payment.getDetails() + " : " + payment.getPaymentType() + "</td>");
                                    }

                                    out.println("<td>" + payment.getDueDate() + "</td>");
                                    out.println("<td>" + payment.getOutstandingCharges() + "</td>");
                                    out.println("<td>");
                                    %>
                        <button type="submit" class="btn btn1" name="details" value="<%=studentID%>" onClick="m(this.value)">Details</button>
                                    <%
                                    out.println("</td></tr>");
                                    
                                }
                            }

                        %>

                        </tbody> 
                    </table>
                </div>
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


<script>
        
    $(document).ready(function () {
        $('#paymentTable').dataTable( {
            "paging":   true,
            "ordering": false,
            "info":     true,
            "searching": true,
            "iDisplayLength": 8
        });
    });
    
    function m(studentID) {
        window.location.assign("PaymentPage.jsp?studentID="+studentID);
        alert(value);
    }

</script>



