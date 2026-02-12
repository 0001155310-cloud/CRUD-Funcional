package com.senai.crudjavafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField nomeUsuario;
    @FXML private PasswordField senhaUsuario;

    @FXML
    public void realizarLogin() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/senai/crudjavafx/MainLayout.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nomeUsuario.getScene().getWindow();
            Scene scene = new Scene(root, 850, 500);

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão - Home");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar o MainLayout!");
            e.printStackTrace();
        }
    }
}