package br.com.aquiaolado.rest;

import br.com.aquiaolado.dto.BairroDTO;
import br.com.aquiaolado.dto.CidadeDTO;
import br.com.aquiaolado.dto.EstadoDTO;
import br.com.aquiaolado.entity.Bairro;
import br.com.aquiaolado.entity.Cidade;
import br.com.aquiaolado.entity.Estado;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/localizacao")
@RequestScoped
@Tag(name = "Localização")

public class LocalizacaoRest {

    @HeaderParam("TOKENAPIKEY") String token;
    private static final Logger LOG = Logger.getLogger(LocalizacaoRest.class);

    @GET
    @Path("/uf")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar Estados", description = "Lista todos os estados cadastrados no sistema")
    public Response listarEstados() {
        LOG.info("listando ufs");
        List<EstadoDTO> estados = Estado.listarTodos().stream()
                .map(item -> new EstadoDTO(item.id, item.sigla, item.nome))
                .collect(Collectors.toList());
        return Response.ok(estados).build();
    }
    
    @GET
    @Path("/uf/pesquisa")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar Estados com Anuncios", description = "Lista todos os estados cadastrados com anuncios no sistema")
    public Response listarEstadosComAnuncios() {
        LOG.info("listando ufs");
        List<EstadoDTO> estados = Estado.listarTodosComAnuncio().stream()
                .map(item -> new EstadoDTO(item.id, item.sigla, item.nome))
                .collect(Collectors.toList());
        return Response.ok(estados).build();
    }

    @GET
    @Path("/{idEstado}/cidades")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Listar cidades de um estado",
            description = "Lista todas as cidades de um estado, apartir do id do estado que for informado"
    )
    public Response listarCidadesPorUf(@PathParam("idEstado") long uf) {
        LOG.info("listando cidades por uf");
        List<CidadeDTO> cidades = Cidade.listarCidadePorUf(uf).stream()
                .map(item -> new CidadeDTO(item.id, item.nome))
                .collect(Collectors.toList());
        return Response.ok(cidades).build();
    }
    
    @GET
    @Path("/{idEstado}/cidades/pesquisa")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Listar cidades de um estado",
            description = "Lista todas as cidades de um estado, apartir do id do estado que for informado"
    )
    public Response listarCidadesComAnuncioPorUf(@PathParam("idEstado") long uf) {
        LOG.info("listando cidades por uf");
        List<CidadeDTO> cidades = Cidade.listarCidadeComAnuncioPorUf(uf).stream()
                .map(item -> new CidadeDTO(item.id, item.nome))
                .collect(Collectors.toList());
        return Response.ok(cidades).build();
    }

    @GET
    @Path("/{idCidade}/bairros")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Listar bairros de uma cidade",
            description = "Lista todos os bairros de uam cidade, apartir do id da cidade que for informada"
    )
    public Response listarBairrosPorCidade(@PathParam("idCidade") long cidade) {
        LOG.info("listando bairros de por cidade");
        List<BairroDTO> bairros = Bairro.listarBairrosPorCidade(cidade).stream()
                .map(item -> new BairroDTO(item.id, item.nome))
                .collect(Collectors.toList());
        return Response.ok(bairros).build();
    }

}
