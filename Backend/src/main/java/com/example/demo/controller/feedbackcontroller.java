import com.example.glossarystore.dto.FeedbackDTO;
import com.example.glossarystore.dto.ResponseDTO;
import com.example.glossarystore.model.Feedback;
import com.example.glossarystore.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*") // Allow requests from any origin
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    /**
     * Get all feedbacks
     * @return ResponseEntity containing all feedbacks
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<List<Feedback>>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(ResponseDTO.success("Feedbacks retrieved successfully", feedbacks));
    }
    
    /**
     * Get feedbacks by type (positive, neutral, negative)
     * @param type The type of feedback
     * @return ResponseEntity containing filtered feedbacks
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<ResponseDTO<List<Feedback>>> getFeedbacksByType(@PathVariable String type) {
        List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();
        List<Feedback> filteredFeedbacks = allFeedbacks.stream()
                .filter(feedback -> type.equalsIgnoreCase(feedback.getFeedbackType()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ResponseDTO.success(
                "Feedbacks of type '" + type + "' retrieved successfully", 
                filteredFeedbacks));
    }
    
    /**
     * Get feedbacks by category
     * @param category The category of feedback
     * @return ResponseEntity containing filtered feedbacks
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ResponseDTO<List<Feedback>>> getFeedbacksByCategory(@PathVariable String category) {
        List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();
        List<Feedback> filteredFeedbacks = allFeedbacks.stream()
                .filter(feedback -> category.equalsIgnoreCase(feedback.getCategory()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ResponseDTO.success(
                "Feedbacks of category '" + category + "' retrieved successfully", 
                filteredFeedbacks));
    }
    
    /**
     * Get feedback by ID
     * @param id The ID of the feedback
     * @return ResponseEntity containing the feedback if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Feedback>> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        
        if (feedback.isPresent()) {
            return ResponseEntity.ok(ResponseDTO.success("Feedback retrieved successfully", feedback.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO.error("Feedback not found with id: " + id));
        }
    }
    
    /**
     * Create a new feedback
     * @param feedbackDTO The feedback data transfer object
     * @return ResponseEntity containing the created feedback
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<Feedback>> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        ResponseDTO<Feedback> response = feedbackService.createFeedback(feedbackDTO);
        
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Update an existing feedback
     * @param id The ID of the feedback to update
     * @param feedbackDTO The updated feedback data
     * @return ResponseEntity containing the updated feedback
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Feedback>> updateFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackDTO feedbackDTO) {
        ResponseDTO<Feedback> response = feedbackService.updateFeedback(id, feedbackDTO);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Delete a feedback
     * @param id The ID of the feedback to delete
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteFeedback(@PathVariable Long id) {
        ResponseDTO<Void> response = feedbackService.deleteFeedback(id);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
