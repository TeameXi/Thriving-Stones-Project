
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>

</style>
<!------ Include the above in your HEAD tag ---------->
<div class="col-md-10">
    <div style="text-align: center;margin: 10px;"><span class="tab_active" style="font-size: 14px">Create Reward</span></div><br/>
    
    <div class="row">
        <form id="createRewardForm" method="post" class="form-horizontal" action="CreateRewardServlet" enctype="multipart/form-data">
            <table id="myTable" class=" table order-list">
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Quantity</td>
                        <td>Point to Redeem</td>
                        <td>Image</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-sm-3">
                            <div class="form-group col-lg-12">
                                <input type="text" name="itemName[0]" class="form-control"/>
                            </div>
                        </td>
                     
                        <td class="col-sm-2">
                            <div class="form-group col-lg-12">
                                <textarea class="form-control" name="description[0]"   cols="50"></textarea>
                            </div>
                        </td>
                        <td class="col-sm-2">
                            <div class="form-group col-lg-12">
                                <input type="number" name="quantity[0]"  class="form-control"/>
                            </div>
                        </td>
                        <td class="col-sm-2">
                            <div class="form-group col-lg-12">
                                <input type="number" name="point[0]"  class="form-control"/>
                            </div>
                        </td>
                        <td class="col-sm-4">
                            <div class="form-group col-lg-12">
                                <input type="file" name="image[0]" class="form-control-file" accept=".png, .jpg">
                            </div>
                        </td>
                        
                        <td class="col-sm-2"><a class="deleteRow"></a></td>
                    </tr>

                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="1" style="text-align: left;">
                            <input type="button" class="btn btn-block btn1 " id="addrow" value="Add Row" />
                        </td>
                        <td colspan="1" style="text-align: left;">
                            <input type="submit" class="btn btn-block btn2 "  value="Submit" />
                        </td>
                        <td colspan="2"></td>
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
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script>
    $(document).ready(function () {
        var counter = 1;
        $("#createRewardForm")

                .on("click", "#addrow", function () {
                    var form = $("#createRewardForm");
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
                            "itemName[0]": {
                                required: true
                            },
                            "quantity[0]": {
                                required: true,
                                range: [0, 10000000000000000000000000000]
                            },
                            "image[0]": {
                                accept: "image/*"
                            },
                            "point[0]":{
                                required: true,
                                range: [0, 1000000000000000000000000000000]
                            }
                        },
                        messages: {
                            "itemName[0]": {
                                required: "please enter item name"
                            },
                            "quantity[0]": {
                                required: "Please enter quantity available",
                                range: "Please enter valid amount"
                            },
                            "image[0]": {
                                accept: "Please select valid image"
                            },
                            "point[0]":{
                                required: "Please enter point required to redeem this item",
                                range: "Please enter valid amount"
                            }
                        }
                    });
                    if (form.valid() === true) {
                        var newRow = $("<tr>");
                        var cols = "";

                        cols += '<td><div class="form-group col-lg-12"><input type="text" name="itemName['+ counter+']" class="form-control"/></div></td>';
                        cols += '<td><div class="form-group col-lg-12"><textarea class="form-control" name="description['+ counter +']"   cols="50"></textarea></div></td>';
                        cols += '<td><div class="form-group col-lg-12"><input type="number" name="quantity['+ counter+']"  class="form-control"/></div></td>';
                        cols += '<td><div class="form-group col-lg-12"><input type="number" name="point['+ counter+']"  class="form-control"/></div></td>';
                        cols += '<td><div class="form-group col-lg-12"><input type="file" name="image['+ counter +']" class="form-control-file" accept=".png, .jpg"></div></td>';
                        cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td>';
                        newRow.append(cols);
                        $("table.order-list").append(newRow);
                        
                        $('input[name="itemName[' + counter + ']"]').rules("add", {
                            required: true,
                            messages: {
                                required: "Please enter item name"
                            }
                        });
                        $('input[name="quantity[' + counter + ']"]').rules("add", {
                            required: true,
                            range: [0, 10000000000000000000000000000],
                            messages: {
                                required: "Please enter quantity",
                                range: "Please enter valid amount"
                            }
                        });
                        $('input[name="point[' + counter + ']"]').rules("add", {
                            required: true,
                            range: [0, 10000000000000000000000000000],
                            messages: {
                                required: "Please enter point required to redeem this item",
                                range: "Please enter valid amount"
                            }
                        });
                        $('input[name="image[' + counter + ']"]').rules("add", {
                            accept: "image/*",
                            messages: {
                                accept: "Please select valid image"
                            }
                        });
                        counter++;
                    }
                });



        $("table.order-list").on("click", ".ibtnDel", function (event) {
            $(this).closest("tr").remove();
            counter -= 1;

        });

    });


</script>