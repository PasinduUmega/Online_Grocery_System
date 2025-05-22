package com.example.grocery.service;

import com.example.grocery.model.Feedback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackFileService {

    private final File file = new File("feedbacks.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Feedback> getAll() {
        try {
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Feedback>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Feedback getById(Long id) {
        return getAll().stream()
                .filter(fb -> fb.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Feedback save(Feedback feedback) {
        List<Feedback> list = getAll();
        if (feedback.getId() == null) {
            feedback.setId(System.currentTimeMillis());
            feedback.setDate(LocalDate.now());
            list.add(0, feedback); // add to top
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(feedback.getId())) {
                    list.set(i, feedback);
                    break;
                }
            }
        }
        try {
            mapper.writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    public void delete(Long id) {
        List<Feedback> list = getAll();
        list.removeIf(fb -> fb.getId().equals(id));
        try {
            mapper.writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
