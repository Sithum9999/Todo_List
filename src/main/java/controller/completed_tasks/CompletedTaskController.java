package controller.completed_tasks;

import controller.active_tasks.ActiveTaskController;
import db.DBConnection;
import model.Active_Tasks;
import model.Completed_Tasks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompletedTaskController implements CompletedTaskService{
    private static CompletedTaskController completedTaskController;

    private CompletedTaskController() {
    }

    public static CompletedTaskController getInstance(){
        if (completedTaskController==null){
            completedTaskController=new CompletedTaskController();
        }
        return completedTaskController;
    }
    @Override
    public List<Completed_Tasks> getAllCompletedTasks() {
        List<Completed_Tasks> completedTasks = new ArrayList<>();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from completed_tasks");
            while (res.next()){
                completedTasks.add(new Completed_Tasks(res.getInt(1), res.getString(2), res.getString(3),res.getString(4) ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  completedTasks;
    }
}
