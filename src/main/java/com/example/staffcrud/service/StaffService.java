package com.example.staffcrud.service;

import com.example.staffcrud.model.Staff;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class StaffService {
    private static final String FILE_PATH = "staff_data.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private int nextId = 1;

    public StaffService() {
        initializeFile();
        determineNextId();
    }

    private void initializeFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing staff data file: " + e.getMessage());
        }
    }

    private void determineNextId() {
        List<Staff> allStaff = getAllStaff();
        if (!allStaff.isEmpty()) {
            nextId = allStaff.stream()
                    .mapToInt(Staff::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    // Create a new staff member
    public boolean createStaff(Staff staff) {
        staff.setId(nextId++);
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            out.println(convertStaffToLine(staff));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving staff: " + e.getMessage());
            return false;
        }
    }

    // Read all staff members
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Staff staff = parseStaffFromLine(line);
                if (staff != null) {
                    staffList.add(staff);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff data: " + e.getMessage());
        }
        return staffList;
    }

    // Get staff by ID
    public Staff getStaffById(int id) {
        return getAllStaff().stream()
                .filter(staff -> staff.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Update staff member
    public boolean updateStaff(Staff updatedStaff) {
        List<Staff> staffList = getAllStaff();
        boolean found = false;

        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == updatedStaff.getId()) {
                staffList.set(i, updatedStaff);
                found = true;
                break;
            }
        }

        if (found) {
            return saveAllStaff(staffList);
        }
        return false;
    }

    // Delete staff member
    public boolean deleteStaff(int id) {
        List<Staff> staffList = getAllStaff();
        boolean removed = staffList.removeIf(staff -> staff.getId() == id);

        if (removed) {
            return saveAllStaff(staffList);
        }
        return false;
    }

    // Helper method to save all staff to file
    private boolean saveAllStaff(List<Staff> staffList) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Staff staff : staffList) {
                out.println(convertStaffToLine(staff));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving staff data: " + e.getMessage());
            return false;
        }
    }

    // Convert Staff object to file line
    private String convertStaffToLine(Staff staff) {
        return String.join(",",
                String.valueOf(staff.getId()),
                staff.getName(),
                staff.getRole(),
                staff.getContactNumber(),
                staff.getNicNumber(),
                staff.getEmail(),
                staff.getAddress().replace(",", "\\,"), // Escape commas in address
                staff.getStaffNumber(),
                DATE_FORMAT.format(staff.getDateJoined()),
                String.valueOf(staff.getSalary())
        );
    }

    // Parse Staff object from file line
    private Staff parseStaffFromLine(String line) {
        try {
            String[] parts = line.split("(?<!\\\\),"); // Split on commas not preceded by backslash
            if (parts.length != 10) return null;

            Staff staff = new Staff();
            staff.setId(Integer.parseInt(parts[0]));
            staff.setName(parts[1]);
            staff.setRole(parts[2]);
            staff.setContactNumber(parts[3]);
            staff.setNicNumber(parts[4]);
            staff.setEmail(parts[5]);
            staff.setAddress(parts[6].replace("\\,", ",")); // Unescape commas
            staff.setStaffNumber(parts[7]);
            staff.setDateJoined(DATE_FORMAT.parse(parts[8]));
            staff.setSalary(Double.parseDouble(parts[9]));

            return staff;
        } catch (ParseException | NumberFormatException e) {
            System.err.println("Error parsing staff data: " + e.getMessage());
            return null;
        }
    }
}