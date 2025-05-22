package com.example.grocery.controller;

import com.example.grocery.model.Feedback;
import com.example.grocery.service.FeedbackFileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackFileService service;

    public FeedbackController(FeedbackFileService service) {
        this.service = service;
    }

    @GetMapping
    public List<Feedback> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Feedback getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback fb) {
        return service.save(fb);
    }

    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id, @RequestBody Feedback fb) {
        fb.setId(id);
        return service.save(fb);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
