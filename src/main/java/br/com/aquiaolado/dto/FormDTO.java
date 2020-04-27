package br.com.aquiaolado.dto;

import java.io.InputStream;
import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FormDTO implements Serializable {

    @FormParam("imagemCapa")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream imagemCapa;

    @FormParam("anuncioDto")
    @PartType(MediaType.APPLICATION_JSON)
    public String anuncioDto;
}
