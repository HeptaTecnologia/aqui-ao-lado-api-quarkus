package br.com.aquiaolado.rest;

import com.google.common.collect.Ordering;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class LocalizacaoRestTest {

    

    @Test
    void listarEstados() {
        Response response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .when()
                .get("/localizacao/uf")
                .then()
                .statusCode(200)
                .body("size()", is(27))
                .extract().response();

        List<String> nomes = response.jsonPath().getList("nome");

        System.out.println(nomes);
        assertTrue(Ordering.natural().isOrdered(nomes));
    }

    void listarEstadosComAnuncio() {
        Response response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .when()
                .get("/localizacao/uf/pesquisa")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .extract().response();

        List<String> nomes = response.jsonPath().getList("nome");

        System.out.println(nomes);
        assertTrue(Ordering.natural().isOrdered(nomes));
    }

    @Test
    void listarCidadesPorUf() {
        Response response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .when()
                .get("/localizacao/1/cidades")
                .then()
                .statusCode(200)
                .body("size()", not(0))
                .extract().response();

        List<String> nomes = response.jsonPath().getList("nome");
        assertTrue(Ordering.natural().isOrdered(nomes));
        System.out.println(nomes);
    }

    @Test
    void listarBairrosPorCidade() {
        Response response = given()
                .header("TOKENAPIKEY", "ca50e4b808a324f3abb93405c9b5255d")
                .when()
                .get("/localizacao/882/bairros")
                .then()
                .statusCode(200)
                .body("size()", not(0))
                .extract().response();

        List<String> nomes = response.jsonPath().getList("nome");
        System.out.println(nomes);
        assertTrue(Ordering.natural().isOrdered(nomes));
    }
}