package net.lr.tasklist.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

/**
 * TaskService has no service dependencies. So we can test it using a plain
 * junit test.
 */
public class TaskServiceImplTest {

    private TaskService taskService = new TaskServiceImpl();

    @Test
    public void testGet() {
        Task task = taskService.getById(1);
        assertThat(task.getTitle(), equalTo("Buy some Coffee"));
    }
    
    @Test
    public void testNotFound() {
        Task task = taskService.getById(3);
        assertThat(task, nullValue());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegal() {
        Task task = new Task();
        taskService.addOrUpdate(task);
    }
    
    @Test
    public void testAdd() {
        assertThat(taskService.getAll().size(), equalTo(2));
        Task task = Task.builder().id(3).title("Task id").description("desc").build();
        taskService.addOrUpdate(task);
        Collection<Task> tasks = taskService.getAll();
        assertThat(tasks.size(), equalTo(3));
    }
    
    @Test
    public void deleteTask() {
        taskService.delete(1);
        assertThat(taskService.getById(1), nullValue());
    }
}
