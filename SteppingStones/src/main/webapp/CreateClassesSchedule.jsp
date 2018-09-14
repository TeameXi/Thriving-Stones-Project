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
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Scheduling</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row"><form>
            <label>Filter By : </label>
            <label class="radio-inline"><input type="radio" name="optradio" checked value="0">All</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 1</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 2</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 3</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 4</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 5</label>
            <label class="radio-inline"><input type="radio" name="optradio">Pri 6</label>
            <label class="radio-inline"><input type="radio" name="optradio">Sec 1</label>
            <label class="radio-inline"><input type="radio" name="optradio">Sec 2</label>
            <label class="radio-inline"><input type="radio" name="optradio">Sec 3</label>
            <label class="radio-inline"><input type="radio" name="optradio">Sec 4</label>
            <input type="hidden" value="<%=branch_id%>" id="branch_id"/>
    </form></div>
<!--    <div class="row">
        <form autocomplete="off" id="searchClassesId" onsubmit="return searchClasses();">
            
            <input type="hidden" value="<%=branch_id%>" id="branch_id"/>
            <div class="form-group">
                <label class="col-lg-2 control-label">Term</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-hourglass-outline"></i></span>

                        <select id="term" name="term" class="form-control">
                            <option value="1">Term I</option>
                            <option value="2">Term II</option>
                            <option value="3">Term III</option>
                            <option value="4">Term IV</option>
                        </select>
                    </div>
                </div>
            </div>
            <br/><br/><br/>
            
                            
           
            <div class="form-group">
                <label class="col-lg-2 control-label">Level</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-search"></i></span>
                        <input id="level" type="text" name="level"  class="form-control" />
                    </div>
                </div>
            </div>
            <br/><br/>
            
            <div class="form-group">
                <label class="col-lg-2 control-label">Year</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-calendar"></i></span>
                        <select id="planningYear" name="planningYear" class="form-control">
                            <% int year = Calendar.getInstance().get(Calendar.YEAR);
                                for(int i = 0; i < 3; i++){
                                    out.println("<option value='"+(year+i)+"'>"+(year+i)+"</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>
            </div>
            <br/><br/>
            
            
            <div class="form-group">
                <div class="col-lg-2 col-lg-offset-2">
                    <button type="submit" class="btn btn-default">View Timetable</button>
                </div>
            </div>

        </form>
    </div>-->
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
<script src="${pageContext.request.contextPath}/styling/js/jquery.autocomplete.js"></script>
<script charset="utf-8">
    $(document).ready(function(){
        scheduler.config.xml_date="%Y-%m-%d %H:%i";
        scheduler.init('scheduler_here',new Date(),"year");
    });
        
//    var level = ["Primary 1", "Primary 2", "Primary 3", "Primary 4", "Primary 5", "Primary 6", "Secondary 1", "Secondary 2", "Secondary 3", "Secondary 4"];
//    autocomplete(document.getElementById("level"), level);
//    
//     scheduler.locale.labels.year_tab ="Year";
//                    scheduler.init('scheduler_here',startMonth,"month"); 
//    function searchClasses(){
//
//        term = $("#term").val();
//        level = $("#level").val();
//        branch_id = $("#branch_id").val();
//        year = $("#planningYear").val();
//        $.ajax({
//            type: 'POST',
//            url: 'RetrieveClassesByTermAndLevel',
//            dataType: 'JSON',
//            data: {term: term,level:level,branch_id:branch_id,year:year},
//            success: function (data) {
//                //Refresh Schedule
//                scheduler.clearAll();
//                
//                //console.log(data);
//                if(data === -1){
//                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
//                    $("#errorMsg").html(html);
//                    $('#errorMsg').fadeIn().delay(2000).fadeOut();
//                }else{  
//                    var classLists = data["class"];
//                    var general_data = data['general'];
//                    
//                    // Subject Dropdown data
//                    var subject = data["subject"];
//                    
//                    // Tutor Dropdown Data
//                    var tutor = data["tutor"];  
//                    
//                    // Holiday checkbox Data
//                    var holiday = data["holiday"];
//
//                    // Radio box
//                    scheduler.locale.labels.section_selectme = "Reminder"; 
//                    var has_reminder = [
//                        { key: 0, label: 'No' },
//                        { key: 1, label: 'Yes' }
//                    ];
//                    
//                    // Checkbox Lists
//                    scheduler.locale.labels.section_selectCheckbox = "Holidays"; 
//                    
//                    // Lightbox field
//                    scheduler.locale.labels.section_template = 'Lesson';
//                    scheduler.locale.labels.section_timetemplate = 'Timing';
//                    var edit_lightbox = [
//                        {name:"template", height: 60, type:"template", map_to:"my_template"},
//                        {name:"timetemplate", height:20, type:"template", map_to:"lessonDate" },
//                        {name:"Assign To", height:20,type:"select", options: tutor, map_to:"tutor"}
//                    ];
//                    
//                    
//                    $("#scheduler_here").css("display","block");
//                    scheduler.config.xml_date="%Y-%m-%d %H:%i";
//                  
//                    
//                    
//                    // Recurring Events
//                    scheduler.config.prevent_cache = true;
//                    scheduler.config.details_on_create=true;
//                    scheduler.config.details_on_dblclick=true;
//                    scheduler.config.occurrence_timestamp_in_utc = true;
//                    scheduler.config.repeat_precise = true;
//                    
//                    
//                    // EDIT ICON 
//                    scheduler.config.icons_select = [
//                        "icon_details",
//                        "icon_delete"
//                    ];
//                    
//                    // Customization of edit and add for lightbox
//                    scheduler.attachEvent("onBeforeLightbox", function(event_id) {
//                        scheduler.resetLightbox();
//                        var ev = scheduler.getEvent(event_id);
//                        //console.log(ev);
//                        tempDate = new Date(ev["start_date"]);
//                        tempDay = tempDate.getDay(); 
//                        
//                        if (ev.restricted ===true){
//                            // Edit Display
//                            scheduler.config.lightbox.sections = edit_lightbox;
//                            
//                            // Display Subject
//                            eventName = subject.find(x => x.key === parseInt(ev["text"]));
//                            eventName = eventName["label"];
//                            ev.my_template = "<b>Subject : </b>"+ eventName+"<br>";
//                            ev.my_template += "<b>Details : </b>"+level+", Term "+term+", Year "+year+"<br>";
//                            
//                            //Display time
//                            temp_year = tempDate.getFullYear();
//                            temp_month = ("0" + (tempDate.getMonth() + 1)).slice(-2);
//                            temp_day = ("0" + tempDate.getDate()).slice(-2);
//                            temp_hours = ("0" + tempDate.getHours()).slice(-2);
//                            temp_minutes = ("0" + tempDate.getMinutes()).slice(-2);
//                            
//                            ev.lessonDate = [temp_year,temp_month,temp_day].join("-")+" ";
//                            ev.lessonDate += [temp_hours,temp_minutes,"00"].join(":");
//                        } else {
//                            // Add Display
//                            var filterHoliday = holiday.filter(function (el) {
//                                return tempDay === parseInt(el.day);
//                            });
//                            if(filterHoliday.length > 0){
//                                var add_lightbox = [
//                                    {name:"Subject", height:20, type:"select", options: subject, map_to:"subject" },
//                                    {name:"Tution fees", height:20,type:"textarea",map_to:"fees"},
//                                    {name:"Assign To", height:20,type:"select", options: tutor, map_to:"tutor"},
//                                    {name:"selectme", height: 20, options: has_reminder, map_to:"has_reminder", type:"radio", vertical: true,default_value:"0" },
//                                    {name:"Total lesson", height:20, map_to:"lessons", type:"textarea"},
//                                    {name:"selectCheckbox",map_to:"holiday", type:"multiselect",options: filterHoliday, vertical:"false" },
//                                    // {name:"recurring", type:"recurring", map_to:"rec_type", button:"recurring"},
//                                    {name:"time", height:72, type:"time", map_to:"auto" }
//                                ];
//                            }else{
//                                var add_lightbox = [
//                                    {name:"Subject", height:20, type:"select", options: subject, map_to:"subject" },
//                                    {name:"Tution fees", height:20,type:"textarea",map_to:"fees"},
//                                    {name:"Assign To", height:20,type:"select", options: tutor, map_to:"tutor"},
//                                    {name:"selectme", height: 20, options: has_reminder, map_to:"has_reminder", type:"radio", vertical: true,default_value:"0" },
//                                    {name:"Total lesson", height:20, map_to:"lessons", type:"textarea"},
//                                    // {name:"recurring", type:"recurring", map_to:"rec_type", button:"recurring"},
//                                    {name:"time", height:72, type:"time", map_to:"auto" }
//                                ];
//                            }
//                            scheduler.config.lightbox.sections = add_lightbox;
//
//                        };   
//                        return true;
//                    });
//
//                    // CUSTOMIZE HEADER FOR DAY AND WEEK VIEW
//                    scheduler.templates.event_text = function(start,end,event){
//                        if(event.text !== "New event"){
//                            tempObj = subject.find(x => x.key === parseInt(event.text));
//                            return tempObj["label"];
//                        }else{
//                            eventName = scheduler.getLabel("subject",event["subject"]);
//                            return eventName;
//                        }
//                    };
//                    
//                    // CUSTOMIZE HEADER FOR MONTH VIEW
//                    scheduler.templates.event_bar_text = function(start,end,event){
//                        if(event.text !== "New event"){
//                            tempObj = subject.find(x => x.key === parseInt(event.text));
//                            return tempObj["label"];
//                        }else{
//                            eventName = scheduler.getLabel("subject",event["subject"]);
//                            return eventName;
//                        }
//                    };
//
//
//                    var curr_month = 0;
//                    if(term === "2"){
//                        curr_month = 3;
//                    }else if(term === "3"){
//                        curr_month = 6;
//                    }else if(term === "4"){
//                        curr_month = 9;
//                    }
//                    // Limit the event date
//                    var startMonth = new Date(year,curr_month,1);
//                    scheduler.locale.labels.year_tab ="Year";
//                    scheduler.init('scheduler_here',startMonth,"month"); 
//                    
//                   
//                    if(typeof(classLists) !== "undefined" || classLists.length > 0){
//                        scheduler.parse(classLists,"json");
//                    }
//
//                    var weekday = new Array(7);
//                    weekday[0] = "Sun";
//                    weekday[1] = "Mon";
//                    weekday[2] = "Tue";
//                    weekday[3] = "Wed";
//                    weekday[4] = "Thur";
//                    weekday[5] = "Fri";
//                    weekday[6] = "Sat";
//                    
//                    scheduler.attachEvent('onEventSave', function(eventId, event) {
//                        console.log()
//                        if(Number.isInteger(eventId)){                            
//                            // Validation
//                            if (!event.text && !event.fees && !event.lesson) {
//                                dhtmlx.alert("Please enter all informations");
//                                return false;
//                            }
//                           
//                            var temp_fees = event.fees;
//                            if (temp_fees !== (parseInt(temp_fees,10)+"" )) {
//                                dhtmlx.alert("Tution fees must be valid number");
//                                return false;
//                            }
//                            
//                            // START DATE
//                            startingDay = new Date(event["start_date"]);
//                            s_year = startingDay.getFullYear();
//                            s_month = ("0" + (startingDay.getMonth() + 1)).slice(-2);
//                            s_day = ("0" + startingDay.getDate()).slice(-2);
//                            s_hours = ("0" + startingDay.getHours()).slice(-2);
//                            s_minutes = ("0" + startingDay.getMinutes()).slice(-2);
//                            
//                            var start_date = [s_year,s_month,s_day].join("-");
//                            var start_time = [s_hours,s_minutes].join(":");
//                            var day = weekday[startingDay.getDay()];
//                            
//                            
//                            // END DATE
//                            var endDay = new Date(event["end_date"]);
//                            e_hours = ("0" + endDay.getHours()).slice(-2);
//                            e_minutes = ("0" + endDay.getMinutes()).slice(-2);
//
//                            var end_time = [e_hours,e_minutes].join(":");
//                            
//                            // 11 lesson
//                            endDay.setDate(endDay.getDate()+(7*10));
//                            e_year = endDay.getFullYear();
//                            e_month = ("0" + (endDay.getMonth() + 1)).slice(-2);
//                            e_day = ("0" + endDay.getDate()).slice(-2);
//                            var end_date = [e_year,e_month,e_day].join("-");
//                            
//                           // console.log(end_date);
////                            if(typeof(event["_end_date"]) !== 'undefined'){
////                                endDay = new Date(event["_end_date"]);
////                                e_year = endDay.getFullYear();
////                                e_month = ("0" + (endDay.getMonth() + 1)).slice(-2);
////                                e_day = ("0" + endDay.getDate()).slice(-2);
////                                end_date = [e_year,e_month,e_day].join("-");
////                            }else{
////                                endDay.setDate(endDay.getDate()+(7*11));
////                                e_year = endDay.getFullYear();
////                                e_month = ("0" + (endDay.getMonth() + 1)).slice(-2);
////                                e_day = ("0" + endDay.getDate()).slice(-2);
////                                end_date = [e_year,e_month,e_day].join("-");
////                            }
//
//                            var timing = [start_time,end_time].join("-");
//                            var holiday = "";
//                            if(typeof event["holiday"] !== "undefined"){
//                                holiday = event["holiday"];
//                            }
//                         
//                            $.ajax({
//                                url: 'CreateAndUpdateScheduleServlet',
//                                dataType: 'JSON',
//                                data: {
//                                    add_new:true,
//                                    lvl_id: general_data["lvl_id"],year:general_data["year"],term:general_data["term"],branch:general_data["branch"],
//                                    subject_id: event["subject"],fees:event["fees"],has_reminder:event["has_reminder"],timing:timing,class_day:day,
//                                    start_date:start_date,end_date:end_date,tutor_id:event["tutor"],
//                                    lesson: event["lessons"],holiday:holiday
//                                },
//                                success: function (data) {
//                                    if(data === -1){
//                                        dhtmlx.alert("Sorry,Something went wrong");
//                                        return false;
//                                    }else{
//                                        dhtmlx.alert("Created successfully");
//                                        return true;
//                                    }
//                                }
//                            });
//                            
//                        }else{
//                            var classId = eventId.split("#")[0];
//                            console.log(event["lessonDate"]);
//                            $.ajax({
//                                url: 'CreateAndUpdateScheduleServlet',
//                                dataType: 'JSON',
//                                data: {
//                                    add_new:false,
//                                    classId:classId,lessonDate:event["lessonDate"],tutorId:event["tutor"]
//                                },
//                                success: function (data) {
//                                    console.log(data);
//                                    if(data === -1){
//                                        dhtmlx.alert("Sorry, Something went wrong");
//                                        return false;
//                                    }else{
//                                        dhtmlx.alert("Updated successfully");
//                                        return true;
//                                    }
//                                }
//                            });
//                            
//                        }
//
//                        
////                        eventService.create(event)
////                          .then(function(result){
////                              console.log(result);
////                      console.log(id);
////                            scheduler.changeEventId(id, result.databaseId);
////                          });
//
//                        return true;
//                    });
//                }
//            }
//        });
//        return false;
//    }
//    
//    

</script>