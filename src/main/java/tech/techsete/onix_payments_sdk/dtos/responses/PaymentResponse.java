package tech.techsete.onix_payments_sdk.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.techsete.onix_payments_sdk.enums.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse implements Serializable {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("status")
    private PaymentStatus status;

    @JsonProperty("fee")
    private BigDecimal fee;

    @JsonProperty("order")
    private PaymentOrderResponse order;

    @JsonProperty("pix")
    private PaymentPixResponse pix;

    @JsonProperty("details")
    private String details;

    @JsonProperty("errorDescription")
    private String errorDescription;
}
