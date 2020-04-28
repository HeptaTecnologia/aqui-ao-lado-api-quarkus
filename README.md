# aqui-ao-lado-api

O "Aqui ao Lado" nasceu da seguinte ideia: "O que podemos fazer para ajudar as pessoas durante a crise do coronavírus?", criamos uma vitrine virtual para conectar produtores e consumidores de forma mais organizada e livre, no qual é possível anunciar e buscar anúncios cadastrados.

Esse projeto é o back-end em Quarkus, uma api consumida por um [Front End em VueJS](https://github.com/HeptaTecnologia/aqui-ao-lado-vuejs) no qual é possível realizar:

* Cadastro de anúncios com whatsapp e instagram
* Exibição dos anúncios em um lista que pode ser filtrada por palavra-chave, bairros ou categoria
* Exibição de detalhes de um anúncio
* Compartilhamento via whatsapp e telegram
* Entrar em contato com o anunciante via whatsapp e instagram
* Recaptcha da google para impedir a submissão do formulário por robôs ([vue-recaptcha-v3](https://github.com/AurityLab/vue-recaptcha-v3))
* Analytics da google para monitorar o uso das páginas ([vue-gtag](https://github.com/MatteoGabriele/vue-gtag))
* Contato e Denúncia via email

## Tecnologias/Stack 

Projeto construído em Quarkus, para aprender mais sobre quarkus visite https://quarkus.io/

This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

* Java 11
* Quarkus 1.3.2
* Panache-ORM
* RESTEasy
* Junit 5
* rest-assured
* quarkus-test-h2
* MySql 8
* Apache Maven 3.6.3+

## Pré-requisitos/Prerequisites

Criar um esquema no mysql8 com o nome aquiaoladodb

Create mysql8 schema aquiaoladodb

## Rodar a aplicação em modo Desenvolvimento/Running the application in dev mode

```
./mvnw quarkus:dev
```

## Empacotando e rodando a applicação/Packaging and running the application

Para empacotar a aplicação `./mvnw package -Dquarkus.profile=prod`.
É gerado o arquivo `aqui-ao-lado-api-1.0.0-SNAPSHOT-runner.jar` na pasta `/target`.
O pacote não é um _über-jar_ , não tem as dependências dentro dele, elas são colocadas na pasta `target/lib`.
Podemos rodar a aplicação usando `java -jar target/aqui-ao-lado-api-1.0.0-SNAPSHOT-runner.jar`.

The application can be packaged using `./mvnw package -Dquarkus.profile=prod`.
It produces the `aqui-ao-lado-api-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.
The application is now runnable using `java -jar target/aqui-ao-lado-api-1.0.0-SNAPSHOT-runner.jar`.

## Construir e rodar imagem Docker/ Build and run docker

Depois de empacotar, temos 2 opções para criar imagens, os arquivos Dockerfile e Dockerfile.hotspot

```
docker build -t <image_tag> . && docker run -p 8099:8099 --name <container_name> <image_tag>
```
``` 
docker build -f Dockerfile.hotspot -t <image_tag> . && docker run -p 8099:8099 --name <container_name> <image_tag> 
```

