<%@page import="org.json.JSONArray"%>
<%@page import="model.SubjectDAO"%>
<%@page import="model.LevelDAO"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Tutor"%>
<%@page import="model.TutorDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<style type="text/css">
    td.details-control {
        background: url("${pageContext.request.contextPath}/styling/img/add.png") no-repeat center center;
        cursor: pointer;
        background-size: 15px 15px;
    }
    tr.shown td.details-control {
        background: url("${pageContext.request.contextPath}/styling/img/minus.png") no-repeat center center;
        background-size: 15px 15px;
    }

    .tutor-text {
        text-align: center;
    }

    .attendance-button {
        text-align: center;
    }
    
    .pending{
        font-size:11px;
        padding:2px;
        border: 1px solid orange;
        font-color:#333;
    }
    
     .paid{
        font-size:11px;
        padding:2px;
        font-color:#333;
        border: 1px solid #F7A4A3;
    }
</style>
<div class="col-lg-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Tutor Payment</span></div>
    <table id="tutorPaymentTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
        <thead>
            <tr>
                <th></th>
                <th style="text-align: center">Tutor Name</th>
                <th style="text-align: center">Total Classes</th>
                <th style="text-align: center">Total Salary</th>
            </tr>
        </thead>
        
        <tbody>
            <%
                ArrayList<Tutor> tutors = TutorDAO.tutorWithTotalClasses(branch_id);
                for(Tutor t : tutors){
                    ArrayList<entity.Class> classes = ClassDAO.listAllClassesBelongToTutors(t.getTutorId(), branch_id);
                    double totalOwed = 0.0;
                    for (entity.Class c : classes) {
                        double classDuration = ClassDAO.getClassTime(c.getClassID());
                        int totalAttendLessons = TutorDAO.calculateTutorAttendLessonCount(t.getTutorId(), c.getClassID());
                        totalOwed += totalAttendLessons*classDuration* c.getTutorRate();
                    }
                    out.println("<tr id='"+t.getTutorId()+"'><td class='details-control'></td><td class='text-center'>"+t.getName()+"</td><td class='text-center'>"+t.getTotalClasses()+"</td><td class='text-center' id="+t.getTutorId()+"> $ "+totalOwed+"</td></tr>");
                }
            %>
        </tbody>
    </table>
</div>
<%@include file="footer.jsp"%>
<script src='https://code.jquery.com/jquery-3.3.1.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>

<script type="text/javascript">
    function format(rowData, tutorID) {
        return '<table id=' + tutorID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th></th><th style="text-align: center">Class</th><th style="text-align: center">Level</th><th style="text-align: center">Subject</th><th style="text-align: center">Hourly Rate</th><th style="text-align: center">Salary Owed</th></tr></thead></table>';
    }

    function formatLessonList(rowData, classID) {
        return '<table id=' + classID + ' class="table table-bordered table-striped" style="width: 100%;">'
                + '<thead><tr><th style="text-align: center">Lesson Date</th><th style="text-align: center">Payment Status</th><th style="text-align: center">Pay?</th>'
                + '</tr></thead></table>';
    }

    $(document).ready(function () {

        branchID = <%=branch_id%>;
        table = $('#tutorPaymentTable').DataTable({
            'columns': [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },

                {"data": "name"},
                {"data": "totalClass"},
                {"data": "salary"}
            ],
            "order": [[1, 'asc']]
        });
        

        
        $('#tutorPaymentTable tbody').on('click', 'td.details-control', function () {
            tr = $(this).parents('tr');
            row = table.row(tr);
            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            } else {
                tutorID = row.data().DT_RowId;
                action = 'retrieveClasses';
                $.ajax({
                    type: 'POST',
                    url: 'TutorPaymentServlet',
                    dataType: 'JSON',
                    data: {action: action, tutorID: tutorID, branchID: branchID},
                    success: function (result) {
                        console.log(result);
                        html = '<div class="innerTable"><div style="text-align: right; margin-bottom: 10px; margin-right: 50px;">'
                            + '<img class="leftArrow" src="${pageContext.request.contextPath}/styling/img/left-arrow.svg" height="15" '
                            + 'width="15" style="margin-right: 58px;"><img '
                            + 'class="rightArrow" src="${pageContext.request.contextPath}/styling/img/right-arrow.svg" height="15" '
                            + 'width="15"></div><div id="table-wrapper"><table id=' + tutorID
                            + ' class="table table-striped table-bordered nowrap" style="width:100%">'
                            + '<thead><tr><th style="text-align: center;">Class</th>';
                        
                        var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
                        for (var i = 0; i < 12; i++) {
                           html += '<th style="text-align: center;"> ' + months[i] + '</th>';
                        }
                        html += '</tr></thead><tbody>';
                        
                        for(c=0;c<result.length;c++){
                            cls = result[c];
                            className = cls.className;
                            classId = cls.id;
                            
                            var lessonMontlyLists = cls.lessonMontlySalary;
                            html += '<tr id="'+classId+'"><td style="text-align:center;">' + className + '</td>';
                            
                            // Adding row
                            firstMonth = lessonMontlyLists[0].month-1;
                            for(mc=0;mc<firstMonth;mc++){
                                html += "<td>-</td>";
                            }
                            
                            for(l=0;l<lessonMontlyLists.length;l++){
                                totalLesson = lessonMontlyLists[l].totalLesson;
                                monthlySalary = lessonMontlyLists[l].monthlySalary;
                                lessonName = lessonMontlyLists[l].lessonName;
                                btnStatus = lessonMontlyLists[l].btnStatus;
                                if(btnStatus === "should pay"){
                                    btnInput = "<input type='button' class='btn btn1' value='Pay'/>";
                                }else if(btnStatus === "shouldn't pay"){
                                    btnInput = "<span class='pending'>Pending</span>";
                                }else{
                                    btnInput = "<span class='paid'>Paid</span>";
                                }
                                month = lessonMontlyLists[l].month-1;
                                html += "<td style='text-align: center;'><b>"+lessonName+"["+months[month]+"]"+"</b></br>Lessons : <b>"+totalLesson+"</b><br/>Amount : <b>$"+monthlySalary+"</b><br/>"+btnInput+"</td>";
                            }
                            
                            // Adding last row
                            lastMonth = lessonMontlyLists[lessonMontlyLists.length-1].month;
                            if(lastMonth !== 12){
                                leftColumn = 12 - lastMonth;
                                for(mc=0;mc<leftColumn;mc++){
                                    html += "<td>-</td>";
                                }
                            }
                        }
                        
                        html += '</table></div></div>';
                        // Open this row
                        row.child(html).show();

                        tr.addClass('shown');
                    }                    
                });
            }
        });
    });
</script>