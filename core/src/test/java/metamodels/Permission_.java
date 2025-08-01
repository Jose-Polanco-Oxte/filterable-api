package metamodels;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import models.Preferences;
import models.Preferences.Theme;
import utils.AttributeMock;

@StaticMetamodel(Preferences.class)
public class Permission_ {
    public static SingularAttribute<Preferences, Integer> id = AttributeMock.of("id", Integer.class);
    public static SingularAttribute<Preferences, Boolean> canRead = AttributeMock.of("canRead", Boolean.class);
    public static SingularAttribute<Preferences, Boolean> canWrite = AttributeMock.of("canWrite", Boolean.class);
    public static SingularAttribute<Preferences, Theme> theme = AttributeMock.of("theme", Theme.class);
}
