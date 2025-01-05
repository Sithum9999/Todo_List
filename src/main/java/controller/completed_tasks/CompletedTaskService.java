package controller.completed_tasks;

import model.Active_Tasks;
import model.Completed_Tasks;

import java.sql.SQLException;
import java.util.List;

public interface CompletedTaskService {
    List<Completed_Tasks> getAllCompletedTasks();
}
