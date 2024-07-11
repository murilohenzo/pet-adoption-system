package br.com.murilohenzo.gateway.config;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import jakarta.validation.constraints.NotNull;

@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @NotNull
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:#{null}}")
    private String jwkSetUri;

    private final SecurityProperties securityProperties;

    public SecurityConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public SecurityWebFilterChain securityFilter(ServerHttpSecurity httpSecurity,
                                                 ObjectProvider<PublicEndpoints> publicEndpoints,
                                                 ObjectProvider<NoCsrfEndpoints> noCsrfEndpoints,
                                                 JwtToAuthenticationTokenConverter converter) {
        log.info("[I52] - SEGURANCA JWT ATIVADO");

        return httpSecurity
                .authorizeExchange(auth -> auth
                        .matchers(publicEndpoints(publicEndpoints))
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .csrf(csrf -> csrf
                        .requireCsrfProtectionMatcher(csrfProtectionMatcher(noCsrfEndpoints))
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                )
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        log.info("[I83] - ATIVANDO INSPECAO DE TOKEN");
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    JwtToAuthenticationTokenConverter jwtToAuthenticationTokenConverter() {
        return new JwtToAuthenticationTokenConverter();
    }

    private AndServerWebExchangeMatcher csrfProtectionMatcher(ObjectProvider<NoCsrfEndpoints> csrfEndpointsObjectProvider) {
        return new AndServerWebExchangeMatcher(
                CsrfWebFilter.DEFAULT_CSRF_MATCHER,
                new NegatedServerWebExchangeMatcher(
                        ServerWebExchangeMatchers.matchers(noCsrfEndpoints(csrfEndpointsObjectProvider))
                )
        );
    }

    @Bean
    PublicEndpoints publicRequestMatcherSupplier() {

        if (this.securityProperties.getPublicEndpoints().isEmpty()) {
            return Collections::emptyList;
        }

        return () -> this.securityProperties.getPublicEndpoints().stream()
                .map(ServerWebExchangeMatchers::pathMatchers)
                .toList();
    }

    @Bean
    NoCsrfEndpoints noCsrfRequestMatcherSupplier() {

        if (this.securityProperties.getNoCsrfEndpoints().isEmpty()) {
            return Collections::emptyList;
        }

        return () -> this.securityProperties.getNoCsrfEndpoints().stream()
                .map(ServerWebExchangeMatchers::pathMatchers)
                .toList();
    }

    private ServerWebExchangeMatcher[] publicEndpoints(ObjectProvider<PublicEndpoints> eps) {
        return eps.stream()
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .distinct()
                .toArray(ServerWebExchangeMatcher[]::new);
    }

    private ServerWebExchangeMatcher[] noCsrfEndpoints(ObjectProvider<NoCsrfEndpoints> csrfEndpoints) {
        return csrfEndpoints.stream()
                .map(Supplier::get)
                .flatMap(List::stream)
                .distinct()
                .toArray(ServerWebExchangeMatcher[]::new);
    }

    private interface PublicEndpoints extends Supplier<List<ServerWebExchangeMatcher>> {}

    private interface NoCsrfEndpoints extends Supplier<List<ServerWebExchangeMatcher>> {}
}

