package com.demo.univer.validator;

import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class GroupNameValidator {
    private static final Pattern GROUP_NAME_PATTERN = Pattern.compile(
            "^\\p{Lu}(\\p{LD}+)?([-_]\\p{LD}+)?$");

    private final ErrorFactory errorFactory;

    public void validate(String name) {
        if (name.length() < 2) {
            throw errorFactory.create(
                    ErrorType.GROUP_NAME_FORMAT_INVALID,
                    "Group name is too short");
        }

        if (name.length() > 50) {
            throw errorFactory.create(
                    ErrorType.GROUP_NAME_FORMAT_INVALID,
                    "Group name is too long");
        }

        if (!GROUP_NAME_PATTERN.matcher(name).matches()) {
            throw errorFactory.create(
                    ErrorType.GROUP_NAME_FORMAT_INVALID);
        }
    }
}
