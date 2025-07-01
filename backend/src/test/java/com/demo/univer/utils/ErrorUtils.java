package com.demo.univer.utils;

import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorUtils {
    private final ErrorFactory errorFactory;

    public void assertError(ErrorType errorType, Executable executable) {
        StatusRuntimeException statusRuntimeException = Assertions.assertThrows
                (StatusRuntimeException.class, executable);

        Assertions.assertEquals(errorType.getStatus().getCode(), statusRuntimeException.getStatus().getCode());
        Assertions.assertEquals(errorType.getErrorCode(), errorFactory.getErrorCode(statusRuntimeException));
    }
}
