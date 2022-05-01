package hu.kozma.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();
/*
    @Test
    public void homePageLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void resourceEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
*/
    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/accommodation/all", String.class);
        System.out.println("no:" + response.toString());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void loginSucceeds() {
        TestRestTemplate template = new TestRestTemplate("user", "password");
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port
                + "/auth/user", String.class);
        System.out.println("succeed:" + response.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}