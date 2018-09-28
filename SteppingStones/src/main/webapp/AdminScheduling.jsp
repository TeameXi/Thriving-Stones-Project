<%@include file="protect_branch_admin.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_material.css" type="text/css" charset="utf-8">
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
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

    .btn-primary.active {
        background: red !important;
    }
    .btn-primary[disabled]:hover {
        background: red !important;
    }

    .modal {
        overflow-y:auto;
    }
</style>
<div class="col-md-10" id="btn">
    <div class="form-inline" style="text-align:center;">
        <button id="0" class="btn btn-primary" type="button" onclick="refreshSchedule(0)" style="text-align: center; width: 6%;">All</button>
        <button id="1" class="btn btn-primary" type="button" onclick="refreshSchedule(1)" style="text-align: center; width: 6%;">Pri 1</button>
        <button id="2" class="btn btn-primary" type="button" onclick="refreshSchedule(2)" style="text-align: center; width: 6%;">Pri 2</button>
        <button id="3" class="btn btn-primary" type="button" onclick="refreshSchedule(3)" style="text-align: center; width: 6%;">Pri 3</button>
        <button id="4" class="btn btn-primary" type="button" onclick="refreshSchedule(4)" style="text-align: center; width: 6%;">Pri 4</button>
        <button id="5" class="btn btn-primary" type="button" onclick="refreshSchedule(5)" style="text-align: center; width: 6%;">Pri 5</button>
        <button id="6" class="btn btn-primary" type="button" onclick="refreshSchedule(6)" style="text-align: center; width: 6%;">Pri 6</button>
        <button id="7" class="btn btn-primary" type="button" onclick="refreshSchedule(7)" style="text-align: center; width: 6%;">Sec 1</button>
        <button id="8" class="btn btn-primary" type="button" onclick="refreshSchedule(8)" style="text-align: center; width: 6%;">Sec 2</button>
        <button id="09" class="btn btn-primary" type="button" onclick="refreshSchedule(9)" style="text-align: center; width: 6%;">Sec 3</button>
        <button id="10" class="btn btn-primary" type="button" onclick="refreshSchedule(10)" style="text-align: center; width: 6%;">Sec 4</button>
    </div>
</div>
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

<div class="modal fade" id="editOptions" tabindex="-1" role="dialog" aria-hidden="true">
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
                    <div class="form-inline">
                        <label class = "form-control-label" style="margin-left: 10px;">Class Timing :</label>
                        <input type="text" class="form-control" id="timing_details" style="text-align: center; width: 30%; margin-left: 10px;" readOnly="true">
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label" style="margin-left: 10px;">Level :</label>
                        <input type="text" class="form-control" id="level_details" style="text-align: center; width: 30%; margin-left: 10px;" readOnly="true">
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label" style="margin-left: 10px;">Subject :</label>
                        <input type="text" class="form-control" id="subject_details" style="text-align: center; width: 30%; margin-left: 10px;" readOnly="true">
                    </div>
                </div><br/>
                <div class="row">
                    <div style="text-align: center;">
                        <label>Do you want to edit the details of a single lesson or the entire class?</label>
                    </div>
                </div><br/>
                <div class="row">
                    <div style="display: block; margin:auto;">
                        <div style="text-align: center;">
                            <button id="editOccurrence" class="btn btn-default">Edit Lesson</button>
                            <button id="editSeries" class="btn btn-default">Edit Class</button>
                            <button id="deleteLesson" class="btn btn-default">Delete Lesson</button>
                            <button id="deleteClass" class="btn btn-default">Delete Class</button>
                        </div>
                    </div>
                </div>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>       
    </div>
</div>

<div class="modal fade" id="editLesson" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Edit Lesson</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="margin-left: 10px;">
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Lesson Start Time :</label>
                        <input type="text" class="form-control" id="datetimepicker" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#datetimepicker').datetimepicker({
                                format: 'YYYY-MM-DD HH:mm:ss',
                                defaultDate: $('#datetimepicker').val()
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Lesson End Time :</label>
                        <input type="text" class="form-control" id="datetimepicker1" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#datetimepicker1').datetimepicker({
                                format: 'YYYY-MM-DD HH:mm:ss',
                                defaultDate: $('#datetimepicker1').val()
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Replacement Tutor :</label>
                        <select class="form-control" id="replacementTutor" style="width: 40%; margin-left: 10px; text-align: center;">
                        </select>
                    </div>
                </div>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="save" class="btn btn-default">Save Changes</button>
            </div>
        </div>       
    </div>
</div>

<div class="modal fade" id="editClass" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Edit Class</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="err" role="alert"></div>
                <div class="row form-inline" style="margin-left: 10px;">
                    <label class = "form-control-label">Start Timing :</label>
                    <input type="text" class="form-control" id="start_time" style="text-align: center; width: 30%; margin-left: 10px;">
                    <script type="text/javascript">
                        $('#start_time').datetimepicker({
                            format: 'HH:mm:ss',
                            defaultDate: $('#start_time').val()
                        });
                    </script>
                </div><br/>
                <div class="row form-inline" style="margin-left: 10px;">
                    <label class = "form-control-label">End Timing :</label>
                    <input type="text" class="form-control" id="end_time" style="text-align: center; width: 30%; margin-left: 10px;">
                    <script type="text/javascript">
                        $('#end_time').datetimepicker({
                            format: 'HH:mm:ss',
                            defaultDate: $('#end_time').val()
                        });
                    </script>
                </div><br/>
                <div class="row form-inline" style="margin-left: 10px;">
                    <label class = "form-control-label">Start Date :</label>
                    <input type="text" class="form-control" id="start_date" style="text-align: center; width: 30%; margin-left: 10px;">
                    <script type="text/javascript">
                        $('#start_date').datetimepicker({
                            format: 'YYYY-MM-DD',
                            defaultDate: $('#start_date').val()
                        });
                    </script>
                </div><br/>
                <div class="row form-inline" style="margin-left: 10px;">
                    <label class = "form-control-label">End Date :</label>
                    <input type="text" class="form-control" id="end_date" style="text-align: center; width: 30%; margin-left: 10px;">
                    <script type="text/javascript">
                        $('#end_date').datetimepicker({
                            format: 'YYYY-MM-DD',
                            defaultDate: $('#end_date').val()
                        });
                    </script>
                </div><br/>
                <div class="row form-inline" style="margin-left: 10px;">
                    <label class = "form-control-label">Replacement Tutor :</label>
                    <select class="form-control" id="replacementClassTutor" style="width: 40%; margin-left: 10px; text-align: center;">
                    </select>
                </div>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="saveClass" class="btn btn-default">Save Changes</button>
            </div>
        </div>       
    </div>
</div>

<div class="modal fade" id="classCreation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Create Class</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="margin-left: 20px;">
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Start Timing :</label>
                        <input type="text" class="form-control" id="create_start_time" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#create_start_time').datetimepicker({
                                format: 'HH:mm:ss'
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">End Timing :</label>
                        <input type="text" class="form-control" id="create_end_time" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#create_end_time').datetimepicker({
                                format: 'HH:mm:ss'
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Start Date :</label>
                        <input type="text" class="form-control" id="create_start_date" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#create_start_date').datetimepicker({
                                format: 'YYYY-MM-DD',
                                defaultDate: $("#create_start_date").val()
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">End Date :</label>
                        <input type="text" class="form-control" id="create_end_date" style="text-align: center; width: 30%; margin-left: 10px;">
                        <script type="text/javascript">
                            $('#create_end_date').datetimepicker({
                                format: 'YYYY-MM-DD',
                                defaultDate: $("#create_end_date").val()
                            });
                        </script>
                    </div>
                </div><br/>
                <div class="row" id="holidays">
                    <div class="form-inline">
                        <label class = "form-control-label">Holiday Dates :</label>
                        <input type="text" class="form-control holidays" id="create_holidays[]" style="text-align: center; width: 30%; margin-left: 10px;" placeholder="YYYY-MM-DD">
                        <button class="btn btn-success" type="button" id="add_new_hols" onclick="add_hols()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Level :</label>
                        <select class="form-control" id="level" style="width: 30%; margin-left: 10px; text-align: center;">
                        </select>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Subject :</label>
                        <select class="form-control" id="subject" style="width: 30%; margin-left: 10px; text-align: center;">
                        </select>
                    </div>
                </div><br/>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Tutor :</label>
                        <select class="form-control" id="assign_tutor" style="width: 30%; margin-left: 10px; text-align: center;">
                            <option value="0">Select Tutor</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="form-inline">
                        <label class = "form-control-label">Class Type :</label>
                        <select class="form-control" id="class_type" style="width: 30%; margin-left: 10px; margin-top: 10px; text-align: center;">
                            <option value="P">Premium</option>
                            <option value="N">Normal</option>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="form-inline">
                        <label class = "form-control-label">Recurring :</label>
                        <input type="checkbox" class="form-check-input" id="recurring" style="margin-left: 10px; margin-top: 5px;">
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="form-inline">
                        <label class = "form-control-label">Payment Reminder :</label>
                        <input type="checkbox" class="form-check-input" id="reminder" style="margin-left: 10px; margin-top: 5px;">
                    </div>
                </div>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="createClass" class="btn btn-default">Create Class</button>
            </div>
        </div>       
    </div>
</div>
<div class="modal fade" id="error" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Oopz! Something went wrong!</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="errorBody">
            </div>  
            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>       
    </div>
</div>
<%@include file="footer.jsp"%>
<script>
    function refreshSchedule(levelID) {
        $("#btn .btn").removeClass('active');
        $("#" + levelID).addClass('active');
        selectedLevelID = levelID;
        action = 'retrieveByLevel';
        $.ajax({
            type: 'POST',
            url: 'AdminScheduleServlet',
            dataType: 'JSON',
            data: {branchID: <%=branch_id%>, action: action, levelID: levelID},
            success: function (data) {
                scheduler.clearAll();
                scheduler.parse(data.data, "json");
            }
        });
    }

    function add_hols() {
        $("#holidays").append('<div class="row" id="new_field"><div class="form-inline"><input type="text" class="form-control holidays" id="create_holidays[]'
                + '" style="text-align: center; width: 29%; margin-left: 134px; margin-top: 10px; margin-right: 5px;" '
                + 'placeholder="YYYY-MM-DD"><button class="btn btn-danger minusbtn" type="button" onclick="remove_hols()" style="margin-top: 10px;">'
                + '<span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button></div></div>');
    }

    function remove_hols() {
        $('#new_field').closest('#new_field').remove();
    }

    $(document).ready(function () {
        $('#0').addClass('active');
        selectedLevelID = 0;
        branchID = <%=branch_id%>
        action = 'retrieve';

        scheduler.attachEvent("onBeforeDrag", function () {
            return false;
        });//block event resize and drag
        scheduler.config.dblclick_create = false;//block event creation by doubleclick
        scheduler.config.readonly_form = true;
        scheduler.config.readonly = true;
        scheduler.config.prevent_cache = true;
        scheduler.config.xml_date = '%Y-%m-%d %H:%i:%s';
        scheduler.init('scheduler_here', new Date(), "month");

        var formatFunc = scheduler.date.date_to_str("%Y-%m-%d %H:%i:%s");

        $.ajax({
            type: 'POST',
            url: 'AdminScheduleServlet',
            dataType: 'JSON',
            data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
            success: function (data) {
                scheduler.parse(data.data, "json");
            }
        });

        scheduler.attachEvent("onClick", function (id, e) {
            lessonID = id;
            $("#editOptions").on('shown.bs.modal', function () {
                action = 'editOptions';

                $.ajax({
                    type: 'POST',
                    url: 'AdminScheduleServlet',
                    dataType: 'JSON',
                    data: {action: action, lessonID: lessonID},
                    success: function (data) {
                        $("#timing_details").val(data.timing);
                        $("#level_details").val(data.level);
                        $("#subject_details").val(data.subject);
                    }
                });
            });
            $("#editOptions").modal('show');
        });

        $("#classCreation").on('hidden.bs.modal', function () {
            $(this).removeData('bs.modal');
        });
        

        scheduler.attachEvent("onEmptyClick", function (date, e) {
            selectedDate = formatFunc(date).split(" ")[0];
            startTime = '10:00:00';
            endTime = '11:30:00';

            $("#classCreation").one('shown.bs.modal', function () {
                action = 'retrieveOptions';

                $('#recurring').prop('checked', false);
                $('#reminder').prop('checked', false);

                $.ajax({
                    type: 'POST',
                    url: 'AdminScheduleServlet',
                    dataType: 'JSON',
                    data: {branchID: branchID, action: action},
                    success: function (data) {
                        $("#create_start_date").val(selectedDate);
                        $("#create_end_date").val(selectedDate);

                        $("#create_start_time").val(startTime);
                        $("#create_end_time").val(endTime);

                        $("#level").empty();
                        for (var i = 0; i < data.level.length; i++) {
                            $("#level").append('<option value="' + data.level[i].id + '">' + data.level[i].name + '</option>');
                        }

                        $("#assign_tutor").empty();
                        for (var i = 0; i < data.tutor.length; i++) {
                            $("#assign_tutor").append('<option value="' + data.tutor[i].id + '">' + data.tutor[i].name + '</option>');
                        }

                        $("#subject").empty();

                        for (var i = 0; i < data.subject.length; i++) {
                            $("#subject").append('<option value="' + data.subject[i].id + '">' + data.subject[i].name + '</option>');
                        }
                    }
                });

                $("#level").change(function () {
                    action = 'retrieveSubjectOptions';
                    levelID = $("#level").val();
                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {levelID: levelID, action: action},
                        success: function (data) {
                            $("#subject").empty();
                            for (var i = 0; i < data.subject.length; i++) {
                                $("#subject").append('<option value="' + data.subject[i].id + '">' + data.subject[i].name + '</option>');
                            }
                        }
                    });
                });

                $('#createClass').on('click', function (e) {
                    holidays = [];
                    index = 0;
                    $.each($('.holidays'), function () {
                        console.log($(this).val());
                        holidays[index] = $(this).val();
                        index++;
                    });
                    
                    e.stopImmediatePropagation();

                    startDate = $("#create_start_date").val();
                    endDate = $("#create_end_date").val();
                    startTime = $("#create_start_time").val();
                    end = $("#create_end_time").val();
                    level = $("#level").val();
                    tutor = $("#assign_tutor").val();
                    subject = $("#subject").val();
                    recurring = $("#recurring").val();
                    reminder = $("#reminder").val();
                    classType = $("#class_type").val();
                    action = 'create';

                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {classType: classType, branchID: branchID, reminder: reminder, recurring: recurring, endDate: endDate, holidays: holidays.toString(), action: action, startDate: startDate, endTime: end, startTime: startTime, levelID: level, subjectID: subject, tutorID: tutor},
                        success: function (data) {
                            if (data.status) {
                                action = 'retrieve';
                                scheduler.clearAll();
                                $.ajax({
                                    type: 'POST',
                                    url: 'AdminScheduleServlet',
                                    dataType: 'JSON',
                                    data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
                                    success: function (data) {
                                        scheduler.parse(data.data, "json");
                                    }
                                });

                                $("#classCreation").modal('hide');

                            } else {
                                $('#error').on('shown.bs.modal', function () {
                                    $('#errorBody').empty();
                                    if (data.hasOwnProperty('start_time_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px;font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.start_time_error + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('end_time_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.end_time_error + '</span></div>');
                                    } else if (data.hasOwnProperty('invalid_timing')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_timing + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('start_date_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px;font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.start_date_error + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('end_date_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.end_date_error + '</span></div>');
                                    } else if (data.hasOwnProperty('invalid_date')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_date + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('overlap')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.overlap + '</span></div>');
                                    }
                                });
                                $('#error').modal('show');
                            }
                        }
                    });
                });
            });

            $("#classCreation").modal('show');
        });

        $("#editOccurrence").off('click').on('click', function (id, e) {
            $('#editLesson').on('shown.bs.modal', function () {
                action = 'retrieveLesson';

                $.ajax({
                    type: 'POST',
                    url: 'AdminScheduleServlet',
                    dataType: 'JSON',
                    data: {lessonID: lessonID, action: action, branchID: branchID},
                    success: function (data) {
                        $("#datetimepicker").val(data.start);
                        $("#datetimepicker1").val(data.end);
                        tutor = data.tutor;
                        $("#replacementTutor").empty();
                        for (var i = 0; i < data.tutors.length; i++) {
                            if (data.tutors[i].id === tutor) {
                                $("#replacementTutor").append("<option value='" + data.tutors[i].id + "' selected>" + data.tutors[i].name + "</option>");
                            } else {
                                $("#replacementTutor").append('<option value="' + data.tutors[i].id + '">' + data.tutors[i].name + '</option>');
                            }
                        }
                    }
                });

                $('#save').on('click', function (e) {
                    start = $('#datetimepicker').val();
                    end = $('#datetimepicker1').val();
                    tutor = $('#replacementTutor').val();
                    action = 'updateLesson';
                    
                    e.stopImmediatePropagation();

                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {lessonID: lessonID, action: action, start: start, end: end, tutor: tutor},
                        success: function (data) {
                            if (data.status) {
                                action = 'retrieve';
                                scheduler.clearAll();
                                $.ajax({
                                    type: 'POST',
                                    url: 'AdminScheduleServlet',
                                    dataType: 'JSON',
                                    data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
                                    success: function (data) {
                                        scheduler.parse(data.data, "json");
                                    }
                                });
                                $("#editLesson").modal('hide');

                            } else {
                                $('#error').on('shown.bs.modal', function () {
                                    $('#errorBody').empty();
                                    if (data.hasOwnProperty('start')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px;font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.start + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('end')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.end + '</span></div>');
                                    } else if (data.hasOwnProperty('invalid_timing')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_timing + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('invalid_tutor')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_tutor + '</span></div>');
                                    }
                                });
                                $('#error').modal('show');
                            }

                        }
                    });
                });
            });
            $("#editLesson").modal('show');
        });

        $("#editSeries").on('click', function (id, e) {
            $('#editClass').on('shown.bs.modal', function () {
                action = 'retrieveClass';

                $.ajax({
                    type: 'POST',
                    url: 'AdminScheduleServlet',
                    dataType: 'JSON',
                    data: {lessonID: lessonID, action: action, branchID: branchID},
                    success: function (data) {
                        $("#start_date").val(data.start);
                        $("#end_date").val(data.endDate);
                        $("#start_time").val(data.startTime);
                        $("#end_time").val(data.end);
                        tutor = data.tutor;
                        $("#replacementClassTutor").empty();
                        for (var i = 0; i < data.tutors.length; i++) {
                            if (data.tutors[i].id === tutor) {
                                $("#replacementClassTutor").append("<option value='" + data.tutors[i].id + "' selected>" + data.tutors[i].name + "</option>");
                            } else {
                                $("#replacementClassTutor").append('<option value="' + data.tutors[i].id + '">' + data.tutors[i].name + '</option>');
                            }
                        }
                    }
                });
                
                $('#saveClass').on('click', function (e) {
                    startTime = $('#start_time').val();
                    endTime = $('#end_time').val();
                    startDate = $('#start_date').val();
                    endDate = $('#end_date').val();
                    tutor = $('#replacementClassTutor').val();
                    action = 'updateClass';
                    
                    e.stopImmediatePropagation();
                    
                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {endDate: endDate, lessonID: lessonID, action: action, startTime: startTime, endTime: endTime, startDate: startDate, tutor: tutor},
                        success: function (data) {
                            if (data.status) {
                                action = 'retrieve';
                                scheduler.clearAll();
                                $.ajax({
                                    type: 'POST',
                                    url: 'AdminScheduleServlet',
                                    dataType: 'JSON',
                                    data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
                                    success: function (data) {
                                        scheduler.parse(data.data, "json");
                                    }
                                });
                                $("#editClass").modal('hide');

                            } else {
                                $('#error').on('shown.bs.modal', function () {
                                    $('#errorBody').empty();
                                    if (data.hasOwnProperty('start_time_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px;font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.start_time_error + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('end_time_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.end_time_error + '</span></div>');
                                    } else if (data.hasOwnProperty('invalid_timing')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_timing + '</span></div>');
                                    }
                                    
                                    if (data.hasOwnProperty('start_date_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px;font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.start_date_error + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('end_date_error')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.end_date_error + '</span></div>');
                                    } else if (data.hasOwnProperty('invalid_date')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_date + '</span></div>');
                                    }

                                    if (data.hasOwnProperty('invalid_tutor')) {
                                        $('#errorBody').append('<div style="margin-bottom: 10px; font-size: 15px; text-align:center; color:red;"><span class="glyphicon glyphicon-remove"></span><span class="pc_title centered">  ' + data.invalid_tutor + '</span></div>');
                                    }
                                });
                                $('#error').modal('show');
                            }

                        }
                    });
                });
            });
            $("#editClass").modal('show');
        });

        $("#deleteLesson").on('click', function (id, e) {
            action = 'delete';
            $.ajax({
                type: 'POST',
                url: 'AdminScheduleServlet',
                dataType: 'JSON',
                data: {lessonID: lessonID, action: action},
                success: function (data) {
                    action = 'retrieve';
                    scheduler.clearAll();
                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
                        success: function (data) {
                            scheduler.parse(data.data, "json");
                        }
                    });
                    $("#editOptions").modal('hide');
                }
            });
        });

        $("#deleteClass").on('click', function (id, e) {
            action = 'deleteClass';
            $.ajax({
                type: 'POST',
                url: 'AdminScheduleServlet',
                dataType: 'JSON',
                data: {lessonID: lessonID, action: action},
                success: function (data) {
                    action = 'retrieve';
                    scheduler.clearAll();
                    $.ajax({
                        type: 'POST',
                        url: 'AdminScheduleServlet',
                        dataType: 'JSON',
                        data: {branchID: branchID, action: action, selectedLevel: selectedLevelID},
                        success: function (data) {
                            scheduler.parse(data.data, "json");
                        }
                    });
                    $("#editOptions").modal('hide');
                }
            });
        });
    });
</script>
