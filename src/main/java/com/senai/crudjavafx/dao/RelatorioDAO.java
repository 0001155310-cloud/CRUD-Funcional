package com.senai.crudjavafx.dao;

import com.senai.crudjavafx.factory.ConnectionFactory;
import com.senai.crudjavafx.model.VendaRelatorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    public List<VendaRelatorio> listarVendas() throws SQLException {
        List<VendaRelatorio> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.data_venda, c.nome, p.nome, v.quantidade, v.valor_total " +
                "FROM vendas v " +
                "INNER JOIN clientes c ON v.cliente_id = c.id " +
                "INNER JOIN produtos p ON v.produto_id = p.id " +
                "ORDER BY v.data_venda DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new VendaRelatorio(
                        rs.getInt("id"),
                        rs.getString("data_venda"),
                        rs.getString("c.nome"),
                        rs.getString("p.nome"),
                        rs.getInt("v.quantidade"),
                        rs.getDouble("v.valor_total")
                ));
            }
        }
        return lista;
    }
}