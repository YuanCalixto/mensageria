# Plataforma de Pedidos com Apache Kafka

Projeto desenvolvido para a disciplina de **Software Concorrente e Distribuído**  
Curso de **Engenharia de Software - UFG**

Este projeto simula o fluxo de processamento de pedidos em uma plataforma de e-commerce utilizando uma arquitetura baseada em microsserviços e comunicação assíncrona com **Apache Kafka**.

##  Visão Geral

A aplicação é composta por três microsserviços que se comunicam por meio de tópicos Kafka:


\[Order-Service] ---> \[Kafka: orders] ---> \[Inventory-Service] ---> \[Kafka: inventory-events] ---> \[Notification-Service]

Cada microsserviço é responsável por uma etapa do processo de pedidos:

### Microsserviços

#### Order-Service
- Expõe um endpoint REST para criação de pedidos:  
  `POST /orders`
- Cria um pedido com:
  - `UUID`: identificador único
  - `timestamp`: horário do pedido
  - `items`: lista de produtos
- Publica o pedido no tópico Kafka `orders`

#### Inventory-Service
- Escuta o tópico Kafka `orders`
- Simula a reserva de estoque com sucesso ou falha aleatória
- Publica um evento no tópico Kafka `inventory-events`, indicando o status da reserva

#### Notification-Service
- Escuta o tópico Kafka `inventory-events`
- Simula o envio de notificações (e-mail ou SMS)
- Apenas imprime no console o status do pedido

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- Java 17 ou superior  
- Maven  
- Docker e Docker Compose  

## Executando o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/YuanCalixto/mensageria.git
cd mensageria
````

### 2. Subir o Kafka com Docker Compose

No diretório do projeto, onde está o arquivo `docker-compose.yml`, execute:

```bash
docker-compose up -d
```

Isso iniciará os containers do Zookeeper e Kafka.

### 3. Executar os Microsserviços

Para cada microsserviço (`order-service`, `inventory-service`, `notification-service`), siga os comandos:

```bash
cd nome-do-microsservico
mvn clean install
mvn spring-boot:run
```

Abra terminais diferentes para executar cada serviço simultaneamente.

## Testando a Aplicação

### Enviar um Pedido (Order-Service)

Com o `Order-Service` rodando (padrão porta 8080), envie um pedido usando o comando abaixo:

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"items": ["notebook", "mouse", "monitor"]}'
```

---

### Exemplo de Payload enviado

```json
{
  "items": ["notebook", "mouse", "monitor"]
}
```

---

### Resposta Esperada do Serviço

```json
{
  "orderId": "1a2b3c4d-5678-90ab-cdef-1234567890ab",
  "timestamp": "2025-06-22T15:00:00",
  "items": ["notebook", "mouse", "monitor"]
}
```

---

## Exemplo de Logs no Console

### Inventory-Service

```
[Inventory-Service] Pedido recebido: 1a2b3c4d-5678-90ab-cdef-1234567890ab
[Inventory-Service] Estoque reservado com sucesso.
[Inventory-Service] Evento publicado no tópico 'inventory-events'
```

### Notification-Service

```
[Notification-Service] Pedido confirmado: 1a2b3c4d-5678-90ab-cdef-1234567890ab
[Notification-Service] Notificação enviada com sucesso (simulada).
```

---

## Tópicos Kafka Utilizados

* `orders`

  * Publicado pelo `Order-Service`
  * Consumido pelo `Inventory-Service`

* `inventory-events`

  * Publicado pelo `Inventory-Service`
  * Consumido pelo `Notification-Service`

---

## Tecnologias Utilizadas

* Java 17
* Spring Boot
* Apache Kafka
* Spring Kafka
* Docker & Docker Compose
* Maven

---

## Créditos

Projeto desenvolvido por **Gabriel Guimarães Cordeiro Bispo, Kauã Júnio Da Silva Lima e Yuan Andrade Calixto Dos Santos**

Disciplina: *Software Concorrente e Distribuído* — Engenharia de Software - UFG
