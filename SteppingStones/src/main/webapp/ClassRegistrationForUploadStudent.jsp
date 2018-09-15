<%-- 
    Document   : ClassRegistrationForUploadStudent
    Created on : 13 Sep, 2018, 9:01:40 PM
    Author     : Riana
--%>

<%@page import="model.ClassDAO"%>
<%@page import="entity.Student"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<style>
    .multiselect-container>li>a>label {
        padding: 4px 20px 3px 20px;
    }
</style>
<%    String student_creation_status = (String) request.getAttribute("creation_status");
    if (student_creation_status != null) {
        if (student_creation_status == "true") {
            out.println("<div id='creation_status' class='row alert alert-danger col-md-12'><strong>Something Went wrong!</strong> </div>");
        } else {
            out.println("<div id='creation_status' class='row alert alert-success col-md-12'><strong>Student record is inserted !</strong> </div>");
        }
    }

    ArrayList<Student> duplicatedUsers = (ArrayList) session.getAttribute("existingUserLists");
    if (duplicatedUsers != null) {
        if (duplicatedUsers.size() > 0) {
            String temp = duplicatedUsers.get(0).getName();
            for(int i=1;i<duplicatedUsers.size();i++){
                temp = ", " + temp;
            }
            out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following students already <strong>( " + temp + " )</strong> exist;</div>");
            session.removeAttribute("existingUserLists");
        }
    }

    ArrayList<String> duplicatedParents = (ArrayList) session.getAttribute("existingParentLists");
    if (duplicatedParents != null) {
        if (duplicatedParents.size() > 0) {
            out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following parents already <strong>( " + String.join(",", duplicatedParents) + " )</strong> exist;</div>");
            session.removeAttribute("existingParentLists");
        }
    }
    ArrayList<Student> insertedStudent = (ArrayList) session.getAttribute("insertedStudent");
%>
<div class="col-md-10">
    <div class="row" id="errorMsg"></div>
    <div class="row">
        <div style="text-align: center;margin: 20px;"><span class="tab_active">Class Registration</span></div>
        <div class='row'>
            <div class='col-sm-1'></div>
            <div class='col-sm-2 bold'>Student Name</div>
            <div class='col-sm-3 bold'>Level</div>
            <div class='col-sm-3 bold'>Class</div>
            <div class='col-sm-1'></div>
        </div>
        <form id="ClassRegistrationForm" method="POST" class="form-horizontal" action="ClassRegistrationForUploadServlet">
            <div class="row">

                <%
                    ClassDAO classes = new ClassDAO();
                    ArrayList<Class> classList = classes.listAllClasses(branch_id);
                    for (int i = 0; i < insertedStudent.size(); i++) {
                        Student s = insertedStudent.get(i);
                        out.println(
                                "<div class='row' rel='" + i + "' id='row_con_" + i + "'>"
                                + "<div class='col-sm-1 bold'></div>"
                                + "<div class='col-sm-2'>"
                                + "<input type='hidden' name='arr_id[]' id='arr_id_" + i + "' class='form-control' value='" + s.getStudentID() + "'>"
                                + "<input type='text' name='arr_name[]' id='arr_name_" + i + "' class='form-control' value = '" + s.getName() + "' readonly='readonly'>"
                                + "</div>"
                                + "<div class='col-sm-3'>"
                                + "<input type='text' name='arr_level[]' id='arr_level_" + i + "' class='form-control' value='" + s.getLevel() + "' readonly='readonly'>"
                                + "</div>"
                                + "<div class='col-sm-3'>"
                                + "<select id='arr_class_" + i + "' name='arr_class[]' multiple='multiple'>");
                        for (Class c : classList) {
                            if (c.getLevel().equals(s.getLevel())) {
                                out.println("<option value='" + c.getClassID() + "'>" + c.getLevel() + " " + c.getSubject() + " (" + c.getClassDay() + " " + c.getClassTime() + ")</option>");
                            }
                        }
                        out.println("</select>"
                                + "</div>"
                                + "<div class='col-sm-1'></div></div><br/><br/>"
                                + "<script type='text/javascript'>$(document).ready(function() {$('#arr_class_" + i + "').multiselect({includeSelectAllOption: true});})</script>");

                    }
                %>
            </div>
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
</div>

<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="styling/js/bootstrap-multiselect.js" type="text/javascript"></script>
<link href="styling/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css"/>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
