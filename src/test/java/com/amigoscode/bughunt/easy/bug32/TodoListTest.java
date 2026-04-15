package com.amigoscode.bughunt.easy.bug32;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TodoListTest {

    @Test
    void containsItemAfterAdd() {
        TodoList list = new TodoList("work");
        TodoList.Item i = list.add("buy milk");

        assertThat(list.contains(i)).isTrue();
    }

    @Test
    void containsItemAfterMarkingDone() {
        TodoList list = new TodoList("work");
        TodoList.Item i = list.add("buy milk");
        i.markDone();

        assertThat(list.contains(i)).isTrue();
    }
}
