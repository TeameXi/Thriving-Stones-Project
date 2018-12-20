<%@page import="java.util.HashMap"%>
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
<style>
    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        min-height: 150px;
        width: 200px;
        margin: 10px;
        height: auto;
    }
    
    .lvl_styling{
        text-align: center;
        padding: 8px;
        margin: 8px;
        border-radius: 5px;
        background-color: #eee;
        position:relative;
    }
    
    .lvl_delete_styling{
        position:absolute;
        top: -8px;
        right: -4px;
        

    }
    
    .lvl_delete_styling:hover{
        color: red !important;
    }
</style>
<div class="col-md-10">
    <div style="margin: 20px;"><h3>Subject Lists</h3></div>
    <div class="row" id="errorMsg"></div>
    <span class="toggler active" data-toggle="grid"><span class="zmdi zmdi-view-dashboard"></span></span>
    <span class="toggler" data-toggle="list"><span class="zmdi zmdi-view-list"></span></span>
    <ul class="surveys grid">
    <%        
        String status = (String) request.getAttribute("status");
        if (status != null) {
                out.println("<div id='creation_status' class='row alert alert-success col-md-12'><strong>" + status + "</strong> </div>");
        }

        SubjectDAO subs = new SubjectDAO();
        ArrayList<Subject> subjects = subs.retrieveSubjectsByBranch(branch_id);
        if(subjects != null && !subjects.isEmpty()){
            for (Subject s : subjects) {
                int id = s.getSubjectId();
                out.println("<li class='survey-item' id='sid_" + id + "'><span class='survey-country list-only'></span>");
                out.println("<span class='survey-name'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i><span id='name_"+id+"'>");
                out.println(s.getSubjectName() + "</span></span>");
                ArrayList<String> level = LevelDAO.retrieveLevelBySubject(id, branch_id);

                if(level != null && !level.isEmpty()){
                    String primary = "";
                    String secondary = "";
                    for(String lvl: level){
                        String[] parts = lvl.split(" ");
                        String part1 = parts[0]; 
                        String part2 = parts[1];
                        if(part1.equals("Primary")){
                            if(primary.equals("")){
                                primary = primary + "Primary <span id='P_"+id+"_"+part2+"'>" + part2+"</span>";
                            }else{
                                primary = primary + "<span id='CP_"+id+"_"+part2+"'>,</span><span id='P_"+id+"_"+part2+"'>" +  part2+"</span>";
                            }
                        }else{
                            if(secondary.equals("")){
                                secondary = secondary + "Secondary <span id='S_"+id+"_"+part2+"'>" +  part2+"</span>";
                            }else{
                                secondary = secondary + "<span id='CS_"+id+"_"+part2+"'>,</span><span id='S_"+id+"_"+part2+"'>" +  part2+"</span>";
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
                    
                    //For Combined Case
                    HashMap<String,String> combined_level = LevelDAO.retrieveCombinedLevelBySubject(id, branch_id); 
                    for(String key : combined_level.keySet()){
                        out.println("<span id='"+key+"' class='survey-country'><i class='zmdi zmdi-plus-circle'>&nbsp;&nbsp;</i>");
                        out.println(combined_level.get(key)+"</span>");
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
            <div class="modal-body smaller-fonts centered">Are you sure you want to delete this subject? Once deleted, all data relating to this subject will be deleted too.</div>
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
                <div class="row" id="Msg"></div>
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Subject Name :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type = "text" class = "form-control" id="subject_name" value =""/></p>
                        <input type="hidden" id="branch_id" value="<%=branch_id%>" />
                        <input type="hidden" id="subject_id" value="" />
                    </div>
                </div><br/>
                <div class="row" id="lvlContainer"></div>
                
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-success"  onclick="editSubject()">Save Changes</button>
            </div>
        </div>       
    </div>
</div>

<%@include file="footer.jsp"%>

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css" rel="stylesheet">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>


<script>
    $(document).ready(function () {

        if ($('#creation_status').length) {
            $('#creation_status').fadeIn().delay(2000).fadeOut();
        }

        $("#editSubject").on("show.bs.modal", function (e) {
            var subject_id = $(e.relatedTarget).data('target-id');
            var branchId = $("#branch_id").val();
            $.ajax({
                url: 'RetrieveSubjectServlet',
                dataType: 'JSON',
                data: {subjectID: subject_id, branchID: branchId},
                success: function (data) {
                    $("#subject_id").val(data["id"]);
                    $("#subject_name").val(data["name"]);
                    html = "";
                    for(var i=0; i < data["lvl_names"].length; i++){
                        wrapper_replaced_id = data["lvl_ids"][i].replace(/,/g, "_");
                        replaced_id = '"'+wrapper_replaced_id+'"';
                        html += "<div id='lvl_wrapper_"+wrapper_replaced_id+"' class='lvl_styling col-sm-3'>"+data["lvl_names"][i] + "<a href='#level_delete_confirmation' data-toggle='modal' onclick='deleteLevel("+data["id"]+","+data["branch_id"] +","+replaced_id+")' class='lvl_delete_styling'><i class='zmdi zmdi-close-circle zmdi-hc-2x'></i></a></div>";
                    }
                    html += "<br/>";
                    $("#lvlContainer").html(html);
                    
                }
            });

        });
    });
    
    function deleteLevel(subjectId, branchId, levelId){
        $("#confirm").prop('onclick', null).off('click');
        $("#confirm").click(function () {
            deleteLevelQueryAjax(subjectId,levelId, branchId);
        });      
    }
    
    function deleteLevelQueryAjax(subject_id,level_id, branch_id){
        $("#level_delete_confirmation").modal('hide');
        $("#lvl_wrapper_"+level_id).remove();
        original_dash_level_id = level_id;
        level_id = level_id.replace(/_/g, ",");
        
        $.ajax({
            type: 'POST',
            url: 'DeleteLevelServlet',
            dataType: 'JSON',
            data: {subjectID: subject_id, levelID: level_id, branchID: branch_id},
            success: function (data) {
                if (data === 1) {
                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Deleted Level from Subject successfully</div>';
                    
                    // Combined class case
                    if(level_id.indexOf(",") > -1){
                        combined_wrapper_ele = document.getElementById(level_id);
                        combined_wrapper_ele.remove();
                        
                        $("#lvl_wrapper_"+original_dash_level_id).remove();
                    }else{
                        $("#subid_" + subject_id).remove();
                        $("#lvl_wrapper_"+level_id).remove();

                        if(level_id > 6){
                            span_lvl_name = "S_"+subject_id+"_"+(level_id-6);
                            if(level_id === 10){
                                prev_span = $('#'+span_lvl_name).prev();
                                prev_span.remove(); 
                            }else{
                                span_comma_name ="CS_"+subject_id+"_"+(level_id-5);
                                $("#"+span_comma_name).remove();
                            }
                            $('#'+span_lvl_name).remove();
                        }else{
                            span_lvl_name = "P_"+subject_id+"_"+(level_id);
                            if(level_id === 6){
                                prev_span = $('#'+span_lvl_name).prev();
                                prev_span.remove();
                            }else{
                                span_comma_name ="CP_"+subject_id+"_"+(level_id+1);
                                $("#"+span_comma_name).remove();
                            }
                            $('#'+span_lvl_name).remove();
                        }
                    }
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#Msg").html(html);
                $('#Msg').fadeIn().delay(1000).fadeOut();
            }
        });
    
    }

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

    function deleteSubject(subject_id) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteSubjectQueryAjax(subject_id);
        });
    }


    function deleteSubjectQueryAjax(subject_id) {
        $('#small').modal('hide');
        $("#sid_" + subject_id).remove();
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
        branch = $("#branch_id").val();
        $('#editSubject').modal('hide');

        $.ajax({
            type: 'POST',
            url: 'UpdateSubjectServlet',
            dataType: 'JSON',
            data: {subjectID: id, name: name, branch: branch},
            success: function (data) {
                if (data === 1) {
                    $("#name_" + id).text(name);

                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update Subject record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }
                
                

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }


</script>

<!-- Delete Dialog -->
<div class="modal fade bs-modal-sm" id="level_delete_confirmation" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to delete this item?</div>
            <div class="modal-footer centered">
                <a id="confirm"><button type="button" class="small_button pw_button del_button autowidth">Yes, Remove</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- End of Delete -->