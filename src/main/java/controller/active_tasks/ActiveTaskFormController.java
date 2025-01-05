package controller.active_tasks;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import controller.completed_tasks.CompletedTaskController;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Active_Tasks;
import model.Completed_Tasks;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ActiveTaskFormController implements Initializable {

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colTitle;

    @FXML
    private DatePicker datePiker;

    @FXML
    private TableView tblActiveTasks;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXCheckBox checkbox;

    private JButton btnDeleteOnAction;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datePiker.getValue().format(formatter);
        boolean isAdded = ActiveTaskController.getInstance().saveTasks(new Active_Tasks(Integer.parseInt(txtId.getText()), txtTitle.getText(), txtDesc.getText(), formattedDate));
        if(isAdded){
            new Alert(Alert.AlertType.INFORMATION,"Task Added Succesfully").show();
            txtId.setText("");
            txtTitle.setText("");
            txtDesc.setText("");
            datePiker.setValue(null) ;
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Task Added Failed").show();
        }
        LoadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(checkbox.isSelected()==true){
            if(ActiveTaskController.getInstance().deleteTasks(txtId.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Task Completed").show();
                CompletedTable();
                txtId.setText("");
                txtTitle.setText("");
                txtDesc.setText("");
                datePiker.setValue(null) ;
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Task Not Completed").show();
            }
        }
        LoadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        LoadTable();

        tblActiveTasks.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue!=null){
                        setTextToValues((Active_Tasks) newValue);
                    }
                });
    }
    public Completed_Tasks completedTasks;
    public void setTextToValues(Active_Tasks tasks){
        completedTasks = new Completed_Tasks(tasks.getId(), tasks.getTitle(), tasks.getDescription(), tasks.getDate());
        txtId.setText(String.valueOf(tasks.getId()));
        txtTitle.setText(tasks.getTitle());
        txtDesc.setText(tasks.getDescription());
        datePiker.setValue(LocalDate.parse(tasks.getDate())) ;
    }

    public void LoadTable(){
        ObservableList<Active_Tasks> activeTasksObservableList = FXCollections.observableArrayList();
        activeTasksObservableList.addAll(ActiveTaskController.getInstance().getAllActiveTasks());
        tblActiveTasks.setItems(activeTasksObservableList);
    }

    public void CompletedTable()  {
            try {
                PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Insert into completed_tasks values(?,?,?,?)");
                stm.setObject(1,completedTasks.getId());
                stm.setObject(2,completedTasks.getTitle());
                stm.setObject(3,completedTasks.getDescription());
                stm.setObject(4,completedTasks.getDate());
                stm.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



    public void btnCompletedOnAction(ActionEvent actionEvent) throws IOException {
            Stage Primarystage = new Stage();
            Primarystage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/completed_tasks.fxml"))));
        Primarystage.show();
        }
}
