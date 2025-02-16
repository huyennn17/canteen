let currentWeekStart = null;

// Initialize when document is ready
$(document).ready(function() {
    setupAlerts();
    setupFormValidation();
    initializeMenuDateSelectors();
    initializeModals();
    
    // Initialize current week display
    const weekStartParam = new URLSearchParams(window.location.search).get('weekStart');
    if (weekStartParam) {
        let currentWeekStartDate = new Date(weekStartParam);
        
        // Ensure we're displaying from Monday
        while (currentWeekStartDate.getDay() !== 1) { // 1 = Monday
            currentWeekStartDate.setDate(currentWeekStartDate.getDate() - 1);
        }
        
        const weekEndDate = new Date(currentWeekStartDate);
        weekEndDate.setDate(weekEndDate.getDate() + 5); // Add 5 days to get to Saturday
        
        document.getElementById('currentWeekDisplay').textContent = 
            `${formatDateStr(currentWeekStartDate)} - ${formatDateStr(weekEndDate)}`;
            
        document.getElementById('currentWeekStart').value = formatDateForInput(currentWeekStartDate);
    }

    $('#weekSelectorModal').on('show.bs.modal', function() {
        currentWeekStart = getNextMonday();
        updateModalWeekDisplay();
    });
});

function showWeekSelector() {
    currentWeekStart = getNextMonday();
    $('#weekSelectorModal').modal('show');
    updateModalWeekDisplay();
}

function getNextMonday() {
    let date = new Date();
    while (date.getDay() !== 1) { // 1 = Monday
        date.setDate(date.getDate() + 1);
    }
    return date;
}

function getMondayOfWeek(date) {
    const result = new Date(date);
    while (result.getDay() !== 1) { // 1 = Monday
        result.setDate(result.getDate() - 1);
    }
    return result;
}

function changeWeek(offset) {
    if (!currentWeekStart) {
        currentWeekStart = getNextMonday();
    }
    
    currentWeekStart.setDate(currentWeekStart.getDate() + (offset * 7));
    
    // Don't allow dates before next Monday
    let nextMonday = getNextMonday();
    if (currentWeekStart < nextMonday) {
        currentWeekStart = nextMonday;
    }
    
    updateModalWeekDisplay();
}

function updateModalWeekDisplay() {
    if (!currentWeekStart) {
        currentWeekStart = getNextMonday();
    }

    const weekEnd = new Date(currentWeekStart);
    weekEnd.setDate(weekEnd.getDate() + 5); // Monday to Saturday
    
    // Update hidden input
    document.getElementById('selectedWeekStart').value = formatDateForInput(currentWeekStart);
    
    // Update week display
    document.getElementById('weekDisplay').textContent = 
        `${formatDateStr(currentWeekStart)} - ${formatDateStr(weekEnd)}`;
    
    // Update days list
    const daysList = document.getElementById('weekDaysList');
    if (daysList) {
        daysList.innerHTML = '';
        let currentDay = new Date(currentWeekStart);
        for (let i = 0; i < 6; i++) {
            const li = document.createElement('li');
            li.className = 'week-day-item';
            li.innerHTML = `
                <span class="glyphicon glyphicon-calendar"></span>
                ${formatDayDateStr(currentDay)}
            `;
            daysList.appendChild(li);
            currentDay.setDate(currentDay.getDate() + 1);
        }
    }
}

// Week Navigation Functions
function changeDisplayWeek(offset) {
    const currentWeekStartInput = document.getElementById('currentWeekStart');
    let currentDate = currentWeekStartInput.value ? new Date(currentWeekStartInput.value) : new Date();
    
    // Ensure we're starting from Monday
    currentDate = getMondayOfWeek(currentDate);
    
    // Add or subtract weeks
    currentDate.setDate(currentDate.getDate() + (offset * 7));
    
    // Redirect with the Monday date
    window.location.href = 'menu?command=VIEW&weekStart=' + formatDateForInput(currentDate);
}

function formatDateStr(date) {
    if (!(date instanceof Date) || isNaN(date)) {
        console.error('Invalid date:', date);
        return '';
    }
    return date.toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric'
    });
}

function formatDayDateStr(date) {
    if (!(date instanceof Date) || isNaN(date)) {
        console.error('Invalid date:', date);
        return '';
    }
    return date.toLocaleDateString('en-US', {
        weekday: 'long',
        month: 'short',
        day: 'numeric'
    });
}

function formatDateForInput(date) {
    if (!(date instanceof Date) || isNaN(date)) {
        console.error('Invalid date:', date);
        return '';
    }
    return date.toISOString().split('T')[0];
}

// Native validation for the selected week start
function validateSelectedWeekStart() {
    const weekStart = document.getElementById('selectedWeekStart').value;
    if (!weekStart) {
        alert('Please select a week start date');
        return false;
    }
    return true;
}

// Initialize week navigation on page load
document.addEventListener('DOMContentLoaded', function() {
    const weekStartParam = new URLSearchParams(window.location.search).get('weekStart');
    const startDate = weekStartParam ? new Date(weekStartParam) : new Date();
    
    // Ensure we start from Monday
    while (startDate.getDay() !== 1) {
        startDate.setDate(startDate.getDate() - 1);
    }
    
    document.getElementById('currentWeekStart').value = formatDateForInput(startDate);
    updateModalWeekDisplay(startDate);
    updateNavigationButtons(startDate);
});

// Menu Creation/Editing Functions
function createMenu(date) {
    document.getElementById('menuForm').reset();
    document.getElementById('menuCommand').value = 'CREATE';
    document.getElementById('menuId').value = '';
    document.getElementById('menuModalTitle').textContent = 'Create New Menu';

    // Set the date if provided, otherwise use tomorrow
    if (date) {
        document.getElementById('menuDate').value = date;
    } else {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        document.getElementById('menuDate').value = formatDateForInput(tomorrow);
    }

    // Show the modal
    $('#menuModal').modal('show');
}

function editMenu(menuId) {
    // Simply redirect to the edit page
    window.location.href = 'menu?command=LOAD&menuId=' + menuId;
}

// Setup Functions
function setupAlerts() {
    $('.alert').each(function() {
        const alert = $(this);
        setTimeout(function() {
            alert.fadeOut('slow');
        }, 5000);
    });
}

function setupFormValidation() {
    $('form').on('submit', function(e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            showAlert('Please fill all required fields', 'warning');
        }
    });
}

function initializeMenuDateSelectors() {
    $('.menu-date-select').on('change', function() {
        const selectedOption = $(this).find('option:selected');
        const menuDate = selectedOption.data('date');
        $(this).closest('form').find('input[name="menuDate"]').val(menuDate);
    }).trigger('change');
}

function initializeModals() {
    // Initialize all modals
    $('.modal').modal({
        show: false,
        backdrop: 'static'
    });
}

// Utility Functions
function formatDate(date) {
    return date.toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric'
    });
}

function formatDayDate(date) {
    return date.toLocaleDateString('en-US', {
        weekday: 'long',
        month: 'short',
        day: 'numeric'
    });
}

function formatDateForInput(date) {
    return date.toISOString().split('T')[0];
}

function showAlert(message, type) {
    const alert = $(`
        <div class="alert alert-${type} alert-dismissible fade in">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${message}
        </div>
    `);
    
    $('.menu-container').prepend(alert);
    setTimeout(function() {
        alert.fadeOut('slow', function() {
            alert.remove();
        });
    }, 5000);
}

function populateMenuForm(data) {
    $('#menuDate').val(formatDateForInput(new Date(data.menuDate)));
    $('select[name="snackId"]').val(data.snack.id);
    $('select[name="appetizerId"]').val(data.appetizer.id);
    $('select[name="vegetableId"]').val(data.vegetable.id);
    $('select[name="proteinId"]').val(data.protein.id);
    $('select[name="carbId"]').val(data.carb.id);
    $('select[name="dessertId"]').val(data.dessert.id);
}
