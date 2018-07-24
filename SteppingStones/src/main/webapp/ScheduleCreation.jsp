<%-- 
    Document   : ScheduleCreation
    Created on : Jul 23, 2018, 11:10:02 PM
    Author     : Riana
--%>

<%@page import="entity.Subject"%>
<%@page import="java.util.List"%>
<%@page import="entity.Tutor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        List<Subject> subjectList = (List<Subject>) request.getAttribute("SubjectList");
        String[] level = {"Primary 3", "Primary 4", "Primary 5", "Primary 6", "Secondary 1", "Secondary 2", "Secondary 3", "Secondary 4"};
        System.out.println(subjectList);
        
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            var ctx = '${pageContext.request.contextPath}'; 
        </script>
        <script src="styling/js/redips-drag-min.js" type="text/javascript"></script>
        <script src="styling/js/script.js" type="text/javascript"></script>
        <link href="styling/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
    <div>
        <input type="date" name="txtStartDate" id="txtStartDate"/>
    </div>
    <div id="redips-drag">
	
				<!-- left container (table with subjects) -->
				<div id="left">
					<table id="table1">
						<colgroup>
							<col width="190"/>
						</colgroup>
						<tbody>
                                                    <%
                                                        for(Subject s :subjectList){
                                                            for(String l: level){
                                                        %>
                                                        
                                                            <tr><td class="dark"><div id="<%=s.getSubjectId() + "_" + l%>" class="redips-drag redips-clone <%=s.getSubjectId() + "_" + l%>"><%=s.getSubjectName() + " " + l%></div><input id="b_<%=s.getSubjectId() + "_" + l%>" class="<%=s.getSubjectId() + "_" + l%>" type="button" value="" onclick="redips.report('<%=s.getSubjectId() + "_" + l%>')" title="Show only <%=s.getSubjectName()%>"/></td></tr>
                                                        <%
                                                            }
                                                        }
                                                    %>
							
                                                            <!--
							<tr><td class="dark"><div id="bi" class="redips-drag redips-clone bi">Biology</div><input id="b_bi" class="bi" type="button" value="" onclick="redips.report('bi')" title="Show only Biology"/></td></tr>
							<tr><td class="dark"><div id="ch" class="redips-drag redips-clone ch">Chemistry</div><input id="b_ch" class="ch" type="button" value="" onclick="redips.report('ch')" title="Show only Chemistry"/></td></tr>
							<tr><td class="dark"><div id="en" class="redips-drag redips-clone en">English</div><input id="b_en" class="en" type="button" value="" onclick="redips.report('en')" title="Show only English"/></td></tr>
							<tr><td class="dark"><div id="et" class="redips-drag redips-clone et">Ethics</div><input id="b_et" class="et" type="button" value="" onclick="redips.report('et')" title="Show only Ethics"/></td></tr>
							<tr><td class="dark"><div id="hi" class="redips-drag redips-clone hi">History</div><input id="b_hi" class="hi" type="button" value="" onclick="redips.report('hi')" title="Show only History"/></td></tr>
							<tr><td class="dark"><div id="it" class="redips-drag redips-clone it">IT</div><input id="b_it" class="it" type="button" value="" onclick="redips.report('it')" title="Show only IT"/></td></tr>
							<tr><td class="dark"><div id="ma" class="redips-drag redips-clone ma">Mathematics</div><input id="b_ma" class="ma" type="button" value="" onclick="redips.report('ma')" title="Show only Mathematics"/></td></tr>
							<tr><td class="dark"><div id="ph" class="redips-drag redips-clone ph">Physics</div><input id="b_ph" class="ph" type="button" value="" onclick="redips.report('ph')" title="Show only Physics"/></td></tr>
                                                            -->
                                                            <tr><td class="redips-trash" title="Trash">Trash</td></tr>
						</tbody>
					</table>
				</div><!-- left container -->
				
				<!-- right container -->
				<div id="right">
					<table id="table2">
						<colgroup>
							<col width="50"/>
							<col width="100"/>
							<col width="100"/>
							<col width="100"/>
							<col width="100"/>
							<col width="100"/>
						</colgroup>
						<tbody>
							<tr>
								<!-- if checkbox is checked, clone school subjects to the whole table row  -->
								<td class="redips-mark blank">
									<input id="week" type="checkbox" title="Apply school subjects to the week" checked/>
									<input id="report" type="checkbox" title="Show subject report"/>
								</td>
								<td class="redips-mark dark">Monday</td>
								<td class="redips-mark dark">Tuesday</td>
								<td class="redips-mark dark">Wednesday</td>
								<td class="redips-mark dark">Thursday</td>
								<td class="redips-mark dark">Friday</td>
								<td class="redips-mark dark">Saturday</td>
								<td class="redips-mark dark">Sunday</td>

							</tr>
							<tr>
								<td class="redips-mark dark">8:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">9:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">10:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">11:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">12:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">13:00</td>
                                                                <td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">14:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">15:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="redips-mark dark">16:00</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div><!-- right container -->
			</div><!-- drag container -->
			<div id="message">Drag school subjects to the timetable (clone subjects with SHIFT key)</div>
<div class="button_container">
				<input type="button" value="Save" class="button" onclick="redips.save()" title="Save timetable"/>
			</div>

</html>
