<%@include file="protect_tutor.jsp"%>
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
</style>
<div class="col-lg-10">
    <div id="tab" style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Attendance Taking</span></div>
    <table id="studentAttendanceTable" class="table display dt-responsive nowrap" style="width:100%;">
        <thead class="thead-light">
            <tr>
                <th scope="col" style="text-align: center;"></th>
                <th scope="col" style="text-align: center;">Class</th>
            </tr>
        </thead>
    </table>
</div>
</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        branchID = <%=branch_id%>
        tutorID = <%=user.getRespectiveID()%>
        action = 'retrieve';
        table = $('#studentAttendanceTable').DataTable({
            "iDisplayLength": 5,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "StudentAttendanceServlet",
                "data": {
                    "branchID": branchID,
                    "action": action,
                    "tutorID": tutorID
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
                {"data": "name"}
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
                $.ajax({
                    type: 'POST',
                    url: 'StudentAttendanceServlet',
                    dataType: 'JSON',
                    data: {action: action, classID: classID, branchID: branchID},
                    success: function (data) {
                        console.log(data);
                        html = '<div class="innerTable"><div style="text-align: right; margin-bottom: 10px; margin-right: 50px;">'
                                + '<button class="btn btn-default" id="updateAttendance">Update Attendance</button>'
                                + '<button class="btn btn-default" id="uncheckAll" style="margin-left: 370px;">Uncheck All</button>'
                                + '<button class="btn btn-default" id="checkAll" style="margin-left: 10px; margin-right: 20px;">Check All</button>'
                                + '<img class="leftArrow" src="${pageContext.request.contextPath}/styling/img/left-arrow.svg" height="15" '
                                + 'width="15" style="margin-right: 58px;"><img '
                                + 'class="rightArrow" src="${pageContext.request.contextPath}/styling/img/right-arrow.svg" height="15" '
                                + 'width="15"></div><div id="table-wrapper"><table id=' + classID
                                + ' class="table table-striped table-bordered nowrap" style="width:100%">'
                                + '<thead><tr><th style="text-align: center;">Student</th><th style="text-align: center;">Attendance</th>';
                        for (var i = 0; i < data[0].lessons.length; i++) {
                            lessonNum = i + 1;
                            html += '<th style="text-align: center;">Lesson ' + lessonNum + '</th>';
                        }

                        html += '</tr></thead><tbody>';
                        for (var i = 0; i < data.length; i++) {
                            lessons = data[i].lessons;
                            html += '<tr id=' + data[i].id + '><td style="text-align:center;">' + data[i].name + '</td><td id=' + data[i].id + ' style="text-align: center;">' + data[i].attendance + '</td>';
                            for (var j = 0; j < lessons.length; j++) {
                                if (lessons[j].attended) {
                                    html += '<td style="text-align: center;"><label style="margin-right: 10px;">' + lessons[j].date + '</label><input type="checkbox" id='
                                            + lessons[j].id + ' class="checkSingle" checked></td>';
                                } else {
                                    html += '<td style="text-align: center;"><label style="margin-right: 10px;">' + lessons[j].date + '</label><input type="checkbox" id='
                                            + lessons[j].id + ' class="checkSingle"></td>';
                                }
                            }
                            html += '</tr>';
                        }
                        html += '</tbody></table></div></div>';
                        
                        // Open this row
                        row.child(html).show();

                        tr.addClass('shown');

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

                        $("#checkAll").on('click', function () {
                            $(".checkSingle").each(function () {
                                this.checked = true;
                            });
                        });

                        $("#uncheckAll").on('click', function () {
                            $(".checkSingle").each(function () {
                                this.checked = false;
                            });
                        });

                        $('#updateAttendance').on('click', function () {
                            lessons = '';
                            
                            $(".checkSingle").each(function () {
                                if (this.checked) {
                                    lessons += this.id + '-' + $(this).closest('tr').attr('id') + '-' + 1 + ' ';
                                }else{
                                    lessons += this.id + '-' + $(this).closest('tr').attr('id') + '-' + 0 + ' ';
                                }
                            });
                            action = 'mark';

                            $.ajax({
                                type: 'POST',
                                url: 'StudentAttendanceServlet',
                                dataType: 'JSON',
                                data: {action: action, lessons: lessons, classID: classID, tutorID: tutorID},
                                success: function (data) {
                                    if (data) {
                                        $("<div id='errorMsg' class='alert alert-success'>Attendance Updated Successfully!</div>").insertAfter($("#tab"));
                                    } else {
                                        $("<div id='errorMsg' class='alert alert-success'>Oops! Something went wrong!</div>").insertAfter($("#tab"));
                                    }
                                    
                                    console.log(data.attendance);
                                    
                                    $("#errorMsg").fadeTo(2000, 0).slideUp(2000, function () {
                                        $(this).remove();
                                    });
                                }
                            });
                        });
                    }
                });
            }
        });
    });
</script>
