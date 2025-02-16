<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        .top-actions { margin: 20px 0; }
        .search-box { margin-bottom: 20px; }
        .modal-header { 
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
        }
        .modal-footer {
            background-color: #f8f9fa;
            border-top: 1px solid #dee2e6;
        }
        .btn-action { margin: 0 2px; }
        .table th { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Student Management</h2>
        
        <div class="top-actions">
            <div class="row">
                <div class="col-md-6">
                    <button type="button" class="btn btn-primary" onclick="openAddModal()">
                        <span class="glyphicon glyphicon-plus"></span> Add New Student
                    </button>
                </div>
                <div class="col-md-6">
                    <div class="search-box">
                        <div class="input-group">
                            <input type="text" class="form-control" id="searchInput" 
                                   placeholder="Search students..." onkeyup="searchStudents()">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Class</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${student_list}">
                        <tr>
                            <td>${student.studentId}</td>
                            <td>${student.firstName}</td>
                            <td>${student.lastName}</td>
                            <td>${student.email}</td>
                            <td>${student.studentClass}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/students?command=VIEW&studentId=${student.studentId}" 
                                   class="btn btn-info btn-sm btn-action">
                                    <span class="glyphicon glyphicon-eye-open"></span> View
                                </a>
                                <button onclick="openUpdateModal(
                                    '${student.studentId}', 
                                    '${student.firstName}', 
                                    '${student.lastName}', 
                                    '${student.email}', 
                                    '${student.studentClass}')" 
                                    class="btn btn-primary btn-sm btn-action">
                                    <span class="glyphicon glyphicon-edit"></span> Edit
                                </button>
                                <button onclick="confirmDelete('${student.studentId}')"
                                   class="btn btn-danger btn-sm btn-action">
                                    <span class="glyphicon glyphicon-trash"></span> Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <!-- Student Modal for Add/Update -->
        <div class="modal fade" id="studentModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" 
                                onclick="closeModal()">&times;</button>
                        <h4 class="modal-title" id="modalTitle">Add New Student</h4>
                    </div>
                    <div class="modal-body">
                        <form id="studentForm" onsubmit="handleFormSubmit(event)">
                            <input type="hidden" name="command" id="formCommand" value="ADD">
                            <input type="hidden" name="studentId" id="studentId">
                            <div class="form-group">
                                <label>First Name:</label>
                                <input type="text" name="firstName" id="firstName" 
                                       class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Last Name:</label>
                                <input type="text" name="lastName" id="lastName" 
                                       class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Email:</label>
                                <input type="email" name="email" id="email" 
                                       class="form-control" required>
                            </div>
                            <div class="form-group password-group">
                                <label>Password:</label>
                                <input type="password" name="password" id="password" 
                                       class="form-control">
                                <small class="text-muted">Leave empty for updates</small>
                            </div>
                            <div class="form-group">
                                <label>Class:</label>
                                <input type="text" name="studentClass" id="studentClass" 
                                       class="form-control" required>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" 
                                        id="modalSubmitBtn">Add Student</button>
                                <button type="button" class="btn btn-default" 
                                        onclick="closeModal()">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-default">
                <span class="glyphicon glyphicon-chevron-left"></span> Back to Dashboard
            </a>
        </div>
    </div>

    <script>
        // Modal management
        const modal = document.getElementById('studentModal');
        const studentForm = document.getElementById('studentForm');

        function openModal() {
            modal.style.display = 'block';
            modal.classList.add('in');
            document.body.classList.add('modal-open');
        }

        function closeModal() {
            modal.style.display = 'none';
            modal.classList.remove('in');
            document.body.classList.remove('modal-open');
            studentForm.reset();
        }

        function openAddModal() {
            document.getElementById('modalTitle').textContent = 'Add New Student';
            document.getElementById('formCommand').value = 'ADD';
            document.getElementById('modalSubmitBtn').textContent = 'Add Student';
            document.querySelector('.password-group').style.display = 'block';
            document.getElementById('password').required = true;
            studentForm.reset();
            openModal();
        }

        function openUpdateModal(id, firstName, lastName, email, studentClass) {
            document.getElementById('modalTitle').textContent = 'Update Student';
            document.getElementById('formCommand').value = 'UPDATE';
            document.getElementById('modalSubmitBtn').textContent = 'Update Student';
            
            document.getElementById('studentId').value = id;
            document.getElementById('firstName').value = firstName;
            document.getElementById('lastName').value = lastName;
            document.getElementById('email').value = email;
            document.getElementById('studentClass').value = studentClass;
            
            document.querySelector('.password-group').style.display = 'none';
            document.getElementById('password').required = false;
            document.getElementById('password').value = '';
            
            openModal();
        }

        // Form handling
        function handleFormSubmit(event) {
            event.preventDefault();
            
            if (!validateForm()) {
                return false;
            }

            const formData = new FormData(studentForm);
            const data = new URLSearchParams(formData);

            fetch('${pageContext.request.contextPath}/students', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: data
            })
            .then(response => {
                if (response.ok) {
                    closeModal();
                    window.location.reload();
                } else {
                    throw new Error('Network response was not ok');
                }
            })
            .catch(error => {
                alert('Error: ' + error.message);
            });
        }

        // Form validation
        function validateForm() {
            const email = document.getElementById('email').value;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            
            if (!emailRegex.test(email)) {
                alert('Please enter a valid email address');
                return false;
            }

            const formCommand = document.getElementById('formCommand').value;
            const password = document.getElementById('password').value;
            if (formCommand === 'ADD' && !password) {
                alert('Password is required for new students');
                return false;
            }

            return true;
        }

        // Delete confirmation
        function confirmDelete(studentId) {
            if (confirm('Are you sure you want to delete this student?')) {
                window.location.href = '${pageContext.request.contextPath}/students?command=DELETE&studentId=' + studentId;
            }
        }

        // Search functionality
        function searchStudents() {
            const searchInput = document.getElementById('searchInput');
            const filter = searchInput.value.toLowerCase();
            const tbody = document.querySelector('table tbody');
            const rows = tbody.getElementsByTagName('tr');

            for (let row of rows) {
                const text = row.textContent || row.innerText;
                row.style.display = text.toLowerCase().includes(filter) ? '' : 'none';
            }
        }
    </script>
</body>
</html>