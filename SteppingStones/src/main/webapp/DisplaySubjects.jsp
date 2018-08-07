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
        height: 170px;
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
    <div style="margin: 20px;"><h3>Tutor Lists</h3></div>
    <div class="row" id="errorMsg"></div>
    <%        String tutor_creation_status = (String) request.getAttribute("creation_status");
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
    %> 


    <div class="row  spaced-top">
        <div class="col-sm-6">
            <form id="searchTutor"> 
                <input class="form-control advanced_targeting_class" type="text" id="filter" placeholder="Subject" style="width:237px; display:inline-block; margin-right:10px">
                <input type = "submit" class="btn btn-default" value = "Search"/>
            </form>
        </div>
        <div class="col-sm-6">
            <div class="portlet light portlet-fit smaller-fonts" >
                <span class="sortby_span">
                    Sort By
                    <select class = "form-control" style = "display:inline-block;width:auto;" onchange="updateSort(this)">
                        <%
                            if (request.getParameter("sortby") != null) {

                            } else { %>
                        <option value = "name" selected>Name</option>
                        <% if (true) { %><option value = "branch">Branch</option><% } %>
                        <option value="latest">Latest</option>
                        <option value="level">Level</option>
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
            int branch_id = 0;
            if (user1 != null) {
                branch_id = user1.getBranchId();
            }

            BranchDAO bDAO = new BranchDAO();
            ArrayList<Branch> branches = bDAO.retrieveAllBranches();

            if (branches.size() > 0) {
                for (Branch b : branches) {
                    int branchId = b.getBranchId();
                    SubjectDAO subs = new SubjectDAO();
                    ArrayList<Subject> subjects = subs.retrieveSubjectsByBranch(branchId);
                    for (Subject s : subjects) {
                        int id = s.getSubjectId();
                        out.println("<li class='survey-item' id='subid_" + id + "'><span class='survey-country list-only'>");
                        out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i><span id='name_" + id + "'>");
                        out.println(s.getSubjectName() + "</span></span>");
                        out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-email'>&nbsp;&nbsp;</i><span id='branch_" + id + "'>");
                        out.println(branchId + "</span></span><br/>");
        %>
        <div class="pull-right">

            <span class="survey-progress">
                <span class="grid-only">
                    <a href="#viewSubject" data-toggle="modal" data-target-id="<%=s.getSubjectId()%>" class="view_more">View detail</a>
                </span>



                <span class="survey-progress-labels">
                    <span class="survey-progress-view list-only">
                        <a href="#viewSubject" data-toggle="modal" data-target-id="<%=s.getSubjectId()%>"><i class="zmdi zmdi-eye"></i></a>
                    </span>


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

<!-- Detail Dialog -->
<div class="modal fade" id="viewSubject" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Subject Details</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">ID :</p>
                </div>
                <div class = "col-sm-8">
                    <p><label id="view_subject_id"></label></p>
                </div>
            </div><br/>

            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">Name :</p>
                </div>
                <div class = "col-sm-8">
                    <p><label id="view_subject_name"></label></p>
                </div>
            </div><br/>

            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">Branch :</p>
                </div>
                <div class = "col-sm-8">
                    <p><label id="view_subject_branch"></label></p>
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
<div class="modal fade" id="editSubject" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <span class="pc_title centered">Edit Subject</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">ID :</p>
                </div>
                <div class = "col-sm-8">
                    <p><input type = "text" class = "form-control" id="subject_id" value =""/></p>
                </div>
            </div><br/>
            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">Name :</p>
                </div>
                <div class = "col-sm-8">
                    <p><input type = "text" class = "form-control" id="subject_name" value =""/></p>
                </div>
            </div><br/>
            <div class="row">
                <div class = "col-sm-4">
                    <p class = "form-control-label">Branch :</p>
                </div>
                <div class = "col-sm-8">
                    <p>
                        <select id="subject_branch" class = "form-control" style = "display:inline-block;width:auto;">
                            <%            
                                for (Branch b : branches) {
                                    out.println("<option value='" + b.getBranchId() + "'>" + b.getName() + "</option>");
                                }
                            %>
                        </select>
                    </p>
                </div>
            </div><br/>
        </div>  

        <div class="modal-footer spaced-top-small centered">
            <button type="button" class="btn btn-success"  onclick="editSubject()">Save Changes</button>
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

