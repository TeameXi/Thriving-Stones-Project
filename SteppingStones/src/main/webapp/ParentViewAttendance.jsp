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
    }
    @media screen and (min-width:768px) and (max-width: 991px) {
        #studentAttendanceTable_filter{
            float: right;
        }
    }
</style>
<div class="col-md-10">
    <div id="tab" style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Attendance Taking</span></div>
    <table id="children" class="table display dt-responsive nowrap" style="width:100%;">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="text-align: center;"></th>
                <th scope="col" style="text-align: center;">Name</th>
            </tr>
        </thead>
    </table>
</div>
</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        parentID = <%=user.getRespectiveID()%>
        action = 'retrieve';
        table = $('#children').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "ParentViewAttendanceServlet",
                "data": {
                    "action": action,
                    "parentID": parentID
                }
            },
            "columnDefs": [
                {
                    "targets": [1],
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
                {"data": "name"}
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
                action = 'displayClass';

                html = '<div class="innerTable"><table id="classes" '
                        + 'class="table display dt-responsive nowrap" style="width:100%;">'
                        + '<thead class="thead-light"><tr>'
                        + '<th scope="col" style="text-align: center;"></th>'
                        + '<th scope="col" style="text-align: center;">Class</th></tr></thead></table><div>';
                // Open this row
                row.child(html).show();

                tr.addClass('shown');

                child = $('#classes').DataTable({
                    "sDom": 't',
                    'ajax': {
                        "type": "POST",
                        "url": "ParentViewAttendanceServlet",
                        "data": {
                            "action": action,
                            "studentID": studentID
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [1],
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
                        {"data": "name"}
                    ],
                    "order": [[1, 'asc']]
                });

                $('#classes tbody').on('click', 'td.details-control', function () {
                    childTR = $(this).parents('tr');
                    r = child.row(childTR);
                    if (r.child.isShown()) {
                        // This row is already open - close it
                        r.child.hide();
                        childTR.removeClass('shown');
                    } else {
                        classID = r.data().id;
                        console.log(classID);
                        action = 'displayAttendances';
                        $.ajax({
                            type: 'POST',
                            url: 'ParentViewAttendanceServlet',
                            dataType: 'JSON',
                            data: {action: action, classID: classID, studentID: studentID},
                            success: function (data) {
                                html = '<div class="innerTable"><div style="text-align: right; margin-bottom: 10px; margin-right: 50px;">'
                                        + '<img class="leftArrow" src="${pageContext.request.contextPath}/styling/img/left-arrow.svg" height="15" '
                                        + 'width="15" style="margin-right: 58px;"><img '
                                        + 'class="rightArrow" src="${pageContext.request.contextPath}/styling/img/right-arrow.svg" height="15" '
                                        + 'width="15"></div><div id="table-wrapper"><table id=' + classID
                                        + ' class="table table-striped table-bordered nowrap nested" style="width:100%">'
                                        + '<thead><tr><th style="text-align: center;">Attendance</th>';
                                for (var i = 0; i < data.lessons.length; i++) {
                                    lessonNum = i + 1;
                                    html += '<th style="text-align: center;">Lesson ' + lessonNum + '</th>';
                                }

                                html += '</tr></thead><tbody>';
                                lessons = data.lessons;
                                html += '<tr><td style="text-align:center;">' + data.attendance + '</td>';
                                for (var i = 0; i < lessons.length; i++) {
                                    lessonNum = i + 1;
                                    if (lessons[i].attended) {
                                        html += '<td style="text-align: center;"><label style="margin-right: 10px;">' + lessons[i].date + '</label><input type="checkbox" id='
                                                + lessons[i].id + '" checked disabled></td>';
                                    } else {
                                        html += '<td style="text-align: center;"><label style="margin-right: 10px;">' + lessons[i].date + '</label><input type="checkbox" id='
                                                + lessons[i].id + '" disabled></td>';
                                    }
                                }
                                html += '</tr></tbody></table></div></div>';

                                // Open this row
                                r.child(html).show();

                                childTR.addClass('shown');

                                $(".leftArrow").on("click", function () {
                                    var leftPos = $('#table-wrapper').scrollLeft();
                                    console.log(leftPos);
                                    $("#table-wrapper").animate({
                                        scrollLeft: leftPos - 200
                                    }, 800);
                                });

                                $(".rightArrow").on("click", function () {
                                    var leftPos = $('#table-wrapper').scrollLeft();
                                    console.log(leftPos);
                                    $("#table-wrapper").animate({
                                        scrollLeft: leftPos + 200
                                    }, 800);
                                });
                            }
                        });
                    }
                });
            }
        });
    });
</script>
