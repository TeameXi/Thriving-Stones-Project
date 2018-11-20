<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<div class="col-md-10">
    <%
        String student = request.getParameter("studentID");
        int studentId = 0;
        if(student != null){
            studentId = Integer.parseInt(student);
        }else{
            response.sendRedirect("DisplayStudents.jsp");
        }
    %>
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Latest Performance  <label id="studentNameId"></label> </span></div>
    <div class="row">
        <div style="display: none;" id='status' class='row alert alert-warning col-md-12'><strong>Sorry, there is no comparison for grade since there is no latest grade belong to that student!</strong> </div>
        <div class='col-md-6'>
          <div id="chart1"></div> 
        </div>
        
        <div class='col-md-6'>
          <div id="chart2"></div> 
        </div>
        
        <div class='col-md-6'>
          <div id="chart3"></div> 
        </div>
        
        <div class='col-md-6'>
          <div id="chart4"></div> 
        </div>
        
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>

<script>
    $(document).ready(function () {
        var studentId = 0;
        studentId = <%=studentId%>;
        $.ajax({
            type: 'POST',
            url: 'GradeTrackingServlet',
            dataType: 'JSON',
            data: {studentID: studentId},
            success: function (data) {
                if(data.length <= 0){
                    $("#status").css("display","block");
                }
                $("#studentNameId").text(" of "+data[0].name);
                for(i=0; i <data.length;i++){
                    var gradeForEachSubjectAtEachlvl = data[i];
                    var series = gradeForEachSubjectAtEachlvl.series;
//                    console.log(series);
                    var basicInfo = gradeForEachSubjectAtEachlvl.basicInfo;
//                    console.log(basicInfo);
                    
                    chart =  new Highcharts.Chart('chart'+(i+1),{
                        title: {
                            text: 'Grades For '+basicInfo
                        },
                        xAxis: {
                            categories: ['CA1','SA1','CA2', 'SA2']
                        },
                        yAxis: {
                            min: 0,
                            title: {
                                text: 'Scores'
                            }
                        },
                        series: series
                    });
                }
            }
        });
    });
</script>