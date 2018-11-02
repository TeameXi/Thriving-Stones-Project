<%@page import="java.util.ArrayList"%>
<%@page import="entity.Lvl_Sub_Rel"%>
<%@page import="model.SubjectDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/list_metro.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('${pageContext.request.contextPath}/styling/img/close.png') no-repeat center center;
    }
    
    .btn_container{
        margin-top:5px;
    }
    
    .btn-default{
        background-color: #9ccbce;
        color:white;
        border:none;
    }
    
    .exTutor{
        margin-top: 10px;
    }
</style>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.18/datatables.min.css"/>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;">Mass Update Tutor Payrate - <span class="tab_active">Individual</span> / <a href="MassUpdateTutorPay_Combine.jsp">Combine Class</a></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-9">
            <%
                ArrayList<Lvl_Sub_Rel> lvlSubRefLists = SubjectDAO.retrieveLevelAndSubjectForHourlyRates(branch_id);
            %>
            <div class="table-responsive-sm">
            <table id="tutorHourlyRateTable" class="table display responsive nowrap" style="width:100%">
                <thead class="thead-light">
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">LevelID</th>
                        <th scope="col">Level</th>
                        <th scope="col">SubjectID</th>
                        <th scope="col">Subject</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for(Lvl_Sub_Rel ref:lvlSubRefLists){
                            out.println("<tr><td class='details-control'></td><td>"+ref.getLevel_id()+"</td><td>"+ref.getLevel_name()+"</td><td>"+ref.getSubject_id()+"</td><td>"+ref.getSubject_name()+"</td></tr>");
                        }
                    %>
                </tbody>
            </table>
            </div>
        </div>
    </div>
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

<%@include file="footer.jsp"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.18/b-1.5.2/b-html5-1.5.2/r-2.2.2/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>

<!--Validation-->
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src="https://oss.maxcdn.com/jquery.bootstrapvalidator/0.5.2/js/bootstrapValidator.min.js"></script>

<!--Edit Function -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>

<script>
    function format(num,d,existingTutorData,levelID,subjectID,branchID) {
        html = "";
        if(num === 1 || num === 2){
            html = d;
        }else{
            html ="<div id='existingTutorWrapper'></div>";
            if(existingTutorData.length > 0){
                html += existingTutorFormat(existingTutorData,levelID,subjectID,branchID);
            }
            
            html += '<div class="externalWrapper">';
            html += newTutorFormat(d);           
            html += '</div>';
        }
        return html;
    }
    
   
    function newTutorFormat(dropdownList){
        html   = '<div class="newTutorContainer"><h4>New Tutor Hourly Pay Rate</h4><form id="payrateForm"><br/>'+
                        '<div class="row">'+
                            '<div class="col-md-1"></div>'+
                            '<label class="col-sm-4 control-label">Tutor Name</label>'+
                            '<label class="col-sm-4 control-label">Hourly Pay Rate</label>'+
                        '</div>'+
                        '<div class="row">'+  
                            '<div class="col-md-1"></div>'+
                            '<div class="col-sm-4">'+
                                dropdownList+
                            '</div>'+
                            '<div class="col-sm-4">'+
                                '<div class="col-md-1"></div>'+
                                '<input type="number" id="inputHourlyRateNum" class="form-control" name="payRate[]"  step=0.001/>'+
                            '</div>'+
                            '<div class="col-sm-2">'+
                                '<button type="button" class="btn btn-default addButton"><i class="zmdi zmdi-plus"></i></button>'+
                            '</div>'+
                        '</div><br/>'+
                        '<div class="form-group hide row" id="optionTemplate">'+
                            '<div class="col-md-1"></div>'+
                            '<div class="col-sm-4">'+
                                dropdownList+
                            '</div>'+
                            '<div class="col-sm-4">'+
                                '<div class="col-md-1"></div>'+
                                '<input type="text" class="form-control" name="payRate[]" />'+
                            '</div>'+
                            '<div class="col-sm-2">'+
                                '<button type="button" class="btn btn-default removeButton"><i class="zmdi zmdi-minus"></i></button>'+
                            '</div>'+             
                        '</div>'+
                        '<div class="row btn_container">'+
                          '<div class="col-md-1"></div>'+
                            '<div class="col-sm-3">'+
                                '<button type="submit" id="submitPayRateBtn" class="btn btn-default">Save Pay Rate</button>'+
                            '</div>'+
                        '</div>'+
                    '</form></div></div></div>';
            return html;
    }
    
    function existingTutorFormat(existingTutorData,levelID,subjectID,branchID){
        var existingContainerHtml = "";
        existingContainerHtml = '<h4>Existing Tutor Hourly Pay Rate</h4><div class="row"><div class="col-md-1"></div><div class="col-md-10"><br/><div class="statusMsg"></div>';
           
        existingContainerHtml += "<table class='table' id='existingTutorTable'><thead><th>Existing Tutor</th><th>PayRate</th><th>Action</th></thead><tbody>";
        for(i = 0 ; i < existingTutorData.length;i++){
            ids = existingTutorData[i].id+':'+levelID+':'+subjectID+':'+branchID;
            //console.log(ids);
            existingContainerHtml +=
                '<tr id="'+ids+'">'+ 
                    '<td class="input-disabled">'+existingTutorData[i].name+
                    '</td>'+
                '<td><a href="#" class="edit_payrate" data-name="payrate"  data-pk="'+ids+'" data-title="Enter Tutor Cost">'+ existingTutorData[i].hourly_pay+'</a></td>'+
                '<td><a data-toggle="modal" class="btn btn-danger btn-sm" href="#small" onclick="deleteTutorRate('+"'"+ids+"'"+')" ><i class="zmdi zmdi-delete"></i> Delete</a></td>'+
                '</tr>';
        }
        existingContainerHtml += '</tbody></table></div></div>';
        return existingContainerHtml;
    }
    
    function appendingRowToExistingTutorFormat(existingTutorData,levelID,subjectID,branchID){
        for(i = 0 ; i < existingTutorData.length;i++){
            ids = existingTutorData[i].id+':'+levelID+':'+subjectID+':'+branchID;
            //console.log(ids);
            existingContainerHtml =
                '<tr id="'+ids+'">'+ 
                    '<td class="input-disabled">'+existingTutorData[i].name+
                    '</td>'+
                '<td><a href="#" class="edit_payrate" data-name="payrate"  data-pk="'+ids+'" data-title="Enter Tutor Cost">'+ existingTutorData[i].hourly_pay+'</a></td>'+
                '<td><a data-toggle="modal" class="btn btn-danger btn-sm" href="#small" onclick="deleteTutorRate('+"'"+ids+"'"+')" ><i class="zmdi zmdi-delete"></i> Delete</a></td>'+
                '</tr>';
            $('#existingTutorTable').append(existingContainerHtml);
        }
    }
    
    function deleteTutorRate(tutor_id) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteTutorRateQueryAjax(tutor_id);
        });
        return false;
    }

    function deleteTutorRateQueryAjax(tutor_id) {
        $('#small').modal('hide');
        
        $.ajax({    
            type:'POST',
            url: 'UpdateAndDeleteTutorHourlyRate',
            data:{ tutorID:tutor_id,action:"delete"},
            dataType: "json",
            success: function(response) {
                if(response === 1){
                    html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Delete successfully</div>';
                    deleteRow(tutor_id); 
                }else{
                    html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                }
                $(".statusMsg").html(html);
                $('.statusMsg').fadeIn().delay(1000).fadeOut();
            }
        });
        
    }
   
    function deleteRow(rowid){   
        var row = document.getElementById(rowid);
        var table = row.parentNode;
        while ( table && table.tagName !== 'TABLE' )
            table = table.parentNode;
        if ( !table )
            return;
        table.deleteRow(row.rowIndex);
    }
    
    function editHourlyRate(){
        $('.edit_payrate').editable({
            url: function(params) {
                $.ajax({    
                    type:'POST',
                    url: 'UpdateAndDeleteTutorHourlyRate',
                    data:{ tutorID:params["pk"],action:"edit",
                            payRate:params["value"]},
                    dataType: "json",
                    success: function(response) {
                        //console.log(response);
                        if(response === 1){
                            html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Updated successfully</div>';
                        }else{
                            html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
                        }
                        $(".statusMsg").html(html);
                        $('.statusMsg').fadeIn().delay(1000).fadeOut();
                    }
                });
            },
            send: 'always',
            type: 'number',
            step:'any',
            pk: 1
        });
    }
    
    
    function createNewTutorPayRate(select_dropdown,levelID,subjectID,branchID){
        $('#payrateForm')
        .bootstrapValidator({
           feedbackIcons: {
               valid: 'glyphicon glyphicon-ok',
               invalid: 'glyphicon glyphicon-remove',
               validating: 'glyphicon glyphicon-refresh'
           },
           fields: {
               'payRate[]': {
                   validators: {
                       notEmpty: {
                           message: 'Enter number'
                       }
                   }
               }
           }
       })
        // Add button click handler
        .on('click', '.addButton', function() {
            var $template = $('#optionTemplate'),
                $clone    = $template
                                .clone()
                                .removeClass('hide')
                                .removeAttr('id')
                                .insertBefore($template),
                $option   = $clone.find('[name="payRate[]"]');

            // Add new field
            $('#payrateForm').bootstrapValidator('addField', $option);
        })
        // Remove button click handler
        .on('click', '.removeButton', function() {
            var $row    = $(this).parents('.form-group'),
            $option = $row.find('[name="payRate[]"]');

            // Remove element containing the option
            $row.remove();

            // Remove field
            $('#payrateForm').bootstrapValidator('removeField', $option);
        })
        // Called after adding new field
        .on('added.field.bv', function(e, data) {
            // data.field   --> The field name
            // data.element --> The new field element
            // data.options --> The new field options

            if (data.field === 'option[]') {
                if ($('#payrateForm').find(':visible[name="payRate[]"]').length >= MAX_OPTIONS) {
                    $('#payrateForm').find('.addButton').attr('disabled', 'disabled');
                }
            }
        })
        // Called after removing the field
         .on('removed.field.bv', function(e, data) {
             if (data.field === 'option[]') {
                 if ($('#payrateForm').find(':visible[name="option[]"]').length < MAX_OPTIONS) {
                     $('#payrateForm').find('.addButton').removeAttr('disabled');
                 }
              }
         })
        //submit
        .on('success.form.bv',function(e){
            e.preventDefault(); // <----- THIS IS NEEDED

            $('#submitPayRateBtn').prop('disabled', true);
            $('#submitPayRateBtn').attr("disabled", "disabled"); 

            tutor_id = $('select[name="tutorDropdown[]"]').map(function () {
                return $(this).val();
            }).get();


            tutor_name = $('select[name="tutorDropdown[]"]').map(function () {
                return  $(this).find('option:selected').text();
            }).get();



            pay_rate = $('input[name="payRate[]"]').map(function () {
                return $(this).val();
            }).get();


            var tutorPayArr = [];
            for(i = 0; i < tutor_id.length-1;i++){
                var tutorPayObj = {
                            "id": tutor_id[i],
                            "level_id": levelID,
                            "subject_id": subjectID,
                            "branch_id": branchID,
                            "name":tutor_name[i],
                            "hourly_pay": pay_rate[i]};
                tutorPayArr[i] = tutorPayObj;
            }


               $.ajax({
                type:'POST',
                url: 'CreateTutorHourlyRate',
                data: {pay_rate_arr: JSON.stringify(tutorPayArr),action:"individual"},
                dataType: "json",
                success: function (data) {
                    if(data === 1){
                        html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update grades successfully</div>';
                        UIUpadateUponSuccessfulCreation(tutorPayArr,levelID,subjectID,branchID);
                    }else{
                        html = '<div class="alert alert-danger col-md-12"><strong>This tutor is existed.Try another tutor!</strong></div>';   
                    }

                    //

                    $(".statusMsg").html(html);
                    $('.statusMsg').fadeIn().delay(1000).fadeOut(); 
                }
            });
      });
}


function UIUpadateUponSuccessfulCreation(tutorPayArr,levelID,subjectID,branchID){
    if($("#existingTutorTable").length){
        // Appending to table
        appendingRowToExistingTutorFormat(tutorPayArr,levelID,subjectID,branchID);

        // allow to edit
        editHourlyRate();

        // Remove current row
        $(".has-feedback.has-success").remove();
        $("#inputHourlyRateNum").val('');
    }else{
        // New table of tutor payrate (currently added)
        new_table = existingTutorFormat(tutorPayArr,levelID,subjectID,branchID);
        $('#existingTutorWrapper').html(new_table);

        // allow to edit
        editHourlyRate();

        // Remove current added row
        $(".has-feedback.has-success").remove();
        $("#inputHourlyRateNum").val('');


//                            new_select_dropdown = "<select name='tutorDropdown[]' class='form-control tutorAssignmentDropdown'>";
//                            for(i=0;i<newTutorLists.length;i++){
//                                select_dropdown += "<option id='tutorDropdown' value='"+newTutorLists[i].id+"'>"+newTutorLists[i].name+"</option>";
//                            }
//
//                            select_dropdown += "</select>";
//                                            html = newTutorFormat(select_dropdown);
//                                            $(".externalWrapper").html(html);
//                                            createNewTutorPayRate(select_dropdown,levelID,subjectID,branchID);

        }
    }
    $(document).ready(function () {
      
        // Dynamic Added Field
        var table = $('#tutorHourlyRateTable').DataTable({
            "columns": [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {"data": "LevelID"},
                {"data": "Level"},
                {"data": "SubjectID"},
                {"data": "Subject"},
            ],
            "columnDefs": [
                {
                    "targets": [1,3],
                    "visible": false,
                    "searchable": false
                }
            ],
        });
          // Add event listener for opening and closing details
        $('#tutorHourlyRateTable').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = table.row( tr );

            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                branchID = <%=branch_id%>;
                levelID = row.data()["LevelID"];
                subjectID = row.data()["SubjectID"];
//                console.log(subjectID);
//                console.log(levelID);
                
                var select_dropdown = "";
                // Open this row
                  $.ajax({
                    url: 'RetrieveTutorHourlyRate',
                    data: {branch_id:branchID,level_id:levelID,subject_id:subjectID,action:"individual"},
                    dataType: "json",
                    complete: function (response) {
                        var responseData = JSON.parse(response.responseText);
                        
                        oldTutorLists = responseData["oldTutor"];
                        newTutorLists = responseData["newTutor"];
                        //console.log(responseData);
                      
                        if (responseData === -1) {
                            html = '<div class="alert alert-danger col-md-5"><strong>Sorry!</strong> Something went wrong</div>';
                            row.child(format(1,html,oldTutorLists,levelID,subjectID,branchID)).show();

                        } else if (responseData.length <= 0) {
                            html = '<div class="alert alert-warning col-md-5"><strong>All Tutor Added.</strong></div>';
                            row.child(format(2,html,oldTutorLists,levelID,subjectID,branchID)).show();
                        } else {
                            // `d` is the original data object for the row
                            select_dropdown = "<select name='tutorDropdown[]' class='form-control tutorAssignmentDropdown'>";
                            for(i=0;i<newTutorLists.length;i++){
                                select_dropdown += "<option id='tutorDropdown' value='"+newTutorLists[i].id+"'>"+newTutorLists[i].name+"</option>";
                            }

                            select_dropdown += "</select>";

                            row.child(format(3,select_dropdown,oldTutorLists,levelID,subjectID,branchID)).show();

                            // Existing Tutor
                            if(oldTutorLists.length > 0){  
                                editHourlyRate();
//                                $('.edit_payrate').editable({
//                                    url: function(params) {
//                                        $.ajax({    
//                                            type:'POST',
//                                            url: 'UpdateAndDeleteTutorHourlyRate',
//                                            data:{ tutorID:params["pk"],action:"edit",
//                                                    payRate:params["value"]},
//                                            dataType: "json",
//                                            success: function(response) {
//                                                console.log(response);
//                                                if(response === 1){
//                                                    html = '<br/><div class="alert alert-success col-md-12"><strong>Success!</strong> Updated successfully</div>';
//                                                }else{
//                                                    html = '<br/><div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
//                                                }
//                                                $(".statusMsg").html(html);
//                                                $('.statusMsg').fadeIn().delay(1000).fadeOut();
//                                            }
//                                        });
//                                    },
//                                    send: 'always',
//                                    type: 'number',
//                                    step:'any',
//                                    pk: 1
//                                });
                            }
                            
                            
                            //New Tutor
                            createNewTutorPayRate(select_dropdown,levelID,subjectID,branchID);
                                  
                                
                         
                         
                        }
                    },
                    error: function () {
                        $('#output').html('There was an error!');
                    }
                });
                
                tr.addClass('shown');
            }
        });
        
       
    });
</script>

