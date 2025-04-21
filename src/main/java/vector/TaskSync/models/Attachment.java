package vector.TaskSync.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl; //url to cloud storage

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
