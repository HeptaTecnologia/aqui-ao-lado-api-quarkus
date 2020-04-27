package br.com.aquiaolado.dto;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.Serializable;

public class ImagemDTO implements Serializable {

    @FormParam("imagem")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream imagem;

    @FormParam("anuncioId")
    @PartType(MediaType.APPLICATION_JSON)
    public String anuncioId;

    @FormParam("chaveAlteracao")
    @PartType(MediaType.APPLICATION_JSON)
    public String chaveAlteracao;

    @FormParam("recaptchaToken")
    @PartType(MediaType.APPLICATION_JSON)
    public String recaptchaToken;

}
