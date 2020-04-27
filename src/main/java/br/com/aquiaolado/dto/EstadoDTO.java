package br.com.aquiaolado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstadoDTO {

    public long id;
    @Size(max = 255,message = "A cidade excede o tamanho máximo (255)")
    public String sigla;
    @Size(max = 255,message = "A cidade excede o tamanho máximo (255)")
    public String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(long id, String sigla, String nome) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
    }

    public EstadoDTO(@NotNull long id) {
        this.id = id;
    }
}
