package br.com.aquiaolado.dto;

import br.com.aquiaolado.entity.Anuncio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnuncioDTO {

    public long id;

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 255,message = "O título excede o tamanho máximo (255)")
    public String titulo;

    @Size(max = 1000,message = "Descrição excede o tamanho máximo (1000)")
    public String descricao;

    public byte[] imagemCapa;

    @Size(max = 255,message = "O email excede o tamanho máximo (255)")
    public String anunciante;

    @Size(max = 255,message = "O Endereço excede o tamanho máximo (255)")
    public String localAnuncio;

    @Size(max = 255,message = "O contato excede o tamanho máximo (255)")
    public  String contato;

    @Size(max = 30,message = "O whatsapp excede o tamanho máximo (30)")
    public  String whatsapp;
    
    @Size(max = 50,message = "O instagram excede o tamanho máximo (50)")
    public  String instagram;

    @NotNull(message = "UF não pode ser nulo")
    public EstadoDTO uf;

    @NotNull(message = "Cidade não pode ser nulo")
    public CidadeDTO cidade;

    public Set<BairroDTO> locaisEntrega = new HashSet<>();

    public Set<CategoriaDTO> categorias = new HashSet<>();

    public String recaptchaToken;


    public static AnuncioDTO convertAnuncioToDTO(Anuncio anuncio){

        AnuncioDTO payload = new AnuncioDTO();

        payload.id = anuncio.id;
        payload.titulo = anuncio.titulo;
        payload.descricao = anuncio.descricao;
        payload.imagemCapa = anuncio.imagemCapa;
        payload.anunciante = anuncio.anunciante;
        payload.localAnuncio = anuncio.localAnuncio;
        payload.contato = anuncio.contato;
        payload.whatsapp = anuncio.whatsapp;
        payload.instagram = anuncio.instagram;

        payload.uf = new EstadoDTO(anuncio.uf.id, anuncio.uf.sigla, anuncio.uf.nome);
        payload.cidade = new CidadeDTO(anuncio.cidade.id, anuncio.cidade.nome);

        payload.locaisEntrega = anuncio.locaisEntrega.stream()
                .map(item -> new BairroDTO(item.id, item.nome))
                .collect(Collectors.toSet());

        payload.categorias = anuncio.categorias.stream()
                .map(item -> new CategoriaDTO(item.id, item.nome, item.cor, item.imagemCategoria))
                .collect(Collectors.toSet());

        return payload;
    }
}
