package metamodels;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import models.Role;
import models.User;
import utils.AttributeMock;

import java.time.Instant;

@StaticMetamodel(User.class)
public class User_ {
    public static SingularAttribute<User, String> name = AttributeMock.of("name", String.class);
    public static SingularAttribute<User, String> email = AttributeMock.of("email", String.class);
    public static SingularAttribute<User, Integer> age = AttributeMock.of("age", Integer.class);
    public static SingularAttribute<User, Long> id = AttributeMock.of("id", Long.class);
    public static SingularAttribute<User, Instant> created = AttributeMock.of("created", Instant.class);
    public static SetAttribute<User, Role> roles = AttributeMock.ofSet("roles", Role.class);
}