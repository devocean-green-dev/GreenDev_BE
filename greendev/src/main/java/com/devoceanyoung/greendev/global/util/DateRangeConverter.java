package com.devoceanyoung.greendev.global.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRangeConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public static String toString(LocalDate startDateTime, LocalDate endDateTime) {
        return String.format("%s ~ %s",
                startDateTime.format(formatter),
                endDateTime.format(formatter));
    }

    public static LocalDate[] toLocalDateArray(String str) {
        String[] parts = str.split(" ~ ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid date range string.");
        }
        LocalDate startDate = LocalDate.parse(parts[0], formatter);
        LocalDate endDate = LocalDate.parse(parts[1], formatter);

        return new LocalDate[] { startDate, endDate };
    }
}
