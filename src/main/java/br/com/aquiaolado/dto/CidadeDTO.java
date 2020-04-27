package br.com.aquiaolado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CidadeDTO {

    public long id;

    @Size(max = 255,message = "A cidade excede o tamanho máximo (255)")
    public String nome;

    public CidadeDTO() {
    }

    public CidadeDTO(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CidadeDTO(@NotNull long id) {
        this.id = id;
    }
}
