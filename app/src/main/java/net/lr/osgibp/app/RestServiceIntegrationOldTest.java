package net.lr.osgibp.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

public class RestServiceIntegrationOldTest {
    
    private ServiceTracker<TaskService, TaskService> taskServiceTracker;

    private TaskService taskService;

    @Before
    public void setUp() throws Exception {
        Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        assertNotNull("OSGi Bundle tests must be run inside an OSGi framework", bundle);
        taskServiceTracker = new ServiceTracker<>(bundle.getBundleContext(), TaskService.class, null);
        taskServiceTracker.open();
        taskService = taskServiceTracker.waitForService(1000);
    }
    
    @After
    public void tearDown() throws Exception {
        taskServiceTracker.close();
    }
    
    @Test
    public void testGet() throws Exception {
        Task task = taskService.getById(1);

        assertEquals(1, task.getId().intValue());
    }
}