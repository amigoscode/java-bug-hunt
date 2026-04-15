package com.amigoscode.bughunt.easy.bug22;

import java.util.ArrayList;
import java.util.List;

public class TaskQueue {

    private final List<Integer> taskIds = new ArrayList<>();
    private final String queueName;

    public TaskQueue(String queueName) {
        this.queueName = queueName;
    }

    public void enqueue(int taskId) {
        taskIds.add(taskId);
    }

    public void cancel(int taskId) {
        taskIds.remove(taskId);
    }

    public int size() {
        return taskIds.size();
    }

    public List<Integer> remaining() {
        return List.copyOf(taskIds);
    }

    public String status() {
        return queueName + ": " + size() + " pending";
    }
}
