package com.amigoscode.bughunt.medium.bug89;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Builds and renders a simple HTML form from a set of labelled input fields.
 * Fields should appear in the rendered HTML in the order they were added.
 *
 * <p>BUG: Uses a {@link HashMap} to store the fields. {@code HashMap} does
 * not guarantee insertion order, so the rendered form may reorder the fields
 * unpredictably. The fix is to use {@link java.util.LinkedHashMap}.
 */
public class FormRenderer {

    private final String formTitle;
    // BUG: HashMap does not preserve insertion order
    private final Map<String, String> fields = new HashMap<>();

    /**
     * Creates a new form renderer with the given title.
     *
     * @param formTitle the form title shown in the heading
     */
    public FormRenderer(String formTitle) {
        this.formTitle = Objects.requireNonNull(formTitle, "formTitle must not be null");
    }

    /**
     * Adds a text input field to the form.
     *
     * @param label       the label displayed next to the input
     * @param placeholder the placeholder text inside the input
     * @return this renderer for chaining
     */
    public FormRenderer addField(String label, String placeholder) {
        Objects.requireNonNull(label, "label must not be null");
        Objects.requireNonNull(placeholder, "placeholder must not be null");
        fields.put(label, placeholder);
        return this;
    }

    /**
     * Renders the form as an HTML string. Fields should appear in insertion
     * order.
     *
     * @return the HTML form string
     */
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<form>\n");
        sb.append("  <h2>").append(formTitle).append("</h2>\n");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            sb.append("  <label>").append(entry.getKey()).append("</label>\n");
            sb.append("  <input placeholder=\"").append(entry.getValue()).append("\" />\n");
        }
        sb.append("</form>");
        return sb.toString();
    }

    /**
     * Returns a comma-separated string of the field labels in the order
     * they appear during iteration.
     *
     * @return labels joined by commas
     */
    public String labelSummary() {
        StringJoiner joiner = new StringJoiner(", ");
        for (String label : fields.keySet()) {
            joiner.add(label);
        }
        return joiner.toString();
    }

    /**
     * Returns the number of fields currently in the form.
     *
     * @return the field count
     */
    public int fieldCount() {
        return fields.size();
    }

    /**
     * Checks if a field with the given label exists.
     *
     * @param label the label to look for
     * @return true if the field exists
     */
    public boolean hasField(String label) {
        return fields.containsKey(label);
    }

    /**
     * Removes a field by label.
     *
     * @param label the label to remove
     * @return true if a field was removed
     */
    public boolean removeField(String label) {
        return fields.remove(label) != null;
    }

    @Override
    public String toString() {
        return "FormRenderer{title='" + formTitle + "', fields=" + fields.size() + "}";
    }
}
