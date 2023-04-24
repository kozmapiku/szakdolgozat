package hu.kozma.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimpleIdDTO {
    @NonNull
    private Long id;
}
