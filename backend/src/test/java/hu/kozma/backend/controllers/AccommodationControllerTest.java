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

import static hu.kozma.backend.model.TestData.*;
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

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void newAccommodationShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(post("/api/accommodation/create")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void newAccommodationShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).saveAccommodation(any(), any(), any());

		ObjectMapper obj = new ObjectMapper();
		MockMultipartFile accommodation = new MockMultipartFile("accommodation", "", "application/json", obj.writeValueAsString(minimumAccommodationDTO()).getBytes());
		MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());

		mockMvc.perform(multipart("/api/accommodation/create")
						.file(file)
						.file(accommodation))
				.andExpect(status().isOk());
	}

	@Test
	public void allAccommodationsShouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/accommodation/all")).andExpect(status().isOk());
	}

	@Test
	public void getOwnAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/accommodation/own")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void getOwnAccommodationsShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(get("/api/accommodation/own")).andExpect(status().isOk());
	}

	@Test
	public void getAccommodationDetailsShouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/accommodation/detail").param("id", String.valueOf(id()))).andExpect(status().isOk());
	}

	@Test
	public void updateAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(post("/api/accommodation/update")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void updateAccommodationsShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).saveAccommodation(any(), any(), any());

		ObjectMapper obj = new ObjectMapper();
		MockMultipartFile accommodation = new MockMultipartFile("accommodation", "", "application/json", asJsonString(minimumAccommodationDTO()).getBytes());
		MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());

		mockMvc.perform(multipart("/api/accommodation/update")
						.file(file)
						.file(accommodation))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/accommodation/delete")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void deleteAccommodationsShouldReturnOkWithUser() throws Exception {

		mockMvc.perform(post("/api/accommodation/delete")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(id())))
				.andExpect(status().isOk());
	}

	@Test
	public void reserveAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/accommodation/reserve")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void reserveAccommodationsShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(post("/api/accommodation/reserve")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(reservationDTO())))
				.andExpect(status().isOk());
	}
}
