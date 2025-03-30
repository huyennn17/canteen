<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment - Canteen Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        .payment-container { padding: 20px; }
        .ticket-list { margin-bottom: 20px; }
        .payment-summary { background: #f9f9f9; padding: 15px; border-radius: 4px; }
        .alert { margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container payment-container">
        <!-- Add notification section -->
        <c:if test="${not empty sessionScope.PAYMENT_MESSAGE}">
            <div class="alert alert-${sessionScope.PAYMENT_STATUS} alert-dismissible fade in">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${sessionScope.PAYMENT_MESSAGE}
            </div>
            <%-- Clear the message after displaying --%>
            <% session.removeAttribute("PAYMENT_MESSAGE");
               session.removeAttribute("PAYMENT_STATUS"); %>
        </c:if>

        <h2>Payment</h2>
        
        <form id="paymentForm" action="payment" method="POST">
            <input type="hidden" name="command" value="PROCESS">
            
            <div class="ticket-list">
                <h3>Student Tickets</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${studentTickets}" var="ticket">
                            <tr>
                                <td>${ticket.studentName}</td>
                                <td><fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/></td>
                                <td>${ticket.ticketType}</td>
                                <c:if test="${ticket.paid}">
							         <td style="color: green;">Paid</td>
							     </c:if>
							     <c:if test="${!ticket.paid}">
							         <td style="color: red;">Unpaid</td>
							     </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
           
        </form>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            // Auto hide alert after 5 seconds
            setTimeout(function() {
                $('.alert').fadeOut('slow');
            }, 5000);
        });
    </script>
</body>
</html>
