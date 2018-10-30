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

    .tutor-text {
        text-align: center;
    }


    .innerTable{
        padding-left:50px;
        padding-right:50px;
        max-width: 1000px;
        position: relative;
    }
</style>
<div class="col-lg-10">
    <div id="tab" style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Attendance Taking</span></div>
    <table id="studentPaymentStatusTable" class="table display dt-responsive nowrap" style="width:100%;">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="text-align: center;"></th>
                <th scope="col" style="text-align: center;">Level</th>
            </tr>
        </thead>
    </table>
</div>
<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData, levelID) {
        return '<div class="innerTable"><table id=' + levelID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th></th><th scope="col" style="text-align: center">Student Name</th><th scope="col" style="text-align: center">Number of Classes</th></tr></thead></table></div>';
    }

    function formatStudentList(rowData, studentID) {
        return '<div class="innerTable"><table id=' + studentID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th scope="col" style="text-align: center;"></th><th scope="col" style="text-align: center">Subject</th><th scope="col" style="text-align: center">Class Day</th>'
                + '</tr></thead></table></div>';
    }

    function formatLessonList(rowData, classID) {
        return '<table id=' + classID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th style="text-align: center">Lesson Date</th><th style="text-align: center">Payment Status</th>'
                + '</tr></thead></table>';
    }


    $(document).ready(function () {
        branchID = <%=branch_id%>
        action = 'retrieve';

        table = $('#studentPaymentStatusTable').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "StudentPaymentStatusServlet",
                "data": {
                    "branchID": branchID,
                    "action": action
                }
            },
            "columnDefs": [
                {
                    "targets": [1],
                    "data": null,
                    "defaultContent": '',
                    "className": 'tutor-text'
                }
            ],
            'columns': [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {"data": "level"}
            ],
            "order": [[1, 'asc']]
        });


        $('#studentPaymentStatusTable tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                levelID = row.data().id;
                action = 'retrieveStudent';

                // Open this row
                row.child(format(row.data(), levelID)).show();
                var childTable = $("#" + levelID).DataTable({
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "StudentPaymentStatusServlet",
                        "data": {
                            "levelID": levelID,
                            "action": action,
                            "branchID": branchID
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [1, 2],
                            "data": null,
                            "defaultContent": '',
                            "className": 'tutor-text'
                        }
                    ],
                    'columns': [
                        {
                            "className": 'details-control',
                            "orderable": false,
                            "data": null,
                            "defaultContent": ''
                        },
                        {"data": "studentName"},
                        {"data": "noOfClass"}
                    ],
                    "order": [[1, 'asc']]
                });
                tr.addClass('shown');

                $('#' + levelID + ' tbody').on('click', 'td.details-control', function () {
                    childTR = $(this).parents('tr');
                    childRow = childTable.row(childTR);

                    if (childRow.child.isShown()) {
                        // This row is already open - close it
                        childRow.child.hide();
                        childTR.removeClass('shown');
                    } else {
                        studentID = childRow.data().id;
                        console.log(levelID);
                        action = 'retrieveClasses';
                        console.log(studentID + ' yayyy');

                        // Open this row
                        childRow.child(formatStudentList(childRow.data(), studentID)).show();

                        studentTable = $("#" + studentID).DataTable({
                            "dom": 'tpr',
                            "iDisplayLength": 5,
                            'ajax': {
                                "type": "POST",
                                "url": "StudentPaymentStatusServlet",
                                "data": {
                                    "studentID": studentID,
                                    "action": action
                                }
                            },
                            "columnDefs": [
                                {
                                    "targets": [1, 2],
                                    "data": null,
                                    "defaultContent": '',
                                    "className": 'tutor-text'
                                }
                            ],
                            'columns': [
                                {
                                    "className": 'details-control',
                                    "orderable": false,
                                    "data": null,
                                    "defaultContent": ''
                                },
                                {"data": "subject"},
                                {"data": "date"}
                            ],
                            "order": [[1, 'asc']]
                        });
                        childTR.addClass('shown');

                        $('#' + studentID + ' tbody').on('click', 'td.details-control', function () {
                            studentTR = $(this).parents('tr');
                            studentRow = studentTable.row(studentTR);

                            if (studentRow.child.isShown()) {
                                // This row is already open - close it
                                studentRow.child.hide();
                                studentTR.removeClass('shown');
                            } else {
                                classID = studentRow.data().id;
                                action = 'retrieveLessons';
                                console.log(studentID);

                                // Open this row
                                studentRow.child(formatLessonList(studentRow.data(), classID)).show();

                                lessonTable = $("#" + classID).DataTable({
                                    "dom": 'tpr',
                                    "iDisplayLength": 5,
                                    'ajax': {
                                        "type": "POST",
                                        "url": "StudentPaymentStatusServlet",
                                        "data": {
                                            "studentID": studentID,
                                            "classID": classID,
                                            "action": action
                                        }
                                    },
                                    "columnDefs": [
                                        {
                                            "targets": [0, 1],
                                            "data": null,
                                            "defaultContent": '',
                                            "className": 'tutor-text'
                                        }
                                    ],
                                    'columns': [
                                        {"data": "date"},
                                        {"data": "paid"}
                                    ],
                                    "order": [[1, 'asc']]
                                });
                                
                                studentTR.addClass('shown');
                            }
                        });
                    }
                });
            }
        });
    });
</script>