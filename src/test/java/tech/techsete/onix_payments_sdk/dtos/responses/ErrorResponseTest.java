package tech.techsete.onix_payments_sdk.dtos.responses;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserializeDetailsAsArray() throws Exception {
        var json = """
                {
                  "statusCode": 400,
                  "errorCode": "VALIDATION_ERROR",
                  "message": "Erro de validação",
                  "details": [
                    {
                      "field": "amount",
                      "value": null,
                      "issue": "Campo obrigatório"
                    }
                  ]
                }
                """;

        var response = objectMapper.readValue(json, ErrorResponse.class);

        assertThat(response.getDetails()).hasSize(1);
        assertThat(response.getDetails().get(0).getField()).isEqualTo("amount");
    }

    @Test
    void shouldDeserializeSingleDetailAsArray() throws Exception {
        var json = """
                {
                  "statusCode": 400,
                  "errorCode": "VALIDATION_ERROR",
                  "message": "Erro de validação",
                  "details": {
                    "field": "amount",
                    "value": null,
                    "issue": "Campo obrigatório"
                  }
                }
                """;

        var response = objectMapper.readValue(json, ErrorResponse.class);

        assertThat(response.getDetails()).hasSize(1);
        assertThat(response.getDetails().get(0).getIssue()).isEqualTo("Campo obrigatório");
    }
}
