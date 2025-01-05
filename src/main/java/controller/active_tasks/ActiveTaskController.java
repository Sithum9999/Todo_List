package controller.active_tasks;

import db.DBConnection;
import model.Active_Tasks;
import model.Completed_Tasks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiveTaskController implements ActiveTaskService{
    private static ActiveTaskController activeTaskController;

    private ActiveTaskController() {
    }

    public static ActiveTaskController getInstance(){
        if (activeTaskController==null){
            activeTaskController=new ActiveTaskController();
        }
        return activeTaskController;
    }

    @Override
    public List<Active_Tasks> getAllActiveTasks() {
        List<Active_Tasks> activeTasks = new ArrayList<>();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from active_tasks");
            while(res.next()){
                activeTasks.add(new Active_Tasks(res.getInt(1), res.getString(2), res.getString(3),res.getString(4) ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activeTasks;
    }

    @Override
    public boolean saveTasks(Active_Tasks task)  {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Insert into active_tasks values (?,?,?,?)");
            stm.setObject(1,task.getId());
            stm.setObject(2,task.getTitle());
            stm.setObject(3,task.getDescription());
            stm.setObject(4,task.getDate());
            return stm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteTasks(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("delete from active_tasks where task_id= ?");
            stm.setObject(1,id);
            return stm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
