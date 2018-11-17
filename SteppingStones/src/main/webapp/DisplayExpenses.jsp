<%-- 
    Document   : DisplayExpenses
    Created on : 9 Oct, 2018, 6:39:20 PM
    Author     : Riana
--%>

<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css">
<style>
    .details{
        text-align: center;
    }
</style>
<div class="col-lg-10">
    <%        
        String success = (String) request.getAttribute("status");
        if (success != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Update error. Please try again.</div>");
        }
    %>
    <div class="col-lg-12">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Expenses</span></div>
    <table id="expensesTable" class="table table-bordered">
        <thead class="thead-light">
            <tr>
                <th style="text-align: center">Expenses Type</th>
                <th style="text-align: center">Description</th>
                <th style="text-align: center">Spending Amount</th>
                <th style="text-align: center">Date Of Expenditure</th>
                <th style="text-align: center">Action</th>
            </tr>
        </thead>
    </table>
</div>
<div class="modal fade" id="updateExpense" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Update Expenses</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
             <form id="updateForm" method="post" action="UpdateExpensesServlet">
            <div class="modal-body">
               
                <table id="myTable" class="table order-list">
                    <tr>
                        <td>Expenses Type</td>
                        <td>
                            <div class="form-group col-lg-12">
                                <select name="type" class="form-control " id="ddltype" >
                                    <option value="" >Select Type</option>
                                    <option value="0">Bank Expenses</option>
                                    <option value="-1">Cash Box Expenses</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                   
                    <tr>
                        <td>Spending Amount</td>
                        <td>
                            <div class="form-group col-lg-12">
                                <input type="text" name="amount"  class="form-control" id="txtamount"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Date Of Expenditure</td>
                        <td>
                            <div class="form-group col-lg-12">
                                <input type="text" name="paymentdate"  class="form-control paymentdate" id="dppaymentdate"/>
                            </div>
                        </td>
                    </tr>
                    
                     <tr>
                        <td>Description</td>
                        <td>
                            <div class="form-group col-lg-12">
                                <input type="hidden" name="paymentid" class="form-control" id="txtpaymentid" />
                                <textarea name="description"  class="form-control" id="txtdescription" col="50"></textarea>
                            </div>
                        </td>
                    </tr>
                </table>  
            </div>
            <div class="modal-footer spaced-top-small centered">
                <button type="submit" class="btn btn-success" onclick="updateDetails()">Save Changes</button>
             
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

            </div>
            </form>
            
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
        $('#dppaymentdate').datetimepicker({
            format: 'YYYY-MM-DD'
        });
        action = 'retrieve';
        table = $('#expensesTable').DataTable({
            'responsive': true,
            "iDisplayLength": 9,
            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            'ajax': {
                "type": "POST",
                "url": "RetrieveExpensesServlet",
                "data": {
                    //"tutorID": tutorID,
                    //"branchID": branchID,
                    "action": action
                }
            },
            "columnDefs": [
                {
                    "targets": [0, 1, 2, 3],
                    "data": null,
                    "defaultContent": '',
                    "class": 'details'
                },
                {
                    "targets": 4,
                    "data": null,
                    "defaultContent": '<button class="btn btn-default edit">Edit</button> <button class="btn btn-danger delete">Delete</button>',
                    "class": 'details'
                }
            ],
            'columns': [
                {"data": "type"},
                {"data": "description"},
                {"data": "amount"},
                {"data": "paymentdate"}
            ],
            "order": [[0, 'asc']]
        });
         $('#expensesTable tbody').on('click', '.edit', function () {
             expenseID = table.row($(this).parents('tr')).data().id;
             type = table.row($(this).parents('tr')).data().type;
             description = table.row($(this).parents('tr')).data().description;
             amount = table.row($(this).parents('tr')).data().amount;
             paymentdate = table.row($(this).parents('tr')).data().paymentdate;
             
             $('#updateExpense').on('shown.bs.modal', function () {
                 document.getElementById("ddltype").value = (type === "Bank Expenses" ? "0" : "-1");
                 document.getElementById("txtdescription").value = description;
                 document.getElementById("txtamount").value = amount;
                 document.getElementById("dppaymentdate").value = paymentdate;
                 document.getElementById("txtpaymentid").value = expenseID;
             });
             $("#updateExpense").modal('show');
         });
         $('#expensesTable tbody').on('click', '.delete', function () {
             var confirmDelete = confirm('Delete this record?');
             if(confirmDelete){
                expenseID = table.row($(this).parents('tr')).data().id;
                action = 'delete';
                $.ajax({
                   type: 'POST',
                   url: 'RetrieveExpensesServlet',
                   dataType: 'JSON',
                   data: {expenseID: expenseID, action: action},
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
                type: {
                    validators: {
                        notEmpty: {
                            message: 'Please select expenses type'
                        }
                    }
                },
                amount: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter expenses amount'
                        },
                        numeric: {
                            message: 'Please enter valid amount'
                        }
                    }
                },
                paymentdate: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter payment date'
                        },
                        date: {
                            format: 'YYYY-MM-DD',
                            message: 'please enter a valid date'
                        }
                    }
                }
            }
        });
        
    });
</script>