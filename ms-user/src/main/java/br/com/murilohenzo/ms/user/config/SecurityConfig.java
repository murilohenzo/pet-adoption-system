package br.com.murilohenzo.ms.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "security.auth.disable", havingValue = "true", matchIfMissing = false)
    public SecurityFilterChain testSecurityFilter(HttpSecurity httpSecurity) throws Exception {
        log.info("[I25] - SEGURANCA JWT DESATIVADA, NAO USAR ESSA CONFIGURACAO EM AMBIENTE PRODUTIVO");

        return httpSecurity
                .authorizeHttpRequests(auth ->
                        auth.anyRequest()
                                .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "security.auth.disable", havingValue = "false", matchIfMissing = true)
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity, JwtToAuthenticationTokenConverter converter) throws Exception {
        log.info("[I38 - SEGURANCA JWT ATIVADO");

        return httpSecurity
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("*")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
                .build();
    }

    @Bean
    JwtToAuthenticationTokenConverter jwtToAuthenticationTokenConverter() {
        return new JwtToAuthenticationTokenConverter();
    }
}
