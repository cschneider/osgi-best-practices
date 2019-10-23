package net.lr.osgibp.app;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

public class RestServiceIntegrationTest {

    @Rule
    public ServiceRule service = new ServiceRule();

    @Test
    public void testGet() throws Exception {
        TaskService taskService = service.require(TaskService.class);
        Task task = taskService.getById(1);

        assertEquals(1, task.getId().intValue());
    }
}