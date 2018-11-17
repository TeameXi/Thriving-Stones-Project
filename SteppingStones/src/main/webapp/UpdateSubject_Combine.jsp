<%@page import="java.util.HashMap"%>
<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Lvl_Sub_Rel"%>
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
</style>
<div class="col-lg-10">
    <div id="header" style="text-align: center;margin: 10px;">Update Subject Fees - <a href="UpdateSubject.jsp">Individual</a>/ <span class="tab_active" style="font-size: 14px">Combine Class</span></div>
    <input type="hidden" value="<%=branch_id%>" id="branch"/>
    <table id="subjectTable" class="table table-bordered table-striped" style="width:100%; font-size: 14px">
        <thead>
            <tr>
                <th style="text-align: center">Subject</th>
                <th style="text-align: center">Offer Levels</th>
                <th style="text-align: center">Course Fees</th>
                <th>Action</th>
            </tr>
        </thead>
        <%
            ArrayList<Lvl_Sub_Rel> combine_classLists = LevelDAO.retrieveSubjectsForCombineClass(branch_id);
            ArrayList<Level> levelLists = LevelDAO.retrieveAllLevelLists();
            HashMap<Integer, String> levelMap = new HashMap<>(); 
            for(Level lvl : levelLists){
                levelMap.put(lvl.getLevel_id(), lvl.getLevelName());
            }
           
            
            for(Lvl_Sub_Rel combineClass : combine_classLists){
                String [] lvlIds = combineClass.getAdditional_level_ids().split(",");
                String lvlNames = "";
                System.out.println(lvlIds);
                int subjectId = combineClass.getSubject_id();
                String tmpId = "";
                for(String lvlId : lvlIds){
                    int lvl = Integer.parseInt(lvlId);
                    lvlNames += levelMap.get(lvl)+" , ";
                    tmpId += lvlId+"_";
                }
                lvlNames = lvlNames.substring(0, lvlNames.length()-2);
                String id = subjectId+"_"+combineClass.getAdditional_level_ids();
                
                tmpId = subjectId+"_"+tmpId;
                tmpId = tmpId.substring(0,tmpId.length()-1);
                out.println("<tr><th>"+combineClass.getSubject_name()+"</th><th>"+lvlNames+"</th><th><input class='form-control' id='costInputId_"+tmpId+"' type='number' step='0.01' value='"+combineClass.getCost()+"'/></th>");
        %>
            <th><button class='btn btn1' onclick='updateCombineClassCost("<%=id%>","<%=tmpId%>")'>Update</button></th></tr>
        <%
            }
        %>
    </table>

</div>
</div>
<%@include file="footer.jsp"%>
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script>
    $('#subjectTable').DataTable();
    function updateCombineClassCost(id,tmpId){
        cost = $("#costInputId_"+tmpId).val();
        branchId = $("#branch").val();
        $.ajax({
            url: 'UpdateSubjectFeesServlet',
            dataType: 'JSON',
            type: "POST",
            data: {id: id, branchId: branchId,cost:cost,action:"combineClassUpdate"},
            success: function (data) {
                if (data) {
                    $("<div id='errorMsg' class='alert alert-success'>Sucessfully updated subject fees!</div>").insertAfter($("#header"));
                } else {
                    $("<div id='errorMsg' class='alert alert-danger'>Oops! Something went wrong!</div>").insertAfter($("#header"));
                }
                $("#errorMsg").fadeTo(2000, 0).slideUp(2000, function () {
                    $(this).remove();
                });
            }
        });
        
    }
</script>