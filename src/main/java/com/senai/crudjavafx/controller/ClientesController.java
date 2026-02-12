package com.senai.crudjavafx.controller;

import com.senai.crudjavafx.dao.ClienteDAO; // Supondo que você criou ou criará este DAO
import com.senai.crudjavafx.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientesController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;

    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;

    private ClienteDAO dao = new ClienteDAO();
    private Cliente clienteSelecionado;

    @FXML
    public void initialize() {
        // Mapeia colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaClientes.setItems(FXCollections.observableArrayList(dao.listarTodos()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void salvarCliente() {
        try {
            if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()) {
                exibirAlerta("Aviso", "Nome e CPF são obrigatórios!");
                return;
            }

            if (clienteSelecionado == null) {
                Cliente novo = new Cliente();
                preencherObjeto(novo);
                dao.salvar(novo);
            } else {
                preencherObjeto(clienteSelecionado);
                dao.atualizar(clienteSelecionado);
            }

            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao salvar cliente: " + e.getMessage());
        }
    }

    private void preencherObjeto(Cliente c) {
        c.setNome(txtNome.getText());
        c.setCpf(txtCpf.getText());
        c.setTelefone(txtTelefone.getText());
        c.setEmail(txtEmail.getText());
        c.setEndereco(txtEndereco.getText());
    }

    @FXML
    public void excluirCliente() {
        clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                dao.deletar(clienteSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) {
                exibirAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        } else {
            exibirAlerta("Aviso", "Selecione um cliente para excluir.");
        }
    }

    @FXML
    public void selecionarItem() {
        clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getNome());
            txtCpf.setText(clienteSelecionado.getCpf());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtEndereco.setText(clienteSelecionado.getEndereco());
        }
    }

    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtEndereco.clear();
        clienteSelecionado = null;
        tabelaClientes.getSelectionModel().clearSelection();
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}