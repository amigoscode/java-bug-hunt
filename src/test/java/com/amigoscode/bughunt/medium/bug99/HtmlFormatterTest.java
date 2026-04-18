package com.amigoscode.bughunt.medium.bug99;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlFormatterTest {

    @Test
    void formatShouldWrapInParagraphTagsViaParentReference() {
        Formatter formatter = new HtmlFormatter("web", true);

        // BUG: static methods are resolved by reference type, not runtime type.
        // Because `formatter` is declared as `Formatter`, this calls
        // Formatter.format() (plain text), not HtmlFormatter.format().
        String result = formatter.format("hello");

        assertThat(result).isEqualTo("<p>hello</p>");
    }

    @Test
    void formatBlockShouldWrapInDivTagsViaParentReference() {
        Formatter formatter = new HtmlFormatter("web", false);

        // BUG: calls Formatter.formatBlock() instead of HtmlFormatter.formatBlock()
        String result = formatter.formatBlock("content");

        assertThat(result).isEqualTo("<div>content</div>");
    }

    @Test
    void formatShouldWorkDirectlyOnHtmlFormatter() {
        // When called directly on HtmlFormatter type, it works correctly
        String result = HtmlFormatter.format("hello");

        assertThat(result).isEqualTo("<p>hello</p>");
    }

    @Test
    void typeShouldReturnHtml() {
        // Instance method override works fine (not static)
        Formatter formatter = new HtmlFormatter("web", true);

        assertThat(formatter.type()).isEqualTo("html");
    }

    @Test
    void formatHeadingShouldWrapInHeadingTags() {
        String result = HtmlFormatter.formatHeading("Title", 1);

        assertThat(result).isEqualTo("<h1>Title</h1>");
    }
}
