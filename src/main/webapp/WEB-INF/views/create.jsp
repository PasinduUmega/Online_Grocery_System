<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Staff | DK Store Admin</title>
    <style>
        :root {
            --primary-green: #2e7d32;
            --light-green: #81c784;
            --very-light-green: #e8f5e9;
            --dark-green: #1b5e20;
            --accent-green: #4caf50;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--very-light-green);
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-bottom: 20px;
        }

        h1 {
            color: var(--primary-green);
            margin-top: 0;
            border-bottom: 2px solid var(--light-green);
            padding-bottom: 10px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: var(--dark-green);
        }

        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--accent-green);
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
        }

        .btn {
            display: inline-block;
            padding: 12px 24px;
            font-size: 16px;
            font-weight: 600;
            text-align: center;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s, transform 0.1s;
        }

        .btn-primary {
            background-color: var(--primary-green);
            color: white;
        }

        .btn-primary:hover {
            background-color: var(--dark-green);
        }

        .btn-secondary {
            background-color: #f5f5f5;
            color: #333;
            margin-right: 10px;
        }

        .btn-secondary:hover {
            background-color: #e0e0e0;
        }

        .buttons-container {
            display: flex;
            justify-content: flex-start;
            margin-top: 20px;
        }

        .header {
            background-color: var(--primary-green);
            color: white;
            padding: 15px 0;
            margin-bottom: 30px;
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        .message {
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            display: none;
        }

        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo">DK Store Admin</div>
    </div>
</div>

<div class="container">
    <div id="message" class="message"></div>

    <div class="card">
        <h1>Add New Staff Member</h1>

        <form id="createStaffForm">
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="role">Role</label>
                <select id="role" name="role" required>
                    <option value="">Select a role</option>
                    <option value="Store Manager">Store Manager</option>
                    <option value="Assistant Manager">Assistant Manager</option>
                    <option value="Produce Specialist">Produce Specialist</option>
                    <option value="Cashier">Cashier</option>
                    <option value="Inventory Clerk">Inventory Clerk</option>
                    <option value="Delivery Driver">Delivery Driver</option>
                    <option value="Customer Service Representative">Customer Service Representative</option>
                    <option value="Organic Section Coordinator">Organic Section Coordinator</option>
                    <option value="Quality Control Specialist">Quality Control Specialist</option>
                </select>
            </div>

            <div class="form-group">
                <label for="contactNumber">Contact Number</label>
                <input type="tel" id="contactNumber" name="contactNumber" required>
            </div>

            <div class="form-group">
                <label for="nicNumber">NIC Number</label>
                <input type="text" id="nicNumber" name="nicNumber" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" required>
            </div>

            <div class="form-group">
                <label for="staffNumber">Staff Number</label>
                <input type="text" id="staffNumber" name="staffNumber" required>
            </div>

            <div class="form-group">
                <label for="dateJoined">Date Joined</label>
                <input type="date" id="dateJoined" name="dateJoined" required>
            </div>

            <div class="form-group">
                <label for="salary">Salary</label>
                <input type="number" id="salary" name="salary" step="0.01" required>
            </div>

            <div class="buttons-container">
                <button type="button" class="btn btn-secondary" onclick="window.location.href='/'">Back</button>
                <button type="submit" class="btn btn-primary">Create Staff</button>
            </div>
        </form>
    </div>
</div>

<script>
    document.getElementById("createStaffForm").addEventListener("submit", function(event) {
        event.preventDefault();

        // Get form values
        const formData = {
            name: document.getElementById("name").value,
            role: document.getElementById("role").value,
            contactNumber: document.getElementById("contactNumber").value,
            nicNumber: document.getElementById("nicNumber").value,
            email: document.getElementById("email").value,
            address: document.getElementById("address").value,
            staffNumber: document.getElementById("staffNumber").value,
            dateJoined: document.getElementById("dateJoined").value,
            salary: parseFloat(document.getElementById("salary").value)
        };

        // Convert to JSON
        const jsonData = JSON.stringify(formData);

        // Send POST request
        fetch("/api/staff", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: jsonData
        })
            .then(function(response) {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("Network response was not ok.");
            })
            .then(function(data) {
                showMessage("Staff member created successfully!", "success");
                // Reset form
                document.getElementById("createStaffForm").reset();
            })
            .catch(function(error) {
                showMessage("Error creating staff member: " + error.message, "error");
                console.error("Error:", error);
            });
    });

    function showMessage(text, type) {
        const messageElement = document.getElementById("message");
        messageElement.textContent = text;
        messageElement.className = "message " + type;
        messageElement.style.display = "block";

        // Auto-hide after 5 seconds
        setTimeout(function() {
            messageElement.style.display = "none";
        }, 5000);
    }

    // Set default date to today
    document.addEventListener("DOMContentLoaded", function() {
        const today = new Date();
        const formattedDate = today.toISOString().substr(0, 10);
        document.getElementById("dateJoined").value = formattedDate;
    });
</script>
</body>
</html>