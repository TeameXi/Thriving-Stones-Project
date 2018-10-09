<%-- 
    Document   : Expenses
    Created on : 9 Oct, 2018, 3:39:25 PM
    Author     : Riana
--%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>

<!------ Include the above in your HEAD tag ---------->
<div class="col-md-10">
    <div class="container">
        <form id="createExpensesForm" method="POST" class="form-horizontal" action="CreateExpensesServlet">
            <table id="myTable" class=" table order-list">
                <thead>
                    <tr>
                        <td>Expenses Type</td>
                        <td>Description</td>
                        <td>Amount</td>
                        <td>Payment Date</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-sm-3">
                            <div class="form-group col-lg-12">
                                <select name="type[0]" class="form-control " >
                                    <option value="" >Select Type</option>
                                    <option value="0">Bank Expenses</option>
                                    <option value="-1">Cash Box Expenses</option>
                                </select>
                            </div>
                        </td>
                        <td class="col-sm-4">
                            <div class="form-group col-lg-12">
                                <input type="text" name="description[0]"  class="form-control"/>
                            </div>
                        </td>
                        <td class="col-sm-2">
                            <div class="form-group col-lg-12">
                                <input type="text" name="amount[0]"  class="form-control"/>
                            </div>
                        </td>
                        <td class="col-sm-2">
                            <div class="form-group col-lg-12">
                                <input type="text" name="paymentdate[0]"  class="form-control paymentdate"/>
                            </div>
                        </td>
                        <td class="col-sm-2"><a class="deleteRow"></a>

                        </td>
                    </tr>

                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <input type="button" class="btn btn-lg btn-block " id="addrow" value="Add Row" />
                        </td>
                        <td colspan="3" style="text-align: left;">
                            <input type="submit" class="btn btn-lg btn-block "  value="Submit" />
                        </td>
                    </tr>
                    <tr>
                    </tr>
                </tfoot>
            </table>
        </form>
    </div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.16.0/jquery.validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script>
    $(document).ready(function () {
        var counter = 1;
        $("#createExpensesForm")

                .on("click", "#addrow", function () {
                    var form = $("#createExpensesForm");
                    form.validate({highlight: function (element) {
                            $(element).closest('.form-group').addClass('has-error');
                        },
                        unhighlight: function (element) {
                            $(element).closest('.form-group').removeClass('has-error');
                        },
                        errorElement: 'span',
                        errorClass: 'help-block',
                        errorPlacement: function (error, element) {
                            if (element.parent('.input-group').length) {
                                error.insertAfter(element.parent());
                            } else {
                                error.insertAfter(element);
                            }
                        },
                        rules: {
                            "type[0]": {
                                required: true
                            },
                            "amount[0]": {
                                required: true,
                                range: [0, 10000000000000000000000000000]
                            },
                            "paymentdate[0]": {
                                date: {
                                    format: "YYYY/MM/DD"
                                },
                                required: true
                            }
                        },
                        messages: {
                            "type[0]": {
                                required: "please select expenses type"
                            },
                            "amount[0]": {
                                required: "Please enter expenses amount",
                                range: "Please enter valid amount"
                            },
                            "paymentdate[0]": {
                                required: "Please enter payment date",
                                date: "Please enter a valid date"
                            }
                        }
                    });
                    if (form.valid() === true) {
                        var newRow = $("<tr>");
                        var cols = "";

                        cols += '<td><div class="form-group col-lg-12"><select name="type[' + counter + ']" class="form-control"><option value="">Select Type</option><option value="0">Bank Expenses</option><option value="-1">Cash Box Expenses</option></select></div></td>';


                        cols += '<td><div class="form-group col-lg-12"><input type="text" class="form-control" name="description[' + counter + ']"/></div></td>';
                        cols += '<td><div class="form-group col-lg-12"><input type="text" class="form-control" name="amount[' + counter + ']"/></div></td>';

                        cols += '<td><div class="form-group col-lg-12"><input type="text" class="form-control paymentdate" name="paymentdate[' + counter + ']"/></div></td>';
                        cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td>';
                        newRow.append(cols);
                        $("table.order-list").append(newRow);
                        $('.paymentdate').datetimepicker({
                            format: 'YYYY-MM-DD'
                        });
                        $('select[name="type[' + counter + ']"]').rules("add", {
                            required: true,
                            messages: {
                                required: "Please select expenses type"
                            }
                        });
                        $('input[name="amount[' + counter + ']"]').rules("add", {
                            required: true,
                            range: [0, 10000000000000000000000000000],
                            messages: {
                                required: "Please enter expenses amount",
                                range: "Please enter valid amount"
                            }
                        });
                        $('input[name="paymentdate[' + counter + ']"]').rules("add", {
                            required: true,
                            date: {
                                format: "YYYY/MM/DD"
                            },
                            messages: {
                                required: "Please enter payment date",
                                date: "Please enter valid date"
                            }
                        });
                        counter++;
                    }
                });



        $("table.order-list").on("click", ".ibtnDel", function (event) {
            $(this).closest("tr").remove();
            counter -= 1;

        });
        $('.paymentdate').datetimepicker({
            format: 'YYYY-MM-DD'
        });

    });




    function calculateRow(row) {
        var price = +row.find('input[name^="price"]').val();

    }

    function calculateGrandTotal() {
        var grandTotal = 0;
        $("table.order-list").find('input[name^="price"]').each(function () {
            grandTotal += +$(this).val();
        });
        $("#grandtotal").text(grandTotal.toFixed(2));
    }
</script>