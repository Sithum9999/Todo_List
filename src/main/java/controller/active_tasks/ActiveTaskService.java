package controller.active_tasks;

import model.Active_Tasks;
import model.Completed_Tasks;

import java.sql.SQLException;
import java.util.List;

public interface ActiveTaskService {

    List<Active_Tasks> getAllActiveTasks();

    boolean saveTasks(Active_Tasks task) throws SQLException;

    boolean deleteTasks(String id);

}
