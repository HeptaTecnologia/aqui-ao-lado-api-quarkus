package br.com.aquiaolado.interceptors;

import br.com.aquiaolado.util.SegurancaUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class RestInterceptor implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(RestInterceptor.class);



    @ConfigProperty(name = "api.key.access")
    public String apiKeyAccess;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        LOG.info("Request Interceptor");
//        String text = String.join(", ",containerRequestContext.getPropertyNames());
        MultivaluedMap<String, String> mapHeaders = (containerRequestContext).getHeaders();
        if(mapHeaders.containsKey("TOKENAPIKEY")){
            LOG.info("token recebido:"+mapHeaders.get("TOKENAPIKEY").get(0));
            try{
                LOG.info("token gerado: "+SegurancaUtils.generateMD5(apiKeyAccess));
                if(!SegurancaUtils.match(
                        SegurancaUtils.generateMD5(apiKeyAccess),
                        mapHeaders.get("TOKENAPIKEY").get(0))){
                    this.setUnauthorizedResponse(containerRequestContext);
                }

            }catch (Exception e){
                LOG.warn(e.getMessage());
                this.setUnauthorizedResponse(containerRequestContext);
            }

        }else {
            LOG.info("Não achou token no método mapHeaders.containsKey");
            this.setUnauthorizedResponse(containerRequestContext);
        }
    }

    public void setUnauthorizedResponse(ContainerRequestContext containerRequestContext){
        LOG.info("Request não autorizado");
        containerRequestContext
                .abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("Voce não tem autorização para acessar essa API")
                        .build()
                );
    }

}
