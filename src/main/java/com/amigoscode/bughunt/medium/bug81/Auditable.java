package com.amigoscode.bughunt.medium.bug81;

/**
 * An interface for components that can produce an audit tag.
 */
public interface Auditable {

    /**
     * Returns the name of this component.
     */
    String name();

    /**
     * Returns a tag prefixed with "AUDIT:" for use in audit trails.
     *
     * @return a tag in the form "AUDIT:name"
     */
    default String tag() {
        return "AUDIT:" + name();
    }
}
