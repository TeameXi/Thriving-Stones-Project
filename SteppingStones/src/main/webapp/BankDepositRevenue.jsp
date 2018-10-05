<%@page import="entity.Level"%>
<%@page import="model.LevelDAO"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    .col-sm-1 {
        width: 2% !important;
    }
</style>
<div class="col-md-10"> 
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Update Bank Deposit Revenue </span> / <a href=".jsp">Update Bank Expenses</a></h5></div>
    <%        
        String status = (String) request.getParameter("status");
        if (status != null) {
            out.println("<div class='row'><div class='col-md-3'></div><div id='errorMsg' class='alert alert-success col-md-3'><strong>"+status+"</strong></div></div>");
        }
        
        String errorMsg = (String) request.getParameter("errorMsg");
        if (errorMsg != null) {
            out.println("<div class='row'><div class='col-md-3'></div><div id='errorMsg' class='alert alert-danger col-md-3'><strong>"+errorMsg+"</strong></div></div>");
        }
    %>
    <form id="AddBankDepositForm" method="POST" class="form-horizontal" action="UpdateBankDepositServlet">
     
<!--        <div class="form-group">
            <label class="col-lg-4 control-label">Tuition Center's Bank Account</label>  
            <div class="col-lg-3 inputGroupContainer">
                <div class="input-group">
                    <span class="input-group-addon"><i class="zmdi zmdi-balance-wallet"></i></span>
                    <input name="bankAccountNum" placeholder="Account Number" class="form-control"  type="text">
                </div>
            </div>
        </div>-->
        </br>
        <div id="bankDeposit"></div>
        <div class="row">
            <div class="col-sm-2 nopadding">
                <div class="form-group">
                        <select class="form-control" id="type" name="type[]">
                            <option value="">Cheque/Bank Transfer</option>
                            <option value="Cheque">Cheque</option>
                            <option value="Bank Transfer">Bank Transfer</option>
                        </select>
                </div>
            </div>
            <div class="col-sm-1"></div>

            <div class="col-sm-2 nopadding">
                <div class="form-group">
                    <input type="text" class="form-control payment_date" id="date" name="date[]" value="" placeholder="Payment Date">
                </div>
            </div>
            <div class="col-sm-1"></div>

            <div class="col-sm-2 nopadding">
                <div class="form-group">
                    <input type="text" class="form-control" id="from" name="from[]" value="" placeholder="From">
                </div>
            </div>
            <div class="col-sm-1"></div>

            <div class="col-sm-2 nopadding">
                <div class="form-group">
                    <input type="text" class="form-control" id="Degree" name="amount[]" value="" placeholder="Amount ($)">
                </div>
            </div>  

            <div class="input-group-btn">
                <button class="btn btn-success" type="button"  onclick="add_fields();"> <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> </button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-7 col-lg-offset-2">
                <button type="submit" class="btn btn-primary center-block" name="insert">Update</button>
            </div>
        </div>
    </form>
</div>

</div>
</div>

<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

<script>
var room = 1;
function add_fields() {
    room++;
    var objTo = document.getElementById('bankDeposit')
    var divtest = document.createElement("div");
	divtest.setAttribute("class", "form-group remove"+room);
	var rdiv = 'remove'+room;
        divtest.innerHTML = '<div class="col-sm-2 nopadding"><div class="form-group"><select class="form-control" id="type" name="type[]"><option value="">Cheque/Bank Transfer</option><option value="Cheque">Cheque</option><option value="Bank Transfer">Bank Transfer</option></select></div></div><div class="col-sm-1"></div>\n\
                <div class="col-sm-2 nopadding"><div class="form-group"><input type="text" class="form-control payment_date" id="date" name="date[]" value="" placeholder="Payment Date"></div></div><div class="col-sm-1"></div>\n\
                <div class="col-sm-2 nopadding"><div class="form-group"> <input type="text" class="form-control" id="from" name="from[]" value="" placeholder="From"></div></div><div class="col-sm-1"></div>\n\
                <div class="col-sm-2 nopadding"><div class="form-group"> <input type="text" class="form-control" id="amount" name="amount[]" value="" placeholder="Amount ($)"></div></div> \n\
                <div class="input-group-btn"><button class="btn btn-danger" type="button" onclick="remove_added_fields('+ room +');"> <span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button></div>';
    
    objTo.appendChild(divtest);
}

function remove_added_fields(rid) {
    $('.remove'+rid).remove();
}

$(function () {
    if($('#errorMsg').length){
        $('#errorMsg').fadeIn().delay(2000).fadeOut();
    }
});

//$(function () {
//    $('.payment_date').datetimepicker({
//        format: 'YYYY-MM-DD'
//    });
//    
//    $('#datetimePicker').datetimepicker();
//    
//    $('#AddBankDepositForm').bootstrapValidator({
//        feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
//            validating: 'glyphicon glyphicon-refresh'
//        },
//        fields: {
//            'from[]': {
//                validators: {
//                    notEmpty: {
//                        message: 'Please enter value'
//                    }
//                }
//            },
//            'amount[]': {
//                validators: {
//                    notEmpty: {
//                        message: 'Please enter amount'
//                    },
//                    numeric: {
//                        message: 'Please enter valid amount'
//                    }
//                }
//            },
//            'type[]': {
//                validators: {
//                    notEmpty: {
//                        message: 'Please choose one option'
//                    }
//                }
//            },
//            'date[]': {
//                validators: {
//                    notEmpty: {
//                        message: 'Please enter date'
//                    },
//                    date: {
//                        format: 'YYYY-MM-DD',
//                        message: 'The value is not a valid date'
//                    }
//                }
//            }
//        }
//    });
//});
    
</script>