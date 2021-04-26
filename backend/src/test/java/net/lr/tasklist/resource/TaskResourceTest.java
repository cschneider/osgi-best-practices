package net.lr.tasklist.resource;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.UriBuilderImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codahale.metrics.MetricRegistry;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

/**
 * DS components with service dependencies or like here even JAX-RS
 * can be tested using mockito.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskResourceTest {
    private static final String RESOURCE_PATH = "http://localhost:8080/tasks";

    @Mock
    TaskService taskService;
    
    @Mock
    MetricRegistry metricRegistry;
    
    @Mock
    UriInfo uriInfo;
    
    @InjectMocks
    TaskResource taskRes;

    private Task task1;

    @Before
    public void before() {
        task1 = task1();
        taskRes.uriInfo = uriInfo;
    }

    @Test
    public void testGetAll() {
        List<Task> expectedTasks = Collections.singletonList(task1);
        when(taskService.getAll()).thenReturn(expectedTasks);
        
        Collection<Task> tasks = taskRes.getTasks();

        assertThat(tasks, contains(task1));
    }
    
    @Test
    public void testAdd() throws IllegalArgumentException, URISyntaxException {
        UriBuilder uriBuilder = new UriBuilderImpl(new URI(RESOURCE_PATH));
        when(uriInfo.getRequestUriBuilder()).thenReturn(uriBuilder);
        
        Response resp = taskRes.addTask(task1);
        
        verify(taskService).addOrUpdate(task1);
        assertThat(resp.getStatus(), equalTo(201));
        URI expectedRedirect = new URI(RESOURCE_PATH + "/1");
        assertThat(resp.getHeaders().get("Location"), contains(expectedRedirect));
    }

    @Test
    public void testGet() {
        when(taskService.getById(1)).thenReturn(task1);
        
        Response resp = taskRes.getTask(1);

        assertThat(resp.getStatus(), equalTo(200));
        assertThat(resp.getEntity(), equalTo(task1));
    }
    
    @Test
    public void testGetNotFound() {
        Response resp = taskRes.getTask(1);
        
        assertThat(resp.getStatus(), equalTo(404));
    }
    
    @Test
    public void testUpdate() {
        taskRes.updateTask(1, task1);
        
        verify(taskService).addOrUpdate(task1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testUpdateIllegal() {
        taskRes.updateTask(2, task1);
    }

    @Test
    public void testDelete() {
        taskRes.deleteTask(1);
        
        verify(taskService).delete(1);
    }

    private Task task1() {
        return Task.builder().id(1).title("Task id").description("desc").build();
    }
}
