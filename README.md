# Coupon API

API REST para gerenciamento de cupons.

## Tecnologias

* Java 17
* Spring Boot
* Spring Data JPA
* H2
* Docker
* Docker Compose
* OpenAPI / Swagger

## Arquitetura

O projeto segue uma arquitetura em camadas simplificada, com as regras de negócio encapsuladas nos objetos de domínio.

* **Controller:** camada HTTP
* **Application:** casos de uso e orquestração das regras de negócio
* **Domain:** regras de negócio
* **Persistence:** implementação da persistência no banco de dados

## Regras de Negócio

* O código do cupom deve conter 6 caracteres alfanuméricos
* Caracteres especiais são removidos do código
* O desconto deve ser de pelo menos 0,5
* A data de expiração não pode estar no passado
* Cupons podem ser criados já publicados
* A exclusão é implementada como soft delete
* Um cupom já excluído não pode ser excluído novamente

## Execução

### Build

```bash
./mvnw clean package
```

### Docker

```bash
docker compose up --build
```

## Swagger

Após iniciar a aplicação, a documentação da API estará disponível em:

http://localhost:8080/swagger-ui.html

## Banco de Dados

A aplicação utiliza um banco de dados H2 em memória.

Os dados são perdidos quando a aplicação é encerrada.



## para possível futuro

- Implementar testes unitários utilizando Junit e mockito
