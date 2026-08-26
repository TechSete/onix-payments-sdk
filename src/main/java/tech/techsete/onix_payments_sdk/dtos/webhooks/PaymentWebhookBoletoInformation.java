package tech.techsete.onix_payments_sdk.dtos.webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhookBoletoInformation implements Serializable {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("digitableLine")
    private String digitableLine;

    @JsonProperty("pdfUrl")
    private String pdfUrl;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}
