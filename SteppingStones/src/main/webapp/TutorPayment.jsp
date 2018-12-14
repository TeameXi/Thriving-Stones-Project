<%@page import="model.LessonDAO"%>
<%@page import="entity.TutorPay"%>
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
    
    .innerTable{
        padding-left:50px;
        padding-right:50px;
        max-width: 1000px;
    }
    
    .scrolling table {
        table-layout: inherit;
        *margin-left: -100px;/*ie7*/
    }

    .scrolling td,th {
        vertical-align: top;
        padding: 10px;

    }

    .scrolling th {
        position: absolute;
        *position: relative; /*ie7*/
        left: 0;
        width: 165px;
        background-color:#717171;
        color:white;
    }
    
    th#no_lessons{
        font-size:11px;
        min-height:20px;
    }
    
    th#got_lessons{
        min-height:87px;
    }
    .outer {
        position: relative
    }
        
    .inner {
        overflow-x: hidden;
        margin-left: 180px;
    }
</style>
<div class="col-lg-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Tutor Payment</span></div>
    <div class="row" id="statusMsg"></div>
    <table id="tutorPaymentTable" class="table display responsive nowrap" style="width:100%; font-size: 14px">
        <thead class="thead-light">
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
                    double totalOwed = 0.0;
                    ArrayList<entity.Class> classes = ClassDAO.listAllClassesBelongToTutors(t.getTutorId(), branch_id);   
                    for (entity.Class c : classes) {
                        if(c.getPayType() == 1){
                            int totalMonthlyLessons = LessonDAO.totalLessonTutorAttendedForClassMonthlyCount(t.getTutorId(), c.getClassID());
                            System.out.println("====");
                            System.out.println(totalMonthlyLessons);
                            totalOwed += c.getTutorRate()*totalMonthlyLessons;
                            
                        }else{
                            double classDuration = ClassDAO.getClassTime(c.getClassID());
                            int totalAttendLessons = TutorDAO.calculateTutorAttendLessonCount(t.getTutorId(), c.getClassID());                        
                            totalOwed += totalAttendLessons*classDuration* c.getTutorRate();
                        }
                    }
                    ArrayList<TutorPay> replacementClasses = ClassDAO.totalReplacementClasses(t.getTutorId(), branch_id);
                    for(TutorPay replacementClass:replacementClasses){
                        totalOwed += replacementClass.getMonthlySalary();
                    }

                    out.println("<tr id='"+t.getTutorId()+"'><td></td><td class='text-center'>"+t.getName()+"</td><td class='text-center'>"+t.getTotalClasses()+"</td><td class='text-center' id='totalOwed_"+t.getTutorId()+"'> $ "+totalOwed+"</td></tr>");
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
    //For replacement 
    function payReplacementSalary(ids,tutorName,subjectName,levelName,replacementAmount){
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            payReplacementQueryAjax(ids,tutorName,subjectName,levelName,replacementAmount,totalLesson);
        });
        return false;
    }
    
    function payReplacementQueryAjax(ids,tutorName,subjectName,levelName,replacementAmount,totalLesson){
        $('#small').modal('hide');
        
        $.ajax({    
            type:'POST',
            url: 'TutorPaymentServlet',
            data:{ ids:ids,action:"replacementPay",
                tutorName:tutorName,subjectName:subjectName,
                levelName:levelName,replacementAmount:replacementAmount},
            dataType: "json",
            success: function(response) {
                if(response === 1){

                    // Update Total Owed View
                    td_id = "totalOwed_"+ids.split("_")[0];
                    originalTotalOwedToTutor = $("#"+td_id).html();
                    originalTotalOwedToTutor = parseFloat(originalTotalOwedToTutor.slice(1));
                    updatedValue = originalTotalOwedToTutor-parseFloat(replacementAmount);
                    $("#"+td_id).html("$ "+updatedValue);
                    
                    // Update the current row
                    $('#'+ids).remove();
                    
                    html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Updated Paid Status successfully</div>';
                    window.open("${pageContext.request.contextPath}/GeneratePayslipServlet?t=r&i=" + ids + "&a=" + replacementAmount + "&c=" + totalLesson , '_blank');
                    
                }else{
                    html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                }
                $("#statusMsg").html(html);
                $('#statusMsg').fadeIn().delay(1000).fadeOut();
            }
        });
    }
    
    function payMontlhySalary(ids,tutorName,subjectName,levelName,monthlySalary,totalLesson) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            payTutorMonthlySalaryQueryAjax(ids,tutorName,subjectName,levelName,monthlySalary,totalLesson);
        });
        return false;
    }
    
    function payTutorMonthlySalaryQueryAjax(ids,tutorName,subjectName,levelName,monthlySalary,totalLesson) {
        $('#small').modal('hide');
        $.ajax({    
            type:'POST',
            url: 'TutorPaymentServlet',
            data:{ ids:ids,action:"pay",
                tutorName:tutorName,subjectName:subjectName,
                levelName:levelName,monthlyAmount:monthlySalary},
            dataType: "json",
            target: "_blank",
            success: function(response) {
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
                    window.open("${pageContext.request.contextPath}/GeneratePayslipServlet?t=m&i=" + ids + "&a=" + monthlySalary + "&c=" + totalLesson, '_blank');
                    
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
            var tr = $(this).parents('tr');
            var row = table.row(tr);
            if ( row.child.isShown() ) {
                row.child.hide();
                tr.removeClass('shown');     
            }else {
                if (table.row( '.shown' ).length ) {

                    $('.details-control', table.row( '.shown' ).node()).click();
                }
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
                        
                        var monthlyPayList = result.monthlyPay;
                        var replacementPayList = result.replacementPay;
                        
                        if(monthlyPayList.length <= 0){
                            html = "<div class='row container'><div class='alert alert-warning col-md-10'>Currently No Pay for this tutor</div></div><br/>";
                        }else{
                            html = '<div class="innerTable"><h4>Monthly Salary With Lesson BreakDown</h4>'
                                +'<div style="text-align: right; margin-bottom: 10px; margin-right: 50px;">'
                                + '<img class="leftArrow" src="${pageContext.request.contextPath}/styling/img/left-arrow.svg" height="15" '
                                + 'width="15" style="margin-right: 58px;"><img '
                                + 'class="rightArrow" src="${pageContext.request.contextPath}/styling/img/right-arrow.svg" height="15" '
                                + 'width="15"></div>';
                               
                            
                            html += '<div class="scrolling outer">'+
                                        '<div class="inner">'+
                                        '<table class="table table-striped table-bordered nowrap"'+
                                            '<thead><tr><th style="text-align: center;">Class</th>';

                            var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
                            for (var i = 0; i < 12; i++) {
                               html += '<td style="text-align: center;"> ' + months[i] + '</td>';
                            }
                            html += '</tr></thead><tbody>';
                        }
                        
                        for(c=0;c<monthlyPayList.length;c++){
                            cls = monthlyPayList[c];
                            className = cls.className;
                            levelName = cls.levelName;
                            subjectName = cls.subjectName;
                            classId = cls.id;
                            
                            
                            var lessonMontlyLists = cls.lessonMontlySalary;
                            
                            classInputId = "no_lessons";
                            if(lessonMontlyLists.length > 0){
                                classInputId = "got_lessons";
                            }
                            html += '<tr id="'+classId+'"><th id='+classInputId+' style="text-align:center;">' + className + '</th>';
                            
                            // Adding row
                            firstMonth = 0;
                            if(lessonMontlyLists.length > 0){
                                firstMonth = lessonMontlyLists[0].month-1;
                            }
                            for(mc=0;mc<firstMonth;mc++){
                                html += "<td style='text-align:center;' >-</td>";
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
                                    btnInput ='<a id="payBtn_'+btnId+'" data-toggle="modal" class="btn btn1 btn-sm" href="#small" onclick="payMontlhySalary('+"'"+btnId+"'"+','+"'"+tutorName+"'"+','+"'"+subjectName+"'"+','+"'"+levelName+"'"+','+monthlySalary+','+"'"+totalLesson+"'" + ')" >Pay</a>';
                                }else if(btnStatus === "shouldn't pay"){
                                    btnInput = "<span class='pending'>Pending</span><br/>";
                                }else{
                                    btnInput = "<span class='paid'>Paid</span><br/>";
                                }
                                month = lessonMontlyLists[l].month-1;
                                html += "<td style='text-align: center;'><b>"+lessonName+"["+months[month]+"]"+"</b></br>Lessons : <b>"+totalLesson+"</b><br/>Amount : <b>$"+monthlySalary+"</b><br/><div id='payBtnContainer_"+btnId+"'>"+btnInput+"</div></td>";
                            }
                            
                            // Adding last row
                            lastMonth = 0;
                            if(lessonMontlyLists.length > 0){
                                lastMonth = lessonMontlyLists[lessonMontlyLists.length-1].month;
                            }
                            if(lastMonth !== 12){
                                leftColumn = 12 - lastMonth;
                                for(mc=0;mc<leftColumn;mc++){
                                    html += "<td style='text-align:center;'>-</td>";
                                }
                            }
                        }
                        
                        html += '</table></div></div>';
                        
                        if(replacementPayList.length > 0){
                            html += '<h4>Replacement Pay</h4><br/>';
                            html += '<table class="table table-bordered"><thead><th>Replacement Class </th><th>Total Lesson</th><th>Amount</th><th>Action</th></thead><tbody>';
                            
                            for(var r = 0 ; r < replacementPayList.length;r++){
                                replacementClass = replacementPayList[r];
                                replacementClassId = replacementClass.replacementClassId;
                                replacementTutorId = replacementClass.replacementTutorId;
                                replacementClassName = replacementClass.replacementClassName;
                                replacementPayPerClass = replacementClass.replacementPayPerClass;
                                totalReplacementLesson = replacementClass.totalReplacementLesson;
                                subjectName = replacementClass.subject;
                                levelName = replacementClass.levels;
                                
                                replacementBtnId =replacementTutorId+"_"+replacementClassId;
                                replacementBtnInput ='<a id="replacementPayBtn_'+replacementBtnId+'" data-toggle="modal" class="btn btn1 btn-sm" href="#small" onclick="payReplacementSalary('+"'"+replacementBtnId+"'"+','+"'"+tutorName+"'"+','+"'"+subjectName+"'"+','+"'"+levelName+"'"+','+replacementPayPerClass+','+"'" + totalReplacementLesson +"'" +')" >Pay</a>';
                              
                                
                                html+= '<tr id="'+replacementBtnId+'"><td>'+replacementClassName+'</td><td>'+totalReplacementLesson+'</td>';
                                html+= '<td>$'+replacementPayPerClass+'</td><td>'+replacementBtnInput+'</td></tr>';
                                
                            }
                            html += '</tbody></table>';
                        }
                        
                        html += '</div>';

                        // Open this row
                        row.child(html).show();
                        tr.addClass('shown');
                        
                        $(".leftArrow").on("click", function () {
                            var leftPos = $('.inner').scrollLeft();
                            console.log(leftPos);
                            $(".inner").animate({
                                scrollLeft: leftPos - 200
                            }, 800);
                        });

                        $(".rightArrow").on("click", function () {
                            var leftPos = $('.inner').scrollLeft();
                            console.log(leftPos);
                            $(".inner").animate({
                                scrollLeft: leftPos + 200
                            }, 800);
                        });

                    }                    
                });
            }
        });
    });
</script>