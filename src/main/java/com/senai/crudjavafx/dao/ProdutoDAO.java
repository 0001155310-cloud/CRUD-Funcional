package com.senai.crudjavafx.dao;

import com.senai.crudjavafx.factory.ConnectionFactory;
import com.senai.crudjavafx.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvar(Produto produto) throws SQLException {
        // Atualizado para incluir codigo, estoque e fornecedor
        String sql = "INSERT INTO produtos (nome, preco, codigo, estoque, fornecedor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getCodigo());
            stmt.setInt(4, produto.getEstoque());
            stmt.setString(5, produto.getFornecedor());
            stmt.execute();
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                // Novos campos adicionados na leitura
                p.setCodigo(rs.getString("codigo"));
                p.setEstoque(rs.getInt("estoque"));
                p.setFornecedor(rs.getString("fornecedor"));
                produtos.add(p);
            }
        }
        return produtos;
    }

    public void atualizar(Produto produto) throws SQLException {
        // Atualizado para refletir as alterações em todos os campos
        String sql = "UPDATE produtos SET nome = ?, preco = ?, codigo = ?, estoque = ?, fornecedor = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getCodigo());
            stmt.setInt(4, produto.getEstoque());
            stmt.setString(5, produto.getFornecedor());
            stmt.setInt(6, produto.getId());
            stmt.execute();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}