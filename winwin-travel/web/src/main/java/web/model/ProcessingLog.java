package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "processing_log")
public class ProcessingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(length = 1000)
    private String inputText;
    @Column(length = 1000)
    private String outputText;
    @CurrentTimestamp
    private LocalDateTime createdAt;

    public ProcessingLog(User user, String inputText, String outputText) {
        this.user = user;
        this.inputText = inputText;
        this.outputText = outputText;
    }
}
