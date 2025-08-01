package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.time.Instant;
import java.util.Set;

@Entity
public class User {
    @Id
    private Long id;

    private String name;

    private String email;

    private Long age;

    private Instant created;

    @ManyToMany()
    @JoinTable(
            name = "user_roles",
            joinColumns = @jakarta.persistence.JoinColumn(name = "user_id"),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }
}
