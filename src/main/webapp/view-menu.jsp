<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Weekly Menu - Canteen Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu-styles.css">
</head>
<body>
    <div class="menu-container">
        <!-- Notification Alert -->
        <c:if test="${not empty sessionScope.NOTIFICATION_MESSAGE}">
            <div class="alert alert-${sessionScope.NOTIFICATION_TYPE} alert-dismissible fade in">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${sessionScope.NOTIFICATION_MESSAGE}
            </div>
            <% session.removeAttribute("NOTIFICATION_MESSAGE");
               session.removeAttribute("NOTIFICATION_TYPE"); %>
        </c:if>

        <!-- Header Section -->
        <div class="menu-header">
            <h2>Weekly Menu</h2>
            
            <!-- Week Navigation -->
            <div class="week-navigation">
                <button type="button" class="btn btn-default" onclick="changeDisplayWeek(-1)">
                    <span class="glyphicon glyphicon-chevron-left"></span> Previous Week
                </button>
                <span id="currentWeekDisplay" class="week-display mx-3">
                    <!-- Remove the fmt:formatDate tags and let JavaScript handle the display -->
                    <!-- Will be populated by JavaScript -->
                </span>
                <button type="button" class="btn btn-default" onclick="changeDisplayWeek(1)">
                    Next Week <span class="glyphicon glyphicon-chevron-right"></span>
                </button>
                <input type="hidden" id="currentWeekStart" name="currentWeekStart" 
                       value="${currentWeekStartStr}">
            </div>

            <c:if test="${userType eq 'admin'}">
                <div class="admin-controls">
                    <button type="button" class="btn btn-success" onclick="showWeekSelector()">
                        <span class="glyphicon glyphicon-calendar"></span> Generate Weekly Menu
                    </button>
                    <button type="button" class="btn btn-primary" onclick="createMenu()">
                        <span class="glyphicon glyphicon-plus"></span> Add Single Menu
                    </button>
                </div>
            </c:if>
        </div>

        <!-- Menu Grid -->
        <jsp:include page="/includes/menu-grid.jsp" />

        <!-- Ticket Section -->
        <c:if test="${userType eq 'student'}">
            <div class="ticket-section">
                <h3>Get Your Meal Ticket</h3>
                <button class="btn btn-primary ticket-button" data-toggle="modal" data-target="#snackModal">
                    <span class="glyphicon glyphicon-tag"></span> Get Snack Ticket
                </button>
                <button class="btn btn-success ticket-button" data-toggle="modal" data-target="#lunchModal">
                    <span class="glyphicon glyphicon-tag"></span> Get Lunch Ticket
                </button>
            </div>
        </c:if>

        <!-- Back to Dashboard -->
        <div class="text-center" style="margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-default">
                <span class="glyphicon glyphicon-chevron-left"></span> Back to Dashboard
            </a>
        </div>
    </div>

    <!-- Include Modals -->
    <jsp:include page="/includes/menu-modals.jsp" />

    <!-- Scripts -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/menu-functions.js"></script>
    
    <!-- Initialize page -->
    <script>
        $(document).ready(function() {
       
            // Initialize modals
            $('.modal').modal({
                show: false,
                backdrop: 'static'
            });
            
            // Check if we need to show modal after verification
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('command') === 'CHECK_WEEK' && 
                !document.querySelector('.alert-warning')) {
                $('#weekSelectorModal').modal('show');
            }
        
        });
    </script>
</body>
</html>