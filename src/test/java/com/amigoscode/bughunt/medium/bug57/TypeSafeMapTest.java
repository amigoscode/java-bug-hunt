package com.amigoscode.bughunt.medium.bug57;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeSafeMapTest {

    @Test
    void getWithCorrectTypeReturnsValue() {
        TypeSafeMap map = new TypeSafeMap();
        map.put("name", "Alice");

        String name = map.get("name", String.class);

        assertThat(name).isEqualTo("Alice");
    }

    @Test
    void getWithWrongTypeShouldReturnCorrectType() {
        TypeSafeMap map = new TypeSafeMap();
        map.put("age", "twenty-five"); // stored as String

        // A truly type-safe get should either throw or return an Integer.
        // With the bug, it silently returns the String disguised as Integer.
        Object result = map.get("age", Integer.class);

        // The returned object should actually be an Integer (or a CCE
        // should have been thrown above). With the bug, it's a String.
        assertThat(result).isInstanceOf(Integer.class);
    }

    @Test
    void getOptionalWithWrongTypeShouldReturnCorrectType() {
        TypeSafeMap map = new TypeSafeMap();
        map.put("count", "not-a-number");

        // getOptional should enforce the type as well
        Object result = map.getOptional("count", Integer.class)
                .orElse(null);

        assertThat(result).isInstanceOf(Integer.class);
    }

    @Test
    void getMissingKeyReturnsNull() {
        TypeSafeMap map = new TypeSafeMap();

        String result = map.get("missing", String.class);

        assertThat(result).isNull();
    }
}
