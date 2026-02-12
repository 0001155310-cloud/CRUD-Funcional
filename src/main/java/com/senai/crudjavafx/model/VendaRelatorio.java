package com.senai.crudjavafx.model;

import java.time.LocalDateTime;

public class VendaRelatorio {
    private int id;
    private String data;
    private String cliente;
    private String produto;
    private int quantidade;
    private double total;

    public VendaRelatorio(int id, String data, String cliente, String produto, int quantidade, double total) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.total = total;
    }

    // Getters (TableView precisa deles)
    public int getId() { return id; }
    public String getData() { return data; }
    public String getCliente() { return cliente; }
    public String getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getTotal() { return total; }
}