package br.com.murilohenzo.gateway.config;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;


public class JwtToAuthenticationTokenConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {


    private final static Logger log = LoggerFactory.getLogger(JwtToAuthenticationTokenConverter.class);

    private static final String RESOURCE_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.just(new JwtAuthenticationToken(jwt, extractAuthorities(jwt)));
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        log.debug("[D51] - EXTRAINDO CLAIMS");
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);
        Collection<String> roles = (Collection<String>) resourceAccess.get(ROLES_CLAIM);
        return roles.stream().filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}