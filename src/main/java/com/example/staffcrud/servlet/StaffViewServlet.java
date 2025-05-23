package com.example.staffcrud.servlet;

import com.example.staffcrud.model.Staff;
import com.example.staffcrud.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaffViewServlet {


    private final StaffService staffService;

    StaffViewServlet(StaffService staffService){
        this.staffService = staffService;
            }

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/create")
    public String create(){
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        int staffId = Integer.parseInt(id);
        Staff staff = staffService.getStaffById(staffId);
        model.addAttribute("staff", staff);
        return "edit";
    }
}
