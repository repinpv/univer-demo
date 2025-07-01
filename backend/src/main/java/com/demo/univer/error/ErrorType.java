package com.demo.univer.error;

import com.demo.univer.grpc.error.v1.ErrorCode;
import io.grpc.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR(
            Status.INTERNAL,
            ErrorCode.ERROR_CODE_INTERNAL_ERROR,
            "Internal error"),
    GROUP_NOT_FOUND(
            Status.NOT_FOUND,
            ErrorCode.ERROR_CODE_GROUP_NOT_FOUND,
            "Group not found"),
    GROUP_NAME_FORMAT_INVALID(
            Status.INVALID_ARGUMENT,
            ErrorCode.ERROR_CODE_GROUP_NAME_FORMAT_INVALID,
            "Group name format invalid"),
    GROUP_NAME_NOT_UNIQUE(
            Status.INVALID_ARGUMENT,
            ErrorCode.ERROR_CODE_GROUP_NAME_NOT_UNIQUE,
            "Group name not unique"),
    STUDENT_NOT_FOUND(
            Status.NOT_FOUND,
            ErrorCode.ERROR_CODE_STUDENT_NOT_FOUND,
            "Student not found"),
    STUDENT_FIO_FORMAT_INVALID(
            Status.INVALID_ARGUMENT,
            ErrorCode.ERROR_CODE_STUDENT_FIO_FORMAT_INVALID,
            "Student fio format invalid"),
    ;

    private final Status status;
    private final ErrorCode errorCode;
    private final String defaultDescription;
}
