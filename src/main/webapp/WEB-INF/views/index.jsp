<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Management | GreenGrocer Admin</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.25/jspdf.plugin.autotable.min.js"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        'primary-green': '#2e7d32',
                        'light-green': '#81c784',
                        'very-light-green': '#e8f5e9',
                        'dark-green': '#1b5e20',
                        'accent-green': '#4caf50',
                    }
                }
            }
        }
    </script>
</head>
<body class="bg-very-light-green">
<!-- Header -->
<header class="bg-primary-green text-white shadow-md">
    <div class="container mx-auto px-4 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">DK Store Admin</h1>
        <div>
            <button id="generatePdfBtn" class="bg-white text-primary-green font-medium py-2 px-4 rounded-md hover:bg-gray-100 transition">
                Export to PDF
            </button>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-4 py-8">
    <!-- Alert Message Container -->
    <div id="alertContainer" class="mb-6 hidden">
        <div id="alertContent" class="p-4 rounded-md"></div>
    </div>

    <!-- Controls Section -->
    <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-6 gap-4">
        <h2 class="text-2xl font-semibold text-dark-green">Staff Management</h2>

        <div class="flex flex-col sm:flex-row gap-4 w-full md:w-auto">
            <div class="relative w-full sm:w-64">
                <input
                        type="text"
                        id="searchInput"
                        placeholder="Search staff..."
                        class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-accent-green"
                >
                <span class="absolute right-3 top-2.5 text-gray-400">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                        </svg>
                    </span>
            </div>

            <a href="/create" class="w-full sm:w-auto bg-primary-green hover:bg-dark-green text-white font-medium py-2 px-6 rounded-md transition flex items-center justify-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2z"/>
                </svg>
                Add New Staff
            </a>
        </div>
    </div>

    <!-- Loading Spinner -->
    <div id="loadingSpinner" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary-green"></div>
    </div>

    <!-- Staff Table -->
    <div id="staffTableContainer" class="bg-white rounded-lg shadow-md overflow-hidden hidden">
        <div class="overflow-x-auto">
            <table id="staffTable" class="w-full table-auto">
                <thead class="bg-light-green text-dark-green">
                <tr>
                    <th class="px-6 py-3 text-left font-semibold">Staff ID</th>
                    <th class="px-6 py-3 text-left font-semibold">Staff Number</th>
                    <th class="px-6 py-3 text-left font-semibold">Name</th>
                    <th class="px-6 py-3 text-left font-semibold">Role</th>
                    <th class="px-6 py-3 text-left font-semibold">Email</th>
                    <th class="px-6 py-3 text-left font-semibold">Date Joined</th>
                    <th class="px-6 py-3 text-left font-semibold">Salary</th>
                    <th class="px-6 py-3 text-center font-semibold">Actions</th>
                </tr>
                </thead>
                <tbody id="staffTableBody">
                <!-- Staff data will be populated here -->
                </tbody>
            </table>
        </div>
    </div>

    <!-- No Results Message -->
    <div id="noResultsMessage" class="hidden bg-white rounded-lg shadow-md p-8 text-center">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 mx-auto text-gray-400 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <h3 class="text-lg font-medium text-gray-700 mb-1">No staff members found</h3>
        <p class="text-gray-500" id="noResultsDetail">Try adjusting your search criteria.</p>
    </div>
</main>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
    <div class="bg-white rounded-lg p-6 max-w-md w-full mx-4">
        <h3 class="text-xl font-bold text-gray-800 mb-4">Confirm Deletion</h3>
        <p class="text-gray-600 mb-6">Are you sure you want to delete this staff member? This action cannot be undone.</p>
        <div class="flex justify-end gap-3">
            <button id="cancelDelete" class="px-4 py-2 bg-gray-200 text-gray-800 rounded hover:bg-gray-300 transition">Cancel</button>
            <button id="confirmDelete" class="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition">Delete</button>
        </div>
    </div>
</div>

<script>
    // Global variables
    const { jsPDF } = window.jspdf;
    let allStaffData = [];
    let staffIdToDelete = null;

    document.addEventListener("DOMContentLoaded", function() {
        // Fetch staff data when page loads
        fetchStaffData();

        // Set up event listeners
        document.getElementById("searchInput").addEventListener("input", filterStaffData);
        document.getElementById("generatePdfBtn").addEventListener("click", generatePDF);
        document.getElementById("cancelDelete").addEventListener("click", hideDeleteModal);
        document.getElementById("confirmDelete").addEventListener("click", deleteStaffMember);
    });

    // Fetch all staff data from API
    function fetchStaffData() {
        showLoading(true);

        fetch("/api/staff")
            .then(function(response) {
                if (!response.ok) {
                    throw new Error("Failed to fetch staff data. Status: " + response.status);
                }
                return response.json();
            })
            .then(function(data) {
                allStaffData = data;
                renderStaffTable(data);
                showLoading(false);
            })
            .catch(function(error) {
                showAlert("Error: " + error.message, "error");
                showLoading(false);
                showNoResults("Failed to load staff data. Please try again later.");
            });
    }

    // Render staff table with provided data
    function renderStaffTable(staffData) {
        const tableBody = document.getElementById("staffTableBody");
        tableBody.innerHTML = "";

        if (staffData.length === 0) {
            showNoResults();
            return;
        }

        staffData.forEach(function(staff) {
            const row = document.createElement("tr");
            row.className = "border-b border-gray-200 hover:bg-very-light-green";

            // Format date for display
            const joinDate = new Date(staff.dateJoined);
            const formattedDate = joinDate.toLocaleDateString();

            // Format salary with currency
            const formattedSalary = new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD'
            }).format(staff.salary);

            row.innerHTML =
                "<td class=\"px-6 py-4\">" + staff.id + "</td>" +
                "<td class=\"px-6 py-4\">" + staff.staffNumber + "</td>" +
                "<td class=\"px-6 py-4 font-medium\">" + staff.name + "</td>" +
                "<td class=\"px-6 py-4\">" + staff.role + "</td>" +
                "<td class=\"px-6 py-4\">" + staff.email + "</td>" +
                "<td class=\"px-6 py-4\">" + formattedDate + "</td>" +
                "<td class=\"px-6 py-4\">" + formattedSalary + "</td>" +
                "<td class=\"px-6 py-4 text-center\">" +
                "<div class=\"flex justify-center space-x-2\">" +
                "<a href=\"/edit/" + staff.id + "\" class=\"text-blue-600 hover:text-blue-800\">" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-pencil-square\" viewBox=\"0 0 16 16\">" +
                "<path d=\"M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z\"/>" +
                "<path fill-rule=\"evenodd\" d=\"M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z\"/>" +
                "</svg>" +
                "</a>" +
                "<button onclick=\"showDeleteModal(" + staff.id + ")\" class=\"text-red-600 hover:text-red-800\">" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-trash\" viewBox=\"0 0 16 16\">" +
                "<path d=\"M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z\"/>" +
                "<path fill-rule=\"evenodd\" d=\"M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z\"/>" +
                "</svg>" +
                "</button>" +

                "</div>" +
                "</td>";

            tableBody.appendChild(row);
        });

        document.getElementById("staffTableContainer").classList.remove("hidden");
        document.getElementById("noResultsMessage").classList.add("hidden");
    }

    // Filter staff data based on search input
    function filterStaffData() {
        const searchValue = document.getElementById("searchInput").value.toLowerCase();

        if (!searchValue) {
            renderStaffTable(allStaffData);
            return;
        }

        const filteredData = allStaffData.filter(function(staff) {
            return (
                staff.name.toLowerCase().includes(searchValue) ||
                staff.role.toLowerCase().includes(searchValue) ||
                staff.email.toLowerCase().includes(searchValue) ||
                staff.staffNumber.toLowerCase().includes(searchValue) ||
                (staff.id.toString()).includes(searchValue)
            );
        });

        renderStaffTable(filteredData);

        if (filteredData.length === 0) {
            showNoResults();
        }
    }

    // Generate PDF of staff data
    function generatePDF() {
        if (allStaffData.length === 0) {
            showAlert("No staff data available to export", "warning");
            return;
        }

        try {
            const doc = new jsPDF();

            // Add title
            doc.setFontSize(18);
            doc.setTextColor(46, 125, 50); // primary-green
            doc.text("GreenGrocer Staff Report", 14, 22);

            // Add generation date
            doc.setFontSize(10);
            doc.setTextColor(100, 100, 100);
            const today = new Date();
            doc.text("Generated: " + today.toLocaleDateString() + " " + today.toLocaleTimeString(), 14, 30);

            // Prepare table data
            const tableColumn = ["ID", "Staff Number", "Name", "Role", "Email", "Date Joined", "Salary"];
            const tableRows = [];

            allStaffData.forEach(function(staff) {
                const joinDate = new Date(staff.dateJoined);
                const formattedDate = joinDate.toLocaleDateString();

                const formattedSalary = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD'
                }).format(staff.salary);

                const staffData = [
                    staff.id,
                    staff.staffNumber,
                    staff.name,
                    staff.role,
                    staff.email,
                    formattedDate,
                    formattedSalary
                ];
                tableRows.push(staffData);
            });

            // Create table
            doc.autoTable({
                head: [tableColumn],
                body: tableRows,
                startY: 35,
                styles: { fontSize: 9 },
                headStyles: {
                    fillColor: [46, 125, 50],
                    textColor: [255, 255, 255]
                },
                alternateRowStyles: {
                    fillColor: [232, 245, 233]
                },
                margin: { top: 35 }
            });

            // Save PDF
            doc.save("GreenGrocer-Staff-Report.pdf");

            showAlert("PDF generated successfully", "success");
        } catch (error) {
            console.error("PDF generation error:", error);
            showAlert("Failed to generate PDF: " + error.message, "error");
        }
    }

    // Show delete confirmation modal
    function showDeleteModal(staffId) {
        staffIdToDelete = staffId;
        document.getElementById("deleteModal").classList.remove("hidden");
    }

    // Hide delete confirmation modal
    function hideDeleteModal() {
        document.getElementById("deleteModal").classList.add("hidden");
        staffIdToDelete = null;
    }

    // Delete staff member
    function deleteStaffMember() {
        if (!staffIdToDelete) return;

        fetch("/api/staff/" + staffIdToDelete, {
            method: "DELETE"
        })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error("Failed to delete staff member. Status: " + response.status);
                }

                // Remove from the local data
                allStaffData = allStaffData.filter(staff => staff.id !== staffIdToDelete);

                // Re-render table
                renderStaffTable(allStaffData);

                showAlert("Staff member deleted successfully", "success");
                hideDeleteModal();
            })
            .catch(function(error) {
                showAlert("Error: " + error.message, "error");
                hideDeleteModal();
            });
    }

    // Show loading spinner
    function showLoading(isLoading) {
        if (isLoading) {
            document.getElementById("loadingSpinner").classList.remove("hidden");
            document.getElementById("staffTableContainer").classList.add("hidden");
            document.getElementById("noResultsMessage").classList.add("hidden");
        } else {
            document.getElementById("loadingSpinner").classList.add("hidden");
        }
    }

    // Show no results message
    function showNoResults(message) {
        document.getElementById("staffTableContainer").classList.add("hidden");
        document.getElementById("noResultsMessage").classList.remove("hidden");

        if (message) {
            document.getElementById("noResultsDetail").textContent = message;
        } else {
            document.getElementById("noResultsDetail").textContent = "Try adjusting your search criteria.";
        }
    }

    // Show alert message
    function showAlert(message, type) {
        const alertContainer = document.getElementById("alertContainer");
        const alertContent = document.getElementById("alertContent");

        // Set alert style based on type
        if (type === "success") {
            alertContent.className = "p-4 rounded-md bg-green-100 text-green-800 border border-green-200";
        } else if (type === "error") {
            alertContent.className = "p-4 rounded-md bg-red-100 text-red-800 border border-red-200";
        } else if (type === "warning") {
            alertContent.className = "p-4 rounded-md bg-yellow-100 text-yellow-800 border border-yellow-200";
        } else {
            alertContent.className = "p-4 rounded-md bg-blue-100 text-blue-800 border border-blue-200";
        }

        alertContent.textContent = message;
        alertContainer.classList.remove("hidden");

        // Auto-hide after 5 seconds
        setTimeout(function() {
            alertContainer.classList.add("hidden");
        }, 5000);
    }
</script>
</body>
</html>