

<%@page import="model.RewardDAO"%>
<%@include file="protect_student.jsp"%>
<%@include file="header.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<style>
    .details{
        text-align: center;
    }
    @media screen and (max-width: 480px){
        body{
            font-size: 10px;
        } 
        .form-control{
            font-size: 10px;
        }
        a {
            font-size: 10px;
        }
        #tab{
            margin-bottom: 20px !important;
        }
        .tableImage{
            width: 20px;
            heigth: 20px;
        }
        .availablePoint{
            text-align: center;
            margin-top: 25px;
        }
    }
    @media screen and (min-width:481px) and (max-width: 767px) {
        body{
            font-size: 12px !important;
        } 
        .form-control{
            font-size: 12px !important;
        }
        a {
            font-size: 12px ;
        }
        #tab{
            margin-bottom: 20px !important;
        }
        .tableImage{
            width: 50px;
            heigth: 50px;
        }
        .availablePoint{
            text-align: center;
            margin-top: 25px;
        }
    }
    @media screen and (min-width:768px) and (max-width: 991px) {
        #rewardTable_filter{
            float: right;
        }
    }
</style>
<div class="col-md-10">
    <%        
        String success = (String) request.getAttribute("status");
        if (success != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Update error. Please try again.</div>");
        }
    %>
    
    <div class="col-lg-12">
    <div style="text-align: center;margin: 10px;"><span class="tab_active">Rewards</span></div>
    
    <div class="availablePoint"> My Available Point : <label><%=RewardDAO.countStudentPoint(user_id)%></label></div>
    <br>
    <table id="rewardTable" class="table table-bordered" style="width:100% !important;">
        <thead class="thead-light">
            <tr>
                <th style="text-align: center">Item Name</th>
                <th style="text-align: center">Image</th>
                <th style="text-align: center">Quantity Available</th>
                <th style="text-align: center">Point to redeem</th>
                <th style="text-align: center">Description</th>
                <th style="text-align: center">Status</th>
            </tr>
        </thead>
    </table>
</div>
<div class="modal fade" id="updateReward" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Update Reward</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
             
            
        </div>  

     
    </div>       
</div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src='https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>
<script src='https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js'></script>
<script src='https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js'></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>



<script type="text/javascript">
    $(document).ready(function () {
        if ($('#creation_status').length) {
            $('#creation_status').fadeIn().delay(3000).fadeOut();
        }
        studentID = <%=user.getRespectiveID()%>
        action = 'retrieve';
        table = $('#rewardTable').DataTable({
            'responsive': true,
            "iDisplayLength": 6,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "StudentRetrieveRewardServlet",
                "data": {
                    //"tutorID": tutorID,
                    "studentID": studentID,
                    "action": action
                }
            },
            "columnDefs": [
                {
                    "targets": [0, 1, 2, 3, 4, 5],
                    "data": null,
                    "defaultContent": '',
                    "class": 'details'
                }
            ],
            'columns': [
                {"data": "name"},
                {"data": "image",
                "render": function (data) {
                        
                    return '<img src="data:image/jpeg;base64,' + data + '" class="tableImage" />';
                    
                    

                }  
               },
                {"data": "quantity"},
                {"data": "point"},
                {"data": "description"},
                {"data": "status"}
            ],
            "order": [[0, 'asc']]
        });
         $('#rewardTable tbody').on('click', '.edit', function () {
             rewardID = table.row($(this).parents('tr')).data().id;
             name = table.row($(this).parents('tr')).data().name;
             description = table.row($(this).parents('tr')).data().description;
             quantity = table.row($(this).parents('tr')).data().quantity;
             image = table.row($(this).parents('tr')).data().image;
             point = table.row($(this).parents('tr')).data().point;
             
             $('#updateReward').on('shown.bs.modal', function () {
                 document.getElementById("txtitemname").value = name;
                 document.getElementById("txtdescription").value = description;
                 document.getElementById("txtquantity").value = quantity;
                 document.getElementById("txtpoint").value = point;
                 //document.getElementById("txtimage").value = image;
                 document.getElementById("txtrewarditemid").value = rewardID;
             });
             $("#updateReward").modal('show');
         });
         $('#rewardTable tbody').on('click', '.delete', function () {
             var confirmDelete = confirm('Delete this record?');
             if(confirmDelete){
                rewardID = table.row($(this).parents('tr')).data().id;
                action = 'delete';
                $.ajax({
                   type: 'POST',
                   url: 'RetrieveRewardServlet',
                   dataType: 'JSON',
                   data: {rewardID: rewardID, action: action},
                   success: function (data) {
                       if(data){
                           table.row(this).remove().draw(false);
                       }

                       /*if (data) {
                           lessonModal.cell(rowIndex, columnIndex).data('Absent' + ' <button id="_present" class="btn btn-default _present">Present</button>').draw();
                           lessonAttendanceTable.cell(lesson_row.index(), 2).data(data.attendance).draw();
                       }*/
                   }
               });
             }
             
         });
         $('#updateForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                itemName: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter item name'
                        }
                    }
                },
                quantity: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter quantity'
                        },
                        numeric: {
                            message: 'Please enter valid amount'
                        }
                    }
                },
                point: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter point required to redeem the item'
                        },
                        numeric: {
                            message: 'Please enter valid amount'
                        }
                    }
                },
                image: {
                    validators: {
                        file: {
                            type: 'image/jpeg,image/png,image/jpg',
                            message: 'please choose a valid image'
                        }
                    }
                }
            }
        });
        
    });
</script>