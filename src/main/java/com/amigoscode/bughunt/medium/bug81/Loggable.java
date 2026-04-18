package com.amigoscode.bughunt.medium.bug81;

/**
 * An interface for components that can produce a logging tag.
 */
public interface Loggable {

    /**
     * Returns the name of this component.
     */
    String name();

    /**
     * Returns a tag prefixed with "LOG:" for use in log output.
     *
     * @return a tag in the form "LOG:name"
     */
    default String tag() {
        return "LOG:" + name();
    }
}
