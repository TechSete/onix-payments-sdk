package tech.techsete.onix_payments_sdk.dtos.webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.techsete.onix_payments_sdk.enums.PaymentMethod;
import tech.techsete.onix_payments_sdk.enums.PaymentWebhookTransactionStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhookTransaction implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("status")
    private PaymentWebhookTransactionStatus status;

    @JsonProperty("paymentMethod")
    private PaymentMethod paymentMethod;

    @JsonProperty("originalAmount")
    private BigDecimal originalAmount;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("commissionAmount")
    private BigDecimal commissionAmount;

    @JsonProperty("originalCurrency")
    private String originalCurrency;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;

    @JsonProperty("installments")
    private Integer installments;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonProperty("payedAt")
    private OffsetDateTime payedAt;

    @JsonProperty("pixInformation")
    private PaymentWebhookPixInformation pixInformation;

    @JsonProperty("boletoInformation")
    private PaymentWebhookBoletoInformation boletoInformation;
}
