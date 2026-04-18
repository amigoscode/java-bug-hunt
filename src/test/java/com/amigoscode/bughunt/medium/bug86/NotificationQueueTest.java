package com.amigoscode.bughunt.medium.bug86;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class NotificationQueueTest {

    @Test
    void shouldStartWithDefaultNotification() {
        NotificationQueue queue = new NotificationQueue("Welcome");

        assertThat(queue.size()).isEqualTo(1);
        assertThat(queue.peek()).isEqualTo("Welcome");
    }

    @Test
    void shouldAllowEnqueuingAfterConstruction() {
        NotificationQueue queue = new NotificationQueue("Welcome");

        // BUG: throws UnsupportedOperationException because singletonList is immutable
        assertThatNoException().isThrownBy(() -> queue.enqueue("New alert"));

        assertThat(queue.size()).isEqualTo(2);
    }

    @Test
    void shouldDequeueInFifoOrder() {
        NotificationQueue queue = new NotificationQueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        assertThat(queue.dequeue()).isEqualTo("First");
        assertThat(queue.dequeue()).isEqualTo("Second");
        assertThat(queue.dequeue()).isEqualTo("Third");
    }

    @Test
    void shouldContainEnqueuedNotification() {
        NotificationQueue queue = new NotificationQueue("Init");
        queue.enqueue("Alert");

        assertThat(queue.contains("Alert")).isTrue();
    }

    @Test
    void shouldReportCorrectSizeAfterMultipleEnqueues() {
        NotificationQueue queue = new NotificationQueue("Init");
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertThat(queue.size()).isEqualTo(4);
    }
}
