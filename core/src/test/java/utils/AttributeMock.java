package utils;

import jakarta.persistence.metamodel.*;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttributeMock {
    public static <T, V> SingularAttribute<T, V> of(String name, Class<V> type) {
        SingularAttribute<T, V> attribute = mock(SingularAttribute.class);
        when(attribute.getName()).thenReturn(name);
        when(attribute.getJavaType()).thenReturn(type);
        return attribute;
    }

    @SuppressWarnings("unchecked")
    public static <T, V> SetAttribute<T, V> ofSet(String name, Class<V> type) {
        SetAttribute<T, V> attribute = mock(SetAttribute.class);
        when(attribute.getName()).thenReturn(name);
        when(attribute.getJavaType()).thenReturn((Class<Set<V>>) (Class<?>) Set.class);
        when(attribute.getElementType()).thenReturn(new Type<>() {
            @Override
            public PersistenceType getPersistenceType() {
                return PersistenceType.BASIC;
            }

            @Override
            public Class<V> getJavaType() {
                return type;
            }
        });
        when(attribute.getCollectionType()).thenReturn(PluralAttribute.CollectionType.SET);
        return attribute;
    }

    @SuppressWarnings("unchecked")
    public static <T, V>ListAttribute<T, V> ofList(String name, Class<V> type) {
        ListAttribute<T, V> attribute = mock(ListAttribute.class);
        when(attribute.getName()).thenReturn(name);
        when(attribute.getJavaType()).thenReturn((Class<List<V>>) (Class<?>) List.class);
        when(attribute.getElementType()).thenReturn(new Type<>() {
            @Override
            public PersistenceType getPersistenceType() {
                return PersistenceType.BASIC;
            }

            @Override
            public Class<V> getJavaType() {
                return type;
            }
        });
        when(attribute.getCollectionType()).thenReturn(PluralAttribute.CollectionType.LIST);
        return attribute;
    }
}
