package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.SaveReservationDTO;
import hu.kozma.backend.dto.UpdateReservationDTO;
import hu.kozma.backend.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static hu.kozma.backend.TestUtil.asJsonString;
import static hu.kozma.backend.model.TestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReservationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReservationService service;

	@Test
	public void ownReservationShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/reservation/own")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void ownReservationShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(get("/api/reservation/own")).andExpect(status().isOk());
	}

	@Test
	public void reservationDetailShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/reservation/detail")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void reservationDetailShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(get("/api/reservation/detail").param("id", String.valueOf(id()))).andExpect(status().isOk());
	}

	@Test
	public void deleteReservationShouldReturnForbiddenWithoutUser() throws Exception {
		doNothing().when(service).deleteReservation(any(), any());
		mockMvc.perform(post("/api/reservation/delete")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void deleteReservationShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).deleteReservation(any(), any());
		mockMvc.perform(post("/api/reservation/delete")
				.content(asJsonString(id()))).andExpect(status().isOk());
	}

	@Test
	public void reserveReservationShouldReturnForbiddenWithoutUser() throws Exception {
		doNothing().when(service).reserveAccommodation(any(), any());
		mockMvc.perform(post("/api/reservation/reserve")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void reserveReservationShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).reserveAccommodation(any(), any());
		mockMvc.perform(post("/api/reservation/reserve")
				.content(asJsonString(saveReservationDTO()))).andExpect(status().isOk());
	}

	@Test
	public void updateReservationShouldReturnForbiddenWithoutUser() throws Exception {
		doNothing().when(service).updateReservation(any(), any());
		mockMvc.perform(post("/api/reservation/update")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void updateReservationShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).updateReservation(any(), any());
		mockMvc.perform(post("/api/reservation/update")
				.content(asJsonString(updateReservationDTO()))).andExpect(status().isOk());
	}

	@ParameterizedTest
	@CsvSource({
			", 3000000, 3000000, 1, A szállás azonosítója nem lehet üres.",
			"1, , 3000000, 1, A kezdő dátum nem lehet üres.",
			"1, 3000000, , 1, A befejező dátum nem lehet üres.",
			"1, 3000000, 3000000, , A vendégek száma nem lehet üres.",
			"1, 3000000, 3000000, 0, A vendégek száma túl kevés."
	})
	@WithMockUser
	public void validateReservationShouldFail(Long accommodationId, Long startDate, Long endDate, Integer guests, String exception) throws Exception {
		doNothing().when(service).reserveAccommodation(any(), any());
		SaveReservationDTO saveReservationDTO = SaveReservationDTO.builder()
				.accommodationId(accommodationId)
				.startDate(startDate)
				.endDate(endDate)
				.guests(guests)
				.build();
		mockMvc.perform(post("/api/reservation/reserve")
						.content(asJsonString(saveReservationDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}

	@ParameterizedTest
	@CsvSource({
			", 1, A foglalás azonosítója nem lehet üres.",
			"1, , A vendégek száma nem lehet üres.",
			"1, 0, A vendégek száma túl kevés.",
	})
	@WithMockUser
	public void validateUpdateReviewShouldFail(Long id, Integer guests, String exception) throws Exception {
		doNothing().when(service).updateReservation(any(), any());
		UpdateReservationDTO updateReservationDTO = UpdateReservationDTO.builder()
				.id(id)
				.guests(guests)
				.build();
		mockMvc.perform(post("/api/reservation/update")
						.content(asJsonString(updateReservationDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}
}
