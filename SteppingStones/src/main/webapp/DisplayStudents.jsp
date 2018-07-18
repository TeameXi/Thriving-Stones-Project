<%@page import="model.StudentDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="entity.StudentGrade"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<style>
    #generate_btn{
        padding: 5px;
        margin-left : 50px;
        background-color:#f7a4a3;
        color:#fff;
        border-radius: 5px;
    }

    #generate_btn:hover{
        background:transparent;
        border: 1px solid #f7a4a3;
        color:#f7a4a3;
    }
</style>
<style>/* Listing */

    * {
        box-sizing: border-box;
    }

    .toggler {
        color: #A1A1A4;
        font-size: 1.25em;
        margin-left: 8px;
        text-align: center;
        cursor: pointer;
    }
    .toggler.active {
        color: #000;
    }

    .surveys {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .survey-item {
        display: block;
        margin-top: 10px;
        padding: 20px;
        border-radius: 2px;
        background: white;
        box-shadow: 0 2px 1px rgba(170, 170, 170, 0.25);
    }

    .survey-name {
        font-weight: 400;
    }

    .list .survey-item {
        position: relative;
        padding: 0;
        font-size: 14px;
        line-height: 40px;
    }
    .list .survey-item .pull-right {
        position: absolute;
        right: 0;
        top: 0;
    }
    @media screen and (max-width: 800px) {
        .list .survey-item .stage:not(.active) {
            display: none;
        }
    }
    @media screen and (max-width: 700px) {
        .list .survey-item .survey-progress-bg {
            display: none;
        }
    }
    @media screen and (max-width: 600px) {
        .list .survey-item .pull-right {
            position: static;
            line-height: 20px;
            padding-bottom: 10px;
        }
    }
    .list .survey-country,
    .list .survey-progress,
    .list .survey-completes,
    .list .survey-end-date {
        color: #A1A1A4;
    }
    .list .survey-country,
    .list .survey-completes,
    .list .survey-end-date,
    .list .survey-name{
        margin: 0 10px;
    }
    .list .survey-country {
        margin-right: 0;
    }

    .list .survey-country,
    .list .survey-name {
        vertical-align: middle;
    }


    .survey-stage .stage {
        display: inline-block;
        vertical-align: middle;
        width: 16px;
        height: 16px;
        overflow: hidden;
        border-radius: 50%;
        padding: 0;
        margin: 0 2px;
        background: #f2f2f2;
        text-indent: -9999px;
        color: transparent;
        line-height: 16px;
    }
    .survey-stage .stage.active {
        background: #A1A1A4;
    }

    .list .list-only {
        display: auto;
    }
    .list .grid-only {
        display: none !important;
    }

    .grid .grid-only {
        display: auto;
    }
    .grid .list-only {
        display: none !important;
    }

    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 200px;
        width: 250px;
        margin: 10px;
    }
    @media screen and (max-width: 600px) {
        .grid .survey-item {
            display: block;
            width: auto;
            height: 150px;
            margin: 10px auto;
        }
    }
    .grid .survey-name {
        display: block;
        max-width: 80%;
        font-size: 16px;
        line-height: 20px;
    }
    .grid .survey-country {
        font-size: 11px;
        line-height: 16px;
        text-transform: uppercase;
    }
    .grid .survey-country,
    .grid .survey-end-date {
        color: #A1A1A4;
    }

    .grid .survey-progress {
        display: block;
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        width: 100%;
        padding: 20px;
        border-top: 1px solid #eee;
        font-size: 13px;
    }


    .grid .survey-progress-labels {
        position: absolute;
        right: 20px;
        top: 0;
        line-height: 55px;
    }
    @media screen and (max-width: 200px) {
        .grid .survey-progress-labels {
            right: auto;
            left: 10px;
        }
    }
    .grid .survey-progress-label {
        line-height: 21px;
        font-size: 13px;
        font-weight: 400;
    }
    .grid .survey-completes {
        line-height: 21px;
        font-size: 13px;
        vertical-align: middle;
    }
    .grid .survey-stage {
        position: absolute;
        top: 20px;
        right: 20px;
    }
    .grid .survey-stage .stage {
        display: none;
    }
    .grid .survey-stage .active {
        display: block;
    }
    .grid .survey-end-date {
        font-size: 12px;
        line-height: 20px;
    }

    .survey-progress-label a{
        vertical-align: middle;
        margin: 0 10px;
        color: #8DC63F !important;
    }

    .survey-completes a{
        vertical-align: middle;
        margin: 0 10px;
        color: red !important;
    }


    .view_more{
        color: #8DC63F !important;
        height: 20px;
    }

    .survey-progress-view a{
        vertical-align: middle;
        margin: 0 10px;
        color: orange !important;
    }
</style>
<div class="col-md-10">
    <div style="margin: 20px;"><h3>Student Lists</h3></div>
    <div class="row" id="errorMsg"></div>
    <span class="toggler active" data-toggle="grid"><span class="zmdi zmdi-view-dashboard"></span></span>
    <span class="toggler" data-toggle="list"><span class="zmdi zmdi-view-list"></span></span>
    <ul class="surveys grid">
        <%
            StudentDAO studentDAO = new StudentDAO();
            ArrayList<Student> students = studentDAO.listAllStudents();

            if (students != null) {
                for (int i = 0; i < students.size(); i++) {
                    Student stu = students.get(i);
                    out.println("<li class='survey-item' id='sid_" + stu.getStudentID() + "'><span class='survey-country list-only'>SG</span>");
                    out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i>");
                    out.println(stu.getName() + "</span>");
                    out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-pin'>&nbsp;&nbsp;</i>");
                    out.println(stu.getAddress() + "</span><br/>");
                    out.println("<span class='survey-country'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i>");
                    out.println(stu.getLevel() + "</span>");


        %>
        <div class="pull-right">

            <span class="survey-progress">
                <span class="grid-only">
                    <a href="#" class="view_more">View detail</a>
                </span>



                <span class="survey-progress-labels">
                    <span class="survey-progress-view list-only">
                        <a href="#"><i class="zmdi zmdi-eye"></i></a>
                    </span>


                    <span class="survey-progress-label">
                        <a href="#editStudent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>"><i class="zmdi zmdi-edit"></i></a>
                    </span>

                    <span class="survey-completes">
                        <a href="#small" onclick="deleteStudent('<%=stu.getStudentID()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
                    </span>
                </span>
            </span>


            <%          out.println("<span class='survey-end-date'><i class='zmdi zmdi-phone'>&nbsp;&nbsp;</i>");
                        out.println(stu.getPhone() + "</span></div></li>");
                    }

                } else {
                    out.println("No Students!");
                }

            %>
    </ul>
</div>
</div>
</div>
<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to delete this item?</div>
            <div class="modal-footer centered">
                <a id="confirm_btn"><button type="button" class="small_button pw_button del_button autowidth">Yes, Remove</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<div class="modal fade" id="editStudent" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Edit Student</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Name :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type = "text" class = "form-control" id="name" value =""/></p>
                        <input type="hidden" id="id" value="" />
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Age :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="age" value =""/></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Gender :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="gender" value =""/></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Gender :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="lvl" value =""/></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Address :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="address"/></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Contact No :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="phone" value =""/></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Required Amount :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="r_amount" value =""/></p>
                    </div>
                </div><br/>
                
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Outstanding Amount :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="o_amount" value =""/></p>
                    </div>
                </div><br/>

            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-success"  onclick="editStudent()">Save Changes</button>
            </div>
        </div>       
    </div>
</div>

<%@include file="footer.jsp"%>

<script>
    $(document).ready(function(){
        $("#editStudent").on("show.bs.modal", function(e) {
            var student_id = $(e.relatedTarget).data('target-id');   
            $.ajax({
               url: 'Retrieve_Update_StudentServlet',
               dataType: 'JSON',
               data: {studentID: student_id},
               success: function (data) {
                  $("#id").val(student_id);
                  $("#name").val(data["name"]);
                  $("#age").val(data["age"]);
                  $("#lvl").val(data["lvl"]);
                  $("#gender").val(data["gender"]);
                  $("#phone").val(data["phone"]);
                  $("#address").val(data["address"]);
                  $("#r_amount").val(data["r_amount"]);
                  $("#o_amount").val(data["o_amount"]);
               }
            });

        });
    });
                    
                    
    (function () {
        $(function () {
            return $('[data-toggle]').on('click', function () {
                var toggle;
                toggle = $(this).addClass('active').attr('data-toggle');
                $(this).siblings('[data-toggle]').removeClass('active');
                if (toggle !== "modal") {
                    return $('.surveys').removeClass('grid list').addClass(toggle);
                }
            });
        });
    }).call(this);
        
    function deleteStudent(student_id) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteStudentQueryAjax(student_id);
        });
    }
        
        
    function deleteStudentQueryAjax(student_id) {
        $('#small').modal('hide');
        $.ajax({
            type: 'POST',
            url: 'DeleteStudentServlet',
            dataType: 'JSON',
            data: {studentID: student_id},
            success: function (data) {
                if (data == 1) {
                    $("#sid_" + student_id).remove();
                    html = '<div class="alert alert-success col-md-5"><strong>Success!</strong> Deleted Student record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }
    
    
    function editStudent(){
        id = $("#id").val();
        $.ajax({
            type: 'POST',
            url: 'DeleteStudentServlet',
            dataType: 'JSON',
            data: {studentID: id},
            success: function (data) {
                if (data == 1) {
                    $("#sid_" + student_id).remove();
                    html = '<div class="alert alert-success col-md-5"><strong>Success!</strong> Update Student record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }
    
    
</script>