package hu.kozma.backend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AnnounceDateDTO {
    //@NonNull
    private Long from;
    //@NonNull
    private Long end;
    //@NonNull
    private Double price;
}
