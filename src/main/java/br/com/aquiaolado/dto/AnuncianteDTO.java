package br.com.aquiaolado.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AnuncianteDTO {

    @NotEmpty(message = "O email não pode ser vazio")
    @Size(max = 255,message = "o Email excede o tamanho máximo (255)")
    public String email;
}
