package hu.kozma.backend.model;

import hu.kozma.backend.dto.*;

import java.util.List;

public class TestData {

	public static String accommodationName() {
		return "Accommodation name";
	}

	public static Integer maxGuests() {
		return 1;
	}

	public static String address() {
		return "address";
	}

	public static Float lat() {
		return 1.0f;
	}

	public static Float lng() {
		return 2.0f;
	}

	public static Long startDate() {
		return 3L;
	}

	public static Long endDate() {
		return 4L;
	}

	public static String description() {
		return "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m";
	}

	public static Long id() {
		return 0L;
	}

	public static String email() {
		return "teszt@gmail.com";
	}

	public static String password() {
		return "12345678";
	}

	public static String password2() {
		return "123456789";
	}

	public static String lastName() {
		return "Teszt";
	}

	public static String firstName() {
		return "Elek";
	}

	public static String comment() {
		return "comment";
	}

	public static Integer star() {
		return 3;
	}

	public static Double price() {
		return 100.0;
	}

	public static byte[] file() {
		return "file".getBytes();
	}

	public static SaveAccommodationDTO minimumSaveAccommodationDTO() {
		return SaveAccommodationDTO.builder()
				.name(accommodationName())
				.address(address())
				.lat(lat())
				.lng(lng())
				.description(description())
				.maxGuests(maxGuests())
				.announces(List.of(minimumAnnounceDateDTO()))
				.build();
	}

	public static UpdateAccommodationDTO minimumUpdateAccommodationDTO() {
		return UpdateAccommodationDTO.builder()
				.id(id())
				.name(accommodationName())
				.address(address())
				.lat(lat())
				.lng(lng())
				.description(description())
				.maxGuests(maxGuests())
				.announces(List.of(minimumAnnounceDateDTO()))
				.build();
	}

	public static AnnounceDateDTO minimumAnnounceDateDTO() {
		return AnnounceDateDTO.builder()
				.startDate(startDate())
				.endDate(endDate())
				.price(price())
				.build();
	}

	public static RegisterDTO registerDTO() {
		return RegisterDTO.builder()
				.email(email())
				.firstName(firstName())
				.lastName(lastName())
				.password(password())
				.build();
	}

	public static LoginDTO loginDTO() {
		return LoginDTO.builder()
				.email(email())
				.password(password())
				.build();
	}

	public static UpdateUserDTO updateUserDTO() {
		return UpdateUserDTO.builder()
				.email(email())
				.firstName(firstName())
				.lastName(lastName())
				.password(password())
				.newPassword(password2())
				.build();
	}

	public static SaveReviewDTO saveReviewDTO() {
		return saveReviewDTOBuilder()
				.build();
	}

	public static SaveReviewDTO.SaveReviewDTOBuilder saveReviewDTOBuilder() {
		return SaveReviewDTO.builder()
				.accommodationId(id())
				.reservationId(id())
				.comment(comment())
				.star(star());
	}

	public static SaveReservationDTO saveReservationDTO() {
		return SaveReservationDTO.builder()
				.accommodationId(id())
				.startDate(startDate())
				.endDate(endDate())
				.guests(maxGuests())
				.build();
	}

	public static UpdateReservationDTO updateReservationDTO() {
		return UpdateReservationDTO.builder()
				.id(id())
				.guests(maxGuests())
				.build();
	}

	/*

	public static ReservationDTO reservationDTO() {
		return ReservationDTO.builder()
				.from(from())
				.end(end())
				.guests(maxGuests())
				.id(id())
				.build();
	}

	public static SimpleIdDTO simpleIdDTO() {
		return SimpleIdDTO.builder()
				.id(id())
				.build();
	}
 */
	public static Accommodation minimumAccommodation() {
		return Accommodation.builder()
				.name(accommodationName())
				.address(address())
				.lat(lat())
				.lng(lng())
				.description(description())
				.maxGuests(maxGuests())
				.build();
	}

	public static User user() {
		return User.builder()
				.email(email())
				.password(password())
				.firstName(firstName())
				.lastName(lastName())
				.build();
	}
}
