package vector.TaskSync.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Data
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;


    private Integer progress;


    private String description;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Team team;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
