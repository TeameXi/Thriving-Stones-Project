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
    <div class="row" id="statusMsg"></div>
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
                    out.println("<tr id='"+t.getTutorId()+"'><td class='details-control'></td><td class='text-center'>"+t.getName()+"</td><td class='text-center'>"+t.getTotalClasses()+"</td><td class='text-center' id='totalOwed_"+t.getTutorId()+"'> $ "+totalOwed+"</td></tr>");
                }
            %>
        </tbody>
    </table>
</div>

<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to pay? Once done,this action can be undone.</div>
            <div class="modal-footer centered">
                <a id="confirm_btn"><button type="button" class="small_button pw_button del_button autowidth">Yes, Pay</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>

<script type="text/javascript">
    function payMontlhySalary(ids,tutorName,subjectName,levelName,monthlySalary) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            payTutorMonthlySalaryQueryAjax(ids,tutorName,subjectName,levelName,monthlySalary);
        });
        return false;
    }
    
    function payTutorMonthlySalaryQueryAjax(ids,tutorName,subjectName,levelName,monthlySalary) {
        $('#small').modal('hide');
        $.ajax({    
            type:'POST',
            url: 'TutorPaymentServlet',
            data:{ ids:ids,action:"pay",
                tutorName:tutorName,subjectName:subjectName,
                levelName:levelName,monthlyAmount:monthlySalary},
            dataType: "json",
            success: function(response) {
                console.log(response);
                if(response === 1){
                    
                    // Update Total Owed View
                    td_id = "totalOwed_"+ids.split("_")[0];
                    originalTotalOwedToTutor = $("#"+td_id).html();
                    originalTotalOwedToTutor = parseFloat(originalTotalOwedToTutor.slice(1));
                    updatedValue = originalTotalOwedToTutor-parseFloat(monthlySalary);
                    $("#"+td_id).html("$ "+updatedValue);
                    
                    //Update Pay BTn
                    $("#payBtn_"+ids).remove();
                    $("#payBtnContainer_"+ids).html("<span class='paid'>Paid</span>");
        
                    html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Updated Paid Status successfully</div>';
                }else{
                    html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                }
                $("#statusMsg").html(html);
                $('#statusMsg').fadeIn().delay(1000).fadeOut();
            }
        });
        
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
                tutorName = row.data().name;
                action = 'retrieveClasses';
                $.ajax({
                    type: 'POST',
                    url: 'TutorPaymentServlet',
                    dataType: 'JSON',
                    data: {action: action, tutorID: tutorID, branchID: branchID},
                    success: function (result) {
                        console.log(result);
                        if(result.length <= 0){
                            html = "<div class='alert alert-warning col-md-12'>Currently No Pay for this tutor</div>";
                        }else{
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
                        }
                        
                        for(c=0;c<result.length;c++){
                            cls = result[c];
                            className = cls.className;
                            levelName = cls.levelName;
                            subjectName = cls.subjectName;
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
                                m = lessonMontlyLists[l].month;
                                y = lessonMontlyLists[l].year;
                                var btnId = 0;
                                if(btnStatus === "should pay"){
                                    btnId =tutorID+"_"+cls.id+"_"+m+"_"+y;
                                    btnInput ='<a id="payBtn_'+btnId+'" data-toggle="modal" class="btn btn1 btn-sm" href="#small" onclick="payMontlhySalary('+"'"+btnId+"'"+','+"'"+tutorName+"'"+','+"'"+subjectName+"'"+','+"'"+levelName+"'"+','+monthlySalary+')" >Pay</a>';
                                }else if(btnStatus === "shouldn't pay"){
                                    btnInput = "<span class='pending'>Pending</span>";
                                }else{
                                    btnInput = "<span class='paid'>Paid</span>";
                                }
                                month = lessonMontlyLists[l].month-1;
                                html += "<td style='text-align: center;'><b>"+lessonName+"["+months[month]+"]"+"</b></br>Lessons : <b>"+totalLesson+"</b><br/>Amount : <b>$"+monthlySalary+"</b><br/><div id='payBtnContainer_"+btnId+"'>"+btnInput+"</div></td>";
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