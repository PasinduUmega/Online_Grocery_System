package com.example.staffcrud.servlet;

import com.example.staffcrud.model.Staff;
import com.example.staffcrud.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffServlet {

    private final StaffService staffService;

    public StaffServlet(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable int id) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Staff not found\"}");
        }
    }

    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody Staff staff) {
        boolean created = staffService.createStaff(staff);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(staff);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to create staff\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable int id, @RequestBody Staff updatedStaff) {
        Staff existingStaff = staffService.getStaffById(id);
        if (existingStaff == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Staff not found\"}");
        }

        updatedStaff.setId(id); // Ensure ID matches the path
        boolean updated = staffService.updateStaff(updatedStaff);
        if (updated) {
            return ResponseEntity.ok(updatedStaff);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to update staff\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable int id) {
        boolean deleted = staffService.deleteStaff(id);
        if (deleted) {
            return ResponseEntity.ok("{\"message\":\"Staff deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Staff not found\"}");
        }
    }
}