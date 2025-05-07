import com.example.glossarystore.dto.FeedbackDTO;
import com.example.glossarystore.dto.ResponseDTO;
import com.example.glossarystore.model.Feedback;
import com.example.glossarystore.model.User;
import com.example.glossarystore.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    /**
     * Get all feedbacks
     * @return List of all feedbacks
     */
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
    
    /**
     * Get feedback by ID
     * @param id The ID of the feedback
     * @return Optional containing the feedback if found
     */
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }
    
    /**
     * Create a new feedback
     * @param feedbackDTO The feedback data transfer object
     * @return The created feedback
     */
    @Transactional
    public ResponseDTO<Feedback> createFeedback(FeedbackDTO feedbackDTO) {
        try {
            User user = new User(feedbackDTO.getUserName(), feedbackDTO.getProfilePic());
            Feedback feedback = new Feedback(
                user, 
                feedbackDTO.getRating(), 
                feedbackDTO.getText(),
                feedbackDTO.getCategory()
            );
            feedback.setContactInfo(feedbackDTO.getContactInfo());
            
            Feedback savedFeedback = feedbackRepository.save(feedback);
            return ResponseDTO.success("Feedback created successfully", savedFeedback);
        } catch (Exception e) {
            return ResponseDTO.error("Failed to create feedback: " + e.getMessage());
        }
    }
    
    /**
     * Update an existing feedback
     * @param id The ID of the feedback to update
     * @param feedbackDTO The updated feedback data
     * @return The updated feedback
     */
    @Transactional
    public ResponseDTO<Feedback> updateFeedback(Long id, FeedbackDTO feedbackDTO) {
        try {
            Optional<Feedback> existingFeedbackOpt = feedbackRepository.findById(id);
            
            if (existingFeedbackOpt.isPresent()) {
                Feedback existingFeedback = existingFeedbackOpt.get();
                
                // Update user information
                User user = existingFeedback.getUser();
                user.setName(feedbackDTO.getUserName());
                
                // Only update profile pic if a new one is provided
                if (feedbackDTO.getProfilePic() != null && !feedbackDTO.getProfilePic().isEmpty()) {
                    user.setProfilePic(feedbackDTO.getProfilePic());
                }
                
                // Update feedback information
                existingFeedback.setRating(feedbackDTO.getRating());
                existingFeedback.setText(feedbackDTO.getText());
                existingFeedback.setCategory(feedbackDTO.getCategory());
                existingFeedback.setContactInfo(feedbackDTO.getContactInfo());
                
                Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
                return ResponseDTO.success("Feedback updated successfully", updatedFeedback);
            } else {
                return ResponseDTO.error("Feedback not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseDTO.error("Failed to update feedback: " + e.getMessage());
        }
    }
    
    /**
     * Delete a feedback
     * @param id The ID of the feedback to delete
     * @return A response indicating success or failure
     */
    @Transactional
    public ResponseDTO<Void> deleteFeedback(Long id) {
        try {
            if (feedbackRepository.existsById(id)) {
                feedbackRepository.deleteById(id);
                return ResponseDTO.success("Feedback deleted successfully");
            } else {
                return ResponseDTO.error("Feedback not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseDTO.error("Failed to delete feedback: " + e.getMessage());
        }
    }
    
    /**
     * Convert Feedback entities to DTOs
     * @param feedbacks List of feedback entities
     * @return List of feedback DTOs
     */
    public List<FeedbackDTO> convertToDTO(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(feedback -> new FeedbackDTO(
                        feedback.getId(),
                        feedback.getUser().getName(),
                        feedback.getUser().getProfilePic(),
                        feedback.getRating(),
                        feedback.getText(),
                        feedback.getCategory(),
                        feedback.getContactInfo()))
                .collect(Collectors.toList());
    }
}