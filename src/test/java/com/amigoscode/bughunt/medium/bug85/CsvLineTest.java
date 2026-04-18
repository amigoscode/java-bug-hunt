package com.amigoscode.bughunt.medium.bug85;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvLineTest {

    private final CsvLine csv = new CsvLine();

    @Test
    void buildShouldJoinFieldsWithComma() {
        String line = csv.build("a", "b", "c");

        assertThat(line).isEqualTo("a,b,c");
    }

    @Test
    void buildShouldRenderNullAsEmpty() {
        String line = csv.build("a", null, "c");

        // BUG: returns "a,null,c" instead of "a,,c"
        assertThat(line).isEqualTo("a,,c");
    }

    @Test
    void buildShouldHandleLeadingNull() {
        String line = csv.build(null, "b", "c");

        // BUG: returns "null,b,c" instead of ",b,c"
        assertThat(line).isEqualTo(",b,c");
    }

    @Test
    void buildFromListShouldRenderNullAsEmpty() {
        List<String> fields = Arrays.asList("x", null, null, "y");

        String line = csv.buildFromList(fields);

        // BUG: returns "x,null,null,y" instead of "x,,,y"
        assertThat(line).isEqualTo("x,,,y");
    }

    @Test
    void countPopulatedShouldExcludeNulls() {
        int count = csv.countPopulated("a", null, "c", null);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void sanitiseShouldReplaceNullsWithEmpty() {
        List<String> result = csv.sanitise("a", null, "c");

        assertThat(result).containsExactly("a", "", "c");
    }
}
