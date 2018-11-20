<%@include file="protect_parent.jsp"%>
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
    .dhx_cal_navline .dhx_cal_date {
        padding-left:100px;
    }
    @media screen and (max-width: 480px){
        body{
            font-size: 7px;
        }
        .dhx_cal_tab, .dhx_cal_tab.active{
            font-size: 7px;
            width: 45px;
            height: 20px;
        }
        .dhx_scale_bar, .dhx_month_body, .dhx_scale_holder, .dhx_body, .dhx_title {
            font-size: 7px;
        }
        .dhx_month_head, .dhx_scale_hour{
            font-size: 9px;
        }
        .week{
            left: 62px !important;
        }
        .month{
            left: 109px !important;
        }
        .year{
            left: 158px !important;
        }
        .dhx_cal_navline .dhx_cal_date{
            font-size: 11px;
            padding-left: 0px;
            top: 35px;
            font-weight: bold;
        }
        .dhx_cal_prev_button, .dhx_cal_next_button{
            height: 5px !important;
            width: 5px !important;
            top: 20px !important;
        }
        .dhx_cal_prev_button{
            left: 77% !important;
        }
        .dhx_cal_today_button{
            font-size: 9px;
            left: 76% !important;
            top: 8px !important;
        }
    }
    @media screen and (max-width: 767px) and (min-width: 481px){
        body{
            font-size: 12px;
        }
        .dhx_cal_tab, .dhx_cal_tab.active{
            font-size: 12px;
            width: 50px;
            height: 25px;
        }
        .dhx_scale_bar, .dhx_month_body, .dhx_scale_holder, .dhx_body, .dhx_title {
            font-size: 12px;
        }
        .dhx_month_head, .dhx_scale_hour{
            font-size: 13px;
        }
        .week{
            left: 68px !important;
        }
        .month{
            left: 120px !important;
        }
        .year{
            left: 177px !important;
        }
        .dhx_cal_navline .dhx_cal_date{
            font-size: 13px;
            padding-left: 0px;
            top: 35px;
            font-weight: bold;
        }
        .dhx_cal_prev_button, .dhx_cal_next_button{
            height: 13px !important;
            width: 13px !important;
            top: 20px !important;
        }
        .dhx_cal_prev_button{
            left: 77% !important;
        }
        .dhx_cal_today_button{
            font-size: 12px;
            left: 79% !important;
            top: 10px !important;
        }
    }
    @media screen and (max-width: 991px) and (min-width: 768px){
        body{
            font-size: 14px;
        }
        .dhx_cal_tab, .dhx_cal_tab.active{
            font-size: 14px;
            width: 60px;
            height: 28px;
        }
        .dhx_scale_bar, .dhx_month_body, .dhx_scale_holder, .dhx_body, .dhx_title {
            font-size: 14px;
        }
        .dhx_month_head, .dhx_scale_hour{
            font-size: 15px;
        }
        .week{
            left: 78px !important;
        }
        .month{
            left: 140px !important;
        }
        .year{
            left: 205px !important;
        }
        .dhx_cal_navline .dhx_cal_date{
            font-size: 15px;
            padding-left: 0px;
            top: 35px;
            font-weight: bold;
        }
        .dhx_cal_prev_button, .dhx_cal_next_button{
            height: 15px !important;
            width: 15px !important;
            top: 20px !important;
        }
        .dhx_cal_prev_button{
            left: 77% !important;
        }
        .dhx_cal_today_button{
            font-size: 14px;
            left: 82% !important;
            top: 10px !important;
        }
    }
</style>

<div class="col-md-10">
    <div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:100%;'>
        <div class="dhx_cal_navline">
            <div class="dhx_cal_prev_button">&nbsp;</div>
            <div class="dhx_cal_next_button">&nbsp;</div>
            <div class="dhx_cal_today_button"></div>
            <div class="dhx_cal_date"></div>
            <div class="dhx_cal_tab day" name="day_tab"></div>
            <div class="dhx_cal_tab week" name="week_tab"></div>
            <div class="dhx_cal_tab month" name="month_tab"></div>
            <div class="dhx_cal_tab year" name="year_tab"></div>
        </div>
        <div class="dhx_cal_header"></div>
        <div class="dhx_cal_data"></div>       
    </div>
</div>
</div>

<script>
    $(document).ready(function () {
        parentID = <%=user.getRespectiveID()%>
        action = 'retrieveParent';

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
            data: {parentID: parentID, action: action},
            success: function (data) {
                scheduler.parse(data.data, "json");
            }
        });
    });
</script>
