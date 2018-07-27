
<!DOCTYPE html>
<head>

</head>
<script src="styling/js/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="styling/js/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<script src="styling/js/dhtmlxscheduler_limit.js" type="text/javascript" charset="utf-8"></script>
<script src="styling/js/connector.js" type="text/javascript" charset="utf-8"></script>
<script src="styling/js/dhtmlxscheduler_minical.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="styling/css/dhtmlxscheduler_material.css" type="text/css" media="screen" title="no title" charset="utf-8">


<style>
    html, body{
        margin:0px;
        padding:0px;
        height:100%;
        overflow:hidden;
    }
    .event_work div,
    .dhx_cal_editor.event_work,
    .dhx_cal_event_line.event_work{
        background-color: #ff9633!important;
    }
    .dhx_cal_event_clear.event_work{
        color: #ff9633!important;
    }

    .event_meeting div,
    .dhx_cal_editor.event_meeting,
    .dhx_cal_event_line.event_meeting
    {
        background-color: #9575cd!important;
    }
    .dhx_cal_event_clear.event_meeting{
        color: #9575cd!important;
    }

    .event_movies div,
    .dhx_cal_editor.event_movies,
    .dhx_cal_event_line.event_movies{
        background-color: #ff5722!important;
    }
    .dhx_cal_event_clear.event_movies{
        color: #ff5722!important;
    }

    .event_rest div,
    .dhx_cal_editor.event_rest,
    .dhx_cal_event_line.event_rest{
        background-color: #0fc4a7!important;
    }
    .dhx_cal_event_clear.event_rest{
        color: #0fc4a7!important;
    }

    .add_event_button{
        position: absolute;
        width: 55px;
        height: 55px;
        background: #ff5722;
        border-radius: 50px;
        bottom: 40px;
        right: 55px;
        box-shadow: 0 2px 5px 0 rgba(0,0,0,0.3);
        z-index: 5;
        cursor:pointer;
    }
    .add_event_button:after{
        background: #000;
        border-radius: 2px;
        color: #FFF;
        content: attr(data-tooltip);
        margin: 16px 0 0 -137px;
        opacity: 0;
        padding: 4px 9px;
        position: absolute;
        visibility: visible;
        font-family: "Roboto";
        font-size: 14px;
        visibility: hidden;
        transition: all .5s ease-in-out;
    }
    .add_event_button:hover{
        background: #ff774c;
    }
    .add_event_button:hover:after{
        opacity: 0.55;
        visibility: visible;
    }
    .add_event_button span:before{
        content:"";
        background: #fff;
        height: 16px;
        width: 2px;
        position: absolute;
        left: 26px;
        top: 20px;
    }
    .add_event_button span:after{
        content:"";
        height: 2px;
        width: 16px;
        background: #fff;
        position: absolute;
        left: 19px;
        top: 27px;
    }

    .dhx_cal_event div.dhx_event_resize.dhx_footer{
        background-color: transparent !important;
    }
</style>

<script>

    function addEvent() {
        var id = scheduler.addEventNow();
        scheduler.showLightbox(id);
    }
    function init() {
        scheduler.config.xml_date = "%Y-%m-%d %H:%i";
        scheduler.config.first_hour = 7;
        scheduler.config.details_on_create = true;
        scheduler.config.details_on_dbl_click = true;
        scheduler.config.now_date = new Date();
        scheduler.config.first_hour = 8;
        scheduler.config.last_hour = 23;
        scheduler.config.time_step = 30;
        scheduler.config.limit_time_select = true;

        scheduler.attachEvent("onEventSave", function (id, ev, is_new) {
            if (!ev.subject) {
                alert("Please select a subject");
                return false;
            }
            if (!ev.level) {
                alert("Please select a level");
                return false;
            }
            return true;
        });

        var subjects = [{key: "", label: "Select Subject"},
            {key: "en", label: "English"},
            {key: "sci", label: "Science"},
            {key: "math", label: "Mathematics"}];

        var levels = [{key: "", label: "Select Level"},
            {key: "p3", label: "P3"},
            {key: "p4", label: "P4"},
            {key: "p5", label: "P5"}];

        scheduler.config.lightbox.sections = [
            {name: "subject", map_to: "subject", options: subjects, type: "select", focus: true},
            {name: "level", map_to: "level", options: levels, type: "select", focus: true},
            {name: "time", height: 72, type: "time", map_to: "auto"}
        ];

        scheduler.locale.labels.section_subject = "Subject";
        scheduler.locale.labels.section_time = "Timeubject";
        scheduler.locale.labels.section_level = "Level";

        scheduler.templates.event_text = function (start, end, event) {
            var toReturn = "";
            for (var i = 0; i < subjects.length; i++) {
                if (event.subject === subjects[i].key)
                    toReturn += subjects[i].label;
            }
            for (var i = 0; i < levels.length; i++) {
                if (event.level === levels[i].key)
                    toReturn += " " + levels[i].label;
            }
            return toReturn;
        };

        scheduler.init("scheduler_here", new Date(), "week");
    }

</script>

<body onload="init();" onresize="modSchedHeight()">

    <div id="scheduler_here" class="dhx_cal_container" style='width:100%;height:100%;'>
        <div class="dhx_cal_navline">
            <div class="dhx_cal_prev_button">&nbsp;</div>
            <div class="dhx_cal_next_button">&nbsp;</div>
            <div class="dhx_cal_today_button"></div>
            <div class="dhx_cal_date"></div>
            <div class="dhx_cal_tab" name="day_tab" style="right:332px;"></div>
            <div class="dhx_cal_tab" name="week_tab" style="right:268px;"></div>
            <div class="dhx_cal_tab" name="month_tab" style="right:204px;"></div>
            <div class="dhx_cal_tab" name="year_tab" style="right:140px;"></div>
        </div>
        <div class="dhx_cal_header">
        </div>
        <div class="dhx_cal_data">
        </div>		
    </div>
    <div class="add_event_button" onclick="addEvent()" data-tooltip="Create new event"><span></span></div>
</body>
