<%@page import="entity.Tutor"%>
<%@page import="model.TutorDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="java.util.Calendar"%>
<%@include file="protect_tutor.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/timetable/reset.css"> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/timetable/style.css"> 

<%@include file="header.jsp"%>


<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><h5><span class="tab_active">Tutor Classes </span></h5></div>
    <div class="row">
        <%            
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int tutor_id = user.getRespectiveID();
            
            TutorDAO tutors = new TutorDAO();
            Tutor tutor = tutors.retrieveSpecificTutorById(tutor_id);
            String tutorName   = tutor.getName();
        %>
        
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
                        <% 
                            ArrayList<entity.Class> mondayClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Mon");
                            int color_count = 1;
                            for(entity.Class mondayClass : mondayClasses){
                                String timingArr[] = mondayClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+mondayClass.getStartDate()+"&"+mondayClass.getEndDate()+"&"+mondayClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+mondayClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }
                            
                        %>
                        </ul>
                    </li>

                    <li class="events-group">
                        <div class="top-info"><span>Tue</span></div>
                        <ul class="Tue">
                        <% 
                            ArrayList<entity.Class> tueClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Tue");
        
                            for(entity.Class tueClass : tueClasses){
                                String timingArr[] = tueClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+tueClass.getStartDate()+"&"+tueClass.getEndDate()+"&"+tueClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+tueClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }
                            
                        %>
                        </ul>
                    </li>


                    <li class="events-group">
                        <div class="top-info"><span>Wed</span></div>
                        <ul class="Wed">
                        <% 
                            ArrayList<entity.Class> wedClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Wed");

                            for(entity.Class wedClass : wedClasses){
                                String timingArr[] = wedClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+wedClass.getStartDate()+"&"+wedClass.getEndDate()+"&"+wedClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+wedClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }

                        %>
                        </ul>
                    </li>


                    <li class="events-group">
                        <div class="top-info"><span>Thur</span></div>
                        <ul class="Thur">
                        <% 
                            ArrayList<entity.Class> thurClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Thur");

                            for(entity.Class thurClass : thurClasses){
                                String timingArr[] = thurClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+thurClass.getStartDate()+"&"+thurClass.getEndDate()+"&"+thurClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+thurClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }

                        %>
                        </ul>
                    </li>

                    <li class="events-group">
                        <div class="top-info"><span>Fri</span></div>
                        <ul class="Fri">
                        <% 
                            ArrayList<entity.Class> friClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Fri");

                            for(entity.Class friClass : friClasses){
                                String timingArr[] = friClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+friClass.getStartDate()+"&"+friClass.getEndDate()+"&"+friClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+friClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }

                        %>
                        </ul>
                    </li>

                    <li class="events-group">
                        <div class="top-info"><span>Sat</span></div>
                        <ul class="Sat">
                        <% 
                            ArrayList<entity.Class> satClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Sat");

                            for(entity.Class satClass : satClasses){
                                String timingArr[] = satClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+satClass.getStartDate()+"&"+satClass.getEndDate()+"&"+satClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+satClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }

                        %>
                        </ul>
                    </li>

                    <li class="events-group">
                        <div class="top-info"><span>Sun</span></div>
                        <ul class="Sun">
                        <% 
                            ArrayList<entity.Class> sunClasses = ClassDAO.listAllClassesBelongToTutorByDay(tutor_id,branch_id,"Sun");

                            for(entity.Class sunClass : sunClasses){
                                String timingArr[] = sunClass.getClassTime().split("-");
                                String startTime = timingArr[0];
                                String endTime = timingArr[1];
                                out.println("<li class='single-event' data-start='"+startTime+"' data-end='"+endTime+"'");
                                out.println("data-content='"+sunClass.getStartDate()+"&"+sunClass.getEndDate()+"&"+sunClass.getMthlyFees()+"&"+tutorName+"'");
                                out.println("data-event='event-"+color_count+"'><a href='#0'><em class='event-name'>"+sunClass.getSubject()+"</em></a></li>");
                                if(color_count > 4){
                                    color_count = 1;
                                }
                            }

                        %>
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
</div>
<%@include file="footer.jsp"%>
<script src="${pageContext.request.contextPath}/vendor/timetable/main.js"></script>
<script src="${pageContext.request.contextPath}/vendor/timetable/modernizr.js"></script>

<script>
$(document).ready(function(){
    createSchedule();
});
</script>
