package vector.TaskSync.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "blank should no be empty")
    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    @NotBlank(message = " Status can not be be null")
    private String status; //e.g To do, in progress and done

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name= "assignee_id")
    private User assignee;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    @OneToMany(mappedBy = "task")
    private List<Attachment> attachments;
}
