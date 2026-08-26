package tech.techsete.onix_payments_sdk.configurations;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Utilitário de configuração para filtros do {@link WebClient}.
 * <p>
 * Esta classe contém interceptores (filtros) que manipulam as requisições de saída,
 * permitindo a injeção dinâmica de comportamentos, como a propagação de cabeçalhos HTTP.
 * </p>
 *
 * @author Edson Isaac
 * @since 1.0.0
 */
public class WebClientFilter {

    /**
     * Filtro que extrai um mapa de cabeçalhos dos atributos da requisição e os injeta no cabeçalho HTTP real.
     * <p>
     * O filtro busca por um atributo chamado {@code "headers"}. Caso encontrado, cada entrada do mapa
     * é convertida para String e adicionada aos cabeçalhos da requisição que será enviada ao servidor.
     * </p>
     *
     * @return Uma instância de {@link ExchangeFilterFunction} para ser registrada no {@link WebClient.Builder}.
     * @see ClientRequest#attributes()
     */
    @SuppressWarnings("unchecked")
    public static ExchangeFilterFunction addHeadersFromAttribute() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            final var headers = clientRequest.attribute("headers")
                    .map(attr -> (Map<String, Object>) attr)
                    .orElse(Map.of());

            return Mono.just(ClientRequest.from(clientRequest)
                    .headers(httpHeaders -> headers.forEach((k, v) -> httpHeaders.add(k, v.toString())))
                    .build());
        });
    }
}