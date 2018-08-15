<%@page import="entity.Level"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@include file="protect_branch_admin.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/timetable/reset.css"> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/timetable/style.css"> 

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
    
    .event-info{
        margin-left: 20px;
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Display Schedule</span></h5></div>
    <div class="row" id="errorMsg"></div>
    <div class="row">
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
            <br/><br/>

            <!--            <div class="form-group">
                            <label class="col-lg-2 control-label">Academic Level</label>  
                            <div class="col-lg-7 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="zmdi zmdi-badge-check"></i></span>
                                    <select name="lvl" class="form-control" >
            <%
                LevelDAO lvlDao = new LevelDAO();
                ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
            %>
            <option value="" >Select Level</option>
            <%  for (Level lvl : lvlLists) {
                    out.println("<option value='" + lvl.getLevel_id() + "'>" + lvl.getLevelName() + "</option>");
                }
            %>
    </select>
</div>
</div>
</div>
<br/>-->

            <div class="form-group">
                <label class="col-lg-2 control-label">Level</label>  
                <div class="col-lg-7 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="zmdi zmdi-search"></i></span>  
                        <input id="level" type="text" name="level"  class="form-control">
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
    </div>
    <br/>                       

    <div class="cd-schedule loading">
        <div class="timeline">
            <ul>
                <li><span>10:00 AM</span></li>
                <li><span>11:00 AM</span></li>
                <li><span>12:00 AM</span></li>
                <li><span>13:00 PM</span></li>
                <li><span>14:00 PM</span></li>
                <li><span>15:00 PM</span></li>
                <li><span>16:00 PM</span></li>
                <li><span>17:00 PM</span></li>
                <li><span>18:00 PM</span></li>
                <li><span>19:00 PM</span></li>
                <li><span>20:00 PM</span></li>
                <li><span>21:00 PM</span></li>
                <li><span>22:00 PM</span></li>
            </ul>
        </div> <!-- .timeline -->

        <div class="events">
            <ul>
                <li class="events-group">
                    <div class="top-info"><span>Mon</span></div>
                    <ul class="Mon">
                       
                    </ul>
                </li>

                <li class="events-group">
                    <div class="top-info"><span>Tue</span></div>
                    <ul class="Tue">
                     
                    </ul>
                </li>


                <li class="events-group">
                    <div class="top-info"><span>Wed</span></div>
                    <ul class="Wed">

                    </ul>
                </li>


                <li class="events-group">
                    <div class="top-info"><span>Thur</span></div>
                    <ul class="Thur">

                    </ul>
                </li>

                <li class="events-group">
                    <div class="top-info"><span>Fri</span></div>
                    <ul class="Fri">

                    </ul>
                </li>
                
                <li class="events-group">
                    <div class="top-info"><span>Sat</span></div>
                    <ul class="Sat">

                    </ul>
                </li>
                
                <li class="events-group">
                    <div class="top-info"><span>Sun</span></div>
                    <ul class="Sun">

                    </ul>
                </li>


            </ul>
        </div>

        <div class="event-modal">
            <header class="header">
                <div class="content">
                    <span class="event-date"></span>
                    <h3 class="event-name"></h3>
                </div>

                <div class="header-bg"></div>
            </header>

            <div class="body">
                <div class="event-info"></div>
                <div class="body-bg"></div>
            </div>

            <a href="#0" class="close">Close</a>
        </div>

        <div class="cover-layer"></div>
    </div> <!-- .cd-schedule -->

</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src="${pageContext.request.contextPath}/styling/js/jquery.autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/vendor/timetable/main.js"></script>

<script>
            var level = ["Primary 1", "Primary 2", "Primary 3", "Primary 4", "Primary 5", "Primary 6", "Secondary 1", "Secondary 2", "Secondary 3", "Secondary 4"];
            autocomplete(document.getElementById("level"), level);

 function searchClasses() {
                $(".Mon").html("");
                $(".Tue").html("");
                $(".Wed").html("");
                $(".Thur").html("");
                $(".Fri").html("");
                          
                term = $("#term").val();
                level = $("#level").val();
                branch_id = $("#branch_id").val();
                $.ajax({
                    type: 'POST',
                    url: 'RetrieveClassesByTermAndLevel',
                    dataType: 'JSON',
                    data: {term: term, level: level, branch_id: branch_id},
                    success: function (data) {
                        if (data === 0) {
                            html = '<div class="alert alert-warning col-md-5">No Class Timetable For <strong>' + level + '( Term ' + term + ' ) </strong></div>';
                            $("#errorMsg").html(html);
                            $('#errorMsg').fadeIn().delay(2000).fadeOut();
                        } else if (data === -1) {
                            html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                            $("#errorMsg").html(html);
                            $('#errorMsg').fadeIn().delay(2000).fadeOut();
                        } else {
                            var color_count = 1;
                            console.log(data);
                            for(var i=0;i< data.length;i++){
                                var classInfo = data[i];
                                var day = classInfo["day"];
                                var time_arr = classInfo["timing"].split("-");       
                                var start_time = time_arr[0];
                                var end_time = time_arr[1];
                                var subject_name = classInfo["subject"];
                                var start_date = classInfo["start_date"];
                                var end_date = classInfo["end_date"];
                                var tutor = "Susan";
                                var fees = classInfo["fees"];

                                
                                var html = '<li class="single-event" data-start="'+start_time+'" data-end="'+end_time+'"';
                                html    += 'data-content="'+start_date+'&'+end_date+'&'+fees+'&'+tutor+'" data-event="event-'+color_count+'"><a href="#0"><em class="event-name">'+subject_name+'</em></a></li>';
                                color_count = color_count+1;
                                if(color_count > 4){
                                    color_count = 1;
                                }
                             
                                $("."+day).append(html);
                            }
                                createSchedule();
                        }
                    }
                });
                return false;
            }


</script>
<script src="${pageContext.request.contextPath}/vendor/timetable/modernizr.js"></script>
