package hu.kozma.backend.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String error;

    private RestError() {
        timestamp = LocalDateTime.now();
    }

    public RestError(HttpStatus status){
        this();
        this.status = status;
    }

    public RestError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.error = "Unexpected error";
    }

    public RestError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.error = message;
    }
}
