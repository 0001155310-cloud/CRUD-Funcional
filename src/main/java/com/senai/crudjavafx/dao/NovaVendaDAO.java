package com.senai.crudjavafx.dao;

import com.senai.crudjavafx.factory.ConnectionFactory;
import com.senai.crudjavafx.model.Cliente;
import com.senai.crudjavafx.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NovaVendaDAO {

    public void registrarVenda(Cliente cliente, Produto produto, int quantidade, double valorTotal) throws SQLException {
        String sql = "INSERT INTO vendas (cliente_id, produto_id, quantidade, valor_total) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, produto.getId());
            stmt.setInt(3, quantidade);
            stmt.setDouble(4, valorTotal);

            stmt.executeUpdate();
        }
    }
}