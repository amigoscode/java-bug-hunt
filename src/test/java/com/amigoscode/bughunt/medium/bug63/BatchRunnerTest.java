package com.amigoscode.bughunt.medium.bug63;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BatchRunnerTest {

    @Test
    void originalExceptionIsPreservedWhenCleanupAlsoFails() {
        BatchRunner.BatchJob job = new BatchRunner.BatchJob() {
            @Override
            public void initialize() {}

            @Override
            public String process() throws Exception {
                throw new ProcessingException("data corrupted");
            }

            @Override
            public void cleanup() throws Exception {
                throw new CleanupException("temp files locked");
            }
        };

        BatchRunner runner = new BatchRunner();

        // The original ProcessingException should be the one thrown to the caller,
        // not the CleanupException from cleanup()
        assertThatThrownBy(() -> runner.execute(job))
                .isInstanceOf(ProcessingException.class)
                .hasMessage("data corrupted");
    }

    @Test
    void cleanupExceptionIsAccessibleAsSuppressed() {
        BatchRunner.BatchJob job = new BatchRunner.BatchJob() {
            @Override
            public void initialize() {}

            @Override
            public String process() throws Exception {
                throw new ProcessingException("parse error");
            }

            @Override
            public void cleanup() throws Exception {
                throw new CleanupException("stream not closed");
            }
        };

        BatchRunner runner = new BatchRunner();

        assertThatThrownBy(() -> runner.execute(job))
                .isInstanceOf(ProcessingException.class)
                .satisfies(thrown -> {
                    assertThat(thrown.getSuppressed()).hasSize(1);
                    assertThat(thrown.getSuppressed()[0])
                            .isInstanceOf(CleanupException.class)
                            .hasMessage("stream not closed");
                });
    }

    @Test
    void successfulExecutionReturnsResult() throws Exception {
        BatchRunner.BatchJob job = new BatchRunner.BatchJob() {
            @Override
            public void initialize() {}

            @Override
            public String process() {
                return "batch complete";
            }

            @Override
            public void cleanup() {}
        };

        BatchRunner runner = new BatchRunner();
        String result = runner.execute(job);

        assertThat(result).isEqualTo("batch complete");
        assertThat(runner.lastRunFullyCompleted()).isTrue();
    }

    @Test
    void cleanupIsCalledEvenWhenProcessingFails() {
        boolean[] cleanupCalled = {false};

        BatchRunner.BatchJob job = new BatchRunner.BatchJob() {
            @Override
            public void initialize() {}

            @Override
            public String process() throws Exception {
                throw new ProcessingException("failed");
            }

            @Override
            public void cleanup() {
                cleanupCalled[0] = true;
            }
        };

        BatchRunner runner = new BatchRunner();

        assertThatThrownBy(() -> runner.execute(job))
                .isInstanceOf(ProcessingException.class);

        assertThat(cleanupCalled[0])
                .describedAs("cleanup should be called even when processing fails")
                .isTrue();
    }

    static class ProcessingException extends Exception {
        public ProcessingException(String message) {
            super(message);
        }
    }

    static class CleanupException extends Exception {
        public CleanupException(String message) {
            super(message);
        }
    }
}
