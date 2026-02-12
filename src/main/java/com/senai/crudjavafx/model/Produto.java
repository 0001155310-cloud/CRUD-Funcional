package com.senai.crudjavafx.model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String codigo;      // Faltava este
    private int estoque;       // Faltava este (o erro está aqui!)
    private String fornecedor;  // Faltava este

    public Produto() {}

    // Construtor completo para facilitar
    public Produto(int id, String nome, double preco, String codigo, int estoque, String fornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.estoque = estoque;
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        // Agora a variável 'estoque' existe e o erro sumirá
        return this.nome + " (Estoque: " + this.estoque + ")";
    }

    // --- GETTERS E SETTERS (ESSENCIAIS) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }
}