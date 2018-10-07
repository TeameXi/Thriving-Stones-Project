<%@include file="protect_student.jsp"%>
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

    .attendance-button {
        text-align: center;
    }
</style>
<div class="col-lg-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Your Attendance</span></div>
    <table id="tutorAttendance" class="table table-bordered" style="background-color: #cdcddf; width:100%; font-size: 14px">
        <thead>
            <tr>
                <th></th>
                <th style="text-align: center">Class</th>
                <th style="text-align: center">Level</th>
                <th style="text-align: center">Subject</th>
            </tr>
        </thead>
    </table>
    
    <div class="inline">
        <button class="btn btn-default" id="expand">Expand All</button>
        <button class="btn btn-default" id="collaspe">Collapse All</button>
    </div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData, classID) {
        return '<table id=' + classID + ' class="table table-bordered" style="background-color: #dddde9; width: 100%;">'
                + '<thead><tr><th style="text-align: center">Lesson</th><th style="text-align: center">Present?</th></tr></thead></table>';
    }

    $(document).ready(function () {
        studentID = <%=user.getRespectiveID()%>
        branchID = <%=branch_id%>
        action = 'retrieve';

        table = $('#tutorAttendance').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "StudentViewAttendanceServlet",
                "data": {
                    "studentID": studentID,
                    "branchID": branchID,
                    "action": action
                }
            },
            "columnDefs": [
                {
                    "targets": [1, 2, 3],
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
                {"data": "date"},
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

        $('#tutorAttendance tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                classID = row.data().id;
                action = 'displayLessons';

                // Open this row
                row.child(format(row.data(), classID)).show();
                var childTable = $("#" + classID).DataTable({
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "StudentViewAttendanceServlet",
                        "data": {
                            "classID": classID,
                            "action": action,
                            "studentID": studentID
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [0,1],
                            "data": null,
                            "defaultContent": '',
                            "className": 'tutor-text'
                        }
                    ],
                    'columns': [
                        {"data": "date"},
                        {"data": "attendance"}
                    ]
                });
                tr.addClass('shown');
            }
        });
    });
</script>