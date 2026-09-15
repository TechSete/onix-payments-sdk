package tech.techsete.onix_payments_sdk.dtos.requests;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldOmitNullOptionalFields() throws Exception {
        final var request = PaymentRequest.builder()
                .identifier("pedido-123")
                .amount(new BigDecimal("5.00"))
                .client(PaymentClientRequest.builder()
                        .name("Cliente")
                        .email("cliente@email.com")
                        .phone("99999999999")
                        .document("51943215200")
                        .build())
                .callbackUrl("https://api.com/webhook")
                .build();

        final var json = objectMapper.writeValueAsString(request);

        assertThat(json).doesNotContain("shippingFee");
        assertThat(json).doesNotContain("extraFee");
        assertThat(json).doesNotContain("discount");
    }
}
