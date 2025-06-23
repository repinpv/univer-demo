package com.demo.univer.validator;

import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class StudentFioValidator {
    private static final Pattern FIO_PATTERN = Pattern.compile(
            "^\\p{Lu}\\p{Ll}+ \\p{Lu}\\p{Ll}+( \\p{Lu}\\p{Ll}+)?$");

    private final ErrorFactory errorFactory;

    public void validate(String fio) {
        if (fio.length() < 5) {
            throw errorFactory.create(
                    ErrorType.STUDENT_FIO_FORMAT_INVALID,
                    "FIO is too short");
        }

        if (fio.length() > 500) {
            throw errorFactory.create(
                    ErrorType.STUDENT_FIO_FORMAT_INVALID,
                    "FIO is too long");
        }

        if (!FIO_PATTERN.matcher(fio).matches()) {
            throw errorFactory.create(ErrorType.STUDENT_FIO_FORMAT_INVALID);
        }
    }
}
