package com.encore.logeat.common.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = parseBearerToken(request);
		User user = parseUserSpecification(token);
		UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(
			user, token, user.getAuthorities());
		authenticated.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticated);

		filterChain.doFilter(request, response);
	}

	private String parseBearerToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
			.filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
			.map(token -> token.substring(7))
			.orElse(null);
	}

	private User parseUserSpecification(String token) {
		String[] split = Optional.ofNullable(token)
			.filter(subject -> subject.length() >= 10)
			.map(jwtTokenProvider::validateTokenAndGetSubject)
			.orElse("anonymous:anonymous")
			.split(":");

		return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
	}
}