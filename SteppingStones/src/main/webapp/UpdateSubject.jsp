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

    .attendance-button {
        text-align: center;
    }
</style>
<div class="col-lg-10">
    <div id="header" style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Update Subject Fees</span></div>
    <table id="subjectTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
        <thead>
            <tr>
                <th></th>
                <th style="text-align: center">Level</th>
                <th style="text-align: center">Total No. Of Subjects Offered</th>
            </tr>
        </thead>
    </table>
    <div class="inline">
        <button class="btn btn1" id="expand">Expand All</button>
        <button class="btn btn2" id="collaspe">Collapse All</button>
    </div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData, levelID) {
        return '<table id=' + levelID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th style="text-align: center">Subject</th><th style="text-align: center">Fees</th><th></th></tr></thead></table>';
    }

    $(document).ready(function () {
        branchID = <%=branch_id%>
        action = 'retrieve';

        table = $('#subjectTable').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "UpdateSubjectFeesServlet",
                "data": {
                    "branchID": branchID,
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
                {"data": "name"},
                {"data": "subjects"}
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

        $('#subjectTable tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                levelID = row.data().id;
                action = 'retrieveSubjects';

                // Open this row
                row.child(format(row.data(), levelID)).show();
                var childTable = $("#" + levelID).DataTable({
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "UpdateSubjectFeesServlet",
                        "data": {
                            "levelID": levelID,
                            "action": action,
                            "branchID": branchID
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": 0,
                            "data": null,
                            "defaultContent": '',
                            "className": 'tutor-text'
                        },
                        {
                            "targets": 1,
                            "data": null,
                            "defaultContent": '',
                            "className": 'tutor-text'
                        },
                        {
                            "targets": 2,
                            "data": null,
                            "defaultContent": '<button class="btn btn1">Update</button>',
                            "className": 'tutor-text'
                        }
                    ],
                    'columns': [
                        {"data": "name"},
                        {"data": "fees"}
                    ],
                    "order": [[1, 'asc']]
                });

                $('#' + levelID + ' tbody').on('click', 'button', function () {
                    subjectID = childTable.row($(this).parents('tr')).data().id;
                    fees = $('#update_fees' + subjectID).val();
                    rowIndex = childTable.row($(this).parents('tr')).index();
                    columnIndex = childTable.cell($(this).closest('td')).index().column;
                    action = 'update';

                    $.ajax({
                        type: 'POST',
                        url: 'UpdateSubjectFeesServlet',
                        dataType: 'JSON',
                        data: {subjectID: subjectID, action: action, levelID: levelID, branchID: branchID, fees: fees},
                        success: function (data) {
                            if (data) {
                                $("<div id='errorMsg' class='alert alert-success'>Sucessfully updated subject fees!</div>").insertAfter($("#header"));
                            } else {
                                $("<div id='errorMsg' class='alert alert-success'>Oops! Something went wrong!</div>").insertAfter($("#header"));
                            }
                            $("#errorMsg").fadeTo(2000, 0).slideUp(2000, function () {
                                $(this).remove();
                            });
                        }
                    });
                });

                tr.addClass('shown');
            }
        });
    });
</script>