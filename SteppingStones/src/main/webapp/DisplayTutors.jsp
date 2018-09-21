<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="model.TutorDAO"%>
<%@page import="entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 170px;
        width: 250px;
        margin: 10px;
    }
</style>
<div class="col-md-10">
    <div style="margin: 20px;"><h4>Tutor Lists</h4></div>
    <div class="row" id="errorMsg"></div>
    <%       
        String tutor_creation_status = (String) request.getAttribute("creation_status");
        if (tutor_creation_status != null) {
            if (tutor_creation_status == "true") {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'><strong>Something Went wrong!</strong> </div>");
            } else {
                out.println("<div id='creation_status' class='row alert alert-success col-md-12'><strong>Tutor record is inserted !</strong> </div>");
            }
        }
        

        ArrayList<Tutor>duplicatedUsers= (ArrayList)session.getAttribute("existingUserLists");
        if(duplicatedUsers != null){
            if(duplicatedUsers.size() > 0){
                String temp = duplicatedUsers.get(0).getName();
                for(int i = 1; i < duplicatedUsers.size(); i++){
                    temp = temp + ", " + duplicatedUsers.get(i).getName();
                }
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following tutors <strong>(" + temp + ")</strong> already exist;</div>");
                session.removeAttribute("existingUserLists");
            }
        }
    %> 


     <div class="row  spaced-top">
        <div class="col-sm-6">
            <form id="searchTutor"> 
        	<input class="form-control advanced_targeting_class" type="text" id="filter" placeholder="Tutor Name" style="width:237px; display:inline-block; margin-right:10px">
                <input type = "submit" class="btn btn-default" value = "Search"/>
            </form>
	    </div>
        <div class="col-sm-6">
            <div class="portlet light portlet-fit smaller-fonts" >
                <span class="sortby_span">
                    Sort By
                    <select class = "form-control" style = "display:inline-block;width:auto;" onchange="updateSort(this)">
                    <%
                    if(request.getParameter("sortby") != null){
                            
                    }else{ %>
                        <option value = "name" selected>Tutor Name</option>
                        <option value = "gender">Gender</option>
                        <% if(true){ %><option value = "branch">Branch</option><% } %>
                        <option value="latest">Latest</option>
                    <%
                    }
                    %>
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
            TutorDAO tutorDAO = new TutorDAO();
            ArrayList<Tutor> tutors = (ArrayList<Tutor>)request.getAttribute("tutors");;
            int toShow = 8; //change according to no. of record shows
            if(tutors == null || tutors.isEmpty()){
                tutors = tutorDAO.retrieveAllTutorsByLimit(0, toShow, branch_id);
            }else{
                tutors = (ArrayList<Tutor>) request.getAttribute("tutors");
            }
           
            if (tutors != null && !tutors.isEmpty()) {              
                for (Tutor tu : tutors) {
                    String dob = tu.getBirth_date();
                    int id = tu.getTutorId();
                    out.println("<li class='survey-item' id='tid_" + id + "'><span class='survey-country list-only'><span id='gender_" + id + "'>" + tu.getGender() + "</span></span>");
                    out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i><span id='name_" + id + "'>");
                    out.println(tu.getName() + "</span></span>");
                    out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-email'>&nbsp;&nbsp;</i><span id='email_" + id + "'>");
                    out.println(tu.getEmail() + "</span></span><br/>");
                    out.println("<span class='survey-country'><i class='zmdi zmdi-phone'>&nbsp;&nbsp;</i><span id='phone_" + id + "'>");
                    out.println(tu.getPhone() + "</span></span>");

        %>
        <div class="pull-right">

            <span class="survey-progress">
                <span class="grid-only">
                    <a href="#viewTutor" data-toggle="modal" data-target-id="<%=tu.getTutorId()%>" class="view_more">View detail</a>
                </span>



                <span class="survey-progress-labels">
                    <span class="survey-progress-view list-only">
                        <a href="#viewTutor" data-toggle="modal" data-target-id="<%=tu.getTutorId()%>"><i class="zmdi zmdi-eye"></i></a>
                    </span>


                    <span class="survey-progress-label">
                        <a href="#editTutor" data-toggle="modal" data-target-id="<%=tu.getTutorId()%>"><i class="zmdi zmdi-edit"></i></a>
                    </span>

                    <span class="survey-completes">
                        <a href="#small" onclick="deleteTutor('<%=tu.getTutorId()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
                    </span>
                </span>
            </span>


            <%
                    }

                } else {
                    out.println("<div class='alert alert-warning col-md-5'>No Tutor Yet! <strong> <a href='CreateTutor.jsp'>Create One</a></strong> </div>");
                }

            %>
    </ul>
<%
    String pageId = (String)request.getAttribute("id");
    int id = 1;
    if(pageId != null){
        id = Integer.parseInt(pageId);
    }
    int totalpage = tutorDAO.retrieveNumberOfTutorByBranch(branch_id);
    double total = totalpage/(toShow*1.0);
    int totalPage = (int)Math.ceil(total);
%> 
    <nav aria-label="Page navigation example" class="text-center">
  <ul class="pagination">
    <%
        for(int i = 1; i <= totalPage; i++){
            if(id != 0 && id == i){
    %>
    <li class="page-item active"><a class="page-link" href="PaginationTutorServlet?page=<%=i%>&toShow=<%=toShow%>&branch=<%=branch_id%>"><%=i%></a></li>
    <%
            }else{
    %>
    <li class="page-item"><a class="page-link" href="PaginationTutorServlet?page=<%=i%>&toShow=<%=toShow%>&branch=<%=branch_id%>"><%=i%></a></li>
    <%
            }
        }
    %>
</ul> 
</nav>
</div>
</div>
</div>

<!-- Delete Dialog -->
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
<!-- End of Delete -->

<!-- Detail Dialog -->
<div class="modal fade" id="viewTutor" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Tutor Details</span>
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
                        <p><label id="view_tutor_nric">-</label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Name :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><label id="view_tutor_name"></label></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Phone :</p>
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
                        <p class = "form-control-label">Image :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><div id="view_image_container">No Image</div></p>
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



            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>       
    </div>
</div>
<!-- End of Detail Dialog -->


<!-- Edit Dialog -->
<div class="modal fade" id="editTutor" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Edit Tutor</span>
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
                        <p><input type="text" class="form-control" id="tutor_nric" value =""/></p>
                        <input type="hidden" id="tutor_id" value="" />
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Name :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type = "text" class = "form-control" id="tutor_name" value =""/></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Phone :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="phone" value =""/></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Address :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><textarea class = "form-control" id="address" value =""></textarea></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Image :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><div id="image_container"></div><input type='file' class = "form-control" id="tutor_image" value =""/><div style="color:red;" id="imageError"></div></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Birth Date :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type='text' class = "form-control" id="dob" value =""/></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Gender :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p id='gender_container'></p>
                    </div>
                </div><br/>



                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Email :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="email"/></p>
                    </div>
                </div><br/>

              

            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-success"  onclick="editTutor()">Save Changes</button>
            </div>
        </div>       
    </div>
</div>

<!-- End of Edit -->

<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script>
$(document).ready(function () {

    $('#tutor_image').on("change",function(){
        var fileExtension = ['jpeg', 'jpg','png'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            $('#imageError').html("Only image(jpg,png,jpeg) are allowed.");
        }else{
            $('#imageError').html("");
        }

    });

    $('#dob').datetimepicker({
        format: 'DD-MM-YYYY'
    });

    if ($('#creation_status').length) {
        $('#creation_status').fadeIn().delay(2000).fadeOut();
    }

    $("#viewTutor").on("show.bs.modal", function (e) {
        var tutor_id = $(e.relatedTarget).data('target-id');
        $.ajax({
            url: 'RetrieveTutorServlet',
            dataType: 'JSON',
            data: {tutorID: tutor_id},
            success: function (data) {
                if(data["nric"] != ""){
                    $("#view_tutor_nric").text(data["nric"]);
                }
                $("#view_tutor_name").text(data["fullname"]);
                if (data["phone"] !== "") {
                    $("#view_phone").text(data["phone"]);
                }
                if (data["address"] !== "") {
                    $("#view_address").text(data["address"]);
                }

                if (data['image_url'] !== "") {
                    $("#view_image_container").html("<img style='width:100px;padding:10px;' src='" + data['image_url'] + "'></img>");
                }

                if (data["birth_date"] !== "") {
                    $("#view_birthDate").text(data["birth_date"]);
                }

                if (data["gender"] === "F") {
                    $("#view_gender").text("Female");
                } else if (data["gender"] === "M") {
                    $("#view_gender").text("Male");
                }

                if (data["branch"] !== "") {
                    $("#branch").val(data["branch"]);
                }

                $("#view_email").text(data["email"]);

            }
        });

    });

    $("#editTutor").on("show.bs.modal", function (e) {
        var tutor_id = $(e.relatedTarget).data('target-id');
        $("#tutor_nric").attr('readonly', false);
        $("#tutor_nric").val("");
        $("#dob").val("");
        $('#tutor_image').val("");

        $("#dob").attr('readonly', false);

        $('#imageError').html("");
        $.ajax({
            url: 'RetrieveTutorServlet',
            dataType: 'JSON',
            data: {tutorID: tutor_id},
            success: function (data) {

                $("#tutor_id").val(data["id"]);
                if (data["nric"] !== "") {
                    $("#tutor_nric").val(data["nric"]);
                    $("#tutor_nric").attr('readonly', true);
                }
                $("#tutor_name").val(data["fullname"]);
                $("#tutor_name").attr('readonly', true);
                $("#phone").val(data["phone"]);
                $("#address").val(data["address"]);


                if (data['image_url'] !== null) {
                    $("#image_container").html("<img style='width:100px;padding:10px;' src='" + data['image_url'] + "'></img>");
                }

                if (data["birth_date"] !== "") {
                    $("#dob").val(data["birth_date"]);
                    $("#dob").attr('readonly', true);
                }

                if (data["gender"] !== "" && data["gender"] === "F") {
                    html = "<label>Female</label><input type='hidden' id='gender' value='F'/>";    
                    $("#gender_container").html(html);
                }else if(data["gender"] === "M"){
                    html = "<label>Male</label><input type='hidden' id='gender' value='M' />";
                    $("#gender_container").html(html);
                }else{
                    html = "<select class='form-control' id='gender'><option value=''>Select Gender</option><option value='F'>Female</option><option value='M'>Male</option></select>";
                    $("#gender_container").html(html);
                }

                if (data["branch"] !== "") {
                    $("#branch").val(data["branch"]);
                }

                $("#email").val(data["email"]);

            }
        });

    });
});

(function () {
    $(function () {
        return $('[data-toggle]').on('click', function () {
            var toggleView;
            toggleView = $(this).addClass('active').attr('data-toggle');
            if(toggleView !== "dropdown"){
                $(this).siblings('[data-toggle]').removeClass('active');
                if (toggleView !== "modal") {
                    return $('.surveys').removeClass('grid list').addClass(toggleView);
                }
            }
        });
    });
}).call(this);


function deleteTutor(tutor_id) {
    $("#confirm_btn").prop('onclick', null).off('click');
    $("#confirm_btn").click(function () {
        deleteTutorQueryAjax(tutor_id);
    });
}


function deleteTutorQueryAjax(tutor_id) {
    $('#small').modal('hide');
    $("#tid_" + tutor_id).remove();
    $.ajax({
        type: 'POST',
        url: 'DeleteTutorServlet',
        dataType: 'JSON',
        data: {tutorID: tutor_id},
        success: function (data) {
            console.log(data);
            if (data === 1) {
                $("#tid_" + tutor_id).remove();
                html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Deleted Tutor record successfully</div>';
            } else {
                html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
            }

            $("#errorMsg").html(html);
            $('#errorMsg').fadeIn().delay(1000).fadeOut();
        }
    });
}


function editTutor() {
        id = $("#tutor_id").val();
        nric = $("#tutor_nric").val();
        phone = $("#phone").val();
        address = $("#address").val();
        file_name = "";
        if($("#tutor_image").get(0).files.length !== 0){
            file_name = $("#tutor_image").prop("files")[0]["name"];
        }
        dob = $("#dob").val();
        gender = $("#gender").val();
        email = $("#email").val();

        $('#editTutor').modal('hide');

        $.ajax({
            type: 'POST',
            url: 'UpdateTutorServlet',
            dataType: 'JSON',
            data: {tutorID: id,nric:nric,phone:phone,address:address,image:file_name,dob:dob,gender:gender,email:email},
            success: function (data) {
                if (data === 1) {
                    $("#name_" + id).text(name);
                    $("#email_" + id).text(email);
                    $("#gender_" + id).text(gender);
                    $("#phone_" + id).text(phone);

                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update Tutor record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }


</script>

