package com.demo.univer.validator;

import org.springframework.stereotype.Component;

@Component
public class FioValidator {
    public void validate(String fio) {
        if (fio.length() > 500) {

        }
    }
}
