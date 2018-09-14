<%@page import="model.StudentGradeDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_tutor.jsp"%>
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

    .table .thead-light th {
        color: #fff;
        background-color: #f7a4a3;
        border-color: #792700;
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


</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Students Grades </span></h5></div>

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
                    ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(user_id, branch_id);
                    for (Class cls : classes) {

                        out.println("<tr><td class='details-control'></td><td>" + cls.getClassID() + "</td><td>" + cls.getLevel() + "</td><td>" + cls.getSubject() + "</td><td>" + cls.getClassTime() + " ( " + cls.getClassDay() + " )");
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


<!--Validation-->
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
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
                CA1_0_arr = d.CA1_0.split("/");
                SA1_0_arr = d.SA1_0.split("/");
                CA2_0_arr = d.CA2_0.split("/");
                SA2_0_arr = d.SA2_0.split("/");

                CA1_0_top = parseInt(CA1_0_arr[0]);
                CA1_0_base = parseInt(CA1_0_arr[1]);

                SA1_0_top = parseInt(SA1_0_arr[0]);
                SA1_0_base = parseInt(SA1_0_arr[1]);

                CA2_0_top = parseInt(CA2_0_arr[0]);
                CA2_0_base = parseInt(CA2_0_arr[1]);

                SA2_0_top = parseInt(SA2_0_arr[0]);
                SA2_0_base = parseInt(SA2_0_arr[1]);

                CA1_1_arr = d.CA1_1.split("/");
                SA1_1_arr = d.SA1_1.split("/");
                CA2_1_arr = d.CA2_1.split("/");
                SA2_1_arr = d.SA2_1.split("/");

                CA1_1_top = parseInt(CA1_1_arr[0]);
                CA1_1_base = parseInt(CA1_1_arr[1]);

                SA1_1_top = parseInt(SA1_1_arr[0]);
                SA1_1_base = parseInt(SA1_1_arr[1]);

                CA2_1_top = parseInt(CA2_1_arr[0]);
                CA2_1_base = parseInt(CA2_1_arr[1]);

                SA2_1_top = parseInt(SA2_1_arr[0]);
                SA2_1_base = parseInt(SA2_1_arr[1]);

                tbody += '<tr><th scope="row"><input type="hidden" name="studentId[]" value="' + d.studentId + '" />' + d.studentName + '</th>' +
                        '<td><input class="form-control numberField" type="number" name="CA1_1_top[]" value="' + CA1_1_top + '"></input><hr/><input class="form-control numberField" type="number" name="CA1_1_base[]" value="' + CA1_1_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="CA1_0_top[]" value="' + CA1_0_top + '"></input><hr/><input class="form-control numberField" type="number" name="CA1_0_base[]" value="' + CA1_0_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="SA1_1_top[]" value="' + SA1_1_top + '"></input><hr/><input class="form-control numberField" type="number" name="SA1_1_base[]" value="' + SA1_1_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="SA1_0_top[]" value="' + SA1_0_top + '"></input><hr/><input class="form-control numberField" type="number" name="SA1_0_base[]" value="' + SA1_0_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="CA2_1_top[]" value="' + CA2_1_top + '"></input><hr/><input class="form-control numberField" type="number" name="CA2_1_base[]" value="' + CA2_1_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="CA2_0_top[]" value="' + CA2_0_top + '"></input><hr/><input class="form-control numberField" type="number" name="CA2_0_base[]" value="' + CA2_0_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="SA2_1_top[]" value="' + SA2_1_top + '"></input><hr/><input class="form-control numberField" type="number" name="SA2_1_base[]" value="' + SA2_1_base + '" min="1"></input></td>' +
                        '<td><input class="form-control numberField" type="number" name="SA2_0_top[]" value="' + SA2_0_top + '"></input><hr/><input class="form-control numberField" type="number" name="SA2_0_base[]" value="' + SA2_0_base + '" min="1"></input></td>';

            });

            htmlTable += '<form class="dynamicTable"><table class="table table-bordered" style="background-color:#e7e7e7">' + thead + tbody + '</table><input type="button"  id="UpdateGradeBtn" class="btn btn-default" value="Update Changes"></input></form>';

            return htmlTable;
        }




        function format(callback, data) {

            $.ajax({
                url: 'RetrieveStudentsWithGradesForSpecificClass',
                data: {class_id: data["ID"]},
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
                            console.log("submit");
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
                            $.ajax({
                                url: 'CreateTuitionGradeServlet',
                                data: {grade_arr: JSON.stringify(studArr)},
                                dataType: "json",
                                success: function (data) {
                                    if(data === 1){
                                        html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update grades successfully</div>';
                                    }else{
                                        html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                                    }
                                    $(".statusMsg").html(html);
                                    $('.statusMsg').fadeIn().delay(1000).fadeOut();
                                }
                            });

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
