package tech.techsete.onix_payments_sdk.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.onix_payments_sdk.dtos.requests.PaymentRequest;
import tech.techsete.onix_payments_sdk.dtos.responses.PaymentResponse;

import java.util.HashMap;
import java.util.Map;

import static tech.techsete.onix_payments_sdk.configurations.WebClientConfiguration.PUBLIC_KEY_HEADER;
import static tech.techsete.onix_payments_sdk.configurations.WebClientConfiguration.SECRET_KEY_HEADER;

/**
 * Serviço responsável pelo recebimento de pagamentos Pix na API Onix Payments.
 * <p>
 * Esta classe fornece métodos síncronos e assíncronos para criação de cobranças
 * Pix usando o endpoint de recebimento da API.
 * </p>
 *
 * @author Edson Isaac
 * @since 1.0.0
 */
@Service("OnixPaymentsPaymentService")
public class PaymentService {

    private static final String PIX_RECEIVE_ENDPOINT = "/gateway/pix/receive";

    private final WebClient webClient;

    /**
     * Construtor para injeção do WebClient configurado para o SDK Onix Payments.
     *
     * @param webClient instância qualificada do {@link WebClient} para chamadas externas.
     */
    public PaymentService(@Qualifier("OnixPaymentsSDKWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Cria uma nova cobrança Pix de forma síncrona.
     *
     * @param publicKey chave pública de autenticação da API Onix Payments.
     * @param secretKey chave secreta de autenticação da API Onix Payments.
     * @param request objeto contendo os dados da cobrança Pix a ser criada.
     * @return {@link PaymentResponse} contendo os detalhes da cobrança criada.
     */
    public PaymentResponse create(@NotBlank String publicKey,
                                  @NotBlank String secretKey,
                                  @Valid PaymentRequest request) {
        return createdAsync(publicKey, secretKey, request).block();
    }

    /**
     * Cria uma nova cobrança Pix de forma assíncrona.
     *
     * @param publicKey chave pública de autenticação da API Onix Payments.
     * @param secretKey chave secreta de autenticação da API Onix Payments.
     * @param request objeto contendo os dados da cobrança Pix a ser criada.
     * @return um {@link Mono} emitindo o {@link PaymentResponse} após a conclusão.
     */
    public Mono<PaymentResponse> createdAsync(@NotBlank String publicKey,
                                              @NotBlank String secretKey,
                                              @Valid PaymentRequest request) {
        return createdAsync(buildAuthenticationHeaders(publicKey, secretKey), request);
    }

    /**
     * Cria uma nova cobrança Pix de forma síncrona.
     *
     * @param headers mapa de cabeçalhos HTTP customizados para a requisição.
     * @param request objeto contendo os dados da cobrança Pix a ser criada.
     * @return {@link PaymentResponse} contendo os detalhes da cobrança criada.
     */
    public PaymentResponse create(Map<String, ?> headers,
                                  @Valid PaymentRequest request) {
        return createdAsync(headers, request).block();
    }

    /**
     * Cria uma nova cobrança Pix de forma assíncrona.
     *
     * @param headers mapa de cabeçalhos HTTP customizados para a requisição.
     * @param request objeto contendo os dados da cobrança Pix a ser criada.
     * @return um {@link Mono} emitindo o {@link PaymentResponse} após a conclusão.
     */
    public Mono<PaymentResponse> createdAsync(Map<String, ?> headers,
                                              @Valid PaymentRequest request) {
        return webClient.post()
                .uri(PIX_RECEIVE_ENDPOINT)
                .attribute("headers", headers != null ? headers : Map.of())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class);
    }

    private Map<String, String> buildAuthenticationHeaders(String publicKey,
                                                           String secretKey) {
        final var headers = new HashMap<String, String>();
        headers.put(PUBLIC_KEY_HEADER, publicKey);
        headers.put(SECRET_KEY_HEADER, secretKey);

        return headers;
    }
}
