package hu.kozma.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void newAccommodationShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(post("/accommodation/new")).andExpect(status().isForbidden());
    }

    @Test
    public void allAccommodationsShouldReturnOk() throws Exception {
        mockMvc.perform(get("/accommodation/all")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "teszt", password = "teszt")
    public void newAccommodationShouldReturnOkWithUser() throws Exception {
        mockMvc.perform(post("/accommodation/new")).andExpect(status().isForbidden());
    }
}
