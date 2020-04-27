package br.com.aquiaolado.rest;

import br.com.aquiaolado.api.ReCaptcha3Api;
import br.com.aquiaolado.constantes.Mensagens;
import br.com.aquiaolado.dto.*;
import br.com.aquiaolado.entity.Anuncio;
import br.com.aquiaolado.service.AnuncioService;
import com.google.gson.Gson;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/anuncio")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "Anuncio")

public class AnuncioRest {

    @HeaderParam("TOKENAPIKEY")
    String token;

    @ConfigProperty(name = "api.key.imagem")
    public String apiKeyImagem;

    private static final Logger LOG = Logger.getLogger(AnuncioRest.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Busca todos os anuncios filtrados",
            description = "Busca todos os anuncios de forma filtrada. Possui 3 parametros obrigatórios que servem como filtros, que são: Cidades, Bairros e o Texto digitado pelo usuário(valor)"
    )
    public Response listarAnuncios(@QueryParam("valor") String valor,
                                   @NotNull(message = "Está faltando cidade") @QueryParam("cidade") Long cidade,
                                   @QueryParam("bairros") String bairros,
                                   @QueryParam("categoria") Long categoria,
                                   @QueryParam("page") Integer page) {

        LOG.info("request listar anuncios\nvalor:" + valor + "\ncidade:" + cidade + "\nbairros:" + bairros + "\ncategoria:" + categoria + "\npágina:" + page);
        Integer pageSize = 12; // TODO colocar isso como parametro ou como uma prop
        if (page == null)
            page = 0;

        try {
            List<Anuncio> anuncios;
            if (bairros != null && !bairros.isEmpty()) {
                List<Long> bairrosLista = Stream.of(bairros.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                anuncios = Anuncio.listarTodosPorFiltro(valor, cidade, categoria, bairrosLista, page, pageSize);
            } else {
                anuncios = Anuncio.listarTodosPorFiltro(valor, cidade, categoria, page, pageSize);
            }
            List<AnuncioDTO> payload = anuncios.stream().map(AnuncioDTO::convertAnuncioToDTO)
                    .collect(Collectors.toList());
            return Response.ok(payload).build();
        } catch (Exception e) {
            LOG.error("erro ao filtrar por bairro -> " + e);
            return Response.serverError().build();
        }

    }

    @GET
    @Path("/{idAnuncio}")
    @Operation(summary = "Busca o anuncio cujo id foi informado")
    public Response detalharAnuncio(@NotNull @PathParam("idAnuncio") long id) {
        LOG.info("request detalhar anuncio id:" + id);
        Anuncio detalhesAnuncio = null;
        try {
            detalhesAnuncio = AnuncioService.detalhesAnuncio(id);
            if (detalhesAnuncio == null) throw new Exception();
        } catch (Exception e) {
            return Response.serverError().entity(Mensagens.ERRO_DETALHES_ANUNCIO).build();
        }
        return Response.ok(AnuncioDTO.convertAnuncioToDTO(detalhesAnuncio)).build();
    }

    @POST
    @Path("/{idAnuncio}/anunciante")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Salva o anunciante do anuncio, cujo id foi informado")
    public Response salvarAnunciante(@NotNull @PathParam("idAnuncio") Long id, @Valid AnuncianteDTO anunciante) {
        LOG.info("request salvar anunciante");

        try {
            AnuncioService.salvarAnunciante(anunciante.email, id);
            return Response.ok().build();
        } catch (Exception e) {
            //loga a exception
            LOG.error("erro ao salvar anunciante -> " + e);
            return Response.serverError().build();
        }
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Operation(summary = "Salva um novo anuncio")
    public Response salvarAnuncio(@MultipartForm FormDTO form) {
        LOG.info("request salvar anuncio");

        Response response;
        try {
            Gson gsonSimples = new Gson();
            AnuncioDTO dto = gsonSimples.fromJson(form.anuncioDto, AnuncioDTO.class);

            String profile = io.quarkus.runtime.configuration.ProfileManager.getActiveProfile();
            if (profile.equalsIgnoreCase("prod") || profile.equalsIgnoreCase("homolog")){
                ReCaptcha3ResponseDTO check = ReCaptcha3Api.check(dto.recaptchaToken);
                if (!check.getSuccess()) {
                    Response.status(Status.BAD_REQUEST).entity("Captcha Inválido!").build();
                }
            }
            String resultado = AnuncioService.salvarAnuncio(dto, form.imagemCapa);
            response = Response.ok(resultado).build();
        } catch (Exception e) {
            response = Response.serverError().entity(Mensagens.ERRO_SALVAR_ANUNCIO).build();
        }
        return response;
    }

    @POST
    @Path("/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Operation(summary = "Atualiza uma imagem")
    public Response salvarImagem(@MultipartForm ImagemDTO form) {
        LOG.info("Atualizar imagem");

        Response response;
        try {

            String profile = io.quarkus.runtime.configuration.ProfileManager.getActiveProfile();
            if (profile.equalsIgnoreCase("prod") || profile.equalsIgnoreCase("homolog")){
                ReCaptcha3ResponseDTO check = ReCaptcha3Api.check(form.recaptchaToken);
                if (!check.getSuccess()) {
                    return Response.status(Status.BAD_REQUEST).entity("Captcha Inválido!").build();
                }
                if(!form.chaveAlteracao.equals(apiKeyImagem)){
                    return Response.status(Status.BAD_REQUEST).entity("Chave de Alteração Inválido!").build();
                }
            }
            String resultado = AnuncioService.atualizarImagem(form);
            response = Response.ok(resultado).build();
        } catch (Exception e) {
            response = Response.serverError().entity(Mensagens.ERRO_SALVAR_ANUNCIO).build();
        }
        return response;
    }
}
