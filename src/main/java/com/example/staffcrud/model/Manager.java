package com.example.staffcrud.model;

import java.util.Date;

public class Manager extends Staff {
    private String department;
    private int teamSize;

    public Manager() {
        super();
    }

    public Manager(int id, String name, String role, String contactNumber, String nicNumber,
                   String email, String address, String staffNumber, Date dateJoined,
                   double salary, String department, int teamSize) {
        super(id, name, role, contactNumber, nicNumber, email, address, staffNumber, dateJoined, salary);
        this.department = department;
        this.teamSize = teamSize;
    }

    // Additional getters and setters for Manager-specific properties
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Manager{" +
                "department='" + department + '\'' +
                ", teamSize=" + teamSize +
                '}';
    }
}