<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<style>
    

#tutor_upload_error{
    padding: 20px;
}

.bold{
    text-align: center;
    font-weight: 300;
    padding: 10px; 
}


</style>

<%  
    
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }
 
%> 
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="CreateNewStudent.jsp">Add Student </a> / <a class="active" href="UploadStudent.jsp">Upload Students</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-5 form" id='upload_container'>
            <p class="worth spaced-top-small">Copy &amp; Paste your student data into the "Template"</p>
            <a href="${pageContext.request.contextPath}/assets/StudentTemplate.csv" target="_blank"><span class="excel-bg small_button autowidth">
                <i class="zmdi zmdi-file-text"></i> Download Template </span></a>
            <p class="worth spaced-top">Upload the Completed Student Template</p>
            <div class="row">    
                <input type="file" id="upload_student_file_id" class="form-control" onchange="checkCSVExtension('upload_student_file_id','student_upload_error','student_container','student')" />
                <div class="row centered spaced-top-small" id = "student_upload_error"></div>
            </div>
        </div>
    </div>
    <div class="row">
        <form  action="UploadTutorServlet" method="Post">
            <div id="student_container"></div>
        </form>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/styling/js/jquery.fileupload.js"></script>

