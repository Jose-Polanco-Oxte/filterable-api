package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Role {

    @Id
    private Long id;

    private String name;

    @ManyToMany()
    List<Preferences> preferences;
}
