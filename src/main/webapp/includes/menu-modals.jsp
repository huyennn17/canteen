<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Week Selector Modal -->
<div class="modal fade" id="weekSelectorModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Generate Weekly Menu</h4>
            </div>
            <div class="modal-body">
                <form id="weekSelectorForm" action="menu" method="POST" onsubmit="return validateSelectedWeekStart();">
                    <input type="hidden" name="command" value="GENERATE_WEEK">
                    <input type="hidden" name="selectedWeekStart" id="selectedWeekStart">
                    
                    <div class="week-navigation text-center mb-3">
                        <button type="button" class="btn btn-default" onclick="changeWeek(-1)">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </button>
                        <span id="weekDisplay" class="mx-3">
                            <!-- Will be populated by JavaScript -->
                        </span>
                        <button type="button" class="btn btn-default" onclick="changeWeek(1)">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </button>
                    </div>

                    <div class="week-preview">
                        <h5>Menu will be generated for:</h5>
                        <ul id="weekDaysList" class="list-unstyled"></ul>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Generate Menu</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Create/Edit Menu Modal -->
<div class="modal fade" id="menuModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="menuModalTitle">Create New Menu</h4>
            </div>
            <div class="modal-body">
                <form id="menuForm" action="menu" method="POST">
                    <input type="hidden" name="command" id="menuCommand" value="CREATE">
                    <input type="hidden" name="menuId" id="menuId">

                    <div class="form-group">
                        <label>Date:</label>
                        <input type="date" name="menuDate" id="menuDate" class="form-control" required>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Snack:</label>
                                <select name="snackId" class="form-control" required>
                                    <option value="">Select Snack</option>
                                    <c:forEach items="${snacksList}" var="snack">
                                        <option value="${snack.id}">${snack.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Appetizer:</label>
                                <select name="appetizerId" class="form-control" required>
                                    <option value="">Select Appetizer</option>
                                    <c:forEach items="${appetizersList}" var="appetizer">
                                        <option value="${appetizer.id}">${appetizer.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Vegetable:</label>
                                <select name="vegetableId" class="form-control" required>
                                    <option value="">Select Vegetable</option>
                                    <c:forEach items="${vegetablesList}" var="vegetable">
                                        <option value="${vegetable.id}">${vegetable.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Protein:</label>
                                <select name="proteinId" class="form-control" required>
                                    <option value="">Select Protein</option>
                                    <c:forEach items="${proteinsList}" var="protein">
                                        <option value="${protein.id}">${protein.name} (${protein.type})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Carbohydrate:</label>
                                <select name="carbId" class="form-control" required>
                                    <option value="">Select Carbohydrate</option>
                                    <c:forEach items="${carbsList}" var="carb">
                                        <option value="${carb.id}">${carb.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Dessert:</label>
                                <select name="dessertId" class="form-control" required>
                                    <option value="">Select Dessert</option>
                                    <c:forEach items="${dessertsList}" var="dessert">
                                        <option value="${dessert.id}">${dessert.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Save Menu</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Snack Ticket Modal -->
<div class="modal fade" id="snackModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Get Snack Ticket</h4>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/ticket" method="POST">
                    <input type="hidden" name="command" value="GENERATE">
                    <input type="hidden" name="ticketType" value="SNACK">
                    <input type="hidden" name="studentId" value="${sessionScope.student.studentId}">
                    <input type="hidden" name="menuDate" id="snackMenuDate">
                    
                    <div class="form-group">
                        <label>Select Date:</label>
                        <select name="menuId" class="form-control menu-date-select" required>
                            <c:forEach items="${weeklyMenu}" var="menu">
                                <option value="${menu.dailyMenuId}" data-date="${menu.menuDate}">
                                    <fmt:formatDate value="${menu.menuDate}" pattern="EEEE, MMM dd, yyyy"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Generate Ticket</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Lunch Ticket Modal -->
<div class="modal fade" id="lunchModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Get Lunch Ticket</h4>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/ticket" method="POST">
                    <input type="hidden" name="command" value="GENERATE">
                    <input type="hidden" name="ticketType" value="LUNCH">
                    <input type="hidden" name="studentId" value="${sessionScope.student.studentId}">
                    <input type="hidden" name="menuDate" id="lunchMenuDate">
                    
                    <div class="form-group">
                        <label>Select Date:</label>
                        <select name="menuId" class="form-control menu-date-select" required>
                            <c:forEach items="${weeklyMenu}" var="menu">
                                <option value="${menu.dailyMenuId}" data-date="${menu.menuDate}">
                                    <fmt:formatDate value="${menu.menuDate}" pattern="EEEE, MMM dd, yyyy"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Generate Ticket</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
