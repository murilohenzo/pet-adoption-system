package com.murilohenzo.petapi.config.security;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

  private final SecurityProperties properties;

  @NotNull
  @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:#{null}}")
  private String jwkSetUri;

  /**
   * Configura uma cadeia de filtros de segurança para ambientes de teste onde a segurança JWT está desativada.
   *
   * @param httpSecurity o HttpSecurity a ser configurado
   * @return uma SecurityFilterChain configurada que permite todas as requisições
   * @throws Exception se ocorrer um erro ao configurar o filtro de segurança
   */
  @Bean
  @ConditionalOnProperty(name = "security.auth.disable", havingValue = "true", matchIfMissing = false)
  public SecurityFilterChain testSecurityFilter(HttpSecurity httpSecurity) throws Exception {
    log.info("[I56] - SEGURANCA JWT DESATIVADA, NAO USAR ESSA CONFIGURACAO EM AMBIENTE PRODUTIVO");

    return httpSecurity
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
      .csrf(AbstractHttpConfigurer::disable)
      .build();
  }

  /**
   * Configura a cadeia de filtros de segurança principal com segurança JWT ativada.
   *
   * @param httpSecurity o HttpSecurity a ser configurado
   * @param converter o conversor para transformar JWTs em tokens de autenticação
   * @param supplierObjectProvider fornece um fornecedor para endpoints públicos
   * @return uma SecurityFilterChain configurada que protege endpoints com base em JWT
   * @throws Exception se ocorrer um erro ao configurar o filtro de segurança
   */
  @Bean
  @ConditionalOnProperty(name = "security.auth.disable", havingValue = "false", matchIfMissing = true)
  public SecurityFilterChain securityFilter(HttpSecurity httpSecurity, JwtToAuthenticationTokenConverter converter, ObjectProvider<PublicEndpoints> supplierObjectProvider) throws Exception {
    log.info("[I76] - SEGURANCA JWT ATIVADO");

    return httpSecurity
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(publicEndpoints(supplierObjectProvider))
        .permitAll()
        .anyRequest()
        .authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
      .build();
  }

  /**
   * Configura um bean JwtDecoder usando a URI do conjunto de chaves JWK.
   *
   * @return um JwtDecoder configurado
   */
  @Bean
  JwtDecoder jwtDecoder() {
    log.info("[I98] - ATIVANDO INSPENCAO DE TOKEN");
    return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
  }

  /**
   * Configura um bean JwtToAuthenticationTokenConverter.
   *
   * @return um JwtToAuthenticationTokenConverter configurado
   */
  @Bean
  JwtToAuthenticationTokenConverter jwtToAuthenticationTokenConverter() {
    return new JwtToAuthenticationTokenConverter(properties);
  }

  /**
   * Fornece um fornecedor para endpoints públicos com base nas propriedades configuradas.
   *
   * @return um fornecedor para endpoints públicos
   */
  @Bean
  PublicEndpoints publicRequestMatcherSupplier() {
    log.info("[I129] - ENDPOINTS PUBLICOS - {}", properties.getPublicEndpoints());
    var eps = properties.getPublicEndpoints();
    if (eps.isEmpty()) {
      log.info("[I122] - ENDPOINTS PUBLICOS NAO MAPEADOS");
      return Collections::emptyList;
    }

    return () -> properties.getPublicEndpoints().stream()
      .map(endpoint -> (RequestMatcher) new AntPathRequestMatcher(endpoint))
      .toList();
  }

  /**
   * Converte o fornecedor de endpoints públicos em um array de RequestMatchers.
   *
   * @param eps o fornecedor de endpoints públicos
   * @return um array de RequestMatchers para endpoints públicos
   */
  private RequestMatcher[] publicEndpoints(ObjectProvider<PublicEndpoints> eps) {
    return eps.stream()
      .map(Supplier::get)
      .flatMap(List::stream)
      .distinct()
      .toArray(RequestMatcher[]::new);
  }

  /**
   * Fornece o usuario do contexto de autenticação atual.
   *
   * @return o usuario do contexto atual
   */
  @Bean
  @RequestScope
  Supplier<Authentication> authenticationSupplier() {
    return () -> {
      var auth = SecurityContextHolder.getContext().getAuthentication();
      log.info("[I155] AuthenticationSupplier - auth={}", auth);
      return auth;
    };
  }

  @Bean
  public AuditorAware<String> auditorProvider() {
    var supplier = authenticationSupplier();
    return () -> {
      var auth = supplier.get();
      if (auth == null) {
        return Optional.of("UNKNOWN");
      } else {
        return Optional.of(auth.getName());
      }
    };
  }

  /**
   * Interface para fornecer uma lista de correspondentes de endpoint públicos.
   */
  interface PublicEndpoints extends Supplier<List<RequestMatcher>> {}
}
