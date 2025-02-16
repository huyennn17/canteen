<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Menu - Canteen Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        .container { padding-top: 20px; padding-bottom: 20px; }
        .form-group { margin-bottom: 20px; }
        .btn { margin-right: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2>Edit Menu for <fmt:formatDate value="${MENU_TO_EDIT.menuDate}" pattern="EEEE, MMM dd, yyyy"/></h2>
                <hr>
                
                <form action="${pageContext.request.contextPath}/menu" method="POST" class="form-horizontal">
                    <input type="hidden" name="command" value="UPDATE">
                    <input type="hidden" name="menuId" value="${MENU_TO_EDIT.dailyMenuId}">
                    
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Date:</label>
                        <div class="col-sm-4">
                            <input type="date" name="menuDate" value="${MENU_TO_EDIT.menuDate}" class="form-control" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Morning Snack:</label>
                        <div class="col-sm-4">
                            <select name="snackId" class="form-control" required>
                                <c:forEach var="snack" items="${snacksList}">
                                    <option value="${snack.id}" ${MENU_TO_EDIT.snack.id eq snack.id ? 'selected' : ''}>
                                        ${snack.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Appetizer:</label>
                        <div class="col-sm-4">
                            <select name="appetizerId" class="form-control" required>
                                <c:forEach var="appetizer" items="${appetizersList}">
                                    <option value="${appetizer.id}" ${MENU_TO_EDIT.appetizer.id eq appetizer.id ? 'selected' : ''}>
                                        ${appetizer.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Vegetable:</label>
                        <div class="col-sm-4">
                            <select name="vegetableId" class="form-control" required>
                                <c:forEach var="vegetable" items="${vegetablesList}">
                                    <option value="${vegetable.id}" ${MENU_TO_EDIT.vegetable.id eq vegetable.id ? 'selected' : ''}>
                                        ${vegetable.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Protein:</label>
                        <div class="col-sm-4">
                            <select name="proteinId" class="form-control" required>
                                <c:forEach var="protein" items="${proteinsList}">
                                    <option value="${protein.id}" ${MENU_TO_EDIT.protein.id eq protein.id ? 'selected' : ''}>
                                        ${protein.name} (${protein.type})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Carbohydrate:</label>
                        <div class="col-sm-4">
                            <select name="carbId" class="form-control" required>
                                <c:forEach var="carb" items="${carbsList}">
                                    <option value="${carb.id}" ${MENU_TO_EDIT.carb.id eq carb.id ? 'selected' : ''}>
                                        ${carb.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">Dessert:</label>
                        <div class="col-sm-4">
                            <select name="dessertId" class="form-control" required>
                                <c:forEach var="dessert" items="${dessertsList}">
                                    <option value="${dessert.id}" ${MENU_TO_EDIT.dessert.id eq dessert.id ? 'selected' : ''}>
                                        ${dessert.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-save"></span> Save Changes
                            </button>
                            <a href="${pageContext.request.contextPath}/menu?command=VIEW" class="btn btn-default">
                                <span class="glyphicon glyphicon-remove"></span> Cancel
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>
