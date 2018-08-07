<%@page import="model.ParentDAO"%>
<%@page import="model.StudentGradeDAO"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="model.StudentDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="entity.StudentGrade"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 200px;
        width: 250px;
        margin: 10px;
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
            //ArrayList<Student> students = studentDAO.listAllStudents();
  
            LinkedHashMap<String, ArrayList<Student>> studentList = studentDAO.listAllStudent(branch_id);
            if(studentList.size() > 0){
                Set<String> level = studentList.keySet();
                for(String lvl: level){
                    out.println(lvl);
                    ArrayList<Student> students = studentList.get(lvl);
                    if (students != null) {
                        for (int i = 0; i < students.size(); i++) {
                            Student stu = students.get(i);
                            int id = stu.getStudentID();
                            out.println("<li class='survey-item' id='sid_" + id + "'><span class='survey-country list-only'>SG</span>");
                            out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i><span id='name_"+id+"'>");
                            out.println(stu.getName() + "</span></span>");
                            out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-pin'>&nbsp;&nbsp;</i><span id='address_"+id+"'>");
                            out.println(stu.getAddress() + "</span></span><br/>");
                            out.println("<span class='survey-country'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i><span id='lvl_"+id+"'>");
                            out.println(stu.getLevel() + "</span></span><br>");
                            out.println("<span class='survey-country'>");
                            out.println(ParentDAO.retrieveParentByStudentID(id) + "</span></span><br>");
                           /*
                           LinkedHashMap<String, ArrayList<String>> gradeLists = StudentGradeDAO.retrieveStudentTuitionGrade(id);
                            if(gradeLists != null && !gradeLists.isEmpty()){
                                Set<String> keys = gradeLists.keySet();
                                out.println("<span class='survey-country'>Tuition Grades<br>" + "</span>");
                                for(String subject: keys){
                                    ArrayList<String> grades = gradeLists.get(subject);
                                    out.println("<span class='survey-country'>" + subject + "</span>");
                                    if(grades != null){
                                        for(String grade: grades){
                                            out.println("<span class='survey-country'>" + grade + "</span>");
                                        }
                                    }
                                    out.println("<br>");
                                }
                            }     */

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


                <%          out.println("<span class='survey-end-date'><i class='zmdi zmdi-phone'>&nbsp;&nbsp;</i><span id='phone_"+id+"'>");
                            out.println(stu.getPhone() + "</span></span></div></li>");
                        }

                    } else {
                        out.println("No Students!");
                    }
                }
            }else{
                out.println("<div class='alert alert-warning col-md-5'>No Student Yet! <strong> <a href='CreateStudent.jsp'>Create One</a></strong> </div>");
   
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
                        <input type="hidden" id="branch_id" value="<%=branch_id%>" />
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Level :</p>
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
            var branch_id = $("#branch_id").val();
            $.ajax({
               url: 'Retrieve_Update_StudentServlet',
               dataType: 'JSON',
               data: {studentID: student_id,branch_id:branch_id},
               success: function (data) {
                  $("#id").val(student_id);
                  $("#name").val(data["name"]);
                  $("#lvl").val(data["lvl"]);
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
                if (data === 1) {
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
        name = $("#name").val();
        lvl = $("#lvl").val();
        address = $("#address").val();
        phone  = $("#phone").val();
        r_amount =  $("#r_amount").val(); 
        o_amount =  $("#o_amount").val(); 
        
        
        $('#editStudent').modal('hide');
        $.ajax({
            type: 'POST',
            url: 'UpdateStudentServlet',
            dataType: 'JSON',
            data: {studentID: id,name:name,lvl:lvl,address:address,phone:phone,r_amount:r_amount,o_amount:o_amount},
            success: function (data) {
                if (data === 1) {
                    $("#name_"+id).text(name);
                    $("#address_"+id).text(address);
                    $("#lvl_"+id).text(lvl);
                    $("#phone_"+id).text(phone);
                    $("#age_"+id).text(age);
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