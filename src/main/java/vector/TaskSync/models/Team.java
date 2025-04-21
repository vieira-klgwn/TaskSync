package vector.TaskSync.models;


import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "teams")
    private List<User> members;

    @OneToMany(mappedBy = "team")
    private List<Task> tasks;

}
