package hu.kozma.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
    @NotNull
    private Long accommodationId;
    @NotNull
    private Long reservationId;
    @NotNull
    private Integer star;
    @Nullable
    private String comment;
    @Nullable
    @JsonProperty("user_name")
    private String userName;
}
