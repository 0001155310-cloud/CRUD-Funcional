package com.senai.crudjavafx.controller;

import com.senai.crudjavafx.dao.ClienteDAO;
import com.senai.crudjavafx.dao.ProdutoDAO;
import com.senai.crudjavafx.dao.NovaVendaDAO; // Importação necessária
import com.senai.crudjavafx.model.Cliente;
import com.senai.crudjavafx.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public class NovaVendaController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Produto> cbProduto;
    @FXML private TextField txtPrecoUnitario;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtTotalVenda;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private NovaVendaDAO vendaDAO = new NovaVendaDAO();

    @FXML
    public void initialize() {
        carregarDados();

        // Listener para atualizar o preço unitário quando escolher um produto
        cbProduto.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                txtPrecoUnitario.setText(String.format("%.2f", novo.getPreco()));
                calcularTotal();
            }
        });
    }

    private void carregarDados() {
        try {
            cbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
            cbProduto.setItems(FXCollections.observableArrayList(produtoDAO.listarTodos()));
        } catch (SQLException e) {
            exibirAlerta("Erro", "Não foi possível carregar clientes ou produtos.");
        }
    }

    @FXML
    public void calcularTotal() {
        try {
            double preco = Double.parseDouble(txtPrecoUnitario.getText().replace(",", "."));
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double total = preco * qtd;
            txtTotalVenda.setText(String.format("R$ %.2f", total));
        } catch (Exception e) {
            txtTotalVenda.setText("R$ 0.00");
        }
    }

    @FXML
    public void finalizarVenda() {
        Cliente cliente = cbCliente.getValue();
        Produto produto = cbProduto.getValue();

        if (cliente == null || produto == null || txtQuantidade.getText().isEmpty()) {
            exibirAlerta("Aviso", "Preencha todos os campos!");
            return;
        }

        try {
            int qtdVenda = Integer.parseInt(txtQuantidade.getText());

            // Verificação de estoque antes de processar
            if (produto.getEstoque() < qtdVenda) {
                exibirAlerta("Estoque Insuficiente", "O produto possui apenas " + produto.getEstoque() + " unidades.");
                return;
            }

            // Remove "R$" e espaços para converter em double
            double total = Double.parseDouble(txtTotalVenda.getText().replace("R$", "").replace(",", ".").trim());

            // 1. Registra a venda na tabela 'vendas'
            vendaDAO.registrarVenda(cliente, produto, qtdVenda, total);

            // 2. Atualiza o estoque do produto no banco
            produto.setEstoque(produto.getEstoque() - qtdVenda);
            produtoDAO.atualizar(produto);

            exibirAlerta("Sucesso", "Venda realizada e estoque atualizado!");
            limparCampos();
            carregarDados(); // Recarrega para mostrar o estoque atualizado na ComboBox

        } catch (SQLException e) {
            exibirAlerta("Erro de Banco", "Erro ao salvar venda: " + e.getMessage());
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Quantidade inválida!");
        }
    }

    @FXML
    public void limparCampos() {
        cbCliente.getSelectionModel().clearSelection();
        cbProduto.getSelectionModel().clearSelection();
        txtPrecoUnitario.clear();
        txtQuantidade.clear();
        txtTotalVenda.setText("R$ 0.00");
    }

    @FXML
    public void excluirVenda() {
        // Implementar se houver TableView nesta tela
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void atualizarPrecoUnitario() {
        Produto selecionado = cbProduto.getValue();
        if (selecionado != null) {
            txtPrecoUnitario.setText(String.format("%.2f", selecionado.getPreco()));
            calcularTotal();
        }
    }
}