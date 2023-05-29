package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.SaveAccommodationDTO;
import hu.kozma.backend.services.AccommodationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static hu.kozma.backend.TestUtil.asJsonString;
import static hu.kozma.backend.model.TestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
		mockMvc.perform(post("/api/accommodation/create")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void newAccommodationShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).saveAccommodation(any(), any(), any());

		MockMultipartFile accommodation =
				new MockMultipartFile("accommodation", "", "application/json",
						asJsonString(minimumSaveAccommodationDTO()).getBytes());
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
		doNothing().when(service).modifyAccommodation(any(), any(), any());

		MockMultipartFile accommodation =
				new MockMultipartFile("accommodation", "", "application/json",
						asJsonString(minimumUpdateAccommodationDTO()).getBytes());
		MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());

		mockMvc.perform(multipart("/api/accommodation/update")
						.file(file)
						.file(accommodation))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteAccommodationsShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(post("/api/accommodation/delete")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void deleteAccommodationsShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).deleteAccommodation(any(), any());
		mockMvc.perform(post("/api/accommodation/delete")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(id())))
				.andExpect(status().isOk());
	}

	@ParameterizedTest
	@CsvSource({
			"Tesz, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A név hossza nem megfelelő.'",
			"'Lorem ipsum dolor sit amet, com', cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A név hossza nem megfelelő.'",
			", cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A név nem lehet üres.'",
			"Teszt név, , 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A cím nem lehet üres.'",
			"Teszt név, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma', 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A cím hossza nem megfelelő.'",
			"Teszt név, cím, -11, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'Az emelet száma túl kicsi.'",
			"Teszt név, cím, 201, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'Az emelet száma túl nagy.'",
			"Teszt név, cím, 10, -1, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'Az ajtó száma túl kicsi.'",
			"Teszt név, cím, 10, 201, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'Az ajtó száma túl nagy.'",
			"Teszt név, cím, 10, 10, , 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A helyszín nem lehet üres.'",
			"Teszt név, cím, 10, 10, 0.0, , 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 5, 'A helyszín nem lehet üres.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, , 5, 'A leírás nem lehet üres.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean', 5, 'A leírás hossza nem megfelelő.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. S', 5, 'A leírás hossza nem megfelelő.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', , 'A maximális befogadó képesség nem lehet üres.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 0, 'A maximális befogadó képesség túl kevés.'",
			"Teszt név, cím, 10, 10, 0.0, 0.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 101, 'A maximális befogadó képesség túl sok.'"
	})
	@WithMockUser
	public void validateSaveAccommodationShouldFail(String name, String address, Integer floor, Integer door, Float lat, Float lng, String description, Integer maxGuests, String exception) throws Exception {
		doNothing().when(service).saveAccommodation(any(), any(), any());

		SaveAccommodationDTO saveAccommodationDTO = SaveAccommodationDTO.builder()
				.name(name)
				.address(address)
				.floor(floor)
				.door(door)
				.lat(lat)
				.lng(lng)
				.description(description)
				.maxGuests(maxGuests)
				.announces(List.of(minimumAnnounceDateDTO()))
				.build();

		MockMultipartFile accommodation =
				new MockMultipartFile("accommodation", "", "application/json;charset=UTF-8",
						asJsonString(saveAccommodationDTO).getBytes());
		MockMultipartFile file = new MockMultipartFile("files", "", MediaType.TEXT_PLAIN_VALUE, file());

		mockMvc.perform(multipart("/api/accommodation/create")
						.file(file)
						.file(accommodation)
						.contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}

}
