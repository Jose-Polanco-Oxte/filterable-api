package metamodels;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import models.Preferences;
import models.Role;
import utils.AttributeMock;

@StaticMetamodel(Role.class)
public class Role_ {
    public static SingularAttribute<Role, Integer> id = AttributeMock.of("id", Integer.class);
    public static SingularAttribute<Role, String> name = AttributeMock.of("name", String.class);
    public static ListAttribute<Role, Preferences> permissions = AttributeMock.ofList("permissions", Preferences.class);
}
