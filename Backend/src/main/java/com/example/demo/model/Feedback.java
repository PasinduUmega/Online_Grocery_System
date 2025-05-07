import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "feedbacks")
public class Feedback implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private Integer rating;
    
    @Column(nullable = false, length = 1000)
    private String text;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private String formattedDate;
    
    @Column(name = "feedback_category")
    private String category;
    
    @Column(name = "contact_info")
    private String contactInfo;
    
    @Column(name = "feedback_type")
    private String feedbackType; // 'positive', 'neutral', 'negative'
    
    // Default constructor
    public Feedback() {
        this.date = LocalDate.now();
        this.formattedDate = formatDate(this.date);
    }
    
    // Constructor with parameters
    public Feedback(User user, Integer rating, String text, String category) {
        this.user = user;
        this.rating = rating;
        this.text = text;
        this.category = category;
        this.date = LocalDate.now();
        this.formattedDate = formatDate(this.date);
        this.setFeedbackTypeBasedOnRating();
    }
    
    // Format the date as "MMM d, yyyy" (e.g., "May 15, 2023")
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return date.format(formatter);
    }
    
    // Set feedback type based on rating
    private void setFeedbackTypeBasedOnRating() {
        if (this.rating == null) return;
        
        if (this.rating >= 4) {
            this.feedbackType = "positive";
        } else if (this.rating == 3) {
            this.feedbackType = "neutral";
        } else {
            this.feedbackType = "negative";
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
        this.setFeedbackTypeBasedOnRating();
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
        this.formattedDate = formatDate(date);
    }
    
    public String getFormattedDate() {
        return formattedDate;
    }
    
    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    public String getFeedbackType() {
        return feedbackType;
    }
    
    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", user=" + user +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", formattedDate='" + formattedDate + '\'' +
                ", category='" + category + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                '}';
    }
}

