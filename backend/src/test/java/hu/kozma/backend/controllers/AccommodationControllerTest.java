package hu.kozma.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.kozma.backend.services.AccommodationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static hu.kozma.backend.model.TestData.file;
import static hu.kozma.backend.model.TestData.minimumAccommodationDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccommodationService service;

    @Test
    public void newAccommodationShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(post("/accommodation/new")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void newAccommodationShouldReturnOkWithUser() throws Exception {
        doNothing().when(service).saveAccommodation(any(), any(), any(), any());

        ObjectMapper obj = new ObjectMapper();
        MockMultipartFile accommodation = new MockMultipartFile("accommodation", "", "application/json", obj.writeValueAsString(minimumAccommodationDTO()).getBytes());
        MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());

        mockMvc.perform(multipart("/accommodation/new")
                        .file(file)
                        .file(accommodation))
                .andExpect(status().isOk());
    }

    @Test
    public void allAccommodationsShouldReturnOk() throws Exception {
        mockMvc.perform(get("/accommodation/all")).andExpect(status().isOk());
    }

    @Test
    public void getOwnAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(get("/accommodation/get_owned")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void getOwnAccommodationsShouldReturnOkWithUser() throws Exception {
        mockMvc.perform(get("/accommodation/get_owned")).andExpect(status().isOk());
    }

    @Test
    public void getAccommodationDetailsShouldReturnOk() throws Exception {
        mockMvc.perform(get("/accommodation/get_details").param("id", any())).andExpect(status().isForbidden());
    }

    @Test
    public void updateAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(get("/accommodation/modify")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void updateAccommodationsShouldReturnOkWithUser() throws Exception {
        mockMvc.perform(get("/accommodation/modify")).andExpect(status().isOk());
    }

    @Test
    public void deleteAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(get("/accommodation/delete")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void deleteAccommodationsShouldReturnOkWithUser() throws Exception {
        mockMvc.perform(get("/accommodation/delete")).andExpect(status().isOk());
    }

    @Test
    public void reserveAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
        mockMvc.perform(get("/accommodation/reserve")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void reserveAccommodationsShouldReturnOkWithUser() throws Exception {
        mockMvc.perform(get("/accommodation/reserve")).andExpect(status().isOk());
    }
}
