<%-- 
    Document   : CreateSubject_Combine
    Created on : Oct 27, 2018, 9:38:37 AM
    Author     : MOH MOH SAN
--%>

<%@page import="entity.Subject"%>
<%@page import="model.SubjectDAO"%>
<%@page import="entity.Level"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>

</style>

<div class="col-md-10">
    <div style="text-align: center;margin: 20px;">Add Subject - <a href="CreateSubject.jsp">Individual </a> / <span class="tab_active">Combine Class</span></div>
   
    <div class="row">
        <form id="createCombinedSubjectForm" method="POST" class="form-horizontal" action="CreateCombineSubjectServlet">
            <table id="myTable" class=" table order-list">
                <thead>
                    <tr>
                        <td>Subject</td>
                        <td>Combined Level</td>
                        <td>Course Fees</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-sm-3">
                            <div class="form-group col-lg-12">
                                <input type='hidden' name='branch' value='<%=branch_id%>'>
                                <%
                                    SubjectDAO subjectDao = new SubjectDAO();
                                    ArrayList<Subject> subjectLists = subjectDao.retrieveAllSubjectsWithId();
                                %>
                                <select name="subject[0]" class="form-control " >
                                    <%  
                                        String subjectDropDownFormat = "";
                                        for (Subject subject : subjectLists) {
                                            subjectDropDownFormat += "<option value='" + subject.getSubjectId()+ "'>" + subject.getSubjectName()+ "</option>";
                                        }
                                        out.println(subjectDropDownFormat);
                                    %>
                                </select>
                            </div>
                        </td>
                     
                        <td class="col-sm-3">
                            <div class="form-group col-lg-12">
                               
                                <%
                                    LevelDAO lvlDao = new LevelDAO();
                                    ArrayList<Level> lvlLists = lvlDao.retrieveAllLevelLists();
                                %>
                                <select id="lvlSelect" multiple="multiple" name="level[0]" class="form-control" >
                                    <%  
                                        String lvlDropDownFormat = "";
                                        for (Level lvl : lvlLists) {
                                            lvlDropDownFormat += "<option value='" + lvl.getLevel_id() + "'>" + lvl.getLevelName() + "</option>";
                                        }
                                        out.println(lvlDropDownFormat);
                                    %>
                                </select>

                                
                            </div>
                        </td>
                        <td class="col-sm-3">
                            <div class="form-group col-lg-12">
                                <input type="text" name="courseFee[0]"  class="form-control"/>
                            </div>
                        </td>
                       
                        <td class="col-sm-2"><a class="deleteRow"></a></td>
                    </tr>

                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="1" style="text-align: left;">
                            <input type="button" class="btn btn-block btn1 " id="addMore" value="Add More" />
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

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css" rel="stylesheet">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.16.0/jquery.validate.js"></script>

<script>
    $(function () {
        $('#lvlSelect').multiselect();
        
        var counter = 1;
        $("#createCombinedSubjectForm")

                .on("click", "#addMore", function () {
                    var form = $("#createCombinedSubjectForm");
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
                            "level[0]":{
                                required:true,
                                minlength:2
                            },
                            "courseFee[0]": {
                                required: true,
                                range: [0, 100000]
                            }
                          
                        },
                        messages: {
                            "level[0]": {
                                required: "Please select at least two",
                                minlength:"Please select at least two"
                               
                            },
                            "courseFee[0]": {
                                required: "Please enter course fees",
                                range: "Please enter valid amount"
                            }
                        }
                    });
                    if (form.valid() === true) {
                        var newRow = $("<tr>");
                        var cols = "";
                        cols += "<td><div class='form-group col-lg-12'><select name='subject[" + counter + "]' class='form-control'><%=subjectDropDownFormat%></select></div></td>";
                        cols += "<td><div class='form-group col-lg-12'><select multiple='multiple' id='lvlSelect' name='level[" + counter + "]' class='form-control lvlSelector'><%=lvlDropDownFormat%></select></div></td>";
                        cols += '<td><div class="form-group col-lg-12"><input type="text" class="form-control" name="courseFee[' + counter + ']"/></div></td>';
                        cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td>';
                        newRow.append(cols);
                        $("table.order-list").append(newRow);
                        $('.lvlSelector').multiselect();
                        $('select[name="level[' + counter + ']"]').rules("add", {
                            required: true,
                            minlength:2,
                            messages: {
                                required: "Please select at least two",
                                minlength:"Please select at least two"
                            }
                        });
                        $('input[name="courseFee[' + counter + ']"]').rules("add", {
                            required: true,
                            range: [0, 100000],
                            messages: {
                                required: "Please enter course fees",
                                range: "Please enter valid amount"
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
