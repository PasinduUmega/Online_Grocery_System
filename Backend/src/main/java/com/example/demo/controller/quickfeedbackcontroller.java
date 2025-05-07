import com.example.glossarystore.dto.QuickFeedbackDTO;
import com.example.glossarystore.dto.ResponseDTO;
import com.example.glossarystore.model.QuickFeedback;
import com.example.glossarystore.service.QuickFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quick-feedbacks")
@CrossOrigin(origins = "*") // Allow requests from any origin
public class QuickFeedbackController {
    
    @Autowired
    private QuickFeedbackService quickFeedbackService;
    
    /**
     * Get all quick feedbacks
     * @return ResponseEntity containing all quick feedbacks
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<List<QuickFeedback>>> getAllQuickFeedbacks() {
        List<QuickFeedback> quickFeedbacks = quickFeedbackService.getAllQuickFeedbacks();
        return ResponseEntity.ok(ResponseDTO.success("Quick feedbacks retrieved successfully", quickFeedbacks));
    }
    
    /**
     * Create a new quick feedback
     * @param quickFeedbackDTO The quick feedback data transfer object
     * @return ResponseEntity containing the created quick feedback
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<QuickFeedback>> createQuickFeedback(@RequestBody QuickFeedbackDTO quickFeedbackDTO) {
        ResponseDTO<QuickFeedback> response = quickFeedbackService.createQuickFeedback(quickFeedbackDTO);
        
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}