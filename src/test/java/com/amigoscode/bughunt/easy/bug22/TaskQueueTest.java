package com.amigoscode.bughunt.easy.bug22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskQueueTest {

    @Test
    void cancelRemovesMatchingTaskId() {
        TaskQueue q = new TaskQueue("jobs");
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        q.cancel(20);

        assertThat(q.remaining()).containsExactly(10, 30);
    }

    @Test
    void cancelNonExistentDoesNotThrow() {
        TaskQueue q = new TaskQueue("jobs");
        q.enqueue(10);

        q.cancel(999);

        assertThat(q.remaining()).containsExactly(10);
    }
}
