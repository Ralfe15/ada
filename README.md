# EventMaster - Venda de Ingressos

Microsservico de venda de ingressos desenvolvido como projeto final do curso ADA.

## Tecnologias

- Java 21, Spring Boot 3.4.3, Maven
- Banco de dados H2 (em memoria)
- Autenticacao JWT
- Spring Security

## Pre-requisitos

- **Java 21** instalado (`java -version`)
- Maven **nao** e necessario globalmente (o projeto inclui o Maven Wrapper)

## Como executar

```bash
./mvnw spring-boot:run
```

No Windows:

```cmd
mvnw.cmd spring-boot:run
```

A aplicacao inicia na porta **8080**. Tres eventos de exemplo sao inseridos automaticamente no banco.

## Eventos pre-cadastrados

| ID | Nome | Preco | Ingressos |
|----|------|-------|-----------|
| `a1b2c3d4-e5f6-7890-abcd-ef1234567890` | Show de Rock | R$ 75,00 | 500 |
| `b2c3d4e5-f6a7-8901-bcde-f12345678901` | Festival de Jazz | R$ 120,00 | 200 |
| `c3d4e5f6-a7b8-9012-cdef-123456789012` | Stand-Up Comedy | R$ 50,00 | 100 |

## Endpoints

### 1. Login (obter token JWT)

**Endpoint publico** - nao requer autenticacao.

```
POST /auth/login?username={usuario}
```

Exemplo com curl:

```bash
curl -X POST "http://localhost:8080/auth/login?username=joao"
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Copie o valor do `token` para usar nas proximas requisicoes.

### 2. Comprar ingresso

**Endpoint protegido** - requer o token JWT no header `Authorization`.

```
POST /api/tickets/purchase
Content-Type: application/json
Authorization: Bearer {token}
```

Body:

```json
{
  "eventId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "customerEmail": "joao@email.com",
  "quantity": 2,
  "paymentMethod": "PIX"
}
```

Valores aceitos para `paymentMethod`: `PIX`, `CREDIT_CARD`, `BOLETO`.

Exemplo com curl:

```bash
curl -X POST http://localhost:8080/api/tickets/purchase \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "eventId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "customerEmail": "joao@email.com",
    "quantity": 2,
    "paymentMethod": "PIX"
  }'
```

Resposta (sucesso):

```json
{
  "orderId": "uuid-do-pedido",
  "eventId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "quantity": 2,
  "totalAmount": 150.00,
  "paymentMethod": "PIX",
  "status": "CONFIRMED"
}
```

### 3. Console H2 (banco de dados)

Acesse pelo navegador:

```
http://localhost:8080/h2-console
```

Configuracoes de conexao:

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:eventmaster` |
| User Name | `sa` |
| Password | *(vazio)* |

Consultas uteis:

```sql
SELECT * FROM events;
SELECT * FROM orders;
```

## Fluxo completo de teste

```bash
# 1. Inicie a aplicacao
./mvnw spring-boot:run

# 2. Obtenha o token
curl -X POST "http://localhost:8080/auth/login?username=joao"

# 3. Compre um ingresso (substitua SEU_TOKEN_AQUI pelo token recebido)
curl -X POST http://localhost:8080/api/tickets/purchase \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "eventId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "customerEmail": "joao@email.com",
    "quantity": 2,
    "paymentMethod": "CREDIT_CARD"
  }'

# 4. Verifique no H2 Console que o pedido foi salvo
#    Abra http://localhost:8080/h2-console e execute: SELECT * FROM orders;
```

## Testes automatizados

```bash
# Executar todos os testes (unitarios + BDD)
./mvnw test
```

No Windows:

```cmd
mvnw.cmd test
```

Os testes incluem:

- **PaymentStrategyTest** - testa as estrategias de pagamento (Strategy Pattern)
- **EventTest** - testa regras de dominio do evento (reserva de ingressos)
- **PurchaseTicketUseCaseTest** - testa o caso de uso com mocks (Mockito)
- **CucumberTest** - cenario BDD em portugues (Gherkin/Cucumber)

## Erros comuns

| Erro | Causa | Solucao |
|------|-------|---------|
| `403 Forbidden` | Token JWT ausente ou invalido | Faca login e use o token no header `Authorization: Bearer {token}` |
| `400 Bad Request` | Campos obrigatorios faltando ou invalidos | Verifique `eventId`, `customerEmail`, `quantity` e `paymentMethod` |
| `409 Conflict` | Ingressos insuficientes | O evento nao tem ingressos disponiveis para a quantidade solicitada |
