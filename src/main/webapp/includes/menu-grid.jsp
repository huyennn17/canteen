<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="menu-cards-wrapper">
    <c:forEach items="${weeklyMenu}" var="menu">
        <div class="menu-card ${empty menu.snack ? 'empty-menu' : ''}">
            <div class="menu-day">
                <fmt:formatDate value="${menu.menuDate}" pattern="EEEE" />
                <br>
                <small>
                    <fmt:formatDate value="${menu.menuDate}" pattern="MMM dd, yyyy" />
                </small>
                <c:if test="${userType eq 'admin'}">
                    <div style="margin-top: 10px;">
                        <c:choose>
                            <c:when test="${empty menu.snack}">
                                <button onclick="createMenu('${menu.menuDate}')" class="btn btn-success btn-sm">
                                    <span class="glyphicon glyphicon-plus"></span> Add Menu
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button onclick="editMenu(${menu.dailyMenuId})" class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-edit"></span> Edit
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </div>
            <div class="menu-content">
                <c:choose>
                    <c:when test="${empty menu.snack}">
                        <div class="empty-menu-message">
                            No menu planned for this day
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="menu-item">
                            <span class="menu-category">Morning Snack:</span>
                            <span>${menu.snack.name}</span>
                        </div>
                        <div class="menu-item">
                            <span class="menu-category">Appetizer:</span>
                            <span>${menu.appetizer.name}</span>
                        </div>
                        <div class="menu-item">
                            <span class="menu-category">Vegetable:</span>
                            <span>${menu.vegetable.name}</span>
                        </div>
                        <div class="menu-item">
                            <span class="menu-category">Protein:</span>
                            <span class="menu-value">
                                ${menu.protein.name}
                                <span class="label label-info">${menu.protein.type}</span>
                            </span>
                        </div>
                        <div class="menu-item">
                            <span class="menu-category">Carbs:</span>
                            <span>${menu.carb.name}</span>
                        </div>
                        <div class="menu-item">
                            <span class="menu-category">Dessert:</span>
                            <span>${menu.dessert.name}</span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>
</div>
