<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Details</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        .detail-card {
            margin-top: 20px;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .actions {
            margin-top: 20px;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .spinner {
            display: inline-block;
            animation: spin 1s linear infinite;
        }
        .hidden {
            display: none;
        }
        .modal-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
        }
        .modal-footer {
            background-color: #f8f9fa;
            border-top: 1px solid #dee2e6;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="detail-card">
                    <h2>Student Details</h2>
                    <hr>
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Student ID:</strong> ${student.studentId}</p>
                            <p><strong>First Name:</strong> ${student.firstName}</p>
                            <p><strong>Last Name:</strong> ${student.lastName}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Email:</strong> ${student.email}</p>
                            <p><strong>Class:</strong> ${student.studentClass}</p>
                        </div>
                    </div>
                    <div class="actions">
                        <button onclick="openUpdateModal('${student.studentId}', '${student.firstName}', 
                                '${student.lastName}', '${student.email}', '${student.studentClass}')" 
                                class="btn btn-primary">Edit</button>
                        <a href="students?command=LIST" class="btn btn-default">Back to List</a>
                        <button onclick="confirmDelete('${student.studentId}')"
                                class="btn btn-danger">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Student Edit Modal -->
    <div class="modal fade" id="studentModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Update Student</h4>
                </div>
                <div class="modal-body">
                    <form id="studentForm">
                        <input type="hidden" name="command" id="formCommand" value="UPDATE">
                        <input type="hidden" name="studentId" id="studentId">
                        <div class="form-group">
                            <label>First Name:</label>
                            <input type="text" name="firstName" id="firstName" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Last Name:</label>
                            <input type="text" name="lastName" id="lastName" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" name="email" id="email" class="form-control" required>
                        </div>
                        <div class="form-group password-group">
                            <label>Password:</label>
                            <input type="password" name="password" id="password" class="form-control">
                            <small class="text-muted">Leave empty to keep current password</small>
                        </div>
                        <div class="form-group">
                            <label>Class:</label>
                            <input type="text" name="studentClass" id="studentClass" class="form-control" required>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary" id="modalSubmitBtn">
                                <span class="button-text">Update Student</span>
                                <span class="glyphicon glyphicon-refresh spinner hidden"></span>
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        const studentForm = document.getElementById('studentForm');
        const modalSubmitBtn = document.getElementById('modalSubmitBtn');
        const studentModal = document.getElementById('studentModal');

        document.addEventListener('DOMContentLoaded', function() {
            studentForm.addEventListener('submit', handleFormSubmit);

            const closeButtons = document.querySelectorAll('[data-dismiss="modal"]');
            closeButtons.forEach(button => {
                button.addEventListener('click', () => {
                    hideModal();
                });
            });
        });

        async function handleFormSubmit(e) {
            e.preventDefault();
            
            if (!validateForm()) {
                return;
            }

            const formData = new FormData(studentForm);
            const queryString = new URLSearchParams(formData).toString();
            
            setLoadingState(true);

            try {
                const response = await fetch('students?' + queryString, {
                    method: 'POST'
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                hideModal();
                window.location.reload();
            } catch (error) {
                alert('Error submitting form: ' + error.message);
                setLoadingState(false);
            }
        }

        function validateForm() {
            const email = document.getElementById('email').value;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            
            if (!emailRegex.test(email)) {
                alert('Please enter a valid email address');
                return false;
            }

            return true;
        }

        function openUpdateModal(id, firstName, lastName, email, studentClass) {
            document.getElementById('studentId').value = id;
            document.getElementById('firstName').value = firstName;
            document.getElementById('lastName').value = lastName;
            document.getElementById('email').value = email;
            document.getElementById('studentClass').value = studentClass;
            
            document.getElementById('password').required = false;
            document.getElementById('password').value = '';
            
            showModal();
        }

        function showModal() {
            studentModal.classList.add('in');
            studentModal.style.display = 'block';
            document.body.classList.add('modal-open');
            
            const backdrop = document.createElement('div');
            backdrop.className = 'modal-backdrop fade in';
            document.body.appendChild(backdrop);
        }

        function hideModal() {
            studentModal.classList.remove('in');
            studentModal.style.display = 'none';
            document.body.classList.remove('modal-open');
            
            const backdrop = document.querySelector('.modal-backdrop');
            if (backdrop) {
                backdrop.remove();
            }
        }

        function confirmDelete(studentId) {
            if (confirm('Are you sure you want to delete this student?')) {
                window.location.href = 'students?command=DELETE&studentId=' + studentId;
            }
        }

        function setLoadingState(isLoading) {
            const buttonText = modalSubmitBtn.querySelector('.button-text');
            const spinner = modalSubmitBtn.querySelector('.spinner');
            
            modalSubmitBtn.disabled = isLoading;
            buttonText.style.display = isLoading ? 'none' : '';
            spinner.classList.toggle('hidden', !isLoading);
        }
    </script>
</body>
</html>