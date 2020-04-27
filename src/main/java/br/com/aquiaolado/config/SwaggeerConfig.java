package br.com.aquiaolado.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "API Aqui Perto",
                description = "Consjunto de serviços usados na aplicação Aqui Perto",
                version = "0.0.1"
        )
)

public class SwaggeerConfig extends Application {

}
