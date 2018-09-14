<%-- 
    Document   : ClassRegistrationDetailsForUpload
    Created on : 14 Sep, 2018, 12:10:09 AM
    Author     : Riana
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Student"%>
<%@page import="entity.Class"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<%
    Map<String,List<String>> idWithClasses = (HashMap<String,List<String>>) request.getAttribute("idWithClasses");
%>
<div class="col-md-10">
     <div class="row" id="errorMsg"></div>
     <div style="text-align: center;margin: 20px;"><span class="tab_active">Class Registration Details</span></div>
    <div class='row'>
        <div class='col-sm-1'></div>
        <div class='col-sm-1 bold'>Student Name</div>
        <div class='col-sm-3 bold'>Class</div>
        <div class='col-sm-1 bold'>Deposit Fees</div>
        <div class='col-sm-1 bold'>Outstanding Deposit Fees</div>
        <div class='col-sm-1 bold'>Monthly Fees</div>
        <div class='col-sm-1 bold'>Outstanding Tuition Fees</div>
        <div class='col-sm-2 bold'>Join Date</div>
        <div class='col-sm-1'></div>
    </div>
     <form id="ClassRegistrationForm" method="POST" class="form-horizontal" action="ClassRegistrationDetailsForUploadServlet">
         <input type="hidden" value="<%=branch_id%>" name="branch_id"/>
     <%
         ClassDAO classDAO = new ClassDAO();
         int count = 0;
         for (String key : idWithClasses.keySet()) {
            Student s =  StudentDAO.retrieveStudentbyID(Integer.parseInt(key.trim()));
            List<String> classIdList = idWithClasses.get(key);
            List<Class> classList = new ArrayList<Class>();
            for(String temp: classIdList){
                if(temp != null || !temp.isEmpty()){
                    classList.add(ClassDAO.getClassByID(Integer.parseInt(temp.trim())));
                }
            }
            for(int i = 0; i<classList.size();i++){
                Class c = classList.get(i);
                out.println("<div class='row' rel='" + count + "' id='row_con_" + count + "'>"
                        + "<div class='col-sm-1 bold'></div>"
                        + "<div class='col-sm-1'>"
                        + "<input type='hidden' name='arr_id[]' id='arr_id_" + count + "' class='form-control' value='" + s.getStudentID() + "'/>"
                        + "<input type='text' name='arr_name[]' id='arr_name_" + count + "' class='form-control' value = '" + s.getName() + "' readonly='readonly'>"
                        + "</div>"
                        + "<div class='col-sm-3'>"
                        + "<input type='hidden' name='arr_classid[]' id='arr_classid_" + count + "' class='form-control' value='" + c.getClassID()+ "'/>"
                        + "<input type='text' id='arr_class_" + count + "' class='form-control' value = '" + c.getLevel() + " " + c.getSubject() + " (" + c.getClassDay() + " " + c.getClassTime() + ")' readonly='readonly'>"
                        + "</div>"
                        + "<div class='col-sm-1'>"
                        + "<input type ='number' name ='arr_deposit[]' id='arr_deposit_" + count + "' class='form-control' required>"
                        + "</div>"
                        + "<div class='col-sm-1'>"
                        + "<input type ='number' name ='arr_outstandingdeposit[]' id='arr_outstandingdeposit_" + count + "' class='form-control' required>"
                        + "</div>"
                        + "<div class='col-sm-1'>"
                        + "<input type ='number' name ='arr_monthly[]' id='arr_monthly_" + count + "' value = '"+c.getMthlyFees()+"'class='form-control' readonly='readonly'>"
                        + "</div>"
                        + "<div class='col-sm-1'>"
                        + "<input type ='number' name ='arr_outstandingTuition[]' id='arr_outstandingTuition_" + count + "' class='form-control' required>"
                        + "</div>"
                        + "<div class='col-sm-2'>"
                        + "<input name='arr_joinDate[]' type='text' class='form-control' id ='arr_joinDate_" + count + "' placeholder='YYYY-MM-DD' required>"
                        + "<script type='text/javascript'>$(document).ready(function() {$('#arr_joinDate_" + count + "').datetimepicker({format: 'YYYY-MM-DD'})});</script>"
                        + "</div>"
                        + "<div class='col-sm-1'></div></div><br/><br/>");
                count++;
            }
         }
     %>
     <div class="form-group">
        <div class="col-lg-9 col-lg-offset-3">
            <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
            <button type="submit" class="btn btn-default">Submit</button> 
        </div>
    </div>
     </form>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="styling/js/bootstrap-multiselect.js" type="text/javascript"></script>
<link href="styling/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css"/>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

