package com.demo.univer.grpc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@GrpcAdvice
public class GprcErrorHandler {

//    @GrpcExceptionHandler(ApiServiceException.class)
//    public Status handleInvalidArgument(ApiServiceException e) {
//        log.debug("ApiServiceException: {}", e.getMessage());
//        return getStatus(e.getStatus()).withDescription(e.getMessage());
//    }
//
//    @GrpcExceptionHandler(InvalidBearerTokenException.class)
//    public Status handleInvalidBearerTokenException(InvalidBearerTokenException e) {
//        log.debug("InvalidBearerTokenException: {}", e.getMessage());
//        return Status.UNAUTHENTICATED.withDescription(e.getMessage());
//    }
//
//    @GrpcExceptionHandler(IllegalArgumentException.class)
//    public Status handleIllegalArgumentException(IllegalArgumentException e) {
//        log.debug("IllegalArgumentException: {}", e.getMessage());
//        return Status.INVALID_ARGUMENT.withDescription(e.getMessage());
//    }
//
//    private Status getStatus(HttpStatus status) {
//        return switch (status) {
//            case NOT_FOUND -> Status.NOT_FOUND;
//            case BAD_REQUEST -> Status.INVALID_ARGUMENT;
//            case CONFLICT -> Status.ALREADY_EXISTS;
//            case FORBIDDEN -> Status.PERMISSION_DENIED;
//            case UNAUTHORIZED -> Status.UNAUTHENTICATED;
//            case SERVICE_UNAVAILABLE -> Status.UNAVAILABLE;
//            default -> Status.INTERNAL;
//        };
//    }
}
