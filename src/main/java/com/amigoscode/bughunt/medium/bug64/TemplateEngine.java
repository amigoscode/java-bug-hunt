package com.amigoscode.bughunt.medium.bug64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple template engine that replaces ${key} placeholders
 * in a template string with values from a variable map.
 */
public class TemplateEngine {

    private final Map<String, String> globalVariables = new HashMap<>();

    /**
     * Registers a global variable available to all templates.
     */
    public void setGlobalVariable(String key, String value) {
        globalVariables.put(key, value);
    }

    /**
     * Renders a template by replacing all ${key} placeholders with
     * corresponding values from the provided variables map,
     * falling back to global variables.
     *
     * @param template the template string containing ${key} placeholders
     * @param variables local variables for this render
     * @return the rendered string
     */
    public String render(String template, Map<String, String> variables) {
        Map<String, String> merged = new HashMap<>(globalVariables);
        merged.putAll(variables);

        String result = template;
        for (Map.Entry<String, String> entry : merged.entrySet()) {
            // BUG: replaceAll treats the first argument as a regex pattern.
            // "${key}" contains $ and { which are regex metacharacters,
            // causing a PatternSyntaxException.
            // Should use replace() (literal) instead of replaceAll() (regex).
            result = result.replaceAll("${" + entry.getKey() + "}", entry.getValue());
        }

        return result;
    }

    /**
     * Renders a template using only global variables.
     */
    public String renderWithGlobals(String template) {
        return render(template, Map.of());
    }

    /**
     * Finds all placeholder keys in the template that have no value.
     */
    public List<String> findUnresolvedPlaceholders(String template, Map<String, String> variables) {
        Map<String, String> merged = new HashMap<>(globalVariables);
        merged.putAll(variables);

        List<String> unresolved = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1);
            if (!merged.containsKey(key)) {
                unresolved.add(key);
            }
        }

        return unresolved;
    }

    /**
     * Returns the number of registered global variables.
     */
    public int globalVariableCount() {
        return globalVariables.size();
    }

    /**
     * Clears all global variables.
     */
    public void clearGlobalVariables() {
        globalVariables.clear();
    }
}
