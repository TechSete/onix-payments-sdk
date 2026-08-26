package tech.techsete.onix_payments_sdk.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("message")
    private String message;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("details")
    private List<ErrorDetailsResponse> details;
}
