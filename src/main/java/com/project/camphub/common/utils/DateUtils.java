package com.project.camphub.common.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static LocalDateTime parseStringToLocalDateTime(String datetime) {

        if (!StringUtils.hasText(datetime)) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (datetime.length() == 10) {
            LocalDate date = LocalDate.parse(datetime, dateFormatter);
            return LocalDateTime.of(date, LocalTime.MIDNIGHT);
        }

        return LocalDateTime.parse(datetime, dateTimeFormatter);
    }
}
