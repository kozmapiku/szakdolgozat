package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.RegisterDTO;
import hu.kozma.backend.dto.UpdateUserDTO;
import hu.kozma.backend.services.AuthenticationService;
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
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationService service;

	@Test
	public void getUserDataShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(get("/api/auth/user")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void getUserDataShouldReturnOkWithUser() throws Exception {
		mockMvc.perform(get("/api/auth/user")).andExpect(status().isOk());
	}

	@Test
	public void registerShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).register(any());
		mockMvc.perform(post("/api/auth/register")
						.content(asJsonString(registerDTO())))
				.andExpect(status().isOk());
	}

	@Test
	public void loginShouldReturnOkWithUser() throws Exception {
		doReturn(null).when(service).login(any());
		mockMvc.perform(post("/api/auth/login")
						.content(asJsonString(loginDTO())))
				.andExpect(status().isOk());
	}

	@Test
	public void updateUserShouldReturnForbiddenWithoutUser() throws Exception {
		mockMvc.perform(post("/api/auth/update")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser
	public void updateUserShouldReturnOkWithUser() throws Exception {
		doNothing().when(service).updateUser(any(), any(), any());
		mockMvc.perform(post("/api/auth/update")
						.content(asJsonString(updateUserDTO())))
				.andExpect(status().isOk());
	}

	@ParameterizedTest
	@CsvSource({
			"tesztgmailcom, Elek, Teszt, teszt123, Az e-mail formátuma nem megfelelő.",
			", Elek, Teszt, teszt123, Az e-mail nem lehet üres.",
			"teszt@gmail.com, , Teszt, teszt123, A keresztnév nem lehet üres.",
			"teszt@gmail.com, Elek, , teszt123, A vezetéknév nem lehet üres.",
			"teszt@gmail.com, Elek, Teszt, teszt12, A jelszó hossza nem megfelelő.",
			"teszt@gmail.com, Elek, Teszt, , A jelszó nem lehet üres.",
	})
	@WithMockUser
	public void validateRegisterShouldFail(String email, String firstName, String lastName, String password, String exception) throws Exception {
		doNothing().when(service).register(any());
		RegisterDTO registerDTO = RegisterDTO.builder()
				.email(email)
				.firstName(firstName)
				.lastName(lastName)
				.password(password)
				.build();
		mockMvc.perform(post("/api/auth/register")
						.content(asJsonString(registerDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}

	@ParameterizedTest
	@CsvSource({
			", teszt123, Az e-mail nem lehet üres.",
			"tesztgmail.com, teszt123, Az e-mail formátuma nem megfelelő.",
			"teszt@gmail.com, teszt12, A jelszó nem elég hosszú.",
	})
	@WithMockUser
	public void validateLoginShouldFail(String email, String password, String exception) throws Exception {
		doReturn(null).when(service).login(any());
		LoginDTO loginDTO = LoginDTO.builder()
				.email(email)
				.password(password)
				.build();
		mockMvc.perform(post("/api/auth/login")
						.content(asJsonString(loginDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}

	@ParameterizedTest
	@CsvSource({
			"tesztgmailcom, Elek, Teszt, teszt123, teszt123, Az e-mail formátuma nem megfelelő.",
			", Elek, Teszt, teszt123, teszt123, Az e-mail nem lehet üres.",
			"teszt@gmail.com, , Teszt, teszt123, teszt123, A keresztnév nem lehet üres.",
			"teszt@gmail.com, Elek, , teszt123, teszt123, A vezetéknév nem lehet üres.",
			"teszt@gmail.com, Elek, Teszt, teszt12, teszt123, A jelszó hossza nem megfelelő.",
			"teszt@gmail.com, Elek, Teszt, , teszt123, A jelszó nem lehet üres.",
			"teszt@gmail.com, Elek, Teszt, teszt123, teszt12, Az új jelszú túl rövid.",
	})
	@WithMockUser
	public void validateUpdateUserShouldFail(String email, String firstName, String lastName, String password, String newPassword, String exception) throws Exception {
		doNothing().when(service).register(any());
		UpdateUserDTO updateUserDTO = UpdateUserDTO.builder()
				.email(email)
				.firstName(firstName)
				.lastName(lastName)
				.password(password)
				.newPassword(newPassword)
				.build();
		mockMvc.perform(post("/api/auth/update")
						.content(asJsonString(updateUserDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().encoding("UTF-8"))
				.andExpect(content().string(containsString(exception)));
	}

}