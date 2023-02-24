package hu.kozma.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class AnnounceDateDto {
    @NonNull
    private long fromDate;
    @NonNull
    private long endDate;
    @NonNull
    private Double price;
}
