<%@page import="java.util.Calendar"%>
<%@page import="entity.Level"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_recurring.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_editors.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/ext/dhtmlxscheduler_multiselect.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/scheduler/dhtmlxscheduler_material.css" type="text/css" charset="utf-8">
<%@include file="header.jsp"%>
<style>
    .autocomplete-items {
        position: absolute;
        border: 1px solid #d4d4d4;
        border-bottom: none;
        border-top: none;
        z-index: 99;
        /*position the autocomplete items to be the same width as the container:*/
        top: 100%;
        left: 0;
        right: 0;
    }
    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff; 
        border-bottom: 1px solid #d4d4d4; 
    }
    .autocomplete-items div:hover {
        /*when hovering an item:*/
        background-color: #e9e9e9; 
    }
    .autocomplete-active {
        /*when navigating through the items using the arrow keys:*/
        background-color: DodgerBlue !important; 
        color: #ffffff; 
    }
    
    .dhx_cal_light.dhx_cal_light_wide{
        width:540px;
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Scheduling</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row">
        <form name="lvlForm">
            <label>Filter By : </label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" checked value="0">All</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="1">Pri 1</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="2">Pri 2</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="3">Pri 3</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="4">Pri 4</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="5">Pri 5</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="6">Pri 6</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="7">Sec 1</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="8">Sec 2</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="9">Sec 3</label>
            <label class="radio-inline"><input type="radio" name="levelRdoBtn" value="10">Sec 4</label>
            <input type="hidden" value="<%=branch_id%>" id="branch_id"/>
    </form>
    </div>

    <br/>                       
                        
    <div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:100%;'>
        <div class="dhx_cal_navline">
                <div class="dhx_cal_prev_button">&nbsp;</div>
                <div class="dhx_cal_next_button">&nbsp;</div>
                <div class="dhx_cal_today_button"></div>
                <div class="dhx_cal_date"></div>
                <div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>
                <div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>
                <div class="dhx_cal_tab" name="year_tab" style="right:280px;"></div>
                <div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
        </div>
        <div class="dhx_cal_header">
        </div>
        <div class="dhx_cal_data">
        </div>      
    </div>
    
    

</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script>
 $(document).ready(function(){
        level_id = 0;
        branch_id = $("#branch_id").val();
        
        var myParam = location.search.split('lvl_id=')[1];
        if(typeof myParam === "undefined"){
            $.ajax({
                type: 'POST',
                url: 'RetrieveAllClassesData',
                dataType: 'JSON',
                data: {branchId:branch_id,levelId:level_id},
                success: function (data) {
                    console.log(data);
                    processingSchedule(data,level_id);
                }

            });
        }else{
            document.lvlForm.levelRdoBtn.value = myParam;
            levelId = parseInt(myParam);
            $.ajax({
                type: 'POST',
                url: 'RetrieveAllClassesData',
                dataType: 'JSON',
                data: {branchId:branch_id,levelId:levelId}
            }).then(function(data){
                processingSchedule(data,levelId,branch_id);
            });  
        }
        
        //radio onchange
        $("input[name='levelRdoBtn']").on('change', function () {
            var selectedValue = $("input[name='levelRdoBtn']:checked").val();
            window.location.href = 'CreateClassesSchedule.jsp?lvl_id='+selectedValue;
        
        });

        
        function processingSchedule(data,level_id,branch_id){
        //Refresh Schedule
                console.log(data);
                scheduler.clearAll();
                if(data === -1){
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                    $("#errorMsg").html(html);
                    $('#errorMsg').fadeIn().delay(2000).fadeOut();
                }else{  
                    var classLists = "";
                    var subject = "";
                    var tutor = "";
                 
                    classLists = data["class"];

                    // Subject Dropdown data
                    subject = data["subject"];
                    
//                    console.log("Level Subject");
//                    console.log(subject);
//                    console.log("======");
//                           
                    // Tutor Dropdown Data
                    tutor = data["tutor"];  
                    
                    // Radio box
                    scheduler.locale.labels.section_selectme = "Reminder"; 
                    var has_reminder = [
                        { key: 0, label: 'No' },
                        { key: 1, label: 'Yes' }
                    ];
                    
                    // Radio box for tutor assignment
                    scheduler.locale.labels.section_changeTutorType = "Tutor Changes Type"; 
                    var tutor_assignment_type = [
                        { key: 0, label: 'Change For 1 Lesson' },
                        { key: 1, label: 'For Following Lessons' }
                    ];
                    
                    // SElect primary box
                    var lvlLists = [
                        { key: 1, label: 'Primary 1' },
                        { key: 2, label: 'Primary 2' },
                        { key: 3, label: 'Primary 3' },
                        { key: 4, label: 'Primary 4' },
                        { key: 5, label: 'Primary 5' },
                        { key: 6, label: 'Primary 6' },
                        { key: 7, label: 'Primary 7' },
                        { key: 8, label: 'Primary 8' },
                        { key: 9, label: 'Primary 9' },
                        { key: 10, label: 'Primary 10' },
                    ];
                    
                    // Checkbox Lists
                    scheduler.locale.labels.section_selectCheckbox = "Holidays"; 
                    
                    // Lightbox field
                    scheduler.locale.labels.section_template = 'Lesson';
                    scheduler.locale.labels.section_datetemplate = 'Lesson Date';
                    scheduler.locale.labels.section_timetemplate = 'Duration';
                    scheduler.locale.labels.section_tutortemplate = 'Assign Tutor';
                    scheduler.locale.labels.section_currenttutortemplate = 'Tutor';
                 
                    
                    // CUSTOM ADD LIGHTBOX
                    scheduler.form_blocks["custom_classendtimeperiod"]={
                            render:function(sns){
                                return "<div class='dhx_cal_ltext'>&nbsp;<input type='date' id='ending' /></div>";
                            },
                            set_value:function(node,value,ev){
                                node.childNodes[1].value=value||"";
                            },
                           get_value:function(node,ev){
                               return node.childNodes[1].value; },
                            focus:function(node){
                                var a=node.childNodes[1]; a.select(); a.focus(); 
                            }
                    }
                    
                    // HOLIDAY DYNAMIC
                    scheduler.form_blocks["custom_holidayDate"]={
                            render:function(sns){
                                return "<div class='dhx_cal_ltext'>&nbsp;<input type='text' id='classHolidayDate' /><button type='button' class='btn btn-default addButton'><i class='zmdi zmdi-plus'></i></button></div>";
                            },
                            set_value:function(node,value,ev){
                                node.childNodes[1].value=value||"";
                            },
                           get_value:function(node,ev){ var convert = scheduler.date.str_to_date("%format%",true); return convert(node.childNodes[1].value); },
                            focus:function(node){
                                var a=node.childNodes[1]; a.select(); a.focus(); 
                            }
                    }
                    
                    if(level_id === 0){
                        var add_lightbox = [
                            {name:"Level", height:20, type:"select", options: lvlLists, map_to:"level" },
                            {name:"Subject", height:20, type:"select", options: subject, map_to:"subject" },
                            {name:"Assign To", height:20,type:"select", options: tutor, map_to:"tutor"},
                            {name:"selectme", height: 20, options: has_reminder, map_to:"has_reminder", type:"radio", vertical: true,default_value:"0" },
                            {name:"Class EndDate", height:14, map_to:"ending", type:"custom_classendtimeperiod"},
                            {name:"Date Skip For Holiday", height:14, map_to:"skip_date", type:"custom_holidayDate"},
                            {name:"time", height:72, type:"time", map_to:"auto" }
                        ];
                    }else{
                        var add_lightbox = [
                            {name:"Subject", height:20, type:"select", options: subject, map_to:"subject" },
                            {name:"Assign To", height:20,type:"select", options: tutor, map_to:"tutor"},
                            {name:"selectme", height: 20, options: has_reminder, map_to:"has_reminder", type:"radio", vertical: true,default_value:"0" },
                            {name:"Class EndDate", height:14, map_to:"ending", type:"custom_classendtimeperiod"},
                            {name:"Date Skip For Holiday", height:14, map_to:"skip_date", type:"custom_holidayDate"},
                            // {name:"recurring", type:"recurring", map_to:"rec_type", button:"recurring"},
                            {name:"time", height:72, type:"time", map_to:"auto" }
                        ];
                    }
                   
//                    console.log(scheduler.form_blocks.custom_classendtimeperiod);
//                    scheduler.form_blocks.custom_classendtimeperiod.button_click=function(index,button,shead,sbody){
//                        console.log("fff");
//                    }
                    scheduler.config.xml_date="%Y-%m-%d %H:%i";
                    
                    // Recurring Events
                    scheduler.config.prevent_cache = true;
                    scheduler.config.details_on_create=true;
                    scheduler.config.details_on_dblclick=true;
                    scheduler.config.occurrence_timestamp_in_utc = true;
                    scheduler.config.repeat_precise = true;
                    
                    // Auto Update End Time
                    scheduler.config.event_duration = 90; 
                    scheduler.config.auto_end_date = true;

                    // EDIT ICON 
                    scheduler.config.icons_select = [
                        "icon_details",
                        "icon_delete"
                    ];
                    
                    // Customization of edit and add for lightbox
                    if(!scheduler._onBeforeLightbox){
                        scheduler.attachEvent("onBeforeLightbox", function(event_id) {
                        // Take out delete btn
                        scheduler.config.buttons_left = [];
                           
                        scheduler.resetLightbox();
  
                        var ev = scheduler.getEvent(event_id);

                        tempDate = new Date(ev["start_date"]);
                        tempDay = tempDate.getDay(); 

                        if (ev.restricted ===true){
                           
                            // Display existing tutor only if exist
                            currentTutor = "";
                            if(ev["tutor"] !== 0){
                                findingTutorName = data["tutor"].find(x=>x.key === ev["tutor"] && x.subject === parseInt(ev["text"]));
                                if(typeof findingTutorName !== "undefined" || findingTutorName === "undefined"){
                                    currentTutor = findingTutorName.label;
                                }
                            }
                            
                            findingLvlIdGivenValue = lvlLists.find(x => x.label === ev.level);
                            editlvlId = findingLvlIdGivenValue.key;
                            
                            //Filter Tutor Lists For level and subject
                            var filterTutor = data["tutor"].filter(function (el) {
                                return editlvlId === parseInt(el.level) && parseInt(ev["text"]) === el.subject;
                            });
                            if(filterTutor.length > 0){
                                var edit_lightbox = [
                                    {name:"template", height: 60, type:"template", map_to:"my_template"},
                                    {name:"currenttutortemplate", height:20,type:"template", map_to:"currentTutorTemple"},
                                    {name:"datetemplate", height:20, type:"template", map_to:"lessonDate"},
                                    {name:"timetemplate", height:20, type:"template", map_to:"lessonTime"},
                                    {name:"Assign To", height:20,type:"select", options: filterTutor, map_to:"tutor"},
                                    {name:"changeTutorType", height: 20, options: tutor_assignment_type, map_to:"tutor_assignment_type", type:"radio",vertical:true,default_value:0}
                                ];
                            }else{
                                var edit_lightbox = [
                                    {name:"template", height: 60, type:"template", map_to:"my_template"},
                                    {name:"currenttutortemplate", height:20,type:"template", map_to:"currentTutorTemple"},
                                    {name:"datetemplate", height:20, type:"template", map_to:"lessonDate"},
                                    {name:"timetemplate", height:20, type:"template", map_to:"lessonTime"},
                                    {name:"tutortemplate", height:20,type:"template", map_to:"tutorText"},
                                    {name:"changeTutorType", height: 20, options: tutor_assignment_type, map_to:"tutor_assignment_type", type:"radio",vertical:true,default_value:0}
                                ];                             
                            }

                            // Edit Display
                            scheduler.config.lightbox.sections = edit_lightbox;
                            
                            // Display Subject
                            eventNameObj = data["subject"].find(x => x.key === parseInt(ev["text"]));
                            eventName = '0';
              
                            if(typeof  eventNameObj !== "undefined"){
                                eventName = eventNameObj.label;
                            }
                            ev.my_template = "<b>Subject : </b>"+ eventName+"<br>";
                            
                            //Display Tutor
                            if(currentTutor !== ""){
                                ev.currentTutorTemple = currentTutor+"<br/>";
                            }else{
                                ev.currentTutorTemple = "Tutor has not been assign yet. Assign now<br/>";
                            }
                            
                            //Display time
                            temp_year = tempDate.getFullYear();

                            ev.my_template += "<b>Details - </b> Level : "+ev.level+", Year: "+temp_year+", Monthly Cost: "+ev.fees+"<br>";
                            temp_month = ("0" + (tempDate.getMonth() + 1)).slice(-2);
                            temp_day = ("0" + tempDate.getDate()).slice(-2);
                            temp_hours = ("0" + tempDate.getHours()).slice(-2);
                            temp_minutes = ("0" + tempDate.getMinutes()).slice(-2);
                            
                            temp_end_date = new Date(ev["end_date"]);
                            temp_end_hours = ("0" + temp_end_date.getHours()).slice(-2);
                            temp_end_minutes = ("0" + temp_end_date.getMinutes()).slice(-2); 
                            
                            // date
                            
                            ev.lessonDate = [temp_year,temp_month,temp_day].join("-");
                            ev.lessonTime = [temp_hours,temp_minutes,"00"].join(":");
                            ev.lessonTime += "-"+[temp_end_hours,temp_end_minutes,"00"].join(":");
                         
                            // Display Tutor Assignment
                            if(filterTutor.length <= 0){
                                ev.tutorText = "No Tutor to assign to";
                            }
                            
                        } else {
                            // Add Display
                            scheduler.config.lightbox.sections = add_lightbox;
                        };   
                        return true;
                    });
                    }

                    // CUSTOMIZE HEADER FOR DAY AND WEEK VIEW
                   
                    scheduler.templates.event_text = function(start,end,event){
                        if(event.text !== "New event"){
                            tempObj = data["subject"].find(x => x.key === parseInt(event.text));
                            return tempObj["label"];
                        }else{
                            eventName = scheduler.getLabel("subject",event["subject"]);
                            return eventName;
                        }
                    };
                    
                    // CUSTOMIZE HEADER FOR MONTH VIEW
                    scheduler.templates.event_bar_text = function(start,end,event){
                        if(event.text !== "New event"){
                            tempObj = subject.find(x => x.key === parseInt(event.text));
                            return tempObj["label"];
                        }else{
                            eventName = scheduler.getLabel("subject",event["subject"]);
                            return eventName;
                        }
                    };


                    // Limit the event date
                    var startMonth = new Date();
                    if(level_id == 0){
                        scheduler.init('scheduler_here',startMonth,"year"); 
                    }else{
                        scheduler.init('scheduler_here',startMonth,"month"); 
                    }
                    
                   
                    if(typeof(classLists) !== "undefined" || classLists.length > 0){
                        scheduler.parse(classLists,"json");
                    }

                    var weekday = new Array(7);
                    weekday[0] = "Sun";
                    weekday[1] = "Mon";
                    weekday[2] = "Tue";
                    weekday[3] = "Wed";
                    weekday[4] = "Thur";
                    weekday[5] = "Fri";
                    weekday[6] = "Sat";
                    
                    if(!scheduler._onEventSave){
                        scheduler.attachEvent('onEventSave', function(eventId, event) {
                        if(Number.isInteger(eventId)){                            
                            // Validation
                            console.log(event);
                            if (!event.ending) {
                                dhtmlx.alert("Please End Date in (yyyy/mm/dd) format");
                                return false;
                            }
                           
                            // START DATE
                            startingDay = new Date(event["start_date"]);
                            s_year = startingDay.getFullYear();
                            s_month = ("0" + (startingDay.getMonth() + 1)).slice(-2);
                            s_day = ("0" + startingDay.getDate()).slice(-2);
                            s_hours = ("0" + startingDay.getHours()).slice(-2);
                            s_minutes = ("0" + startingDay.getMinutes()).slice(-2);
                            
                            var starting = [s_year,s_month,s_day].join("-");
                            var ending = event.ending;
                            var start_time = [s_hours,s_minutes].join(":");
                            var day = weekday[startingDay.getDay()];
                            
                            
                            // END DATE
                            endDay = new Date(event["end_date"]);
                            e_hours = ("0" + endDay.getHours()).slice(-2);
                            e_minutes = ("0" + endDay.getMinutes()).slice(-2);

                            var end_time = [e_hours,e_minutes].join(":");
                            
                            var timing = [start_time,end_time].join("-");                       
                            $.ajax({
                                url: 'CreateAndUpdateScheduleServlet',
                                dataType: 'JSON',
                                data: {
                                    add_new:true,
                                    lvl_id: level_id,branch:branch_id,
                                    subject_id: event["subject"],has_reminder:event["has_reminder"],timing:timing,class_day:day,
                                    start_date:starting,end_date:ending,tutor_id:event["tutor"]
                                },
                                success: function (data) {
                                    if(data === -1){
                                        dhtmlx.alert("Sorry,Something went wrong");
                                        return false;
                                    }else{
                                        dhtmlx.alert("Created successfully");
                                        return true;
                                    }
                                }
                            });
                            
                        }else{
                            var classId = eventId.split("#")[0];
                            lessondDate = event["lessonDate"];
                            lessonTime = event["lessonTime"];
                            tutorId = event["tutor"];
                            tutor_assignmentType = event["tutor_assignment_type"];
                            
                            if (tutorId === "") {
                                dhtmlx.alert("Select One Tutor");
                                return false;
                            }
                              
                            $.ajax({
                                url: 'CreateAndUpdateScheduleServlet',
                                dataType: 'JSON',
                                data: {
                                    add_new:false,
                                    classId:classId,
                                    lessonDate:lessondDate,
                                    lessonTime:lessonTime,
                                    tutorId:tutorId,
                                    tutor_assignmentType:tutor_assignmentType
                                },
                                success: function (data) {
                                    console.log(data);
                                    if(data === -1){
                                        dhtmlx.alert("Tutor is not updated");
                                        return false;
                                    }else if(data === 2){
                                        dhtmlx.alert("These Tutor got other class.Select another one");
                                        return false;
                                    }else{
                                        dhtmlx.alert("Updated successfully");
                                        return true;
                                    }
                                }
                            });
                            
                        }

                        return true;
                    });
                    }
                }
   }
});
</script>