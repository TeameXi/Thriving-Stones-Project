<%@include file="protect_tutor.jsp"%>
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_material.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<%@include file="header.jsp"%>

<style type="text/css" media="screen">
    html, body{
        margin:0px;
        padding:0px;
        height:100%;
    }   

    .student-text{
        text-align: center;
    }
</style>

<div class="col-md-10">
    <div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:100%;'>
        <div class="dhx_cal_navline">
            <div class="dhx_cal_prev_button">&nbsp;</div>
            <div class="dhx_cal_next_button">&nbsp;</div>
            <div class="dhx_cal_today_button"></div>
            <div class="dhx_cal_date"></div>
            <div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>
            <div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>
            <div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
            <div class="dhx_cal_tab" name="year_tab" style="right:280px;"></div>
        </div>
        <div class="dhx_cal_header"></div>
        <div class="dhx_cal_data"></div>       
    </div>
</div>
</div>

<div class="modal fade" id="classDetails" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Class Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class = "col-sm-4">
                        <label class = "form-control-label">Class Timing :</label>
                        <input type="text" class="form-control" id="view_class_time" readonly="true" style="text-align: center;">
                    </div>
                    <div class = "col-sm-4">
                        <label class = "form-control-label">Start Date :</label>
                        <input type="text" class="form-control" id="view_start_date" readonly="true" style="text-align: center;">
                    </div>
                    <div class = "col-sm-4">
                        <label class = "form-control-label">End Date :</label>
                        <input type="text" class="form-control" id="view_end_date" readonly="true" style="text-align: center;">
                    </div><br/>
                </div><br/>
                <div class="row">
                    <div class = "col-sm-4">
                        <label class = "form-control-label">Class Size :</label>
                        <input type="text" class="form-control" id="view_class_size" readonly="true" style="text-align: center;">
                    </div>

                    <div class = "col-sm-4">
                        <label class = "form-control-label">Assigned To :</label>
                        <input type="text" class="form-control" id="view_tutor" readonly="true" style="text-align: center;">
                    </div>

                    <div class = "col-sm-4">
                        <label class = "form-control-label">Attendance :</label>
                        <input type="text" class="form-control" id="view_lesson_attendance" readonly="true" style="text-align: center;">
                    </div><br/>
                </div><br/>

                <div class="row" style="margin-left: 5px;">
                    <label class = "form-control-label">Edit Lesson Date :</label>
                    <div class="form-inline">
                        <input type="text" class="form-control" id="datetimepicker" style="text-align: center; width: 40%;" placeholder="Start Time">
                        <input type="text" class="form-control" id="datetimepicker1" style="text-align: center; width: 40%;" placeholder="End Time">
                        <button class="btn btn-default" id="alter_date">Edit</button>
                    </div>
                    <br/>
                    <script type="text/javascript">
                        $('#datetimepicker').datetimepicker({
                            format: 'YYYY-MM-DD HH:mm:ss'
                        });
                        
                        $('#datetimepicker1').datetimepicker({
                            format: 'YYYY-MM-DD HH:mm:ss'
                        });
                    </script>
                </div><br/>

                <div class="table-responsive">
                    <table id="studentAttendanceTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
                        <thead>
                            <tr>
                                <th style="text-align: center">Student Name</th>
                                <th style="text-align: center">Contact No.</th>
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
</div>
<script>
    $(document).ready(function () {
        tutorID = <%=user.getRespectiveID()%>
        action = 'retrieve';

        scheduler.attachEvent("onBeforeDrag", function () {
            return false;
        });//block event resize and drag
        scheduler.config.dblclick_create = false;//block event creation by doubleclick
        scheduler.config.readonly_form = true;
        scheduler.config.readonly = true;
        scheduler.config.prevent_cache = true;
        scheduler.config.xml_date = "%Y-%m-%d %H:%i:%s";
        scheduler.init('scheduler_here', new Date(), "month");

        $.ajax({
            type: 'POST',
            url: 'TutorScheduleServlet',
            dataType: 'JSON',
            data: {tutorID: tutorID, action: action},
            success: function (data) {
                scheduler.parse(data.data, "json");
            }
        });

        scheduler.attachEvent("onClick", function (id, e) {
            $('#classDetails').on('shown.bs.modal', function () {
                action = 'retrieveClassDetails';

                $.ajax({
                    type: 'POST',
                    url: 'TutorScheduleServlet',
                    dataType: 'JSON',
                    data: {lessonID: id, action: action},
                    success: function (data) {
                        $("#view_class_time").val(data.className);
                        $("#view_start_date").val(data.startDate);
                        $("#view_end_date").val(data.endDate);
                        $("#view_class_size").val(data.classSize);
                        $("#view_tutor").val(data.tutor);
                        $("#view_lesson_attendance").val(data.attendance);
                        $("#datetimepicker").val(data.changedStart);
                        $("#datetimepicker1").val(data.changedEnd);
                    }
                });

                action = 'retrieveStudents';

                table = $('#studentAttendanceTable').DataTable({
                    destroy: true,
                    "dom": 'tpr',
                    "iDisplayLength": 5,
                    'ajax': {
                        "type": "POST",
                        "url": "TutorScheduleServlet",
                        "data": {
                            "lessonID": id,
                            "action": action
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [0, 1],
                            "data": null,
                            "defaultContent": '',
                            "className": 'student-text'
                        },
                        {
                            "targets": 2,
                            "data": null,
                            "defaultContent": '<button class="btn btn-default">Present</button>',
                            "className": 'student-text'
                        }
                    ],
                    'columns': [
                        {"data": "name"},
                        {"data": "phone"},
                        {"data": "attended"}
                    ]
                });

                $('#studentAttendanceTable tbody').on('click', 'button', function () {
                    studentID = table.row($(this).parents('tr')).data().id;
                    columnIndex = table.cell($(this).closest('td')).index().column;
                    rowIndex = table.cell($(this).closest('td')).index().row;
                    action = 'mark';

                    $.ajax({
                        type: 'POST',
                        url: 'TutorScheduleServlet',
                        dataType: 'JSON',
                        data: {lessonID: id, studentID: studentID, tutorID: tutorID, action: action},
                        success: function (data) {
                            if (data.status) {
                                table.cell(rowIndex, columnIndex).data('Present').draw();
                                $("#view_lesson_attendance").val(data.attendance);
                            }
                        }
                    });
                });

                $('#alter_date').on('click', function () {
                    startDate = $('#datetimepicker').val();
                    endDate = $('#datetimepicker').val();
                    action = 'updateLessonDate';

                    $.ajax({
                        type: 'POST',
                        url: 'TutorScheduleServlet',
                        dataType: 'JSON',
                        data: {lessonID: id, action: action, startDate: startDate, endDate: endDate},
                        success: function (data) {
                        }
                    });
                });
            });
            $("#classDetails").modal('show');
        });

    });
</script>
