<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>


    #student_upload_error{
        padding: 20px;
    }

    .bold{
        text-align: center;
        font-weight: 300;
        padding: 10px; 
    }


</style>

<%    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }

%> 
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="CreateStudent.jsp">Add Student </a> / <span class="tab_active">Upload Students</span></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-5 form" id='upload_container'>
            <p class="worth spaced-top-small">Copy &amp; Paste your student data into the "Template"</p>
            <a href="${pageContext.request.contextPath}/assets/StudentTemplate.csv" target="_blank"><span class="excel-bg small_button autowidth">
                    <i class="zmdi zmdi-file-text"></i> Download Template </span></a>
            <p class="worth spaced-top">Upload the Completed Student Template</p>
            <div class="row">    
                <input type="file" name="upload_student_file" id="upload_student_file_id" class="form-control" onchange="checkCSVExtension('upload_student_file_id', 'student_upload_error', 'student_container',<%=branch_id%>)" />
                <div class="row centered spaced-top-small" id = "student_upload_error" name="student_error_txt"></div>
            </div>
        </div>
    </div>
    <div class="row">
        <form  action="UploadStudentServlet" method="Post">
            <input type="hidden" name="branch" value="<%=branch_id%>" />
            <div id="student_container"></div>
        </form>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/styling/js/jquery.fileupload.js"></script>
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.10.8/xlsx.full.min.js">
</script>

<script>
    $scope.ExcelExport= function (event) {

      var file_upload_el = $("#" + file_upload_id);
    var error_el = $("#" + error_lbl_id);
    var file_name = file_upload_el.prop("files")[0]["name"];
    var file_extension = file_name.split('.').pop();
    
    var reader = new FileReader();
    reader.onload = function(){
        var fileData = reader.result;
        var wb = XLSX.read(fileData, {type : 'binary'});

        wb.SheetNames.forEach(function(sheetName){
        var rowObj =XLSX.utils.sheet_to_row_object_array(wb.Sheets[sheetName]);
        var jsonObj = JSON.stringify(rowObj);
        console.log(jsonObj)
        })
    };
    reader.readAsBinaryString(file_name);
    };
</script>-->