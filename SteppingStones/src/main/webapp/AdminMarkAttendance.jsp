<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
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
                <th style="text-align: center">Level</th>
                <th style="text-align: center">Subject</th>
                <th style="text-align: center">Overall Attendance</th>
            </tr>
        </thead>
    </table>
    <div class="inline">
        <button class="btn btn-default" id="expand">Expand All</button>
        <button class="btn btn-default" id="collaspe">Collapse All</button>
    </div>
</div>

<div class="modal fade" id="lessonAttendance" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Class Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table id="lessonAttendanceTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
                        <thead>
                            <tr>
                                <th></th>
                                <th style="text-align: center">Lesson Date</th>
                                <th style="text-align: center">Attendance</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div><br/>
        </div>  

        <div class="modal-footer spaced-top-small centered">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
    </div>       
</div>
<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script type="text/javascript">
    function format(rowData, classID) {
        return '<table id=' + classID + ' class="table table-bordered" style="background-color: #a0e7a0; width: 100%;">'
                + '<thead><tr><th></th><th style="text-align: center">Student Name</th><th style="text-align: center">Phone No.</th><th style="text-align: center">Attendance</th></tr></thead></table>';
    }

    function formatLessonList(rowData, studentID) {
        return '<table id=' + studentID + ' class="table table-bordered" style="background-color: #e7e7a0; width: 100%;">'
                + '<thead><tr><th style="text-align: center">Lesson Date</th><th style="text-align: center">Present?</th>'
                + '</tr></thead></table>';
    }

    function formatLessonModal(rowData) {
        return '<table id="lessonModal" class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead></th><th style="text-align: center">Student Name</th><th style="text-align: center">Contact No.</th><th style="text-align: center">Present?</th>'
                + '</tr></thead></table>';
    }

    $(document).ready(function () {
        //tutorID = <%=user.getRespectiveID()%>
        branchID = <%=branch_id%>
        action = 'retrieve';

        table = $('#studentAttendanceTable').DataTable({
            'responsive': true,
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "AdminMarkStudentAttendanceServlet",
                "data": {
                    //"tutorID": tutorID,
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
                },
                {
                    "targets": 4,
                    "data": null,
                    "defaultContent": '<button class="btn btn-default">View Attendances</button>',
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

        // Handle click on "Expand All" button
        $('#expand').on('click', function () {
            // Expand row details
            table.rows(':not(.parent)').nodes().to$().find('td:first-child').trigger('click');
        });

        // Handle click on "Collapse All" button
        $('#collaspe').on('click', function () {
            // Collapse row details
            table.rows().every(function () {
                // If row has details expanded
                if (this.child.isShown()) {
                    // Collapse row details
                    this.child.hide();
                    $(this.node()).removeClass('shown');
                }
            });
        });

        $('#studentAttendanceTable tbody').on('click', 'button', function () {
            classID = table.row($(this).parents('tr')).data().id;
            action = 'retrieveModal';
            $('#lessonAttendance').on('shown.bs.modal', function () {
                lessonAttendanceTable = $("#lessonAttendanceTable").DataTable({
                    destroy: true,
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "AdminMarkStudentAttendanceServlet",
                        "data": {
                            "classID": classID,
                            "action": action
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [1, 2],
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
                        {"data": "date"},
                        {"data": "attendance"}
                    ],
                    "order": [[1, "asc"]]
                });
                $('#lessonAttendanceTable tbody').on('click', 'td.details-control', function () {
                    lesson_tr = $(this).parents('tr');
                    lesson_row = lessonAttendanceTable.row(lesson_tr);
                    if (lesson_row.child.isShown()) {
                        lesson_row.child.hide();
                        lesson_tr.removeClass('shown');
                    } else {
                        lessonID = lesson_row.data().id;
                        action = 'retrieveLessonDetails';
                        lesson_row.child(formatLessonModal(lesson_row.data())).show();
                        lessonModal = $("#lessonModal").DataTable({
                            "dom": 'tpr',
                            "iDisplayLength": 5,
                            'ajax': {
                                "type": "POST",
                                "url": "AdminMarkStudentAttendanceServlet",
                                "data": {
                                    "lessonID": lessonID,
                                    "action": action
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
                                    "defaultContent": '',
                                    "className": 'student-text'
                                },
                                {
                                    "targets": 2,
                                    "data": null,
                                    "orderable": false,
                                    "defaultContent": '<button class="btn btn-default">Present</button>',
                                    "className": 'student-text'
                                }
                            ],
                            'columns': [
                                {"data": "name"},
                                {"data": "phone"},
                                {"data": "attended"}
                            ],
                            "order": [[1, 'asc']]
                        });
                        $('#lessonModal tbody').on('click', 'button', function () {
                            studentID = lessonModal.row($(this).parents('tr')).data().id;
                            rowIndex = lessonModal.row($(this).parents('tr')).index();
                            columnIndex = lessonModal.cell($(this).closest('td')).index().column;
                            action = 'markLessonModal';
                            $.ajax({
                                type: 'POST',
                                url: 'AdminMarkStudentAttendanceServlet',
                                dataType: 'JSON',
                                data: {classID: classID, lessonID: lessonID, studentID: studentID, action: action},
                                success: function (data) {
                                    if (data) {
                                        lessonModal.cell(rowIndex, columnIndex).data('Present').draw();
                                        lessonAttendanceTable.cell(lesson_row.index(), 2).data(data.attendance).draw();
                                    }
                                }
                            });
                        });
                        lesson_tr.addClass('shown');
                    }
                });
            });
            $("#lessonAttendance").modal('show');
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
                row.child(format(row.data(), classID)).show();
                var childTable = $("#" + classID).DataTable({
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "AdminMarkStudentAttendanceServlet",
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
                $('#' + classID + ' tbody').on('click', 'td.details-control', function () {
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
                        childRow.child(formatLessonList(childRow.data(), studentID)).show();
                        lessonTable = $("#" + studentID).DataTable({
                            "dom": 'tpr',
                            "iDisplayLength": 5,
                            'ajax': {
                                "type": "POST",
                                "url": "AdminMarkStudentAttendanceServlet",
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
                        $('#' + studentID + ' tbody').on('click', 'button', function () {
                            lessonID = lessonTable.row($(this).parents('tr')).data().id;
                            rowIndex = lessonTable.row($(this).parents('tr')).index();
                            columnIndex = lessonTable.cell($(this).closest('td')).index().column;
                            action = 'mark';
                            $.ajax({
                                type: 'POST',
                                url: 'AdminMarkStudentAttendanceServlet',
                                dataType: 'JSON',
                                data: {classID: classID, lessonID: lessonID, studentID: studentID, action: action},
                                success: function (data) {
                                    if (data) {
                                        lessonTable.cell(rowIndex, columnIndex).data('Present').draw();
                                        childTable.cell(childRow.index(), 3).data(data.attendance).draw();
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