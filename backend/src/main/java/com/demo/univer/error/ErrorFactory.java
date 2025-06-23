package com.demo.univer.error;

import com.demo.univer.gprc.error.ErrorCode;
import com.demo.univer.gprc.error.ErrorResponse;
import com.demo.univer.utils.TimeUtils;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ErrorFactory {
    public static final Metadata.Key<ErrorResponse> ERROR_RESPONSE_KEY = ProtoUtils.keyForProto(
            ErrorResponse.getDefaultInstance());

    private final TimeUtils timeUtils;

    public StatusRuntimeException create(ErrorType errorType) {
        return create(errorType, errorType.getDefaultDescription());
    }

    public StatusRuntimeException create(ErrorType errorType, String defaultDescription) {
        return create(errorType, errorType.getDefaultDescription(), null);
    }

    public StatusRuntimeException create(ErrorType errorType, Exception cause) {
        return create(errorType, errorType.getDefaultDescription(), cause);
    }

    public StatusRuntimeException create(ErrorType errorType, String description, Exception cause) {
        Metadata metadata = new Metadata();

        ErrorResponse errorResponse = ErrorResponse.newBuilder()
                .setErrorCode(errorType.getErrorCode())
                .setErrorDescription(description)
                .setTime(timeUtils.currentTimestamp())
                .build();

        metadata.put(ERROR_RESPONSE_KEY, errorResponse);

        Status status = errorType.getStatus().withDescription(description);
        if (cause != null) {
            status = status.withCause(cause);
        }
        return status.asRuntimeException(metadata);
    }

    public ErrorCode getErrorCode(StatusRuntimeException statusRuntimeException) {
        return Optional.ofNullable(statusRuntimeException)
                .map(StatusRuntimeException::getTrailers)
                .map(metadata -> metadata.get(ERROR_RESPONSE_KEY))
                .map(ErrorResponse::getErrorCode)
                .orElse(null);
    }

    public StatusException toStatusException(StatusRuntimeException e) {
        return new StatusException(e.getStatus(), e.getTrailers());
    }
}
