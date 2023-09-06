package com.gracetech.gestionimmoback.security.jwt;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gracetech.gestionimmoback.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author Mr Bil
 * Ici le username sera remplacé par l'email de l'utilisateur
 */
@Component
public class JwtTokenUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID = "userId";
    final Date createdDate = new Date(System.currentTimeMillis());

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private Long expiration;

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts
        		.parserBuilder()
        		.setSigningKey(getSignInKey())
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
    }

    public String getEmailFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(final String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(final Date created, final Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }

    public String generateToken(final JwtUser userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream().map( e -> e.getAuthority()).collect(Collectors.toList());
        claims.put(AUTHORITIES_KEY, authorities);
        claims.put(USER_ID, userDetails.getId());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(final Map<String, Object> claims, final String subject) {
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder().setClaims(claims).setSubject(subject)
        		.setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Date calculateExpirationDate(final Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    public Boolean canTokenBeRefreshed(final String token, final Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset);
    }

    public String refreshToken(final String token) {
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder().setClaims(claims)
        		.signWith(getSignInKey(), SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(final String email, final String token) {
        final Authentication auth = getAuthentication(token);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return email.equals(principal.getUsername()) && !isTokenExpired(token);
    }

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        List<String> authoritiesList = new ArrayList<>();

        if (claims.get(AUTHORITIES_KEY) instanceof List<?>) {
            authoritiesList = (List<String>) claims.get(AUTHORITIES_KEY);
        }
        Collection<? extends GrantedAuthority> authorities = authoritiesList.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserPrincipal principal = new UserPrincipal();
        principal.setUsername(claims.getSubject());
        if (claims.get(USER_ID) instanceof Integer iD) {
            principal.setId(Long.valueOf(iD));
        }

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    private Key getSignInKey() {
    	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
