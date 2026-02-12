package com.senai.crudjavafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController {
    @FXML
    private BorderPane root;

    private static BorderPane staticRoot;

    @FXML
    public void initialize() {
        staticRoot = root; // Guarda a referência para uso do mudarTela
        carregarTela("Home.fxml");
    }

    public static void mudarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/com/senai/crudjavafx/" + fxml));
            Parent novaTela = loader.load();
            if (staticRoot != null) {
                staticRoot.setCenter(novaTela);
            }
        } catch (Exception e) {
            System.err.println("Erro ao mudar para a tela: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML public void abrirHome() { carregarTela("Home.fxml"); }
    @FXML public void abrirProdutos() { carregarTela("CadastrarProdutos.fxml"); }
    @FXML public void abrirClientes() { carregarTela("CadastrarClientes.fxml"); }
    @FXML public void abrirLogin() { carregarTela("Login.fxml"); }
    @FXML public void abrirNovaVenda() { carregarTela("NovaVenda.fxml"); }
    @FXML public void abrirRelatorio() { carregarTela("RelatorioVendas.fxml"); }

    @FXML
    public void abrirAjuda(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText(
                "Nome: Sistema de Reservas\n" +
                        "Versão: 1.0.0\n" +
                        "Desenvolvedor: Ryan Lucas Fernandes Pinheiro\n" +
                        "Ano: 2026"
        );
        alert.showAndWait();
    }

    private void carregarTela(String fxml) {
        try {
            var url = getClass().getResource("/com/senai/crudjavafx/" + fxml);
            if (url == null) {
                System.err.println("ARQUIVO FXML NÃO ENCONTRADO: " + fxml);
                return;
            }
            root.setCenter(FXMLLoader.load(url));
        } catch (Exception e) {
            System.err.println("ERRO AO CARREGAR A TELA: " + fxml);
            e.printStackTrace();
        }
    }
}