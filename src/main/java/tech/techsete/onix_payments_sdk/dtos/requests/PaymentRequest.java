package tech.techsete.onix_payments_sdk.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest implements Serializable {

    @NotBlank
    @JsonProperty("identifier")
    private String identifier;

    @NotNull
    @Positive
    @JsonProperty("amount")
    private BigDecimal amount;

    @PositiveOrZero
    @JsonProperty("shippingFee")
    private BigDecimal shippingFee;

    @PositiveOrZero
    @JsonProperty("extraFee")
    private BigDecimal extraFee;

    @PositiveOrZero
    @JsonProperty("discount")
    private BigDecimal discount;

    @Valid
    @NotNull
    @JsonProperty("client")
    private PaymentClientRequest client;

    @Valid
    @JsonProperty("products")
    private List<PaymentProductRequest> products;

    @Valid
    @JsonProperty("splits")
    private List<PaymentSplitRequest> splits;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("callbackUrl")
    private String callbackUrl;
}
