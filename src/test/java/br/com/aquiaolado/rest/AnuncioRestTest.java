package br.com.aquiaolado.rest;

import br.com.aquiaolado.dto.AnuncioDTO;
import br.com.aquiaolado.dto.CidadeDTO;
import br.com.aquiaolado.dto.EstadoDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.emptyArray;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class AnuncioRestTest {

    @Test
    void listarAnunciosPorCidade() {
        given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .param("cidade", "882")
                .when()
                .get("/anuncio")
                .then()
                .statusCode(200)
                .body("size()", not(0),
                        "cidade.id", hasItem(882))
        ;
    }

    @Test
    void listarAnunciosPorCidadeCategoria() {
        given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .param("cidade", "882")
                .param("categoria", "4")
                .when()
                .get("/anuncio")
                .then()
                .statusCode(200)
                .body("size()", not(0),
                        "cidade.id", hasItem(882),
                        "categorias[0].id", hasItems(4))
        ;
    }

    @Test
    void listarAnunciosPorCidadeCategoriaValor() {
        given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .param("cidade", "882")
                .param("categoria", "4")
                .param("valor", "açai")
                .when()
                .get("/anuncio")
                .then()
                .statusCode(200)
                .body("size()", not(0),
                        "cidade.id", hasItem(882),
                        "categorias[0].id", hasItems(4),
                        "descricao", hasItem(containsString("açai"))
                )
        ;
    }

    @Test
    void listarAnunciosPorCidadeCategoriaValorBairros() {
        given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .param("cidade", "882")
                .param("categoria", "4")
                .param("valor", "açai")
                .param("bairros", "1")
                .when()
                .get("/anuncio")
                .then()
                .statusCode(200)
                .body("size()", not(0),
                        "cidade.id", hasItem(882),
                        "categorias[0].id", hasItems(4),
                        "descricao", hasItem(containsString("açai"))
                )
        ;
    }

    @Test
    void listarAnunciosComCidadeNulo() {
        given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .param("categoria", "4")
                .when()
                .get("/anuncio")
                .then()
                .statusCode(400)
                .body("parameterViolations", not(emptyArray()))
        ;
    }

    @Test
    void novoAnuncio() {

        AnuncioDTO dto = new AnuncioDTO();
        dto.titulo = "Novo anuncio Teste Junit";
        dto.descricao = "Descrição do anuncio";
        dto.anunciante = "email@mail.com";
        dto.localAnuncio = "Brasília";
        dto.contato = "61-99959-5432";
        dto.uf = new EstadoDTO(7);
        dto.cidade = new CidadeDTO(882);

        Response response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/anuncio")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.jsonPath().prettyPrint());
    }

    @Test
    void novoAnuncioComErroNaUF() {

        AnuncioDTO dto = new AnuncioDTO();
        dto.titulo = "Novo anuncio Teste Junit";
        dto.descricao = "Descrição do anuncio";
        dto.anunciante = "email@mail.com";
        dto.localAnuncio = "Brasília";
        dto.contato = "61-99959-5432";
//        dto.uf = new EstadoDTO(7);
        dto.cidade = new CidadeDTO(882);

        ValidatableResponse response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/anuncio")
                .then()
                .statusCode(400)
                .body("exception", Matchers.nullValue(),
                        "parameterViolations", not(emptyArray()),
                        "parameterViolations.message", hasItems(containsString("UF não pode ser nulo")));

        System.out.println(response.extract().response().jsonPath().prettyPrint());
    }

    @Test
    void novoAnuncioComErroNaCidade() {

        AnuncioDTO dto = new AnuncioDTO();
        dto.titulo = "Novo anuncio Teste Junit";
        dto.descricao = "Descrição do anuncio";
        dto.anunciante = "email@mail.com";
        dto.localAnuncio = "Brasília";
        dto.contato = "61-99959-5432";
        dto.uf = new EstadoDTO(7);
//        dto.cidade = new CidadeDTO(882);

        ValidatableResponse response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/anuncio")
                .then()
                .statusCode(400)
                .body("exception", Matchers.nullValue(),
                        "parameterViolations", not(emptyArray()),
                        "parameterViolations.message", hasItems(containsString("Cidade não pode ser nulo")));

        System.out.println(response.extract().response().jsonPath().prettyPrint());
    }

    @Test
    void novoAnuncioComErroNoTamanhoDoCampo() {

        AnuncioDTO dto = new AnuncioDTO();
        dto.titulo = "abcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyzabcdefghijklkmnopqrstuvxyz";
        dto.descricao = "Descrição do anuncio";
        dto.anunciante = "email@mail.com";
        dto.localAnuncio = "Brasília";
        dto.contato = "61-99959-5432";
        dto.uf = new EstadoDTO(7);
        dto.cidade = new CidadeDTO(882);

        ValidatableResponse response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/anuncio")
                .then()
                .statusCode(400)
                .body("exception", Matchers.nullValue(),
                        "parameterViolations", not(emptyArray()),
                        "parameterViolations.message", hasItems(containsString("O título excede o tamanho máximo (255)")));

        System.out.println(response.extract().response().jsonPath().prettyPrint());
    }
}