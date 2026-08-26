package tech.techsete.onix_payments_sdk.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.onix_payments_sdk.dtos.responses.ErrorResponse;
import tech.techsete.onix_payments_sdk.exceptions.OnixPaymentsApiException;

import java.util.Map;

/**
 * Configuração do cliente HTTP reativo para o SDK Onix Payments.
 * <p>
 * Esta classe fornece a configuração necessária para a comunicação com a API da Onix Payments,
 * criando e configurando uma instância de {@link WebClient} do Spring WebFlux.
 * </p>
 *
 * O WebClient é configurado com:
 * <ul>
 *   <li>URL base da API Onix Payments</li>
 *   <li>Cabeçalhos HTTP padrão para comunicação JSON</li>
 *   <li>Filtro global para interceptar respostas HTTP</li>
 * </ul>
 *
 * <p>
 * Este componente é fundamental para os serviços do SDK que precisam se comunicar
 * com os endpoints da API Onix Payments, fornecendo uma interface reativa para
 * requisições HTTP.
 * </p>
 */

@Slf4j
@Configuration("OnixPaymentsSdkWebClientConfiguration")
public class WebClientConfiguration {

    public static final String PUBLIC_KEY_HEADER = "x-public-key";
    public static final String SECRET_KEY_HEADER = "x-secret-key";

    /**
     * Cria e configura um {@link WebClient} para comunicação com a API Onix Payments.
     * <p>
     * Este bean cria uma instância de WebClient configurada especificamente para
     * interagir com a API da Onix Payments. A configuração inclui:
     * </p>
     * <ul>
     *   <li>URL base da API: https://app.onixpayments.com.br/api/v1</li>
     *   <li>Cabeçalho padrão Accept: application/json</li>
     *   <li>Cabeçalho padrão Content-Type: application/json</li>
     *   <li>Filtro global para interceptar respostas HTTP</li>
     * </ul>
     * <p>
     * O filtro global verifica os códigos de status da resposta HTTP:
     * se o status indicar erro cliente (4xx) ou servidor (5xx),
     * lê o corpo da resposta para log detalhado e lança uma exceção
     * com a mensagem do erro. Caso contrário, permite que a resposta
     * continue normalmente.
     * </p>
     * <p>
     * Isso centraliza o tratamento de erros HTTP no WebClient, evitando
     * a necessidade de múltiplos tratamentos em cada serviço que o utiliza.
     * </p>
     * <p>
     * O WebClient resultante é utilizado pelos serviços do SDK para realizar
     * requisições HTTP reativas à API da Onix Payments.
     * </p>
     *
     * @return uma instância configurada de {@link WebClient}
     */
    @Bean(name = "OnixPaymentsSDKWebClient")
    public WebClient onixPaymentsWebClient() {

        return WebClient.builder()
                .baseUrl("https://app.onixpayments.com.br/api/v1")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .filter(this::validateAuthenticationHeaders)
                .filter(WebClientFilter.addHeadersFromAttribute())
                .filter((request, next) -> next.exchange(request)
                        .flatMap(this::handleErrors)
                )
                .build();
    }

    /**
     * Intercepta a resposta HTTP para tratamento centralizado de erros.
     * <p>
     * Se o código HTTP da resposta indicar erro cliente (4xx) ou servidor (5xx),
     * este método lê o corpo da resposta, registra o erro no log e retorna
     * um {@link Mono} com exceção. Caso contrário, retorna a resposta normalmente.
     * </p>
     *
     * @param response a resposta HTTP recebida
     * @return um {@link Mono} contendo a resposta ou erro tratado
     */
    private Mono<ClientResponse> handleErrors(ClientResponse response) {
        if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
            return response.bodyToMono(ErrorResponse.class)
                    .defaultIfEmpty(ErrorResponse.builder()
                            .statusCode(response.statusCode().value())
                            .message("Erro ao chamar API Onix Payments")
                            .build())
                    .flatMap(errorResponse -> {
                        log.error(
                                "Erro HTTP {} na chamada API Onix Payments: errorCode={}, message={}",
                                response.statusCode().value(),
                                errorResponse.getErrorCode(),
                                errorResponse.getMessage()
                        );
                        return Mono.error(new OnixPaymentsApiException(errorResponse));
                    });
        }
        return Mono.just(response);
    }

    @SuppressWarnings("unchecked")
    private Mono<ClientResponse> validateAuthenticationHeaders(ClientRequest request,
                                                               ExchangeFunction next) {
        final var headers = request.attribute("headers")
                .map(attr -> (Map<String, Object>) attr)
                .orElse(Map.of());

        validateRequiredHeader(headers, request, PUBLIC_KEY_HEADER);
        validateRequiredHeader(headers, request, SECRET_KEY_HEADER);

        return next.exchange(request);
    }

    private void validateRequiredHeader(Map<String, Object> headers,
                                        ClientRequest request,
                                        String headerName) {
        final var headerValue = headers.containsKey(headerName)
                ? headers.get(headerName)
                : request.headers().getFirst(headerName);

        if (headerValue == null || headerValue.toString().isBlank()) {
            throw new IllegalArgumentException("Header obrigatório não informado: " + headerName);
        }
    }
}
