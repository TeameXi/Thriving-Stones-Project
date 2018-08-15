<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<%@include file="protect_tutor.jsp"%>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Retrieve or Update Student's Grade</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
        <%           
            Map<Integer, String> classSub = (Map<Integer, String>) request.getAttribute("classSub");
            int studentID = (Integer) request.getAttribute("studentID");
            String studentName = (String) request.getAttribute("studentName");
            request.setAttribute("studentID", studentID);

            if (!classSub.isEmpty()) {
        %>

        <form action="RetrieveUpdateGradesServlet" method="post">
            <input type="hidden" name="studentID" value="${studentID}">
            <div class="form-group">
                <label class="col-lg-2 control-label">Student :</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <label><%= studentName%></label>
                    </div>
                </div>
            </div>
            <br/><br/>

           
            <div class="form-group">
                <label class="col-lg-2 control-label">Subject</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-book"></i></span>
                        <select name = "subjects" class="form-control">
                            <option value="-1" selected>Select Subject</option>
                            <%
                                Set<Integer> classes = classSub.keySet();
                                for (int cls : classes) {
                                    out.println("<option value=" + cls + ">" + classSub.get(cls) + "</option>");
                                }

                            %>
                        </select>
                    </div>
                </div>
            </div>
            <br/><br/>
            
            

            <div class="form-group">
                <label class="col-lg-2 control-label">Level</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                        <select name = "assessmentType" class='form-control'>
                            <option value="-1" selected>Select Assessment Type</option>
                            <option value="CA1">CA1</option>
                            <option value="SA1">SA1</option>
                            <option value="CA2">CA2</option>
                            <option value="SA2">SA2</option>
                        </select>
                    </div>
                </div>
            </div> 
            <br/><br/>
            
            <div class="form-group">
                <label class="col-lg-2 control-label">Grade </label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-star-circle"></i></span>
                        <input name="grade" class="form-control" type="text">
                    </div>
                </div>
            </div>
            <br/><br/>
            
            <div class="form-group">
                <div class="col-lg-2 col-lg-offset-2">
                    <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                    <button type="submit" class="btn btn-default" name="insert" value="insert">Update Grade</button>
                </div>
            </div>
        </form>         

        <%   
            } else {
                out.println("No grades to update, please create grade first!");
            }

        %>  
        </div>
    </div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
