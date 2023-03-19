package hu.kozma.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnnounceDateDTO {
    @NonNull
    private Long from;
    @NonNull
    private Long end;
    //@NonNull
    private Double price;
}
