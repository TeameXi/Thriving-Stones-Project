<!DOCTYPE html>
<html lang="en">
<head>
	<title>Menu</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/util.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
</head>
<body>
	
<div class="limiter">
	<div class="signIn"><img style="max-width: 100%;" src="${pageContext.request.contextPath}/styling/img/menu.png"></img></div>
        <div class="container-login100">
            <section class="featured-content">
                <ul class="featured-list">
                    <li class="featured-list_item">
                        <a href="CreateTutor.jsp" class="featured-list_link">
                            <img src="${pageContext.request.contextPath}/styling/img/icons/user.png" class="featured-list_img" alt ="Admin">
                            <h3 class="featured-list_title">Admin Management</h3>
                        </a>
                    </li>
                    <li class="featured-list_item">
                        <a href="scheduling" class="featured-list_link">
                            <img class="featured-list_img" src="${pageContext.request.contextPath}/styling/img/icons/schedule.png" alt="Schedule">
                            <h3 class="featured-list_title">Scheduling Classes</h3>
                        </a>
                    </li>
                    <li class="featured-list_item">
                        <a href="finanical_reports" class="featured-list_link">
                            <img class="featured-list_img" src="${pageContext.request.contextPath}/styling/img/icons/report.png" alt="Reports">
                            <h3 class="featured-list_title">Financial Reports</h3>
                        </a>
                    </li>
                    <li class="featured-list_item">
                        <a href="payment.html" class="featured-list_link">
                            <img class="featured-list_img" src="${pageContext.request.contextPath}/styling/img/icons/payment.png" alt="Payment">
                            <h3 class="featured-list_title">Payment Handling</h3>
                        </a>
                    </li>
                    <li class="featured-list_item">
                        <a href="students.html" class="featured-list_link">
                            <img class="featured-list_img" src="${pageContext.request.contextPath}/styling/img/icons/student.png" alt="Student">
                            <h3 class="featured-list_title">Students Data</h3>
                        </a>
                    </li>
                    <li class="featured-list_item">
                        <a href="#" class="featured-list_link">
                            <img class="featured-list_img" src="${pageContext.request.contextPath}/styling/img/icons/module.png" alt="Modules Planning">
                            <h3 class="featured-list_title">Module Planning</h3>
                        </a>
                    </li>

                </ul>
            </section>	
        </div>
</div>
	
</body>
</html>
