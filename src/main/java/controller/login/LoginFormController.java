package controller.login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Login;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
            boolean Login = LoginController.getInstance().getAllLogin(txtUsername.getText(), txtPassword.getText());
            System.out.println(Login);
            if(Login){
                new Alert(Alert.AlertType.INFORMATION,"Login Successful");
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/active_tasks.fxml"))));
                stage.show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Login Failed");
            }
        }
    }
