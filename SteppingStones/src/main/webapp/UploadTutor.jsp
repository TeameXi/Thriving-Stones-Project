<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
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

<%    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }

%> 
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="CreateTutor.jsp">Add Tutor </a> / <span class="tab_active">Upload Tutor</span></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-5 form" id='upload_container'>
            <p class="worth spaced-top-small">Copy &amp; Paste your tutor data into the "Template"</p>
            <a href="${pageContext.request.contextPath}/assets/TutorTemplate.csv" target="_blank"><span class="excel-bg small_button autowidth">
                    <i class="zmdi zmdi-file-text"></i> Download Template </span></a>
            <p class="worth spaced-top">Upload the Completed Tutor Template</p>
            <div class="row">    
                <input type="file" name="upload_tutor_file" id="upload_tutor_file_id" class="form-control" onchange="checkCSVExtension('upload_tutor_file_id', 'tutor_upload_error', 'tutor_container', 'tutor',<%=0%>)" />
                <div class="row centered spaced-top-small" id = "tutor_upload_error" name="tutor_error_txt"></div>
            </div>
        </div>
    </div>

    <div class="row">
        <form  action="UploadTutorServlet" method="Post">
            <%  
                
                
               
                if (user != null && user.getBranchId() != 0) {
                  
                    BranchDAO branchDao = new BranchDAO();
                    Branch branch = branchDao.retrieveBranchById(user.getBranchId());
            %>
            <div class="form-group" id="branchContainer">
                <div class="col-lg-3"></div>  
                <div class="col-lg-5 inputGroupContainer">
                    <div class="input-group">
                        <label><span><i class="zmdi zmdi-city">Branch : </i></span></label>
                        <label><%=branch.getName()%></label>
                    </div>
                </div>
            </div>

            <%
                }
            %>
            <br/>
            <div id="tutor_container"></div>
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

    $(function () {
        $('#branchContainer').hide();
    });
</script>

