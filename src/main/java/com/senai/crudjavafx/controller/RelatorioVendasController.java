package com.senai.crudjavafx.controller;

import com.senai.crudjavafx.dao.RelatorioDAO;
import com.senai.crudjavafx.model.VendaRelatorio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class RelatorioVendasController {

    @FXML private TableView<VendaRelatorio> tabelaVendas;
    @FXML private TableColumn<VendaRelatorio, String> colData;
    @FXML private TableColumn<VendaRelatorio, String> colCliente;
    @FXML private TableColumn<VendaRelatorio, Double> colTotal;

    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFim;

    private RelatorioDAO dao = new RelatorioDAO();

    @FXML
    public void initialize() {
        // Vincula as colunas aos atributos do VendaRelatorio
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        atualizarTabela();
    }

    @FXML
    public void atualizarTabela() {
        try {
            tabelaVendas.setItems(FXCollections.observableArrayList(dao.listarVendas()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarVendas() {
        atualizarTabela();
    }

    @FXML
    public void limparCampos() {
        dpInicio.setValue(null);
        dpFim.setValue(null);
        tabelaVendas.getItems().clear();
    }

    @FXML
    public void excluirVenda() {
    }
}