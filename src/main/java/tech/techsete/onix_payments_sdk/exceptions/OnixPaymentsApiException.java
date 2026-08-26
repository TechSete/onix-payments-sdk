package tech.techsete.onix_payments_sdk.exceptions;

import lombok.Getter;
import tech.techsete.onix_payments_sdk.dtos.responses.ErrorResponse;

@Getter
public class OnixPaymentsApiException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public OnixPaymentsApiException(ErrorResponse errorResponse) {
        super(resolveMessage(errorResponse));
        this.errorResponse = errorResponse;
    }

    private static String resolveMessage(ErrorResponse errorResponse) {
        if (errorResponse == null || errorResponse.getMessage() == null) {
            return "Erro ao chamar API Onix Payments";
        }
        return errorResponse.getMessage();
    }
}
