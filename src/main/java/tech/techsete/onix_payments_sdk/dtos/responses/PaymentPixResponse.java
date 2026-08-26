package tech.techsete.onix_payments_sdk.dtos.responses;

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
public class PaymentPixResponse implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("image")
    private String image;

    @JsonProperty("base64")
    private String base64;
}
