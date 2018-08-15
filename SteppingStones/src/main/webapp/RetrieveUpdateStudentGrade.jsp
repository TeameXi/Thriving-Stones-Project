<%@page import="java.util.LinkedHashMap"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.StudentGrade"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Student"%>

<%@include file="header.jsp"%>
<%@include file="protect_tutor.jsp"%>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Retrieve or Update Student's Grade</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row">
        <form action="RetrieveUpdateGradesServlet" method="post">
            <div class="form-group">
                <label class="col-lg-2 control-label">Student Name:</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                        <input type ="text" name ="studentName" class='form-control'><br>
                        
                    </div>
                </div>
            </div>
            <br/><br/>
            
            <div class="form-group">
                <div class="col-lg-5 col-lg-offset-2">
                    <button type="submit" value = "retrieve" name = "retrieve" class='btn btn-default'>Retrieve</button> 
                    <button type="submit" value = "update" name = "update" class="btn btn-success">Update</button>
                </div>
            </div>
            <br/><br/>

           
           
        </form>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println("<br/><div class='alert alert-danger col-md-5' id='errorMsg'><strong>"+status+"</strong></div>");
            }else{
                String studentName = (String)request.getAttribute("studentName");
                LinkedHashMap<String, ArrayList<String>> gradeLists = (LinkedHashMap<String, ArrayList<String>>) request.getAttribute("gradeLists");
                
                if(gradeLists != null && !gradeLists.isEmpty()){
        %>
                
                    
                    <h4>Tuition Grades Of Student - <strong><%=studentName%></strong></h4><br/>
                 
        <%
                    
                    Set<String> keys = gradeLists.keySet();
                    if(keys != null){
                        out.println("<div class='col-md-5'><table class='table table-bordered'>");
                        out.println("<thead class='table_title'><tr><th>Subject</th><th>Marks</th></tr></thead><tbody>");
                        for(String subject: keys){
                            ArrayList<String> grades = gradeLists.get(subject);
                            out.println("<td>"+subject+"</td>");
                            if(grades != null){
                                for(String grade: grades){
                                    out.println("<td>"+grade+"</td>");
                                }
                            }
                        }
                        out.println("</tbody></table></div>");
                    
                    }
                    
                  
                }
            }
        %>
    </div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script>
    $(document).ready(function () {
        if ($('#errorMsg').length) {
            $('#errorMsg').fadeIn().delay(2000).fadeOut();
        }
    });

</script>
