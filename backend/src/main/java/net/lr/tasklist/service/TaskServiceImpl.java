package net.lr.tasklist.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import net.lr.tasklist.model.Task;
import net.lr.tasklist.model.TaskService;

@Component
public class TaskServiceImpl implements TaskService {
    private Map<Integer, Task> tasks = new HashMap<>();

    public TaskServiceImpl() {
        tasks.put(1, Task.builder().id(1).title("Buy some Coffee").description("The good stuff").build());
        tasks.put(2, Task.builder().id(2).title("Finish demo").description("JAX-RS issues").build());
    }
    
    @Override
    public Task getById(Integer id) {
        return tasks.get(id);
    }

    @Override
    public void addOrUpdate(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Id property must be set");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public Collection<Task> getAll() {
        return tasks.values();
    }

    @Override
    public void delete(Integer id) {
        tasks.remove(id);
    }

}
