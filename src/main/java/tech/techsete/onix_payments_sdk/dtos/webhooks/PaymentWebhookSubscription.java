package tech.techsete.onix_payments_sdk.dtos.webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.techsete.onix_payments_sdk.enums.PaymentSubscriptionIntervalType;
import tech.techsete.onix_payments_sdk.enums.PaymentSubscriptionStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhookSubscription implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("cycle")
    private Integer cycle;

    @JsonProperty("startAt")
    private OffsetDateTime startAt;

    @JsonProperty("intervalType")
    private PaymentSubscriptionIntervalType intervalType;

    @JsonProperty("intervalCount")
    private Integer intervalCount;

    @JsonProperty("status")
    private PaymentSubscriptionStatus status;
}
