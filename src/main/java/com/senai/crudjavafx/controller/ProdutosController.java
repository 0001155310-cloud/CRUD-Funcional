package com.senai.crudjavafx.controller;

import com.senai.crudjavafx.dao.ProdutoDAO;
import com.senai.crudjavafx.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutosController {
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtEstoque;
    @FXML private TextField txtFornecedor;


    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colCodigo;
    @FXML private TableColumn<Produto, String> colFornecedor;
    @FXML private TableColumn<Produto, Integer> colEstoque;

    private ProdutoDAO dao = new ProdutoDAO();
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaProdutos.setItems(FXCollections.observableArrayList(dao.listarTodos()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void salvarProduto() {
        try {
            if (produtoSelecionado == null) {
                // Cria um novo objeto vazio
                Produto novo = new Produto();

                // Preenche todos os campos usando os Setters
                novo.setNome(txtNome.getText());
                novo.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
                novo.setCodigo(txtCodigo.getText());
                novo.setEstoque(Integer.parseInt(txtEstoque.getText()));
                novo.setFornecedor(txtFornecedor.getText());

                dao.salvar(novo);
            } else {
                // Atualiza o produto que já estava selecionado
                produtoSelecionado.setNome(txtNome.getText());
                produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));
                produtoSelecionado.setCodigo(txtCodigo.getText());
                produtoSelecionado.setEstoque(Integer.parseInt(txtEstoque.getText()));
                produtoSelecionado.setFornecedor(txtFornecedor.getText());

                dao.atualizar(produtoSelecionado);
            }
            atualizarTabela();
            limparCampos();
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "Preço e Estoque devem ser números válidos!");
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    @FXML
    public void excluirProduto() {
        if (produtoSelecionado != null) {
            try {
                dao.deletar(produtoSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) { exibirAlerta("Erro", e.getMessage()); }
        }
    }

    @FXML
    public void selecionarItem() {
        produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            txtNome.setText(produtoSelecionado.getNome());
            txtPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
        }
    }
    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtPreco.clear();
        produtoSelecionado = null;
        tabelaProdutos.getSelectionModel().clearSelection();
    }
    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.show();
    }
}