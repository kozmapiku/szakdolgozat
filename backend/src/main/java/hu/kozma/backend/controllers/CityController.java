package hu.kozma.backend.controllers;

import hu.kozma.backend.model.City;
import hu.kozma.backend.rest.RestResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/city")
@RestController
@CrossOrigin(origins = "*")
public class CityController {

    @GetMapping("/all")
    public ResponseEntity<?> getALl() {
        return RestResponseHandler.generateResponse(Arrays.stream(City.values()).map(City::toString));
    }
}
