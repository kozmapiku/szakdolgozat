package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.SaveReviewDTO;
import hu.kozma.backend.services.ReviewService;
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
import static hu.kozma.backend.model.TestData.saveReviewDTO;
import static hu.kozma.backend.model.TestData.saveReviewDTOBuilder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReviewControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewService service;

	@Test
	public void createReviewShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(post("/api/review/create")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void createReviewShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).addReview(any(), any());
		mockMvc.perform(post("/api/review/create")
						.content(asJsonString(saveReviewDTO())))
				.andExpect(status().isOk());
	}

	@Test
	public void getOwnReviewShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/review/own")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void getOwnShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(get("/api/review/own")).andExpect(status().isOk());
	}

	@ParameterizedTest
	@CsvSource({
			"1, 1, 0, , Az értékelésnek legalább 1-nek kell lennie.",
			"1, 1, 6, , Az értékelés maximum 5 lehet.",
			"1, 1, 3, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Doneca.', A komment túl hosszú.",
			", 1, 3, , A szállás azonosítója kötelező.",
			"1, , 3, , A foglalás azonosítója kötelező.",
	})
	@WithMockUser
	public void validateReviewShouldFail(Long accommodationId, Long reservationId, Integer star, String comment, String exception) throws Exception {
		doNothing().when(service).addReview(any(), any());
		SaveReviewDTO saveReviewDTO = saveReviewDTOBuilder().accommodationId(accommodationId).reservationId(reservationId).star(star).comment(comment).build();
		mockMvc.perform(post("/api/review/create")
						.content(asJsonString(saveReviewDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}
}
