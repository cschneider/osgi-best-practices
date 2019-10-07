package net.lr.tasklist.model;

import java.util.Collection;

/**
 * Manages a set of tasks by id
 */
public interface TaskService {
    Task getById(Integer id);

    void addOrUpdate(Task task);

    void delete(Integer id);
    
    Collection<Task> getAll();
}
