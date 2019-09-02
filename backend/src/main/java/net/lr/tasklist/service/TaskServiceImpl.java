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
		tasks.put(1, new Task(1, "Buy some Coffee", "The good stuff"));
		tasks.put(2, new Task(2, "Finish demo", "JAX-RS issues"));
	}

    @Override
    public Task getTask(Integer id) {
        return tasks.get(id);
    }

    @Override
    public void addTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Id property must be set");
        }
        System.err.println("Adding task " + task.getId());
        tasks.put(task.getId(), task);
    }

    @Override
    public Collection<Task> getTasks() {
    	return tasks.values();
    }

    @Override
    public void updateTask(Task task) {
    	tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(Integer id) {
    	tasks.remove(id);
    }

}
