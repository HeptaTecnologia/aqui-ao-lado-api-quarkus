package br.com.aquiaolado.rest;

import br.com.aquiaolado.dto.CategoriaDTO;
import br.com.aquiaolado.entity.Categoria;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/categoria")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "Categoria (Tipo de Produto)")
public class CategoriaRest {

    @HeaderParam("TOKENAPIKEY") String token;
    private static final Logger LOG = Logger.getLogger(CategoriaRest.class);

    @GET
    @Operation(summary = "Listar Categorias",description = "Lista todas as categorias de produtos cadastrados no sistema")
    public Response listaCategorias(){
        LOG.info("request listar categorias");
        List<CategoriaDTO> categorias = Categoria.listarTodas().stream()
                .map(item -> new CategoriaDTO(item.id, item.nome, item.cor, item.imagemCategoria))
                .collect(Collectors.toList());
        return Response.ok(categorias).build();
    }

}
