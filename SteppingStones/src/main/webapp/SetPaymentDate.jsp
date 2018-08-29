<%-- 
    Document   : SetPaymentDate
    Created on : Aug 29, 2018, 2:09:36 AM
    Author     : Xin
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <form action="SetPaymentDateServlet">
        Payment Date: <input type="text" name="paymentDate"/><br/>
        <br/>
        Class: <select name="classID">
            <option value="">-Please select a class</option>
            <%
                ArrayList<Class> classes = ClassDAO.listAllClasses(1);
                for(Class c: classes) {
                    out.println("<option value='"+ c.getClassID() +"'>" + c.getClassDay() + " " + c.getClassTime() + "</option>");
                }
            %>
            </select>
            <input type="submit" value="Set">
    </form>
</html>
