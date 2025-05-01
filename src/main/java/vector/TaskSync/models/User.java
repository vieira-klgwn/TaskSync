package vector.TaskSync.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore // Prevent serialization of objects. In case you don't put this, you fall in a circle of responses when you request for the response
    private List<Token> tokens;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
    name = "user_teams",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @JsonIgnore
    private List<Team> teams;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
