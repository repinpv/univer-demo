package com.demo.univer.utils;

import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Component
public class TimeUtils {
    public LocalDate toLocalDate(Timestamp timestamp) {
        return LocalDate.ofInstant(
                Instant.ofEpochSecond(
                        timestamp.getSeconds()),
                ZoneOffset.UTC);
    }

    public Timestamp toTimestamp(LocalDate localDate) {
        return Timestamp.newBuilder()
                .setSeconds(
                        localDate
                                .toEpochSecond(
                                        LocalTime.MIN,
                                        ZoneOffset.UTC))
                .build();
    }
}
