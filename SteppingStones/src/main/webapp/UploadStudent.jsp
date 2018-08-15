<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
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

<%    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }

%> 
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="CreateStudent.jsp">Add Student </a> / <a class="active" href="UploadStudent.jsp">Upload Students</a></h5></div>
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
<script>
                    var Password = {

                        _pattern: /[a-zA-Z0-9_\-\+\.]/,

                        _getRandomByte: function ()
                        {
                            if (window.crypto && window.crypto.getRandomValues)
                            {
                                var result = new Uint8Array(1);
                                window.crypto.getRandomValues(result);
                                return result[0];
                            } else if (window.msCrypto && window.msCrypto.getRandomValues)
                            {
                                var result = new Uint8Array(1);
                                window.msCrypto.getRandomValues(result);
                                return result[0];
                            } else
                            {
                                return Math.floor(Math.random() * 256);
                            }
                        },

                        generate: function (length)
                        {
                            return Array.apply(null, {'length': length})
                                    .map(function ()
                                    {
                                        var result;
                                        while (true)
                                        {
                                            result = String.fromCharCode(this._getRandomByte());
                                            if (this._pattern.test(result))
                                            {
                                                return result;
                                            }
                                        }
                                    }, this)
                                    .join('');
                        }
                    };

                    function generatePassword(len) {
                        var pwd = Password.generate(len);
                        return pwd;
                    }
</script>
