<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session == null || (session.getAttribute("student") == null && session.getAttribute("admin") == null)) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String userType = (String) session.getAttribute("userType");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Canteen Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            padding-bottom: 30px;
        }
        .navbar {
            background-color: #337ab7;
            border: none;
            border-radius: 0;
            margin-bottom: 30px;
        }
        .navbar-brand {
            color: white !important;
            font-weight: bold;
        }
        .navbar-nav > li > a {
            color: white !important;
            padding: 15px 20px;
        }
        .navbar-nav > li > a:hover {
            background-color: #286090 !important;
        }
        .navbar-nav > .active > a {
            background-color: #286090 !important;
        }
        .dashboard-container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }
        .header h2 {
            color: #333;
            margin: 0;
            font-size: 24px;
        }
        .user-info {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 6px;
            margin-bottom: 30px;
        }
        .user-info p {
            margin-bottom: 10px;
            color: #555;
        }
        .user-info strong {
            color: #333;
        }
        .footer-actions {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .welcome-icon {
            color: #337ab7;
            margin-right: 10px;
        }
        .navbar-right {
            margin-right: 15px;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Canteen Management</a>
            </div>

            <div class="collapse navbar-collapse" id="navbar">
                <% if (userType.equals("admin")) { %>
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/students?command=LIST">
                            <span class="glyphicon glyphicon-user"></span> Manage Students
                        </a></li>
                        <li>
                            <a href="#" onclick="openMenuView()">
                                <span class="glyphicon glyphicon-cutlery"></span> View Menu
                            </a>
                        </li>
                        <li>
                        	<a href="${pageContext.request.contextPath}/prices?command=VIEW">
                            	<span class="glyphicon glyphicon-cog"></span> Configure Prices
                        	</a>
                        </li>
                        <li>
                        	<a href="${pageContext.request.contextPath}/students?command=VIEW-STUDENT-TICKETS">
                            	<span class="glyphicon glyphicon-signal"></span> Student Tickets
                        	</a>
                        </li>
                    </ul>
                <% } else { %>
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="#" onclick="openMenuView()">
                                <span class="glyphicon glyphicon-cutlery"></span> View Menu
                            </a>
                        </li>
                        <li>
	                        <a href="${pageContext.request.contextPath}/payment?command=VIEW">
	                            <span class="glyphicon glyphicon-credit-card"></span> My Payments
	                        </a>
                        </li>
                        <li>
	                        <a href="${pageContext.request.contextPath}/payment?command=VIEW-PAID-TICKET">
	                            <span class="glyphicon glyphicon-credit-card"></span> My Tickets
	                        </a>
                        </li>
                    </ul>
                <% } %>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${pageContext.request.contextPath}/logout">
                        <span class="glyphicon glyphicon-log-out"></span> Logout
                    </a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="dashboard-container">
        <div class="header">
            <% if (userType.equals("admin")) { %>
                <h2><span class="glyphicon glyphicon-user welcome-icon"></span>Welcome, Administrator</h2>
            <% } else { %>
                <h2><span class="glyphicon glyphicon-user welcome-icon"></span>Welcome, ${student.firstName} ${student.lastName}</h2>
            <% } %>
        </div>

        <div class="user-info">
            <% if (userType.equals("admin")) { %>
                <p><strong><span class="glyphicon glyphicon-envelope"></span> Email:</strong> ${admin.adminEmail}</p>
                <p><strong><span class="glyphicon glyphicon-briefcase"></span> Role:</strong> Administrator</p>
            <% } else { %>
                <p><strong><span class="glyphicon glyphicon-envelope"></span> Email:</strong> ${student.email}</p>
                <p><strong><span class="glyphicon glyphicon-education"></span> Class:</strong> ${student.studentClass}</p>
            <% } %>
        </div>
    </div>

    <script>
        function getThisWeekMonday() {
            let d = new Date();
            while (d.getDay() !== 1) { // 1 = Monday
                d.setDate(d.getDate() - 1);
            }
            return d;
        }

        function openMenuView() {
            const thisMonday = getThisWeekMonday().toISOString().split('T')[0];
            window.location.href = '${pageContext.request.contextPath}/menu?command=VIEW&weekStart=' + thisMonday;
        }
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>