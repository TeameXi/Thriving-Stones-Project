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
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Mass Update Tutor Hourly Rates</span></h5></div>
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
    function format (num,d,existingTutorData,levelID,subjectID,branchID) {
        html = "";
        if(num == 1 || num == 2){
            html = d;
        }else{
            html ="<div id='existingTutorWrapper'></div>";
            if(existingTutorData.length > 0){
                html += existingTutorFormat(existingTutorData,levelID,subjectID,branchID);
            }
                    
            html    += '<h4>New Tutor Hourly Pay Rate</h4><form id="payrateForm">'+
                        '<div class="row">'+
                            '<label class="col-sm-4 control-label">Tutor Name</label>'+
                            '<label class="col-sm-4 control-label">Hourly Pay Rate</label>'+
                        '</div>'+
                        '<div class="row">'+  
                            '<div class="col-sm-4">'+
                                d+
                            '</div>'+
                            '<div class="col-sm-4">'+
                                '<input type="number" class="form-control" name="payRate[]"  step=0.001/>'+
                            '</div>'+
                            '<div class="col-sm-2">'+
                                '<button type="button" class="btn btn-default addButton"><i class="zmdi zmdi-plus"></i></button>'+
                            '</div>'+
                        '</div><br/>'+
                        '<div class="form-group hide row" id="optionTemplate">'+
                            '<div class="col-sm-4">'+
                                d+
                            '</div>'+
                            '<div class="col-sm-4">'+
                                '<input type="text" class="form-control" name="payRate[]" />'+
                            '</div>'+
                            '<div class="col-sm-2">'+
                                '<button type="button" class="btn btn-default removeButton"><i class="zmdi zmdi-minus"></i></button>'+
                            '</div>'+             
                        '</div>'+
                        '<div class="row btn_container">'+
                            '<div class="col-sm-3">'+
                                '<button type="submit" id="submitPayRateBtn" class="btn btn-default">Save Pay Rate</button>'+
                            '</div>'+
                        '</div>'+
                    '</form></div></div>';
        }
        return html;
    }
    
    function existingTutorFormat(existingTutorData,levelID,subjectID,branchID){
        var existingContainerHtml = "";
        existingContainerHtml = '<div class="row"><div class="col-md-1"></div><div class="col-md-10"><h4 class="centered">Existing Tutor Hourly Pay Rate</h4><div class="statusMsg"></div>';
           
        existingContainerHtml += "<table class='table' id='existingTutorTable'><thead><th>Existing Tutor</th><th>PayRate</th><th>Action</th></thead><tbody>";
        for(i = 0 ; i < existingTutorData.length;i++){
            existingContainerHtml +=
                '<tr id="'+i+'">'+ 
                    '<td class="input-disabled">'+existingTutorData[i].name+
                    '</td>'+
                '<td><a href="#" class="edit_payrate" data-name="payrate"  data-pk="'+existingTutorData[i].id+':'+levelID+':'+subjectID+':'+branchID+'" data-title="Enter Tutor Cost">'+ existingTutorData[i].pay+'</a></td>'+
                '<td><button class="btn btn-danger btn-sm">Delete</button></td>'+
                '</tr>';
        }
        existingContainerHtml += '</tbody></table>';
        return existingContainerHtml;
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
                    data: {branch_id:branchID,level_id:levelID,subject_id:subjectID},
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
                                    select_dropdown += "<option value='"+newTutorLists[i].id+"'>"+newTutorLists[i].name+"</option>";
                                }

                                select_dropdown += "</select>";
                    
                                row.child(format(3,select_dropdown,oldTutorLists,levelID,subjectID,branchID)).show();
                                
                                // Existing Tutor
                                if(oldTutorLists.length > 0){        
                                    $('.edit_payrate').editable({
                                        url: function(params) {
                                            $.ajax({    
                                                type:'POST',
                                                url: 'UpdateAndDeleteTutorHourlyRate',
                                                data:{ tutorID:params["pk"],action:"edit",
                                                        payRate:params["value"]},
                                                dataType: "json",
                                                complete: function(response) {
                                                    $('#output').html(response.responseText);
                                                },
                                                error: function() {
                                                    $('#output').html('Bummer: there was an error!');
                                                },
                                            });
                                        },
                                        send: 'always',
                                        type: 'number',
                                        pk: 1
                                    });
                                }
                                
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
                                        
                                        
                                        pay_rate = $('input[name="payRate[]"]').map(function () {
                                            return $(this).val();
                                        }).get();
                                        
                                     
                                        var tutorPayArr = [];
                                        for(i = 0; i < tutor_id.length-1;i++){
                                            var tutorPayObj = {
                                                        "tutor_id": tutor_id[i],
                                                        "level_id": levelID,
                                                        "subject_id": subjectID,
                                                        "branch_id": branchID,
                                                        "hourly_pay": pay_rate[i]};
                                            tutorPayArr[i] = tutorPayObj;
                                        }

                                        if($("#existingTutorTable").length){
                                            
                                        }else{
                                            new_table = existingTutorFormat(tutorPayArr,levelID,subjectID,branchID);
                                            $('#existingTutorWrapper').html(new_table);
                                        }
                                        
                                        
                                        //console.log($('.tutorAssignmentDropdown').find('option:selected').text());
//                                        $.ajax({
//                                            url: 'CreateTutorHourlyRate',
//                                            data: {pay_rate_arr: JSON.stringify(tutorPayArr)},
//                                            dataType: "json",
//                                            success: function (data) {
//                                                if(data === 1){
//                                                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update grades successfully</div>';
////                                                    for(i = 0; i < tutor_id.length-1;i++){
////                                                        var tutorPayObj = {
////                                                                        "tutor_id": tutor_id[i],
////                                                                        "level_id": levelID,
////                                                                        "subject_id": subjectID,
////                                                                        "branch_id": branchID,
////                                                                        "hourly_pay": pay_rate[i]};
////                                                            tutorPayArr[i] = tutorPayObj;
////                                                    }
//                                                }else{
//                                                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';   
//                                                }
//                                                
//                                                //
//                                                
//                                                $(".statusMsg").html(html);
//                                                $('.statusMsg').fadeIn().delay(1000).fadeOut(); 
//                                            }
//                                        });
                                        
                                        
                                  });
                         
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