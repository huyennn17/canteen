<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Price Configuration - Canteen Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Meal Price Configuration</h2>
        
        <c:if test="${not empty sessionScope.PRICE_MESSAGE}">
            <div class="alert alert-${sessionScope.PRICE_STATUS} alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${sessionScope.PRICE_MESSAGE}
            </div>
            <% session.removeAttribute("PRICE_MESSAGE");
               session.removeAttribute("PRICE_STATUS"); %>
        </c:if>

        <div class="text-left" style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-default">
                <span class="glyphicon glyphicon-chevron-left"></span> Back to Dashboard
            </a>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Current Prices</div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Meal Type</th>
                            <th>Price</th>
                            <th>Last Updated</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${currentPrices}" var="price">
                            <tr>
                                <td>${price.mealType}</td>
                                <td>$<fmt:formatNumber value="${price.price}" pattern="#,##0.00"/></td>
                                <td><fmt:formatDate value="${price.effectiveDateTime}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading">Set New Price</div>
            <div class="panel-body">
                <form action="${pageContext.request.contextPath}/prices" method="POST" class="form-horizontal">
                    <input type="hidden" name="command" value="UPDATE">
                    
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Meal Type:</label>
                        <div class="col-sm-4">
                            <select name="mealType" class="form-control" required>
                                <option value="LUNCH">Lunch</option>
                                <option value="SNACK">Snack</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">New Price:</label>
                        <div class="col-sm-4">
                            <input type="number" name="price" step="0.01" min="0" class="form-control" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-4">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-save"></span> Update Price Now
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Price History</div>
            <div class="panel-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#lunchHistory">Lunch History</a></li>
                    <li><a data-toggle="tab" href="#snackHistory">Snack History</a></li>
                </ul>

                <div class="tab-content" style="padding-top: 20px;">
                    <div id="lunchHistory" class="tab-pane fade in active">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Price</th>
                                    <th>Effective From</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${priceHistory.LUNCH}" var="price">
                                    <tr>
                                        <td>$<fmt:formatNumber value="${price.price}" pattern="#,##0.00"/></td>
                                        <td><fmt:formatDate value="${price.effectiveDateTime}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div id="snackHistory" class="tab-pane fade">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Price</th>
                                    <th>Effective From</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${priceHistory.SNACK}" var="price">
                                    <tr>
                                        <td>$<fmt:formatNumber value="${price.price}" pattern="#,##0.00"/></td>
                                        <td><fmt:formatDate value="${price.effectiveDateTime}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>
