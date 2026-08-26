package tech.techsete.onix_payments_sdk.dtos.webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.techsete.onix_payments_sdk.enums.PaymentWebhookEvent;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhookPayload implements Serializable {

    @JsonProperty("event")
    private PaymentWebhookEvent event;

    @JsonProperty("token")
    private String token;

    @JsonProperty("offerCode")
    private String offerCode;

    @JsonProperty("checkoutUrl")
    private String checkoutUrl;

    @JsonProperty("client")
    private PaymentWebhookClient client;

    @JsonProperty("transaction")
    private PaymentWebhookTransaction transaction;

    @JsonProperty("subscription")
    private PaymentWebhookSubscription subscription;

    @JsonProperty("orderItems")
    private List<PaymentWebhookOrderItem> orderItems;

    @JsonProperty("trackProps")
    private PaymentWebhookTrackProps trackProps;
}
