package tech.techsete.onix_payments_sdk.dtos.webhooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhookTrackProps implements Serializable {

    @JsonProperty("utm_id")
    private String utmId;

    @JsonProperty("utm_source")
    private String utmSource;

    @JsonProperty("utm_medium")
    private String utmMedium;

    @JsonProperty("utm_campaign")
    private String utmCampaign;

    @JsonProperty("utm_content")
    private String utmContent;

    @JsonProperty("utm_term")
    private String utmTerm;

    @JsonProperty("fbc")
    private String fbc;

    @JsonProperty("fbp")
    private String fbp;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("country")
    private String country;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;
}
