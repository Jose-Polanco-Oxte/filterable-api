package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Preferences {

    @Id
    private Long id;

    private boolean canCreate;

    private boolean canRead;

    private Theme theme;


    public enum Theme {
        LIGHT, DARK, SYSTEM
    }
}
