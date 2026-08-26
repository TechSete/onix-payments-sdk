# Onix Payments SDK

SDK Java para integração com a API da Onix Payments, com suporte inicial ao recebimento de pagamentos via Pix.

## Requisitos

- Java 17+
- Spring Boot 4+
- Maven 3.9+ ou Maven Wrapper

## Instalação local

Enquanto o SDK não estiver publicado em um repositorio remoto, instale o pacote no Maven local:

```bash
./mvnw clean install
```

Depois, adicione a dependência no projeto consumidor:

```xml
<dependency>
    <groupId>tech.techsete</groupId>
    <artifactId>onix-payments-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuração

O SDK possui autoconfiguração Spring Boot. Ao adicionar a dependência, os componentes do pacote `tech.techsete.onix_payments_sdk` são registrados automaticamente.

O cliente HTTP configurado usa a URL base:

```text
https://app.onixpayments.com.br/api/v1
```

Todas as requisições para a API exigem os headers:

```text
x-public-key: sua chave publica
x-secret-key: sua chave secreta
```

O SDK valida esses headers no `WebClientConfiguration` antes de enviar a chamada.

## Receber Pix

Injete o `PaymentService` no seu serviço:

```java
import org.springframework.stereotype.Service;
import tech.techsete.onix_payments_sdk.services.PaymentService;

@Service
public class CheckoutService {

    private final PaymentService paymentService;

    public CheckoutService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### Exemplo sincrono

```java
import tech.techsete.onix_payments_sdk.dtos.requests.PaymentClientRequest;
import tech.techsete.onix_payments_sdk.dtos.requests.PaymentProductRequest;
import tech.techsete.onix_payments_sdk.dtos.requests.PaymentRequest;
import tech.techsete.onix_payments_sdk.dtos.requests.PaymentSplitRequest;
import tech.techsete.onix_payments_sdk.dtos.responses.PaymentResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

PaymentRequest request = PaymentRequest.builder()
        .identifier("pedido-123")
        .amount(new BigDecimal("100.50"))
        .client(PaymentClientRequest.builder()
                .name("Joao da Silva")
                .email("joao@gmail.com")
                .phone("(11) 99999-9999")
                .document("123.456.789-00")
                .build())
        .products(List.of(
                PaymentProductRequest.builder()
                        .id("produto-1")
                        .name("Produto 1")
                        .quantity(1)
                        .price(new BigDecimal("80.00"))
                        .build(),
                PaymentProductRequest.builder()
                        .id("produto-2")
                        .name("Produto 2")
                        .quantity(2)
                        .price(new BigDecimal("10.25"))
                        .build()
        ))
        .splits(List.of(
                PaymentSplitRequest.builder()
                        .producerId("producer-id")
                        .amount(new BigDecimal("10.00"))
                        .build()
        ))
        .dueDate(LocalDate.now().plusDays(1))
        .metadata(Map.of(
                "provider", "Checkout",
                "orderId", "1234"
        ))
        .callbackUrl("https://minha.api.com/pix/webhook")
        .build();

PaymentResponse response = paymentService.create(
        "SUA_CHAVE_PUBLICA_AQUI",
        "SUA_CHAVE_SECRETA_AQUI",
        request
);
```

### Exemplo reativo

```java
paymentService.createdAsync("SUA_CHAVE_PUBLICA_AQUI", "SUA_CHAVE_SECRETA_AQUI", request)
        .subscribe(response -> {
            String transactionId = response.getTransactionId();
            String pixCode = response.getPix().getCode();
        });
```

## Uso com headers customizados

Também e possivel passar os headers manualmente:

```java
Map<String, String> headers = Map.of(
        "x-public-key", "SUA_CHAVE_PUBLICA_AQUI",
        "x-secret-key", "SUA_CHAVE_SECRETA_AQUI"
);

PaymentResponse response = paymentService.create(headers, request);
```

## Tratamento de erros

Respostas HTTP `4xx` e `5xx` são desserializadas para `ErrorResponse` e encapsuladas em `OnixPaymentsApiException`.

```java
import tech.techsete.onix_payments_sdk.exceptions.OnixPaymentsApiException;

try {
    PaymentResponse response = paymentService.create(publicKey, secretKey, request);
} catch (OnixPaymentsApiException exception) {
    var error = exception.getErrorResponse();

    Integer statusCode = error.getStatusCode();
    String errorCode = error.getErrorCode();
    String message = error.getMessage();
}
```

Se `x-public-key` ou `x-secret-key` não forem informados, o SDK lanca `IllegalArgumentException` antes de enviar a requisicao.

## DTOs principais

### PaymentRequest

| Campo | Tipo | Obrigatorio | Descrição |
| --- | --- | --- | --- |
| `identifier` | `String` | Sim | Identificador único da transação gerado pela aplicação. |
| `amount` | `BigDecimal` | Sim | Valor da transação em reais. |
| `shippingFee` | `BigDecimal` | Não | Valor do frete em reais. |
| `extraFee` | `BigDecimal` | Não | Outras taxas em reais. |
| `discount` | `BigDecimal` | Não | Desconto em reais. |
| `client` | `PaymentClientRequest` | Sim | Dados do cliente. |
| `products` | `List<PaymentProductRequest>` | Não | Produtos da transação. |
| `splits` | `List<PaymentSplitRequest>` | Não | Repasses para outras contas. |
| `dueDate` | `LocalDate` | Não | Data de vencimento no formato `yyyy-MM-dd`. |
| `metadata` | `Object` | Não | Metadados como objeto chave-valor ou string. |
| `callbackUrl` | `String` | Não | URL para notificação de alteração de status. |

### PaymentResponse

| Campo | Tipo | Descrição |
| --- | --- | --- |
| `transactionId` | `String` | ID único da transação criado pela Onix Payments. |
| `status` | `PaymentStatus` | Status atual da transação. |
| `fee` | `BigDecimal` | Taxa cobrada pela transação. |
| `order` | `PaymentOrderResponse` | Dados do pedido. |
| `pix` | `PaymentPixResponse` | Dados do Pix, incluindo código copia e cola. |
| `details` | `String` | Detalhes adicionais. |
| `errorDescription` | `String` | Descrição de erro quando houver falha. |

### Status possiveis

```text
OK
FAILED
PENDING
REJECTED
CANCELED
```

## Comandos uteis

```bash
./mvnw test
./mvnw clean install
```
