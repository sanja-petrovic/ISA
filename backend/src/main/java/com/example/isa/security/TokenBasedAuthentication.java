package com.example.isa.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private String token;
	private final UserDetails principal;

	public TokenBasedAuthentication(UserDetails principal) {
		super(principal.getAuthorities());
		this.principal = principal;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public UserDetails getPrincipal() {
		return principal;
	}

}
