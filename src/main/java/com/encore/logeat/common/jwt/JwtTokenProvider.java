package com.encore.logeat.common.jwt;

import com.encore.logeat.common.jwt.refresh.UserRefreshToken;
import com.encore.logeat.common.jwt.refresh.UserRefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:jwt.yml")
@Service
public class JwtTokenProvider {

	private final String secretKey;
	private final long expirationMinutes;
	private final long refreshExpirationHours;
	private final String issuer;
	private final UserRefreshTokenRepository userRefreshTokenRepository;
	private final long reissueLimit;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public JwtTokenProvider(@Value("${secret-key}") String secretKey,
		@Value("${expiration-minutes}") long expirationMinutes,
		@Value("${refresh-expiration-hours}") long refreshExpirationHours,
		@Value("${issuer}") String issuer, UserRefreshTokenRepository userRefreshTokenRepository) {
		this.secretKey = secretKey;
		this.expirationMinutes = expirationMinutes;
		this.refreshExpirationHours = refreshExpirationHours;
		this.issuer = issuer;
		this.userRefreshTokenRepository = userRefreshTokenRepository;
		reissueLimit = refreshExpirationHours * 60 / expirationMinutes;
	}

	public String createAccessToken(String userSpecification) {

		return Jwts.builder()
			.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
			.setSubject(userSpecification)
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))
			.compact();
	}

	public String createRefreshToken() {

		return Jwts.builder()
			.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
			.compact();
	}

	@Transactional
	public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
		String subject = decodeJwtPayloadSubject(oldAccessToken);
		userRefreshTokenRepository.findByUserIdAndReissueCountLessThan(
			Long.parseLong(subject.split(":")[0]), reissueLimit)
			.ifPresentOrElse(
				UserRefreshToken::increaseReissueCount,
				() -> {
					throw new ExpiredJwtException(null, null, "Refresh token expried.");
				}
			);
		return createAccessToken(subject);
	}

	@Transactional
	public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
		validateAndParseToken(refreshToken);
		String memberId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
		userRefreshTokenRepository.findByUserIdAndReissueCountLessThan(Long.parseLong(memberId), reissueLimit)
			.filter(memberRefreshToken -> memberRefreshToken.validateRefreshToken(refreshToken))
			.orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
	}


	private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
		return objectMapper.readValue(
			new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]),
				StandardCharsets.UTF_8),
			Map.class
		).get("sub").toString();
	}

	private Jws<Claims> validateAndParseToken(String token) {	// validateTokenAndGetSubject에서 따로 분리
		return Jwts.parser()
			.setSigningKey(secretKey.getBytes())
			.parseClaimsJws(token);
	}

	public String validateTokenAndGetSubject(String token) {
		return validateAndParseToken(token).getBody().getSubject();
	}
}