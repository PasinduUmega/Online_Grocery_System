import com.example.glossarystore.dto.QuickFeedbackDTO;
import com.example.glossarystore.dto.ResponseDTO;
import com.example.glossarystore.model.QuickFeedback;
import com.example.glossarystore.repository.QuickFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuickFeedbackService {
    
    @Autowired
    private QuickFeedbackRepository quickFeedbackRepository;
    
    /**
     * Get all quick feedbacks
     * @return List of all quick feedbacks
     */
    public List<QuickFeedback> getAllQuickFeedbacks() {
        return quickFeedbackRepository.findAll();
    }
    
    /**
     * Create a new quick feedback
     * @param quickFeedbackDTO The quick feedback data transfer object
     * @return The created quick feedback
     */
    @Transactional
    public ResponseDTO<QuickFeedback> createQuickFeedback(QuickFeedbackDTO quickFeedbackDTO) {
        try {
            QuickFeedback quickFeedback = new QuickFeedback(
                quickFeedbackDTO.getText(),
                quickFeedbackDTO.getType()
            );
            
            QuickFeedback savedQuickFeedback = quickFeedbackRepository.save(quickFeedback);
            return ResponseDTO.success("Quick feedback created successfully", savedQuickFeedback);
        } catch (Exception e) {
            return ResponseDTO.error("Failed to create quick feedback: " + e.getMessage());
        }
    }
}
