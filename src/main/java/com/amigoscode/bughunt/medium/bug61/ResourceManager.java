package com.amigoscode.bughunt.medium.bug61;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages lifecycle of closeable resources during processing.
 * Opens a resource via a factory, performs work, and ensures cleanup.
 */
public class ResourceManager {

    private final List<String> auditLog = new ArrayList<>();

    /**
     * Functional interface for creating closeable resources.
     */
    public interface ResourceFactory {
        Closeable open() throws IOException;
    }

    /**
     * Functional interface representing work performed on a resource.
     */
    public interface ResourceWorker {
        String execute(Closeable resource) throws Exception;
    }

    /**
     * Opens a resource from the factory, performs work using the worker,
     * and closes the resource afterwards.
     *
     * @param factory the factory that provides the resource
     * @param worker  the worker that processes the resource
     * @return the result of processing
     * @throws Exception if opening, processing, or closing fails
     */
    public String process(ResourceFactory factory, ResourceWorker worker) throws Exception {
        auditLog.add("OPEN");
        Closeable resource = factory.open();

        String result = worker.execute(resource);

        // BUG: if worker.execute() throws, this line is never reached
        // and the resource is never closed
        resource.close();
        auditLog.add("CLOSE");

        return result;
    }

    /**
     * Processes multiple resources sequentially, collecting results.
     *
     * @param factories list of resource factories
     * @param worker    the worker applied to each resource
     * @return list of results from each resource
     * @throws Exception if any processing fails
     */
    public List<String> processAll(List<ResourceFactory> factories, ResourceWorker worker) throws Exception {
        List<String> results = new ArrayList<>();
        for (ResourceFactory factory : factories) {
            results.add(process(factory, worker));
        }
        return results;
    }

    /**
     * Returns the audit log of open/close events.
     */
    public List<String> getAuditLog() {
        return new ArrayList<>(auditLog);
    }

    /**
     * Clears the audit log.
     */
    public void clearAuditLog() {
        auditLog.clear();
    }

    /**
     * Returns the number of operations logged.
     */
    public int auditLogSize() {
        return auditLog.size();
    }
}
