package br.com.murilohenzo.ms.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Slf4j
/*
 * Esta classe implementa a interface Spring {@link Converter} para converter um objeto Jwt em um objeto
 * {@link AbstractAuthenticationToken}, específico do Spring Security.
 */
public class JwtToAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converte um objeto Jwt em um objeto {@link AbstractAuthenticationToken}.
     *
     * @param jwt O objeto Jwt a ser convertido.
     * @return Um {@link AbstractAuthenticationToken} que representa o JWT fornecido.
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<SimpleGrantedAuthority> authorities = jwt.getClaimAsStringList("roles").stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
