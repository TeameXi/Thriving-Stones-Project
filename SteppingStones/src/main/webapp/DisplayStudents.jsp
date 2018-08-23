<%@page import="model.LevelDAO"%>
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
    <div class="row" id="errorMsg"></div>
    <%        String student_creation_status = (String) request.getAttribute("creation_status");
        if (student_creation_status != null) {
            if (student_creation_status == "true") {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'><strong>Something Went wrong!</strong> </div>");
            } else {
                out.println("<div id='creation_status' class='row alert alert-success col-md-12'><strong>Student record is inserted !</strong> </div>");
            }
        }

        ArrayList<String> duplicatedUsers = (ArrayList) session.getAttribute("existingUserLists");
        if (duplicatedUsers != null) {
            if (duplicatedUsers.size() > 0) {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following students already <strong>( " + String.join(",", duplicatedUsers) + " )</strong> exist;</div>");
                session.removeAttribute("existingUserLists");
            }
        }

        ArrayList<String> duplicatedParents = (ArrayList) session.getAttribute("existingParentLists");
        if (duplicatedParents != null) {
            if (duplicatedParents.size() > 0) {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following parents already <strong>( " + String.join(",", duplicatedParents) + " )</strong> exist;</div>");
                session.removeAttribute("existingParentLists");
            }
        }
    %> 

    <div class="col-md-10">
        <%
            String lvlStr = (String) request.getParameter("level");
            int levelID = 0;
            if (lvlStr == null) {
                lvlStr = "1";
            }
            levelID = Integer.parseInt(lvlStr);
            String level = LevelDAO.retrieveLevel(levelID);
        %>
        <div><h4>Student Lists from <strong><%=level%></strong></h4></div>
        <div class="row" id="errorMsg"></div>
        <div class="row  spaced-top">
            <div class="col-sm-6">
                <div class="portlet light portlet-fit smaller-fonts" >
                    <span class="sortby_span">
                        Sort By
                        <select class = "form-control" style = "display:inline-block;width:auto;" name= "level" onchange="updateLevel(this)">
                            <option value='0'>Level</option>
                            <option value='1'>Primary 1</option>
                            <option value='2'>Primary 2</option>
                            <option value='3'>Primary 3</option>
                            <option value='4'>Primary 4</option>
                            <option value='5'>Primary 5</option>
                            <option value='6'>Primary 6</option>
                            <option value='7'>Secondary 1</option>
                            <option value='8'>Secondary 2</option>
                            <option value='9'>Secondary 3</option>
                            <option value='10'>Secondary 4</option>
                        </select>
                    </span>
                    <br style="clear:both">
                </div>
            </div>
        </div>
        <span class="toggler active" data-toggle="grid"><span class="zmdi zmdi-view-dashboard"></span></span>
        <span class="toggler" data-toggle="list"><span class="zmdi zmdi-view-list"></span></span>
        <ul class="surveys grid">
            <%
                ArrayList<Student> students = (ArrayList<Student>) request.getAttribute("students");
                int toShow = 8; //change according to no. of record shows
                if (students == null || students.isEmpty()) {
                    students = StudentDAO.listAllStudentsByLimit(branch_id, levelID, 0, toShow);
                } else {
                    students = (ArrayList<Student>) request.getAttribute("students");
                }

                if (students != null && !students.isEmpty()) {
                    for (int i = 0; i < students.size(); i++) {
                        Student stu = students.get(i);
                        int id = stu.getStudentID();
                        out.println("<li class='survey-item' id='sid_" + id + "'><span class='survey-country list-only'>SG</span>");
                        out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i><span id='name_" + id + "'>");
                        out.println(stu.getName() + "</span></span>");
                        out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-pin'>&nbsp;&nbsp;</i><span id='address_" + id + "'>");
                        out.println(stu.getAddress() + "</span></span><br/>");
                        out.println("<span class='survey-country'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i><span id='lvl_" + id + "'>");
                        out.println(stu.getLevel() + "</span></span><br>");
                        out.println("<span class='survey-country'>");
                        out.println(ParentDAO.retrieveParentByStudentID(id) + "</span></span><br>");


            %>
            <div class="pull-right">

                <span class="survey-progress">
                    <span class="grid-only">
                        <a href="#viewStudent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>" class="view_more">View detail</a>
                    </span>



                    <span class="survey-progress-labels">
                        <span class="survey-progress-view list-only">
                            <a href="#viewStudent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>"><i class="zmdi zmdi-eye"></i></a>
                        </span>


                        <span class="survey-progress-label">
                            <a href="#editStudent" data-toggle="modal" data-target-id="<%=stu.getStudentID()%>"><i class="zmdi zmdi-edit"></i></a>
                        </span>

                        <span class="survey-completes">
                            <a href="#small" onclick="deleteStudent('<%=stu.getStudentID()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
                        </span>
                    </span>
                </span>


                <%          out.println("<span class='survey-end-date'><i class='zmdi zmdi-phone'>&nbsp;&nbsp;</i><span id='phone_" + id + "'>");
                            out.println(stu.getPhone() + "</span></span></div></li>");
                        }

                    } else {
                        out.println("<div class='alert alert-warning col-md-5'>No Student Yet! <strong> <a href='CreateStudent.jsp'>Create One</a></strong> </div>");
                    }
                %>
        </ul>
        <%
            String pageId = (String) request.getAttribute("id");
            int id = 1;
            if (pageId != null) {
                id = Integer.parseInt(pageId);
            }
            int totalpage = StudentDAO.retrieveNumberOfStudentByLevel(levelID);
            double total = totalpage / (toShow * 1.0);
            int totalPage = 0;
            if (total != 0) {
                totalPage = (int) Math.ceil(total);
            }

        %> 

        <nav aria-label="Page navigation example" class="text-center">
            <ul class="pagination">
                <%        for (int i = 1; i <= totalPage; i++) {
                        if (id != 0 && id == i) {
                %>
                <li class="page-item active"><a class="page-link" href="PaginationStudentServlet?page=<%=i%>&toShow=<%=toShow%>&level=<%=levelID%>&branch=<%=branch_id%>"><%=i%></a></li>
                    <%
                    } else {
                    %>
                <li class="page-item"><a class="page-link" href="PaginationStudentServlet?page=<%=i%>&toShow=<%=toShow%>&level=<%=levelID%>&branch=<%=branch_id%>"><%=i%></a></li>
                    <%
                            }
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

<!-- Detail Dialog -->
<div class="modal fade" id="viewStudent" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Student Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">NRIC :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_student_nric">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Name :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_student_name"></label></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Level :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_level"></label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Contact No :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_phone">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Address :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_address">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Birth Date :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_birthDate">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Gender :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_gender">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Email :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_email">-</label></p>
                    </div>
                </div><br/>
                
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Required Amount :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_reqAmt">-</label></p>
                    </div>
                </div><br/>


                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Outstanding Amount :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_outstandingAmt">-</label></p>
                    </div>
                </div><br/>



            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>       
    </div>
</div>
<!-- End of Detail Dialog -->

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
    $(document).ready(function () {
        $("#editStudent").on("show.bs.modal", function (e) {
            var student_id = $(e.relatedTarget).data('target-id');
            var branch_id = $("#branch_id").val();
            $.ajax({
                url: 'Retrieve_Update_StudentServlet',
                dataType: 'JSON',
                data: {studentID: student_id, branch_id: branch_id},
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
        $("#viewStudent").on("show.bs.modal", function (e) {       
            var student_id = $(e.relatedTarget).data('target-id');
            var branch_id = $("#branch_id").val();
            $.ajax({
                url: 'RetrieveStudentServlet',
                dataType: 'JSON',
                data: {studentID: student_id, branch_id: branch_id},
                success: function (data) {
                    if(data["nric"] != ""){
                        $("#view_student_nric").text(data["nric"]);
                    }
                    $("#view_student_name").text(data["fullname"]);
                    if (data["birth_date"] !== "") {
                        $("#view_birthDate").text(data["birth_date"]);
                    }
                    if (data["gender"] === "F") {
                        $("#view_gender").text("Female");
                    } else if (data["gender"] === "M") {
                        $("#view_gender").text("Male");
                    }
                    $("#view_level").text(data["level"]);
                    if (data["branch"] !== "") {
                        $("#branch").val(data["branch"]);
                    }
                    if (data["address"] !== "") {
                        $("#view_address").text(data["address"]);
                    }
                    if (data["phone"] !== "") {
                        $("#view_phone").text(data["phone"]);
                    }
                    $("#view_email").text(data["email"]);
                    if (data["reqAmt"] !== "") {
                        $("#view_reqAmt").text(data["reqAmt"]);
                    }
                    if (data["outstandingAmt"] !== "") {
                        $("#view_outstandingAmt").text(data["outstandingAmt"]);
                    }
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


    function editStudent() {
        id = $("#id").val();
        name = $("#name").val();
        lvl = $("#lvl").val();
        address = $("#address").val();
        phone = $("#phone").val();
        r_amount = $("#r_amount").val();
        o_amount = $("#o_amount").val();


        $('#editStudent').modal('hide');
        $.ajax({
            type: 'POST',
            url: 'UpdateStudentServlet',
            dataType: 'JSON',
            data: {studentID: id, name: name, lvl: lvl, address: address, phone: phone, r_amount: r_amount, o_amount: o_amount},
            success: function (data) {
                if (data === 1) {
                    $("#name_" + id).text(name);
                    $("#address_" + id).text(address);
                    $("#lvl_" + id).text(lvl);
                    $("#phone_" + id).text(phone);
                    html = '<div class="alert alert-success col-md-5"><strong>Success!</strong> Update Student record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }
    


    function updateLevel(level) {
        var lvlID = level.value;
        var branch_id = $("#branch_id").val();
        console.log(lvlID);
        console.log(branch_id);
        location.href = "?level=" + lvlID;
    }

</script>