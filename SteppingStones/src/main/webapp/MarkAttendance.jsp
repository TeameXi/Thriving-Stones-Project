<%@include file="protect_tutor.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<style type="text/css">
    td.details-control {
        background: url("${pageContext.request.contextPath}/styling/img/add.png") no-repeat center center;
        cursor: pointer;
        background-size: 15px 15px;
    }
    tr.shown td.details-control {
        background: url("${pageContext.request.contextPath}/styling/img/minus.png") no-repeat center center;
        background-size: 15px 15px;
    }

    .student-text {
        text-align: center;
    }

    .attendance-button {
        text-align: center;
    }
</style>
<div class="col-lg-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Attendance Taking</span></div>
    <table id="studentAttendanceTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
        <thead>
            <tr>
                <th></th>
                <th style="text-align: center">Class</th>
                <th style="text-align: center">Subject</th>
                <th style="text-align: center">Level</th>
            </tr>
        </thead>
    </table>
</div>
</div>
<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData) {
        return '<table id="studentList" class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th></th><th style="text-align: center">Student Name</th><th style="text-align: center">Phone No.</th><th style="text-align: center">Attendance</th></tr></thead></table>';
    }

    function formatLessonList(rowData) {
        return '<table id="lessonList" class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th style="text-align: center">Lesson Date</th><th style="text-align: center">Present?</th>'
                + '</tr></thead></table>';
    }

    $(document).ready(function () {
        tutorID = <%=user.getRespectiveID()%>
        branchID = <%=branch_id%>
        action = 'retrieve';
        
        table = $('#studentAttendanceTable').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "MarkStudentAttendanceServlet",
                "data": {
                    "tutorID": tutorID,
                    "branchID": branchID,
                    "action": action
                }
            },
            "columnDefs": [
                {
                    "targets": [1, 2, 3],
                    "data": null,
                    "defaultContent": '',
                    "className": 'student-text'
                }
            ],
            'columns': [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {"data": "class"},
                {"data": "level"},
                {"data": "subject"}
            ],
            "order": [[1, 'asc']]
        });

        $('#studentAttendanceTable tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                classID = row.data().id;
                action = 'retrieveStudents';

                // Open this row
                row.child(format(row.data())).show();
                var childTable = $("#studentList").DataTable({
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "MarkStudentAttendanceServlet",
                        "data": {
                            "classID": classID,
                            "action": action
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": 1,
                            "data": null,
                            "defaultContent": '',
                            "className": 'student-text'
                        },
                        {
                            "targets": 2,
                            "data": null,
                            "defaultContent": '',
                            "className": 'student-text'
                        },
                        {
                            "targets": 3,
                            "data": null,
                            "defaultContent": '',
                            "className": 'student-text'
                        }
                    ],
                    'columns': [
                        {
                            "className": 'details-control',
                            "orderable": false,
                            "data": null,
                            "defaultContent": ''
                        },
                        {"data": "name"},
                        {"data": "phone"},
                        {"data": "attendance"}
                    ],
                    "order": [[1, 'asc']]
                });
                tr.addClass('shown');

                $('#studentList tbody').on('click', 'td.details-control', function () {
                    childTR = $(this).parents('tr');
                    childRow = childTable.row(childTR);

                    if (childRow.child.isShown()) {
                        // This row is already open - close it
                        childRow.child.hide();
                        childTR.removeClass('shown');
                    } else {
                        studentID = childRow.data().id;
                        action = 'retrieveLessons';
                        // Open this row
                        childRow.child(formatLessonList(childRow.data())).show();
                       
                        lessonTable = $("#lessonList").DataTable({
                            "dom": 'tpr',
                            "iDisplayLength": 5,
                            'ajax': {
                                "type": "POST",
                                "url": "MarkStudentAttendanceServlet",
                                "data": {
                                    "classID": classID,
                                    "action": action,
                                    "studentID": studentID
                                }
                            },
                            "columnDefs": [
                                {
                                    "targets": 0,
                                    "data": null,
                                    "defaultContent": '',
                                    "className": 'student-text'
                                },
                                {
                                    "targets": 1,
                                    "data": null,
                                    "defaultContent": '<button class="btn btn-default">Present</button>',
                                    "className": 'student-text'
                                }
                            ],
                            'columns': [
                                {"data": "date"},
                                {"data": "attended"}
                            ]
                        });

                        $('#lessonList tbody').on('click', 'button', function () {
                            lessonID = lessonTable.row($(this).parents('tr')).data().id;
                            rowIndex = lessonTable.row($(this).parents('tr')).index();  
                            columnIndex = lessonTable.cell($(this).closest('td')).index().column;
                            action = 'mark';
                            $.ajax({
                                type: 'POST',
                                url: 'MarkStudentAttendanceServlet',
                                dataType: 'JSON',
                                data: {classID: classID, lessonID: lessonID, studentID: studentID, tutorID: tutorID, action: action},
                                success: function (data) {
                                    if(data){
                                        lessonTable.cell(rowIndex, columnIndex).data('Present').draw();
                                    }
                                }
                            });
                        });
                        childTR.addClass('shown');
                    }
                });
            }
        });
    });
</script>