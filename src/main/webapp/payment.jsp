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
                <h3>Unpaid Tickets</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="selectAll"></th>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${unpaidTickets}" var="ticket">
                            <tr>
                                <td>
                                    <input type="checkbox" name="ticketIds" value="${ticket.id}" 
                                           data-price="${ticket.price}" class="ticket-checkbox">
                                </td>
                                <td><fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/></td>
                                <td>${ticket.ticketType}</td>
                                <td>$<fmt:formatNumber value="${ticket.price}" pattern="#,##0.00"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="payment-summary">
                <h4>Payment Summary</h4>
                <p>Total Amount: $<span id="totalAmount">0.00</span></p>
                <button type="submit" class="btn btn-primary" id="payButton" disabled>
                    Pay Selected Tickets
                </button>
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

            function updateTotal() {
                var total = 0;
                $('.ticket-checkbox:checked').each(function() {
                    total += parseFloat($(this).data('price'));
                });
                $('#totalAmount').text(total.toFixed(2));
                $('#payButton').prop('disabled', total === 0);
            }

            $('#selectAll').change(function() {
                $('.ticket-checkbox').prop('checked', $(this).is(':checked'));
                updateTotal();
            });

            $('.ticket-checkbox').change(function() {
                updateTotal();
            });
        });
    </script>
</body>
</html>
