package com.amigoscode.bughunt.medium.bug61;

import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResourceManagerTest {

    @Test
    void resourceIsClosedWhenWorkerThrowsException() {
        AtomicBoolean closed = new AtomicBoolean(false);
        Closeable trackingResource = () -> closed.set(true);

        ResourceManager.ResourceFactory factory = () -> trackingResource;
        ResourceManager.ResourceWorker failingWorker = resource -> {
            throw new RuntimeException("processing failed");
        };

        ResourceManager manager = new ResourceManager();

        assertThatThrownBy(() -> manager.process(factory, failingWorker))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("processing failed");

        // Resource must be closed even when worker throws
        assertThat(closed.get())
                .describedAs("Resource should be closed even when processing throws")
                .isTrue();
    }

    @Test
    void resourceIsClosedOnSuccessfulProcessing() throws Exception {
        AtomicBoolean closed = new AtomicBoolean(false);
        Closeable trackingResource = () -> closed.set(true);

        ResourceManager.ResourceFactory factory = () -> trackingResource;
        ResourceManager.ResourceWorker worker = resource -> "done";

        ResourceManager manager = new ResourceManager();
        String result = manager.process(factory, worker);

        assertThat(result).isEqualTo("done");
        assertThat(closed.get()).isTrue();
    }

    @Test
    void auditLogRecordsCloseEvenOnFailure() {
        Closeable trackingResource = () -> {};
        ResourceManager.ResourceFactory factory = () -> trackingResource;
        ResourceManager.ResourceWorker failingWorker = resource -> {
            throw new IOException("disk error");
        };

        ResourceManager manager = new ResourceManager();

        assertThatThrownBy(() -> manager.process(factory, failingWorker))
                .isInstanceOf(IOException.class);

        // Audit log should show both OPEN and CLOSE
        assertThat(manager.getAuditLog()).containsExactly("OPEN", "CLOSE");
    }
}
