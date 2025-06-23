package com.demo.univer.error;

import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcExceptionHandlerImpl implements GrpcExceptionHandler {
    private final ErrorFactory errorFactory;

    @Override
    public StatusException handleException(Throwable exception) {
        return switch (exception) {
            case StatusException e -> e;
            case StatusRuntimeException e -> errorFactory.toStatusException(e);
            default -> errorFactory.toStatusException(
                    errorFactory.create(ErrorType.INTERNAL_ERROR));
        };
    }
}
