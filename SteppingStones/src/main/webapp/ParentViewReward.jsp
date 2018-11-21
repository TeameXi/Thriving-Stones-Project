<%@include file="protect_parent.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">

<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>
<style type="text/css">
    td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/list_metro.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/close.png') no-repeat center center;
    }

    .innerTable{
        padding-left:50px;
        padding-right:50px;
        max-width: 1000px;
    }

    #table-wrapper {
        width: 95%;
        float: left;
        overflow-x: hidden;
    }

    .text{
        text-align: center;
    }

    .nested > thead:first-child > tr:first-child > th:nth-child(2) {
        padding-left: 180px;
    }

    .nested > tbody > tr > td:nth-child(2) {
        padding-left: 180px !important;
    }

    .nested > thead:first-child > tr:first-child > th:first-child {
        position: absolute;
        display: inline-block;
        background-color: #dee8eb;
        width: 15%;
        height: 26px;
    }

    .nested > tbody > tr > td:first-child {
        position: absolute;
        display: inline-block;
        background-color: #dee8eb;
        width: 15%;
        height: 26px;
    }
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
        #tab{
            margin-bottom: 20px !important;
        }
        .tableImage{
            width: 20px;
            heigth: 20px;
        }
        .availablePoint{
            text-align: center;
            margin-top: 25px;
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
        #tab{
            margin-bottom: 20px !important;
        }
        .tableImage{
            width: 50px;
            heigth: 50px;
        }
        .availablePoint{
            text-align: center;
            margin-top: 25px;
        }
    }
    @media screen and (min-width:768px) and (max-width: 991px) {
        #studentAttendanceTable_filter{
            float: right;
        }
    }
</style>
<div class="col-md-10">
    <div id="tab" style="text-align: center;margin: 10px;"><span class="tab_active">Reward</span></div>
    <table id="children" class="table display dt-responsive nowrap" style="width:100%;">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="text-align: center;"></th>
                <th scope="col" style="text-align: center;">Name</th>
                <th scope="col" style="text-align: center;">Point</th>
            </tr>
        </thead>
    </table>
</div>
</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        parentID = <%=user.getRespectiveID()%>
        action = 'retrieveParent';
        table = $('#children').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "StudentRetrieveRewardServlet",
                "data": {
                    "action": action,
                    "parentID": parentID
                }
            },
            "columnDefs": [
                {
                    "targets": [1, 2],
                    "data": null,
                    "defaultContent": '',
                    "className": 'text'
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
                {"data": "point"}
            ],
            "order": [[1, 'asc']]
        });

        $('#children tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);
            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                studentID = row.data().id;
                console.log(studentID);
                action = 'retrieve';

                html = '<div class="innerTable"><table id="classes" '
                        + 'class="table display dt-responsive nowrap" style="width:100%;">'
                        + '<thead class="thead-light"><tr>'
                        //+ '<th scope="col" style="text-align: center;"></th>'
                        + '<th scope="col" style="text-align: center;">Item Name</th>'
                        + '<th scope="col" style="text-align: center;">Image</th>'
                        + '<th scope="col" style="text-align: center;">Quantity Available</th>'
                        + '<th scope="col" style="text-align: center;">Point to Redeem</th>'
                        + '<th scope="col" style="text-align: center;">Description</th>'
                        + '<th scope="col" style="text-align: center;">Status</th></tr></thead></table><div>';
                
                // Open this row
                row.child(html).show();

                tr.addClass('shown');

                child = $('#classes').DataTable({
                    "sDom": 't',
                    'ajax': {
                        "type": "POST",
                        "url": "StudentRetrieveRewardServlet",
                        "data": {
                            "action": action,
                            "studentID": studentID
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [0, 1, 2, 3, 4, 5],
                            "data": null,
                            "defaultContent": '',
                            "class": 'details'
                        }
                    ],
                    'columns': [
                        {"data": "name"},
                        {"data": "image",
                        "render": function (data) {

                            return '<img src="data:image/jpeg;base64,' + data + '" class="tableImage" width="90" height="90"/>';



                        }  
                       },
                        {"data": "quantity"},
                        {"data": "point"},
                        {"data": "description"},
                        {"data": "status"}
                    ],
                    "order": [[0, 'asc']]
                });

            }
        });
    });
</script>
