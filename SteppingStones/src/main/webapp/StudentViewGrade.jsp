<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_student.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.18/datatables.min.css"/>
<style>
    td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/list_metro.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/close.png') no-repeat center center;
    }

    .btn{
        background-color: #9ccbce;
        color:white;
    }

    .innerTable{
        padding-left:50px;
        padding-right:50px;

    }

    hr { 
        display: block;
        margin-top: 0.1em;
        margin-bottom: 0.1em;
        margin-left: auto;
        margin-right: auto;
        border-style: inset;
        border-width: 1.5px;
        border-color:black;
        width:90%;
    }

    .form-control.numberField{
        width:77%;
        margin-left:10px;
        height:28px;
    }
    
    #improvementLbl{
        color: salmon;
    }
    
    .remarkContainer{
        background-color: #fff;
    }
/*    #child_table tr:nth-child(even) {background: #FFF}*/

    @media screen and (max-width: 480px){
        body{
            font-size: 10px;
        } 
        .form-control{
            font-size: 10px;
        }
        a {
            font-size: 10px;
        }
    }
    @media screen and (min-width:481px) and (max-width: 767px) {
        body{
            font-size: 12px !important;
        } 
        .form-control{
            font-size: 12px !important;
        }
        a {
            font-size: 12px ;
        }
    }
    @media screen and (min-width:768px) and (max-width: 991px) {
        #gradeTable_filter{
            float: right;
            margin-top: -39px;
        }
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">My Grades </span></h5></div>

    <div class="table-responsive-sm">
        <table id="gradeTable" class="table display responsive nowrap" style="width:100%">
            <thead class="thead-light">
                <tr>
                    <th scope="col"></th>
                    <th scope="col">ID</th>
                    <th scope="col">Level</th>
                    <th scope="col">Subject</th>
                    <th scope="col">Class Timing</th>
                </tr>
            </thead>
            <tbody>
                <%                    
                    ArrayList<Class> classes = ClassDAO.getStudentEnrolledClass(user.getRespectiveID());
                    for (Class cls : classes) {

                        out.println("<tr><td class='details-control'></td><td>" + cls.getClassID() + "</td><td>" + cls.getLevel() + "</td><td>" + cls.getSubject() + "</td><td>" + cls.getStartTime() + "-" + cls.getEndTime() + " ( " + cls.getClassDay() + " )");
                        request.setAttribute("ClassID", cls.getClassID());
                    }
                %>
            </tbody> 
        </table>
    </div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>

<script>
    $(document).ready(function () {
        var table = $('#gradeTable').DataTable({
            "columns": [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {"data": "ID"},
                {"data": "Level"},
                {"data": "Subject"},
                {"data": "Class Timing"}
            ],
            "columnDefs": [
                {
                    "targets": [1],
                    "visible": false,
                    "searchable": false
                }
            ],
            "order": [[1, 'asc']]
        });




        // Add event listener for opening and closing details
        $('#gradeTable').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                // Open this row  
                format(row.child, row.data());
                tr.addClass('shown');
            }
        });

        function drawTable(data) {
            var htmlTable = "";
            var thead = '<col></col>' +
                    '<colgroup span="2"></colgroup>' +
                    '<colgroup span="2"></colgroup>' +
                    '<colgroup span="2"></colgroup>' +
                    '<colgroup span="2"></colgroup>' +
                    '<tr>' +
                    '<td rowspan="2"></td>' +
                    '<th colspan="2" scope="colgroup">CA1</th>' +
                    '<th colspan="2" scope="colgroup">SA1</th>' +
                    '<th colspan="2" scope="colgroup">CA2</th>' +
                    '<th colspan="2" scope="colgroup">SA2</th>' +
                    '</tr>' +
                    '<tr>' +
                    '<th scope="col">School</th>' +
                    '<th scope="col">Tuition</th>' +
                    '<th scope="col">School</th>' +
                    '<th scope="col">Tuition</th>' +
                    '<th scope="col">School</th>' +
                    '<th scope="col">Tuition</th>' +
                    '<th scope="col">School</th>' +
                    '<th scope="col">Tuition</th>'
            '</tr>';


            var tbody = '';
            $.each(data, function (i, d) {
               
                CA1_0_arr = d.CA1_0;
                SA1_0_arr = d.SA1_0;
                CA2_0_arr = d.CA2_0;
                SA2_0_arr = d.SA2_0;
                
                CA1_1_arr = d.CA1_1;
                SA1_1_arr = d.SA1_1;
                CA2_1_arr = d.CA2_1;
                SA2_1_arr = d.SA2_1;
                
                // SA1 after CA1
                SA1_1_improvement_rate = d.SA1_1_improvement_rate+"%";
                if(SA1_1_improvement_rate === "0%"){
                    SA1_1_improvement_rate = "Nil";
                }
                SA1_0_improvement_rate = d.SA1_0_improvement_rate+"%";
                if(SA1_0_improvement_rate === "0%"){
                    SA1_0_improvement_rate = "Nil";
                }
                
                // CA2 after SA1
                CA2_1_improvement_rate = d.CA2_1_improvement_rate+"%";
                if(CA2_1_improvement_rate === "0%"){
                    CA2_1_improvement_rate = "Nil";
                }
                
                CA2_0_improvement_rate = d.CA2_0_improvement_rate+"%";
                if(CA2_0_improvement_rate === "0%"){
                    CA2_0_improvement_rate = "Nil";
                }
                
                SA2_1_improvement_rate = d.SA2_1_improvement_rate+"%";
                if(SA2_1_improvement_rate === "0%"){
                    SA2_1_improvement_rate = "Nil";
                }
                
                SA2_0_improvement_rate = d.SA2_0_improvement_rate+"%";
                if(SA2_0_improvement_rate === "0%"){
                    SA2_0_improvement_rate = "Nil";
                }
                

                tbody += '<tr><th scope="row">' + d.studentName + '</th>' +
                        '<td>' + CA1_1_arr+ '</td>' +
                        '<td>' + CA1_0_arr + '</td>' +
//                          '<td>' + CA1_1_arr+ '</td>' +
//                        '<td>' + CA1_0_arr + '</td>' +
//                          '<td>' + CA1_1_arr+ '</td>' +
//                        '<td>' + CA1_0_arr + '</td>' +
//                          '<td>' + CA1_1_arr+ '</td>' +
//                        '<td>' + CA1_0_arr + '</td>';
                        '<td>' + SA1_1_arr + '</td>' +
                        '<td>' + SA1_0_arr + '</td>' +
                        '<td>' + CA2_1_arr + '</td>' +
                        '<td>' + CA2_0_arr + '</td>' +
                        '<td>' + SA2_1_arr + '</td>' +
                        '<td>' + SA2_0_arr + '</td>';
                
                tbody += '<tr class="remarkContainer"><th id="improvementLbl">% Improvement</th>'+
                        '<td>'+"Nil"+'</td>'+
                        '<td>'+"Nil"+'</td>'+
                        '<td>'+SA1_1_improvement_rate +'</td>'+
                        '<td>'+SA1_0_improvement_rate+'</td>'+
                        '<td>'+CA2_1_improvement_rate+'</td>'+
                        '<td>'+CA2_0_improvement_rate+'</td>'+
                        '<td>'+SA2_1_improvement_rate+'</td>'+
                        '<td>'+SA2_0_improvement_rate+'</td>'+
                        '</tr>';
                
                tbody += '<tr class="remarkContainer"><td>Comment</td><td colspan="2">-</td><td colspan="2"></td><td colspan="2"></td><td colspan="2"></td></tr>';
      

            });

            htmlTable += '<form class="dynamicTable"><table id="child_table" class="table table-bordered" style="background-color:#e7e7e7">' + thead + tbody + '</table></form>';

            return htmlTable;
        }




        function format(callback, data) {

            $.ajax({
                url: 'StudentViewGradeServlet',
                data: {class_id: data["ID"],student_id: <%=user.getRespectiveID()%>},
                dataType: "json",
                complete: function (response) {

                    var responseData = JSON.parse(response.responseText);
                    console.log(responseData);
                    var html = "";
                    if (data === -1) {
                        html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                        callback(html).show();
                    } else if (responseData.length <= 0) {
                        html = '<div class="alert alert-warning col-md-5"><strong>No Student in this class Yet!</strong></div>';
                        callback(html).show();
                    } else {



                        var gradeContainer = '<div class="innerTable"><h4 style="text-align:center;">Grade Results</h4><br/><div class="statusMsg"></div>';

                        if (callback) {
                            callback(gradeContainer + drawTable(responseData) + "</div>").show();
                        }

                        // Update the grade
                        $("#UpdateGradeBtn").on("click", function () {
                            student_id = $('input[name="studentId[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            ca1_tuition_top = $('input[name="CA1_0_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            
                            ca1_tuition_base = $('input[name="CA1_0_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            sa1_tuition_top = $('input[name="SA1_0_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            
                            sa1_tuition_base = $('input[name="SA1_0_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            ca2_tuition_top = $('input[name="CA2_0_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            ca2_tuition_base = $('input[name="CA2_0_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            sa2_tuition_top = $('input[name="SA2_0_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            sa2_tuition_base = $('input[name="SA2_0_base[]"]').map(function () {
                                return $(this).val();
                            }).get();


                            // For School
                           ca1_school_top = $('input[name="CA1_1_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            
                            ca1_school_base = $('input[name="CA1_1_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            sa1_school_top = $('input[name="SA1_1_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            
                            sa1_school_base = $('input[name="SA1_1_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            ca2_school_top = $('input[name="CA2_1_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            ca2_school_base = $('input[name="CA2_1_base[]"]').map(function () {
                                return $(this).val();
                            }).get();

                            sa2_school_top = $('input[name="SA2_1_top[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            sa2_school_base = $('input[name="SA2_1_base[]"]').map(function () {
                                return $(this).val();
                            }).get();
                            
                            var studArr = [];
                            for (i = 0; i < student_id.length; i++) {
                                var stdObj = {"student_id": student_id[i],
                                    "class_id": data["ID"],
                                    "CA1_0_top": ca1_tuition_top[i],
                                    "CA1_0_base": ca1_tuition_base[i],
                                    "SA1_0_top": sa1_tuition_top[i],
                                    "SA1_0_base": sa1_tuition_base[i],
                                    "CA2_0_top": ca2_tuition_top[i],
                                    "CA2_0_base": ca2_tuition_base[i],
                                    "SA2_0_top": sa2_tuition_top[i],
                                    "SA2_0_base": sa2_tuition_base[i],
                                    "CA1_1_top": ca1_school_top[i],
                                    "CA1_1_base": ca1_school_base[i],
                                    "SA1_1_top": sa1_school_top[i],
                                    "SA1_1_base": sa1_school_base[i],
                                    "CA2_1_top": ca2_school_top[i],
                                    "CA2_1_base": ca2_school_base[i],
                                    "SA2_1_top": sa2_school_top[i],
                                    "SA2_1_base": sa2_school_base[i]};
                                studArr[i] = stdObj;
                            }
                        });
                    }
                },
                error: function () {
                    $('#output').html('There was an error!');
                }
            });
        }
    });


</script>
