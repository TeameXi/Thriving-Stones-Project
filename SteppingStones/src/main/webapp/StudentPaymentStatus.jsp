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

    .attendance-button {
        text-align: center;
    }
</style>
<div class="col-lg-10">
    <div id="header" style="text-align: center;margin: 10px;">Student Payment - <a href="PaymentSummaryPage.jsp">View 1</a>/ <span class="tab_active" style="font-size: 14px">View 2</span></div>
    <table id="studentPaymentStatusTable" class="table display dt-responsive nowrap" style="width:100%;">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="text-align: center;"></th>
                <th scope="col" style="text-align: center;">Level</th>
            </tr>
        </thead>
    </table>
    <div class="inline">
        <button class="btn btn1" id="expand">Expand All</button>
        <button class="btn btn2" id="collaspe">Collapse All</button>
    </div>
</div>

<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData, levelID) {
        return '<div class="innerTable"><table id=' + levelID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th scope="col" style="text-align: center">Student Name</th><th scope="col" style="text-align: center">Total Outstanding Amount</th><th></th></tr></thead></table></div>';
    }

    $(document).ready(function () {
        branchID = <%=branch_id%>
        action = 'retrieve';

        table = $('#studentPaymentStatusTable').DataTable({
            //"searching": false,
            "iDisplayLength": 10,
            "aLengthMenu": [[10, 25, -1], [10, 25, "All"]],
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
                    "iDisplayLength": 10,
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
                            "defaultContent": '<button class="btn btn1 details">Details</button>',
                            "className": 'tutor-text'
                        }
                    ],
                    'columns': [
                        {"data": "studentName"},
                        {"data": "totalOutstandingAmt"}
                    ],
                    "order": [[1, 'des']]

                });
                tr.addClass('shown');

                $('#' + levelID + ' tbody').on('click', '.details', function () {
                    studentID = childTable.row($(this).parents('tr')).data().id;
                    action = 'update';
                    //console.log(studentID + action);
                    window.location.assign("PaymentPage.jsp?studentID="+studentID);

                });
            }
        });
    });
</script>