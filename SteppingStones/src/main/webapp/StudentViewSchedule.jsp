<%@include file="protect_student.jsp"%>
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_material.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<%@include file="header.jsp"%>
<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

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

<script>
    $(document).ready(function () {
        studentID = <%=user.getRespectiveID()%>
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
            url: 'StudentScheduleServlet',
            dataType: 'JSON',
            data: {studentID: studentID, action: action},
            success: function (data) {
                scheduler.parse(data.data, "json");
            }
        });
    });
</script>
