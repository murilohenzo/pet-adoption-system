package br.com.murilohenzo.ms.user.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
/*
 * Esta classe implementa a interface Spring {@link Converter} para converter um objeto Jwt em um objeto
 * {@link AbstractAuthenticationToken}, específico do Spring Security.
 */
public class JwtToAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String RESOURCE_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    /**
     * Converte um objeto Jwt em um objeto {@link AbstractAuthenticationToken}.
     *
     * @param jwt O objeto Jwt a ser convertido.
     * @return Um {@link AbstractAuthenticationToken} que representa o JWT fornecido.
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        return new JwtAuthenticationToken(jwt, extractAuthorities(jwt));
    }

    /**
     * Extrai as autoridades (roles) de um objeto Jwt.
     *
     * @param jwt O objeto Jwt do qual extrair as autoridades.
     * @return Uma coleção de {@link SimpleGrantedAuthority} representando as autoridades extraídas.
     */
    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        log.debug("[D51] - EXTRAINDO CLAIMS");
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);
        Collection<String> roles = (Collection<String>) resourceAccess.get(ROLES_CLAIM);
        return roles.stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
