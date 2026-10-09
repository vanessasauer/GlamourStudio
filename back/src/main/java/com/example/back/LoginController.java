package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private String key = "ksdflmskjfjijrjmnggjmigmj90iijmgr";


    @FXML
    private TextField txtKey;

    @FXML //Função que será usada no nosso loggin-view
    protected void onLoginButtonClick(ActionEvent event) throws IOException {

        //se for igual a key, será um login realizado com sucesso
        if (txtKey.getText().equals(key)) {
            showMessage(Alert.AlertType.INFORMATION, "Login Efetuado com sucesso!");

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));

            //É o que faz trocar de uma tela para outra
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);


        } else {

            showMessage(Alert.AlertType.ERROR, "Erro ao efetuar login!");

        }
    }


    private void showMessage(Alert.AlertType type, String msg) {
        Alert alerta = new Alert(type);

        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

}
