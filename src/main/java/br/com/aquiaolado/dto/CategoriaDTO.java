package br.com.aquiaolado.dto;

public class CategoriaDTO {

    public long id;
    public String nome;
    public String cor;
    public String imagem;

    public CategoriaDTO(long id, String nome, String cor, String imagem) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.imagem = imagem;
    }
}
