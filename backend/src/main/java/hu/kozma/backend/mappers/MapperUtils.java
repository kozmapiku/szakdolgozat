package hu.kozma.backend.mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class MapperUtils {
    public static LocalDate toDate(Long date) {
        return date != null ? Instant.ofEpochMilli(date)
                .atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public static Long toLongDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }
}
