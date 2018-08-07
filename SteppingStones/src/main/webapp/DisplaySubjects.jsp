<%@page import="model.LevelDAO"%>
<%@page import="entity.Subject"%>
<%@page import="model.SubjectDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="model.TutorDAO"%>
<%@page import="entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
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
        width: 14px;
        height: 14px;
        overflow: hidden;
        border-radius: 50%;
        padding: 0;
        margin: 0 2px;
        background: #f2f2f2;
        text-indent: -9999px;
        color: transparent;
        line-height: 14px;
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
        height: 150px;
        width: 200px;
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
    <div style="margin: 20px;"><h3>Subject Lists</h3></div>
    <div class="row" id="errorMsg"></div>
    <span class="toggler active" data-toggle="grid"><span class="zmdi zmdi-view-dashboard"></span></span>
    <span class="toggler" data-toggle="list"><span class="zmdi zmdi-view-list"></span></span>
    <ul class="surveys grid">
    <%        
        String tutor_creation_status = (String) request.getAttribute("creation_status");
        if (tutor_creation_status != null) {
            if (tutor_creation_status == "true") {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'><strong>Something Went wrong!</strong> </div>");
            } else {
                out.println("<div id='creation_status' class='row alert alert-success col-md-12'><strong>Tutor record is inserted !</strong> </div>");
            }
        }

        ArrayList<String> duplicatedUsers = (ArrayList) session.getAttribute("existingUserLists");
        if (duplicatedUsers != null) {
            if (duplicatedUsers.size() > 0) {
                out.println("<div id='creation_status' class='row alert alert-danger col-md-12'>The following users are already <strong>( " + String.join(",", duplicatedUsers) + " )</strong> existed;</div>");
                session.removeAttribute("existingUserLists");
            }
        }
 
        int branchID = 0;
        if (user1 != null) {
            branchID = user1.getBranchId();
        }

        SubjectDAO subs = new SubjectDAO();
        ArrayList<Subject> subjects = subs.retrieveSubjectsByBranch(branchID);
        if(subjects != null && !subjects.isEmpty()){
            for (Subject s : subjects) {
                int id = s.getSubjectId();
                out.println("<li class='survey-item' id='sid_" + id + "'><span class='survey-country list-only'>SG</span>");
                out.println("<span class='survey-name'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i><span id='name_"+id+"'>");
                out.println(s.getSubjectName() + "</span></span>");
                ArrayList<String> level = LevelDAO.retrieveLevelBySubject(id, branchID);

                if(level != null && !level.isEmpty()){
                    String primary = "";
                    String secondary = "";
                    for(String lvl: level){
                        String[] parts = lvl.split(" ");
                        String part1 = parts[0]; 
                        String part2 = parts[1];
                        if(part1.equals("Primary")){
                            if(primary.equals("")){
                                primary = primary + "Primary " + part2;
                            }else{
                                primary = primary + ", " +  part2;
                            }
                        }else{
                            if(secondary.equals("")){
                                secondary = secondary + "Secondary " +  part2;
                            }else{
                                secondary = secondary + ", " +  part2;
                            }

                        }
                    }
                    if(!primary.equals("")){
                        out.println("<span class='survey-country'><i class='zmdi zmdi-bookmark'>&nbsp;&nbsp;</i><span id='lvl_"+id+"'>");
                        out.println(primary + "</span></span><br>");
                    }
                    if(!secondary.equals("")){
                        out.println("<span class='survey-country'><i class='zmdi zmdi-bookmark'>&nbsp;&nbsp;</i><span id='lvl_"+id+"'>");
                        out.println(secondary + "</span></span><br>");
                    }     
                }
        %>
        <div class="pull-right">

            <span class="survey-progress">
                <span class="survey-progress-labels">
                    <span class="survey-progress-label">
                        <a href="#editSubject" data-toggle="modal" data-target-id="<%=s.getSubjectId()%>"><i class="zmdi zmdi-edit"></i></a>
                    </span>

                    <span class="survey-completes">
                        <a href="#small" onclick="deleteSubject('<%=s.getSubjectId()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
                    </span>
                </span>
            </span>

        <%
            }                  
        } else {
            out.println("<div class='alert alert-warning col-md-5'>No Subject Yet! <strong> <a href='CreateSubject.jsp'>Create One</a></strong> </div>");
        }

        %>
    </ul>
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

<!-- Edit Dialog -->
<div class="modal fade" id="editSubject" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Edit Subject</span>
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
                        <p><input type = "text" class = "form-control" id="name" value ="<%=branch_id%>"/></p>
                        <input type="hidden" id="id" value="" />
                        <input type="hidden" id="branch_id" value="<%=branch_id%>" />
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script>
    $(document).ready(function () {

        if ($('#creation_status').length) {
            $('#creation_status').fadeIn().delay(2000).fadeOut();
        }

        $("#viewSubject").on("show.bs.modal", function (e) {
            var subject_id = $(e.relatedTarget).data('target-id');

            $.ajax({
                url: 'RetrieveSubjectServlet',
                dataType: 'JSON',
                data: {subjectID: subject_id},
                success: function (data) {
                    $("#view_tutor_name").text(data["name"]);


                    /*if (data["branch"] !== "") {
                     $("#branch").val(data["branch"]);
                     }*/
                }
            });

        });

        $("#editSubject").on("show.bs.modal", function (e) {
            var subject_id = $(e.relatedTarget).data('target-id');
            var branchId = $("#branch_" + subject_id).val();
            $.ajax({
                url: 'RetrieveSubjectServlet',
                dataType: 'JSON',
                data: {subjectID: subject_id, branchID: branchId},
                success: function (data) {

                    $("#subject_id").val(data["id"]);
                    $("#subject_name").val(data["name"]);
                    $("#subject_name").attr('readonly', true);

                    if (data["branch"] !== "") {
                     $("#branch").val(data["branch"]);
                     $("#branch").attr('readonly',true);
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

    function deleteSubject(subject_id) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteSubjectQueryAjax(subject_id);
        });
    }


    function deleteSubjectQueryAjax(subject_id) {
        $('#small').modal('hide');
        $("#subid_" + subject_id).remove();
        $.ajax({
            type: 'POST',
            url: 'DeleteSubjectServlet',
            dataType: 'JSON',
            data: {subjectID: subject_id},
            success: function (data) {
                console.log(data);
                if (data === 1) {
                    $("#subid_" + subject_id).remove();
                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Deleted Subject successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(1000).fadeOut();
            }
        });
    }


    function editSubject() {
        id = $("#subject_id").val();
        name = $("#subject_name").val();
        $('#editTutor').modal('hide');

        $.ajax({
            type: 'POST',
            url: 'UpdateSubjectServlet',
            dataType: 'JSON',
            data: {subjectID: id, name: name, branch: branch},
            success: function (data) {
                if (data === 1) {
                    $("#name_" + id).text(name);

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

