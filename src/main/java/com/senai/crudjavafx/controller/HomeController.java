package com.senai.crudjavafx.controller;

import javafx.fxml.FXML;

public class HomeController {

    @FXML
    public void irParaClientes() {
        MainController.mudarTela("CadastrarClientes.fxml");
    }

    @FXML
    public void irParaProdutos() {

        MainController.mudarTela("CadastrarProdutos.fxml");
    }

    @FXML
    public void irParaVendas() {
        MainController.mudarTela("NovaVenda.fxml");
    }

    @FXML
    public void irParaRelatorios() {
        MainController.mudarTela("RelatorioVendas.fxml");
    }
}