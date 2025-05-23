package com.example.staffcrud.model;

import java.util.Date;

public class Staff {
    // Private fields for encapsulation
    private int id;
    private String name;
    private String role;
    private String contactNumber;
    private String nicNumber;
    private String email;
    private String address;
    private String staffNumber;
    private Date dateJoined;
    private double salary;


    public Staff() {
    }

    public Staff(int id, String name, String role, String contactNumber, String nicNumber,
                 String email, String address, String staffNumber, Date dateJoined, double salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contactNumber = contactNumber;
        this.nicNumber = nicNumber;
        this.email = email;
        this.address = address;
        this.staffNumber = staffNumber;
        this.dateJoined = dateJoined;
        this.salary = salary;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //displaying staff information
    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", nicNumber='" + nicNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", staffNumber='" + staffNumber + '\'' +
                ", dateJoined=" + dateJoined +
                ", salary=" + salary +
                '}';
    }
}