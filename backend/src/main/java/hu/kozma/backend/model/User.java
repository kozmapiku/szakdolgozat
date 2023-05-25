package hu.kozma.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static hu.kozma.backend.model.Role.USER;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;
	@Column(name = "token_expired", nullable = false)
	private boolean tokenExpired = true;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = USER;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	public String getFullName() {
		return lastName + " " + firstName;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return tokenExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
