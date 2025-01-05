package controller.completed_tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Completed_Tasks;

import java.net.URL;
import java.util.ResourceBundle;

public class CompletedTaskFormController implements Initializable {

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colTitle;

    @FXML
    private TableView tblCompletedTasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        LoadTable();
    }
    public void LoadTable(){
        ObservableList<Completed_Tasks> completedTasksObservableList = FXCollections.observableArrayList();
        completedTasksObservableList.addAll(CompletedTaskController.getInstance().getAllCompletedTasks());
        tblCompletedTasks.setItems(completedTasksObservableList);
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        LoadTable();
    }
}
