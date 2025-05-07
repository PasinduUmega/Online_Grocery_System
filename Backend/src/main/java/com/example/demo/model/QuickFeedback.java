import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quick_feedbacks")
public class QuickFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String text;
    
    @Column(nullable = false)
    private String type; // 'positive' or 'negative'
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    // Default constructor
    public QuickFeedback() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public QuickFeedback(String text, String type) {
        this.text = text;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "QuickFeedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}