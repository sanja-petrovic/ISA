package com.example.isa.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class TokenBasedAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	@Setter
	private String accessToken;
	@Setter
	private String refreshToken;
	private final UserDetails principal;

	public TokenBasedAuthentication(UserDetails principal) {
		super(principal.getAuthorities());
		this.principal = principal;
	}

	@Override
	public boolean isAuthenticated() {
		return accessToken != null;
	}

	@Override
	public Object getCredentials() {
		return accessToken;
	}

	@Override
	public UserDetails getPrincipal() {
		return principal;
	}

}
